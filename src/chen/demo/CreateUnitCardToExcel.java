package chen.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import chen.db.DB;
import chen.db.DB.DBType;
import chen.file.CSVWriter;
import chen.file.FileUtil;
import chen.judge.Judge;
import chen.print.Print;

/**
 * 临夏数据库卡片缺失问题<br/>
 * 将27号库中各家单位缺失的卡片导出到Excel中，每家单位一个Excel，模板按照“其他”
 * @author zhangyuchen
 *
 */
public class CreateUnitCardToExcel {
	
	Connection conn;
	Statement tempStat;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public CreateUnitCardToExcel() {
		conn = DB.conn(DBType.ORACLE, DB.oracleUrl("orcl"), "linxia27", "linxia27");
		if (conn == null) {
			throw new RuntimeException("no connection to db.");
		}
	}
	
	public static void main(String[] args) throws Exception {
		CreateUnitCardToExcel ins = new CreateUnitCardToExcel();
		
		List<UnitInfo> units = ins.getUnitList();
		ins.printUnitInfo(units);
		
		ins.tempStat = ins.conn.createStatement();
		
		for (UnitInfo unit : units) {
//			if (!"甘肃广河县经济开发区办公室".equals(unit.name)) {
//				continue;
//			}
			System.out.println("============== query " + unit.name + " ... ... ");
			ins.queryUnitCards(unit);
			System.out.println("============== query " + unit.name + " finish ");
		}
		
		ins.conn.close();
		System.out.println("====================== over ==================================");
	}

	List<UnitInfo> getUnitList() throws Exception {
		List<UnitInfo> unitList = new ArrayList<UnitInfo>();

		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery(SQL_unit);
		while (rs.next()) {
			UnitInfo info = new UnitInfo();
			info.id = rs.getString(1);
			info.name = rs.getString(2);
			info.cardCount = rs.getString(3);

			unitList.add(info);
		}

		return unitList;
	}
	
	void printUnitInfo(List<UnitInfo> units) {
		String f = "E://temp/cardtest/unitinfo.txt";
		FileUtil.write(f, "unit info record count : " + units.size() + "\r\n", false);
		Print.print(f, units, true);
	}
	
	
	void queryUnitCards(UnitInfo info) throws Exception {
		PreparedStatement pstat = conn.prepareStatement(SQL_findCardsByUnit);
		pstat.setString(1, info.id);
		ResultSet rs = pstat.executeQuery();
		
		CSVWriter writer = new CSVWriter("E://temp/cardtest/" + info.name + "[" + info.id + "].csv");
		// 输出标题
		String[] titles = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			CardField field = fields[i];
			titles[i] = field.title;
		}
		writer.write(titles);
		
		while (rs.next()) {
			String[] row = new String[fields.length];
			
			for (int i = 0; i < fields.length; i++) {
				CardField field = fields[i];
				String value = rs.getString(field.name);
				
				if (field.isDate && !Judge.empty(value)) {
					java.sql.Date d = rs.getDate(field.name);
					row[i] = convertDate(d);
					continue;
				}
				
				if (Judge.empty(field.indexTable)) {
					row[i] = value == null ? "" : value;
				} else {
					row[i] = queryIndexValue(field, value);
				}
			}
			
			writer.write(row);
		}
		
		rs.close();
		pstat.close();
		writer.close();
	}
	
	String queryIndexValue(CardField field, String index) throws Exception {
		String sql = String.format(SQL_index, field.indexTable, index);
		ResultSet rs = tempStat.executeQuery(sql);
		if (rs.next()) {
			String ret = rs.getString(1);
//			rs.close();
			return ret;
		}
		return "";
	}
	
	String convertDate(java.sql.Date d) {
		return dateFormat.format(d);
	}
	
	
	/** 查询丢失卡片的单位（id,name,卡片数量)*/
	String SQL_unit = "select t1.orgunit, org.stdname,  count(*) " +
       " from GAMS_ASSETCARD t1 left join linxia08.MD_ORG org on t1.orgunit = org.recid" + 
       " where t1.recid not in (select t2.recid from linxia08.gams_assetcard t2) and t1.yewxlh = 1" +
       " group by t1.orgunit, org.stdname";
	
	class UnitInfo {
		String id;
		String name;
		String cardCount;
		
		public UnitInfo() {
			super();
		}

		public UnitInfo(String id, String name, String cardCount) {
			this.id = id;
			this.name = name;
			this.cardCount = cardCount;
		}

		@Override
		public String toString() {
			return "UnitInfo [unitid=" + id + ", unitname=" + name + ", cardCount=" + cardCount + "]";
		}
	}
	
	/** 根据单位找到它缺失的卡片 */
	String SQL_findCardsByUnit = "select * from GAMS_ASSETCARD t where t.orgunit = ? and t.yewxlh = 1 and t.recid not in (select t2.recid from linxia08.gams_assetcard t2)";
	
	/** 卡片字段若是索引，使用该SQL查询 */
	String SQL_index = "select t.stdname from linxia08.%1$s t where t.recid = '%2$s' ";
	
	/**
	 * 卡片字段信息
	 * @author zhangyuchen
	 *
	 */
	class CardField {
		String name; // 字段名
		String title; // 字段标题
		String indexTable; // 索引表
		boolean isDate = false; // 是否是日期
		
		public CardField(String name, String title) {
			this.name = name;
			this.title = title;
		}
		
		public CardField(String name, String title, boolean isDate) {
			this.name = name;
			this.title = title;
			this.isDate = isDate;
		}

		public CardField(String name, String title, String indexTable) {
			this.name = name;
			this.title = title;
			this.indexTable = indexTable;
		}
	}
	
	CardField[] fields = new CardField[] {
			new CardField("QINGCBH", "原资产编号"),
			new CardField("ZICFLID", "资产分类名称", "GAMS_JC_ASSETCLASS"),
			new CardField("ZICMC", "资产名称"),
			new CardField("GUIGXH", "规格型号"),
			new CardField("SHUL", "数量"),
			new CardField("DANJ", "均价"),
			new CardField("JIAZLXID", "价值类型", "GAMS_JC_VALUETYPE"),
			new CardField("JIAZ", "价值"),
			new CardField("CAIZXZJ", "财政性资金"),
			new CardField("FEICZXZJ", "非财政性资金"),
			new CardField("PIZWH", "批准文号"),
			new CardField("YUJNXSMZL", "预计寿命/栽种树龄"),
			new CardField("YUJNXSMZL", "预计使用年限（月）"),
			new CardField("KFF", "开发方"),
			new CardField("SHIYRID", "使用人", "GAMS_JC_PERSONNEL"),
			new CardField("GUANLBMID", "管理部门", "GAMS_JC_DEPARTMENT"),
			new CardField("SHIYBMID", "使用部门", "GAMS_JC_DEPARTMENT"),
			new CardField("CUNFDDID", "存放地点", "GAMS_JC_DEPOSITARY"),
			new CardField("SHIYFXID", "使用方向", "GAMS_JC_USAGE"),
			new CardField("SHIYZKID", "使用状态", "GAMS_JC_USEDSTATE"),
			new CardField("RUZXS", "入账形式", "GAMS_JC_ENTRYFORM"),
			new CardField("XCFS", "形成方式", "GAMS_JC_XCFS"),
			new CardField("KUAIJPZH", "会计凭证号"),
			new CardField("CAIWRZRQ", "财务入账日期", true),
			new CardField("QUDRQ", "取得日期", true),
			new CardField("QUDFSID", "取得方式", "GAMS_JC_GAINMANNER"),
			new CardField("ZUOLWZ", "坐落位置"),
			new CardField("YUSXMBH", "预算项目编号"),
			new CardField("SHEBYTID", "用途", "GAMS_JC_DEVICEUSE"),
			new CardField("BIANZQKID", "编制情况", "GAMS_JC_WEAVESTATE"),
			new CardField("CHANQXSID", "产权形式", "GAMS_JC_PROPERTYFORM"),
			new CardField("QUANSXZID", "权属性质", "GAMS_JC_PROPERTYKIND"),
			new CardField("QUANSNX", "权属年限"),
			new CardField("QUANSZH", "权属证号"),
			new CardField("QUANSZM", "权属证明"),
			new CardField("SHEJYT", "设计用途"),
			new CardField("TOURSYRQ", "投入使用日期", true),
			new CardField("CAIGZZXSID", "采购组织形式", "GAMS_JC_PURORGFORM"),
			new CardField("SHENGCCJ", "生产厂家"),
			new CardField("PINP", "品牌"),
			new CardField("SHIYXZ", "使用性质"),
			new CardField("TUDSYQR", "房屋所有权人"),
			new CardField("JUNGRQ", "竣工日期", true),
			new CardField("JIANZJGID", "建筑结构", "GAMS_JC_BUILDFRAME"),
			new CardField("DILYTID", "地类（用途）", "GAMS_JC_LANDCLASSUSE"),
			new CardField("TDCHANQXSID", "土地产权形式", "GAMS_JC_EQUITYFORM"),
			new CardField("TUDJCID", "土地级次", "GAMS_JC_LANDLEVEL"),
			new CardField("MIANJ", "建筑面积"),
			new CardField("MIANJ", "使用权面积"),
			new CardField("ZIYMJ", "自用面积"),
			new CardField("ZIYJZ", "自用价值"),
			new CardField("CHUJMJ", "出借面积"),
			new CardField("CHUJJZ", "出借价值"),
			new CardField("CHUZMJ", "出租面积"),
			new CardField("CHUZJZ", "出租价值"),
			new CardField("DUIWTZMJ", "对外投资面积"),
			new CardField("DUIWTZJZ", "对外投资价值"),
			new CardField("QITMJ", "其他面积"),
			new CardField("DUYMJ", "其中：独用面积"),
			new CardField("FENTMJ", "其中：分摊面积"),
			new CardField("QUNUANMJ", "其中：取暖面积"),
			new CardField("QITJZ", "其他价值"),
			new CardField("SHOUYNX", "收益年限"),
			new CardField("JIXS", "销售商"),
			new CardField("YONGTFLID", "车辆用途", "GAMS_JC_VEHICHLEUSESORT"),
			new CardField("CHELCD", "车辆产地"),
			new CardField("CHELSBH", "车辆识别号"),
			new CardField("CHEPH", "车牌号"),
			new CardField("FADJH", "发动机号"),
			new CardField("BAOXJZRQ", "保修截止日期", true),
			new CardField("PAIQL", "排气量"),
			new CardField("LICENCEDATE", "发证日期", true),
			new CardField("ZHENGSH", "证书号"),
			new CardField("ZHENGSH", "授权证明号"),
			new CardField("ZHENGSH", "著作权证书号"),
			new CardField("ZHUCDJJG", "注册登记机关"),
			new CardField("ZHUANLH", "专利号"),
			new CardField("ZHUANLH", "专利申请号"),
			new CardField("SHOUQGGZ", "授权公告日"),
			new CardField("ZHUCDJRQ", "注册登记日期", true),
			new CardField("TUDSYQLXID", "使用权类型", "GAMS_JC_LANDUSETYPES"),
			new CardField("WENWDJID", "文物等级", "GAMS_JC_CULRELICGRADE"),
			new CardField("CHUSZZNF", "出生/栽种年份"),
			new CardField("CHANGD", "产地"),
			new CardField("HETBH", "合同编号"),
			new CardField("LAIYD", "来源地"),
			new CardField("FAMMC", "发明名称"),
			new CardField("FAMMC", "授权证明"),
			new CardField("FAMR", "发明人"),
			new CardField("FAMR", "著作权人"),
			new CardField("FAPH", "发票号"),
			new CardField("GANGSK", "纲属科"),
			new CardField("CANGPND", "藏品年代"),
			new CardField("BAOCNX", "保存年限"),
			new CardField("DANGAH", "档案号"),
			new CardField("CHUBS", "出版社"),
			new CardField("CHUBRQ", "出版日期", true),
			new CardField("CHUBRQ", "申请日期", true),
			new CardField("ZHEJZTID", "折旧状态", "GAMS_JC_DEPRECSTATE"),
			new CardField("ZHEJFFID", "折旧方法", "GAMS_JC_DEPRECMETHOD"),
			new CardField("JIANZZB", "减值准备"),
			new CardField("YUEZJE", "月折旧额"),
			new CardField("CANZL", "残值率"),
			new CardField("YUJSMZL", "折旧年限（月）"),
			new CardField("YITZJYS", "已提折旧月数"),
			new CardField("LEIJZJ", "累计折旧"),
			new CardField("BEIZ", "备注"),
	};
}
