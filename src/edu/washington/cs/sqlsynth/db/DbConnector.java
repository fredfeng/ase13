package edu.washington.cs.sqlsynth.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import plume.Pair;

import edu.washington.cs.sqlsynth.entity.SQLQuery;
import edu.washington.cs.sqlsynth.entity.TableColumn;
import edu.washington.cs.sqlsynth.entity.TableColumn.ColumnType;
import edu.washington.cs.sqlsynth.entity.TableInstance;
import edu.washington.cs.sqlsynth.util.Globals;
import edu.washington.cs.sqlsynth.util.Log;
import edu.washington.cs.sqlsynth.util.TableInstanceReader;
import edu.washington.cs.sqlsynth.util.Utils;

public class DbConnector {
	
	public static boolean NO_ORDER_MATCHING = false;
	
	//when joining multiple tables, each table must be used at least
	//once in the joining conditions
	public static boolean USE_ALL_TABLES = true;

	private Connection con = null;
	
	private static DbConnector c = null;
	
	private DbConnector() {}
	
	public static DbConnector instance() {
		if(c == null) {
			c = new DbConnector();
		}
		return c;
	}

	public void initializeInputTables(Collection<TableInstance> inputTables) {
		if(con == null) {
		    connect();
		}
		for (TableInstance t : inputTables) {
			this.initializeTable(t);
		}
	}
	
	
	public boolean areAllTablesUsedInJoining(Collection<TableInstance> tables, Collection<Pair<TableColumn, TableColumn>> joinColumns) {
		//check whether all tables are involved in the joining conditions
		//for instance, if there are three tables A, B, C, but the joining columns are only: A.a == B.b
		//this should not be joined
		Set<String> tableNames = new HashSet<String>();
		for(TableInstance t : tables) {
			tableNames.add(t.getTableName());
		}
//		System.out.println("Table names: " + tableNames);
		//look at the join columns
		for(Pair<TableColumn, TableColumn> p : joinColumns) {
			tableNames.remove(p.a.getTableName());
			tableNames.remove(p.b.getTableName());
		}
//		System.out.println("Joini columns: " + joinColumns);
		
		return tableNames.isEmpty();
	}
	
	public TableInstance joinTable(Collection<TableInstance> tables, Collection<Pair<TableColumn, TableColumn>> joinColumns) {
		String newTableName = "join";
		for(TableInstance t : tables) {
			newTableName = newTableName + "_" + t.getTableName();
		}
		TableInstance newTable = new TableInstance(newTableName);
		//create tables
		this.initializeInputTables(tables);
		//construct the SQL statements
		StringBuilder joinSQL = new StringBuilder();
		
		//all columns
		List<String> selectColumns = new LinkedList<String>();
		for(TableInstance t : tables) {
			for(TableColumn c : t.getColumns()) {
				selectColumns.add(c.getFullName());
			}
		}
		//remove repetitive columns, e.g., if a.id == b.id is used,
		//either a.id or b.id should be appeared just once
		//it used a simple heuristic, first rank all table names alphabetically,
		//and always retain the first table name
		Set<String> removed = this.getRemovableColumns(tables, joinColumns);
		
		//remove the repetitive table columns
		selectColumns.removeAll(removed);
		
		joinSQL.append("select ");
		int count = 0;
		for(String r : selectColumns) {
			if(count != 0) {
				joinSQL.append(", ");
			}
			joinSQL.append(r);
			count++;
		}
		joinSQL.append(" from ");
		count = 0;
		for(TableInstance t : tables) {
			if(count != 0) {
				joinSQL.append(", ");
			}
			joinSQL.append(t.getTableName());
			count++;
		}
		if(!joinColumns.isEmpty()) {
		    joinSQL.append(" where ");
		}
		count = 0;
		for(Pair<TableColumn, TableColumn> p :joinColumns) {
			if(count != 0) {
				joinSQL.append(" and ");
			}
			joinSQL.append(p.a.getFullName() + "=" + p.b.getFullName());
			count++;
		}
		
		Log.logln("During joining: " + joinSQL
		    		+ ", joining condition: " + joinColumns);
		
		ResultSet r = this.executeQuery(this.con, joinSQL.toString());
		this.insertResultSetIntoEmptyTable(newTable, r);
		
		return newTable;
	}
	
	
	Set<String> getRemovableColumns(Collection<TableInstance> tables, Collection<Pair<TableColumn, TableColumn>> joinColumns) {
		//remove repetitive columns, e.g., if a.id == b.id is used,
		//either a.id or b.id should be appeared just once
		//it used a simple heuristic, first rank all table names alphabetically,
		//and always retain the first table name
//		List<String> sortedNames = this.sortTableByNames(tables);
		
		//then decide which column should be removed
		List<Set<String>> joinClosures = new LinkedList<Set<String>>();
		for(Pair<TableColumn, TableColumn> p : joinColumns) {
			Set<String> tmpSet = new HashSet<String>();
			tmpSet.add(p.a.getFullName());
			tmpSet.add(p.b.getFullName());
			joinClosures.add(tmpSet);
		}
		
		//test whether two sets have overlapped, if so merge then
		boolean flag = true;
		while (flag) {
			flag = false;
			for (int i = 0; i < joinClosures.size(); i++) {
				for (int j = i + 1; j < joinClosures.size(); j++) {
					Set<String> seti = joinClosures.get(i);
					Set<String> setj = joinClosures.get(j);
					if (Utils.hasOverlap(seti, setj)) {
						// we merge set i and setj
						joinClosures.get(i).addAll(joinClosures.get(j));
						joinClosures.remove(j);
						flag = true;
						break;
					}
				}
				if(flag) {
					break;
				}
			}
		}
		
		Set<String> removed = new LinkedHashSet<String>();
		//XXX same logic as in the TableUtils
		for(Set<String> set : joinClosures) {
			//for each set only retain the highest ranked one
			List<String> l = new LinkedList<String>(set);
			Collections.sort(l);
			Utils.checkTrue(l.size() > 1);
			removed.addAll(l.subList(1, l.size()));
		}
		
		System.out.println("Removable column names from joining conditions: " + removed);
		
		return removed;
	}
	
	private void insertResultSetIntoEmptyTable(TableInstance newTable, ResultSet rs) {
		Utils.checkTrue(newTable.getColumnNum() == 0);
		try {
		    ResultSetMetaData meta = rs.getMetaData();
		    int columnCount = meta.getColumnCount();
		    TableColumn[] columns = new TableColumn[columnCount];
		    for(int i = 0; i < columnCount; i++) {
		    	String columnName = meta.getColumnName(i + 1); //it is 1-based
		    	columns[i] = new TableColumn(newTable.getTableName(), columnName,
		    			getColumnType(meta.getColumnType(i + 1)),
		    			columnName.endsWith(TableInstanceReader.KEY));
		    }
		    //feed data
		    while(rs.next()) {
				for(int i = 0; i < columnCount; i++) {
					Object v = columns[i].isIntegerType() ? rs.getInt(i + 1) : rs.getString(i + 1); 
					columns[i].addValue(v);
					Utils.checkNotNull(v);
				}
			}
		    //set the column
		    for(TableColumn c : columns) {
		    	newTable.addColumn(c);
		    }
		} catch (SQLException e ) {
			throw new Error(e);
		}
	}
	
	private ColumnType getColumnType(int t) {
		if(t == Types.INTEGER || t== Types.BIGINT || t == Types.DECIMAL) {
			return ColumnType.Integer;
		} else if (t == Types.VARCHAR) {
			return ColumnType.String;
		} else {
			throw new Error();
		}
	}
	

	public boolean checkSQLQueryWithOutput(TableInstance output, SQLQuery sql) {
		return checkSQLQueryWithOutput(output, sql.toSQLString());
	}
	public boolean checkSQLQueryWithOutputNestedConditions(TableInstance output, SQLQuery sql) {
		return checkSQLQueryWithOutput(output, sql.toNestedSQLString());
	}
	private static String COMMA = ",";
	boolean checkSQLQueryWithOutput(TableInstance output, String sql) {
		//convert all to string for comparison
		//FIXME may have co-incident matching
		StringBuilder outputSb = new StringBuilder();
		for(int i = 0; i < output.getRowNum(); i++) {
			List<Object> objs = output.getRowValues(i);
			if(i != 0) {
				outputSb.append(Globals.lineSep);
			}
			for(int index = 0; index < objs.size(); index++) {
				if(index != 0) {
					outputSb.append(COMMA);
				}
				outputSb.append(objs.get(index) + "");
			}
		}
		//query the database to check if the results are the same!
		ResultSet rs = this.executeQuery(con, sql);
		String queryResultStr = tablizeResultSet(rs);
		System.out.println("output sb: ");
		System.out.println(outputSb.toString());
		System.out.println("query result: ");
		System.out.println(queryResultStr.toString());
		
		boolean matched = false;
		if(NO_ORDER_MATCHING) {
			matched = noOrderMatch(outputSb.toString(), queryResultStr);
		} else {
		    matched = outputSb.toString().equals(queryResultStr);
		}
		System.out.println("Matched: " + matched);
		return matched;
	}
	
	private boolean noOrderMatch(String a, String b) {
		Set<String> s1 = new HashSet<String>();
		String[] as = a.split(Globals.lineSep);
		for(String s : as) {
			s1.add(s);
		}
		
		Set<String> s2 = new HashSet<String>();
		String[] bs = b.split(Globals.lineSep);
		for(String s : bs) {
			s2.add(s);
		}
//		System.out.println(s1);
//		System.out.println(s2);
		return s1.equals(s2);
	}

	public void connect() {
		String url = MySQLConfigReader.getURL();
		String dbName = MySQLConfigReader.getDbname();
		String driver = MySQLConfigReader.getDriver();
		String userName = MySQLConfigReader.getUsername();
		String password = MySQLConfigReader.getPassword();
		System.out.println("URL: " + url);
		System.out.println("dbname: " + dbName);
		System.out.println("driver:  " + driver);
		System.out.println("username: " + userName);
		System.out.println("password: " + password);
		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Connected to the database");
		} catch (Exception e) {
			throw new Error(e);
		}
	}

	public void disconnect() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new Error();
			}
		}
	}
	
	public static boolean NO_DROP_TABLE = false;
	
	void initializeTable(TableInstance table) {
		String tableName = table.getTableName();
		if(NO_DROP_TABLE) {
			if(this.isTableExist(tableName)) {
				this.deleteAllTableContent(tableName);
			} else {
				this.createTable(table);
			}
			this.insertTableData(table);
		} else {
		    //always drop the table first
		    if(this.isTableExist(tableName)) {
			    this.dropTable(tableName);
		    }
		    //also inserting data
		    this.createTable(table);
		    this.insertTableData(table);
		}
	}
	
	private void createTable(TableInstance table) {
		String tableName = table.getTableName();
		//then create the table again
		StringBuilder sql = new StringBuilder();
		sql.append("create table ");
		sql.append(tableName);
		sql.append(" (");
		for(int i = 0; i < table.getColumnNum(); i++) {
			if(i != 0) {
				sql.append(" , ");
			}
			TableColumn c = table.getColumns().get(i);
			sql.append(c.getColumnName());
			sql.append(" ");
			sql.append(c.getMySQLColumnType());		
		}
		sql.append(" )");
		this.executeSQL(con, sql.toString());
		//}
	}
	
	private void insertTableData(TableInstance table) {
		String tableName = table.getTableName();
		//then insert the data
		for(int i = 0; i < table.getRowNum(); i++) {
			List<Object> values = table.getRowValuesWithQuoate(i);
			StringBuilder insert = new StringBuilder();
			insert.append("insert into ");
			insert.append(tableName);
			insert.append(" values(");
			for(int index = 0; index < values.size(); index++) {
				if(index != 0) {
					insert.append(", ");
				}
				insert.append(values.get(index));
			}
			insert.append(" )");
			this.executeSQL(con, insert.toString());
		}
	}
	
	boolean isTableExist(String tableName) {
		String sql = "select * from " + tableName;
		Statement s = null;
		try {
			s = con.createStatement ();
		} catch (SQLException e) {
			throw new Error(e);
		}
		try {
			s.executeQuery(sql);
		} catch (SQLException e) {
			return false;
		}
		try {
			s.close();
		} catch (SQLException e) {
			throw new Error(e);
		}
		return true;
	}
	
	void deleteAllTableContent(String tableName) {
		String delete = "delete from " + tableName;
		try {
			System.out.println("executing: " + delete);
			Statement s = con.createStatement ();
			s.executeUpdate(delete);
			s.close();
		} catch (SQLException e) {
			throw new Error(e);
		}
	}
	
	void dropTable(String tableName) {
		String drop = "drop table " + tableName;
		try {
			System.out.println("executing: " + drop);
			Statement s = con.createStatement ();
			s.executeUpdate(drop);
			s.close();
		} catch (SQLException e) {
			throw new Error(e);
		}
	}
	
	private void executeSQL(Connection con, String sql) {
		try {
			System.out.println("executing: " + sql.toString());
			Statement s = con.createStatement ();
			s.execute(sql);
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Error("Error in executing: " + sql);
		}
	}
	
	private ResultSet executeQuery(Connection con, String sql) {
		try {
			System.out.println("executing: " + sql.toString());
			Statement s = con.createStatement ();
			ResultSet rs = s.executeQuery(sql);
//			s.close();
			return rs;
		} catch (SQLException e) {
			System.err.println("Error in executing: " + sql);
			throw new Error(e);
		}
	}
	
	private String tablizeResultSet(ResultSet rs) {
		try {
			Utils.checkTrue(!rs.isClosed());
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount();
			int count = 0;
			StringBuilder sb = new StringBuilder();
			while(rs.next()) {
				if(count != 0) {
					sb.append(Globals.lineSep);
				}
				for(int i = 0; i < columnCount; i++) {
					if(i != 0) {
						sb.append(COMMA);
					}
//					System.out.println(i);
					int t = meta.getColumnType(i + 1); //note it is 1-based
					String v = null;
					if(t == Types.INTEGER || t== Types.BIGINT || t == Types.DECIMAL) {
						v = rs.getInt(i + 1) + "";
					} else if (t == Types.VARCHAR) {
						v = rs.getString(i + 1);
					} else {
						Utils.checkTrue(false, "Type: " + t);
					}
					Utils.checkNotNull(v);
					sb.append(v);
				}
				count ++;
			}
			return sb.toString();
		} catch (SQLException e) {
			throw new Error(e);
		}
		
	}
}
