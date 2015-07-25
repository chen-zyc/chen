package chen.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DB {

	public static enum DBType {
		ORACLE("oracle.jdbc.driver.OracleDriver"),
		MYSQL("com.mysql.jdbc.Driver"),
		SQLSERVER("com.microsoft.jdbc.sqlserver.SQLServerDriver");
		
		private String driver;
		
		private DBType(String driver) {
			this.driver = driver;
		}
		
		public String driver() {
			return this.driver;
		}
	}
	
	
	/*================= 连接 ===========================*/
	
	/**
	 * 获取数据库连接
	 * @param type 数据库类型
	 * @param url
	 * @param user
	 * @param pwd
	 * @return
	 */
	public static Connection conn(DBType type, String url, String user, String pwd) {
		if (type == null) {
			return null;
		}
		try {
			Class.forName(type.driver());
		} catch (ClassNotFoundException e) {
			return null;
		}
		try {
			return DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			return null;
		}
	}
	
	/**
	 * 关闭
	 * @param ps
	 * @param rs
	 */
	public static void close(PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/*================= 获取数据库URL信息 ===========================*/
	
	/**
	 * MySQL URL信息
	 * @param address 地址，如127.0.0.1
	 * @param port 端口，如3306
	 * @param dbName 数据库名称
	 * @return
	 */
	public static String mysqlUrl(String address, String port, String dbName) {
		return "jdbc:mysql://" + address + ":" + port + "/" + dbName + "";
	}
	
	/**
	 * 【重载】地址默认使用127.0.0.1，端口默认使用3306
	 * @param dbName
	 * @return
	 */
	public static String mysqlUrl(String dbName) {
		return "jdbc:mysql://127.0.0.1:3306/" + dbName + "";
	}
	
	/**
	 * oracle URL信息
	 * @param address 地址，如127.0.0.1
	 * @param port 端口，如1521
	 * @param dbName 数据库名称
	 * @return
	 */
	public static String oracleUrl(String address, String port, String dbName) {
		return "jdbc:oracle:thin:@" + address + ":" + port + ":" + dbName;
	}
	
	/**
	 * 【重载】地址默认使用127.0.0.1，端口默认使用1521
	 * @param dbName
	 * @return
	 */
	public static String oracleUrl(String dbName) {
		return "jdbc:oracle:thin:@127.0.0.1:1521:" + dbName;
	}
	
	/**
	 * sqlserver URL信息
	 * @param serverName
	 * @param port
	 * @return
	 */
	public static String sqlserverUrl(String serverName, String port) {
		return "jdbc:microsoft:sqlserver://" + serverName + ":" + port;
	}
	
	/*================= 构建 ===========================*/
	
	/**
	 * 构建PreparedStatement
	 * @param conn
	 * @param sql
	 * @param args
	 * @return
	 */
	public static PreparedStatement prepare(Connection conn, String sql, Object...args) {
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			if (args != null && args.length > 0) {
				for (int i = 0, n = args.length; i < n; i++) {
					ps.setObject(i + 1, args[i]);
				}
			}
			return ps;
		} catch (SQLException e) {
			return null;
		}
	}

	/*================= 查询 ===========================*/
	
	/**
	 * 查询表的记录总数
	 * @param conn
	 * @param tableName
	 * @return 表的记录总数或-1
	 */
	public static long recordCount(Connection conn, String tableName) {
		String sql = "select count(*) from " + tableName;
		PreparedStatement ps = prepare(conn, sql, new Object[0]);
		if (ps == null) {
			return -1;
		}
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps, rs);
		}
		return -1;
	}
	
	/**
	 * 查询数据库所有用户创建的表
	 * @param conn
	 * @param type
	 * @return
	 */
	public static List<String> allTables(Connection conn, DBType type) {
		String sql = null;
		List<String> tables = new ArrayList<String>();
		switch (type) {
			case ORACLE:
				sql = "select t.TABLE_NAME from user_tables t";
				break;

			default:
				throw new RuntimeException("现在还不支持这种类型");
		}
		PreparedStatement ps = prepare(conn, sql);
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			while (rs.next()) {
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
		} finally {
			close(ps, rs);
		}
		return tables;
	}
	
	/**
	 * 获取用户表的所有字段名
	 * @param conn
	 * @param table
	 * @param type
	 * @return
	 */
	public static Set<String> userTableFields(Connection conn, String table, DBType type) {
		String sql = null;
		Set<String> fields = new HashSet<String>();
		switch (type) {
			case ORACLE:
				sql = "select t.COLUMN_NAME from  user_tab_columns t where t.TABLE_NAME='" + table + "' ";
				break;

			default:
				throw new RuntimeException("现在还不支持这种类型");
		}
		PreparedStatement ps = prepare(conn, sql);
		try {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				fields.add(rs.getString(1));
			}
		} catch (SQLException e) {
		}
		return fields;
	}
	
	/*================= 解析 ===========================*/
	
	
}
