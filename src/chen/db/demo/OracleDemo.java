package chen.db.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chen.db.DB;
import chen.db.DB.DBType;
import chen.file.demo.ReadFileToMapDemo;
import chen.print.Print;

/**
 * 查询数据库中表的记录总数
 * @author zhangyuchen
 *
 */
public class OracleDemo {

	public static void main(String[] args) {
		OracleDemo qrc = new OracleDemo();
		Connection conn = DB.conn(DBType.ORACLE, DB.oracleUrl("orcl"), "zyc_test", "zyc_test");
		
		int i = 6;
		
		if (i == 0) {
			List<TableCount> countList = qrc.allTablesRecordCount(conn);
			Collections.sort(countList);
			Print.print(countList);
			Print.print("D://temp/0_all_tables_record_count_log.txt", countList, false);
		}

		if (i == 1) {
			List<TableSize> sizeList = qrc.allTablesSize(conn);
			Collections.sort(sizeList);
			Print.print(sizeList);
			Print.print("D://temp/1_all_tables_size_log.txt", sizeList, false);
		}

		if (i == 2) {
			List<RecordSize> rs = qrc.allTablesRecordSize(conn);
			Print.print(rs);
			Print.print("D://temp/2_all_tables_record_size_log.txt", rs, false);
		}

		if (i == 3) {
			List<RecordSize> rs = qrc.recordSize(conn);
			Print.print(rs);
			Print.print("D://temp/3_all_tables_record_size_log.txt", rs, false);
		}

		if (i == 4) {
//			List<String> tables = Arrays.asList("GAMS_TRANS_BASEMAPPING", "CORE_AUTHACL", "CORE_AUTHAUTHACL");
			Map<String, Long> map = new ReadFileToMapDemo().parseFile();
			List<String> tables = new ArrayList<String>(map.keySet());
			List<RecordSize> rs = qrc.recordSize(conn, tables);
			Print.print(rs);
			Print.print("D://temp/4_file_table_record_size_log.txt", rs, false);
		}
		
		if (i == 5) {
			Map<String, Long> map = new ReadFileToMapDemo().parseFile();
			List<String> tables = new ArrayList<String>(map.keySet());
			List<RecordSize> rs = qrc.recordSize2(conn, tables);
			Print.print(rs);
			Print.print("D://temp/5_file_tables_record_size_log.txt", rs, false);
		}
		
		// 需要统计的表，去重，排序
		if(i==6){
			List<String> tables = new ArrayList<String>(Arrays.asList("GAMS_TRANS_BASEMAPPING", "CORE_AUTHACL", "CORE_AUTHAUTHACL", "GAMS_TRANS_REPORTMAPPING"
					, "CORE_COLLATE_GBK", "B0101_ZB", "SM_LOGIN_RECORD", "G0102_AUDITPARAM_PARELAITION", "RP_ZB_EXT"
					, "B0605_LOGINFO", "PARTICIPANT", "G0109_COMMASTERDATA", "RP_FORMULA_TRACER", "GAMS_JC_ASSETCLASS_94GB"
					, "RP_MAPCELL", "PARAMETER", "NEXTITEM", "GAMS_JC_FINAATTACH", "B0704_SEARCHPLAN", "G0104_SYSTEMOPTION"
					, "GAMS_JC_ASSETCLASS", "GAMS_JC_ASSETCLASS_GB", "GAMS_JC_ASSETCLASS_GBUP", "GAMS_TRANS_BIZMAPPING"
					, "GAMS_JC_ADMDIVISIONS", "GAMS_REPORT_TRANS_CELL", "GAMS_ASSETBUSTRACE", "B0801_PLANTASKLOG", "GAMS_ASSETCARDCHANGE"
					, "B0204_QUERYCUSTSET", "RP_FORMULA", "GAMS_ASSETCARD", "B0501_WORKITEMREFER", "RP_MAINBODY_TYPE"
					, "GAMS_JC_INDSORT", "GAMS_CARDCHANGITEM", "GAMS_PT_PARAMETER", "GAMS_ASSETCHECK_I"));
			tables.addAll(Arrays.asList("B0605_LOGINFO", "GAMS_TRANS_BASEMAPPING", "G0102_AUDITPARAM_PARELAITION", "B0101_ZB"
					, "CORE_METADATA", "CORE_AUTHACL", "B0204_QUERYCUSTSET", "GAMS_REPORT_TRANS_CELL", "GAMS_TRANS_REPORTMAPPING"
					, "RP_FORMULA_TRACER", "RP_MAPCELL", "TASK_HIS_XASSETS_TODO", "CORE_AUTHAUTHACL", "GAMS_JC_ASSETCLASS_94GB"
					, "RP_ZB_EXT", "G0109_COMMASTERDATA", "GAMS_ASSETCARD", "GAMS_ASSETCARDCHANGE", "GAMS_JC_ADMDIVISIONS"
					, "GAMS_JC_ASSETCLASS", "GAMS_JC_ASSETCLASS_GB", "GAMS_JC_ASSETCLASS_GBUP", "GAMS_JC_FINAATTACH"
					, "PARTICIPANT", "RP_FORMULA", "SM_LOGIN_RECORD", "TASK_ASSIGNMENT", "TASK_XASSETS_TODO", "B0501_WORKITEMREFER "
					, "GAMS_JC_INDSORT", "GAMS_ASSETBUSTRACE", "B0801_PLANTASKLOG"));
			tables.addAll(Arrays.asList("TASK_XASSETS_MATTER", "XTASK_MD_TODOLIST", "G1038_ENCLOSURE", "B0100_USERPROFILE"
					, "G0102_Auditparam_Parelaition", "GAMS_JC_DEPOSITARY", "GAMS_JC_PERSONNEL", "MD_ORG", "GAMS_JC_DEPARTMENT"
					, "GAMS_ASSETCHECK_I", "GAMS_ASSETCHANGE_I", "GAMS_ASSETCORRECT_I", "GAMS_ASSETDEPREC_I", "GAMS_ASSETDISPOSEAPP_I"
					, "GAMS_ASSETDISPOSEREG_I", "GAMS_ASSETINCOME_I", "GAMS_ATTACHMENTCARD", "GAMS_ASSETBUSTRACE", "GAMS_ASSETBUSSDOC"
					, "GAMS_CARDCHANGITEM", "GAMS_DECLAREMATERIAL", "GAMS_CARDIMPORT_FILES", "GAMS_FILES"));
			
			// 去重
			Set<String> temp1= new HashSet<String>(tables);
			tables = new ArrayList<String>(temp1);
			
			// 排序
			Collections.sort(tables);
			
			List<RecordSize> rs = qrc.recordSize2(conn, tables);
			Print.print(rs);
			// 转换一下输出格式
			for (int c = 0, n = rs.size(); c < n; c++) {
				RecordSize t = rs.get(c);
				rs.set(c, new RecordSize2(t.tableName, t.size));
			}
			Print.print("D://temp/6_file_tables_record_size_log.txt", rs, false);
		}
		
		
		DB.close(conn);
	}
	
	/**
	 * 查询Oracle数据库所有表的总记录数
	 */
	public List<TableCount> allTablesRecordCount(Connection conn) {
		List<TableCount> tableCount = new ArrayList<TableCount>();
		// 表名和记录数
		List<String> tables = DB.allTables(conn, DBType.ORACLE);
		for (String table : tables) {
			long count = DB.recordCount(conn, table);
			tableCount.add(new TableCount(table, count));
		}
		return tableCount;
	}
	
	/**
	 * 查询Oracle数据库所有表的总记录数，返回Map
	 */
	public Map<String, Long> allTablesRecordCountMap(Connection conn) {
		Map<String, Long> tableCount = new HashMap<String, Long>();
		// 表名和记录数
		List<String> tables = DB.allTables(conn, DBType.ORACLE);
		for (String table : tables) {
			long count = DB.recordCount(conn, table);
			tableCount.put(table, count);
		}
		return tableCount;
	}
	
	
	
	/**
	 * 所有表现在所占的空间大小
	 * @param conn
	 * @return
	 */
	public List<TableSize> allTablesSize(Connection conn) {
		List<TableSize> sizes = new ArrayList<OracleDemo.TableSize>();
		String sql = "select t.segment_name, t.BYTES from user_segments t where t.segment_type='TABLE' ";
		PreparedStatement ps = DB.prepare(conn, sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				sizes.add(new TableSize(rs.getString(1), rs.getLong(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(ps, rs);
		}
		return sizes;
	}
	
	public Long tableSize(Connection conn, String table) {
		String sql = "select t.BYTES from user_segments t where t.segment_name='" + table + "' ";
		PreparedStatement ps = DB.prepare(conn, sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(ps, rs);
		}
		return -1L;
	}
	
	/**
	 * 所有表现在所占的空间大小,返回Map
	 * @param conn
	 * @return
	 */
	public Map<String, Long> allTablesSizeMap(Connection conn) {
		Map<String, Long> sizes = new HashMap<String, Long>();
		String sql = "select t.segment_name, t.BYTES from user_segments t where t.segment_type='TABLE' ";
		PreparedStatement ps = DB.prepare(conn, sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				sizes.put(rs.getString(1), rs.getLong(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.close(ps, rs);
		}
		return sizes;
	}
	

	
	/**
	 * 每张表中一条记录大约占据的空间大小<br>
	 * 根据现在数据库实际存储大小来估算
	 * @param conn
	 * @return
	 */
	public List<RecordSize> allTablesRecordSize(Connection conn) {
		List<RecordSize> ret = new ArrayList<OracleDemo.RecordSize>();

		Map<String, Long> count = allTablesRecordCountMap(conn);
		Map<String, Long> size = allTablesSizeMap(conn);
		for (Map.Entry<String, Long> entry : count.entrySet()) {
			Long c = entry.getValue();
			Long s = size.get(entry.getKey());
			if (c != null && c > 0 && s != null) {
				ret.add(new RecordSize(entry.getKey(), s / c));
			}
		}

		return ret;
	}
	
	/**
	 * 每张表中一条记录大约占据的空间大小<br>
	 * 根据现在数据库实际存储大小来估算
	 * @param conn
	 * @return
	 */
	public Map<String, Long> allTablesRecordSizeMap(Connection conn) {
		Map<String, Long> ret = new HashMap<String, Long>();
		
		Map<String, Long> count = allTablesRecordCountMap(conn);
		Map<String, Long> size = allTablesSizeMap(conn);
		for (Map.Entry<String, Long> entry : count.entrySet()) {
			Long c = entry.getValue();
			Long s = size.get(entry.getKey());
			if (c != null && c > 0 && s != null) {
				ret.put(entry.getKey(), s / c);
			}
		}
		
		return ret;
	}
	
	
	
	/**
	 * 所有表现在所占的空间大小
	 * @param conn
	 * @return
	 */
	public List<RecordSize> recordSize(Connection conn) {
		List<RecordSize> sizes = new ArrayList<RecordSize>();
		List<String> tables = DB.allTables(conn, DBType.ORACLE);
		for (String table : tables) {
			sizes.add(new RecordSize(table, rowSpace(conn, table)));
		}
		return sizes;
	}
	
	public List<RecordSize> recordSize(Connection conn, List<String> tables) {
		List<RecordSize> sizes = new ArrayList<RecordSize>();
		for (String table : tables) {
			sizes.add(new RecordSize(table, rowSpace(conn, table)));
		}
		return sizes;
	}
	
	/**
	 * 表中每一行的大小<br>
	 * 如果表中没有数据的话无法获得准确的估算<br>
	 * 如果表中有blob字段的话，会抛出异常
	 * @param conn
	 * @param table
	 * @return
	 */
	public Long rowSpace(Connection conn, String table) {
		Set<String> fields = DB.userTableFields(conn, table, DBType.ORACLE);
		if(fields.size() <= 0) {
			return -1L;
		}
		
		List<String> fieldsList = new ArrayList<String>(fields);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT AVG(NVL(VSIZE(t." + fieldsList.get(0) + "), 1))");
		for (int i = 1, n = fieldsList.size(); i < n; i++) {
			sql.append("+AVG(NVL(VSIZE(t." + fieldsList.get(i) + "), 1))");
		}
		sql.append(" from " + table + " t ");
		
		PreparedStatement ps = DB.prepare(conn, sql.toString());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			} else {
				return -1L;
			}
		} catch (SQLException e) {
//			System.out.println(table);
//			e.printStackTrace();
			return -1L;
//			System.exit(0);
		} finally {
			DB.close(ps, rs);
		}
//		return -1L;
	}
	
	public List<RecordSize> recordSize2(Connection conn, List<String> tables) {
		List<RecordSize> sizes = new ArrayList<RecordSize>();
		for (String table : tables) {
			sizes.add(new RecordSize(table, rowSpace2(conn, table)));
		}
		return sizes;
	}
	
	public Long rowSpace2(Connection conn, String table) {
		Long tableSize = tableSize(conn, table);
		if (tableSize < 0) {
			System.out.println("table:" + table + ", size=0");
			return 0L;
		}
		Long tableCount = DB.recordCount(conn, table);
		if (tableCount <= 0) {
			System.out.println("table:" + table + ", count=0");
			return tableSize;
		}
		return tableSize / tableCount;
	}
	
	/*===================== 用于排序的内部类 ===========================*/
	
	/**
	 * 表名和表的记录数
	 * @author zhangyuchen
	 *
	 */
	private static class TableCount implements Comparable<TableCount> {
		public String tableName;
		public Long recordCount;
		
		public TableCount(String tableName, Long recordCount) {
			this.tableName = tableName;
			this.recordCount = recordCount;
		}

		@Override
		public String toString() {
			return String.format("%1$30s has %2$10d records.", tableName, recordCount);
		}

		@Override
		public int compareTo(TableCount o) {
			return (int) (o.recordCount - this.recordCount);
		}
	}
	
	/**
	 * 表名和表的大小
	 * @author zhangyuchen
	 *
	 */
	private static class TableSize implements Comparable<TableSize> {
		public String tableName;
		public Long size;
		
		public TableSize(String tableName, Long size) {
			this.tableName = tableName;
			this.size = size;
		}

		@Override
		public String toString() {
			return String.format("%1$30s has %2$10d bytes.", tableName, size);
		}

		@Override
		public int compareTo(TableSize o) {
			return (int) (o.size - this.size);
		}
	}
	
	/**
	 * 每条记录所占大小
	 * @author zhangyuchen
	 *
	 */
	private static class RecordSize implements Comparable<RecordSize> {

		public String tableName;
		public Long size;
		
		public RecordSize(String tableName, Long size) {
			this.tableName = tableName;
			this.size = size;
		}

		@Override
		public String toString() {
			return String.format("%1$30s record size has %2$10d bytes.", tableName, size);
		}

		@Override
		public int compareTo(RecordSize o) {
			return (int) (o.size - this.size);
		}
		
	}
	
	/**
	 * 每条记录所占大小
	 * @author zhangyuchen
	 *
	 */
	private static class RecordSize2 extends RecordSize implements Comparable<RecordSize> {

		public RecordSize2(String tableName, Long size) {
			super(tableName, size);
		}

		@Override
		public String toString() {
			return String.format("%1$30s : %2$10d", tableName, size);
		}
		
	}
	
	
}
