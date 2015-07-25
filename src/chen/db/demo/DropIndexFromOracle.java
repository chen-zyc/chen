package chen.db.demo;

import java.util.Scanner;

public class DropIndexFromOracle {

	private static final String	constraint		= "select t.table_name from USER_CONSTRAINTS t where t.index_name = '%1$s';";
	private static final String	searchPK		= "select t.constraint_name from USER_CONSTRAINTS t where t.table_name = '%1$s' and t.constraint_type = 'P';";
	private static final String	disablePK		= "alter table %1$s  disable constraint \"%2$s\";";
	private static final String	dropIndex		= "drop index \"%1$s\";";
	private static final String	createUnique	= "create unique index \"%1$s\" on %2$s (RECID)\n tablespace %3$s\n pctfree 10\n initrans 2\n maxtrans 255\n storage\n (\n initial 64K\n next 1M\n minextents 1\n maxextents unlimited\n);";
	private static final String	enablePK		= "alter table %1$s  enable constraint \"%2$s\";";
	private static final String	searchMulti		= "select * from %1$s t2 where t2.recid in (select t.recid from %1$s t group by t.recid having count(*)>1)";

	public static void main(String[] args) {
		System.out.print("约束名：");
		Scanner scanner = new Scanner(System.in);
		String constraintName = scanner.next().trim();
		System.out.println();

		System.out.print("使用" + String.format(constraint, constraintName) + "查询表名:");
		String tableName = scanner.next().trim();
		System.out.println();

		System.out.print("使用" + String.format(searchPK, tableName) + "查看" + tableName + "表的主键约束名:");
		String pkName = scanner.next().trim();
		System.out.println();

		System.out.print("tablespace:");
		String tablespace = scanner.next().trim();
		System.out.println();

		System.out.println("-- 将主键置为不可用");
		System.out.println(String.format(disablePK, tableName, pkName));

		System.out.println("-- 删除唯一性约束");
		System.out.println(String.format(dropIndex, constraintName));

		System.out.println("-- 现在可以升级了... ...\n");

		System.out.println("-- 查询重复的数据并删除重复的数据");
		System.out.println(String.format(searchMulti, tableName));

		System.out.println("-- 创建唯一性约束");
		System.out.println(String.format(createUnique, constraintName, tableName, tablespace));

		System.out.println("-- 主键约束重置为可用状态");
		System.out.println(String.format(enablePK, tableName, pkName));
	}

}
