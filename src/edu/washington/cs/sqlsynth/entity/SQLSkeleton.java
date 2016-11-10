package edu.washington.cs.sqlsynth.entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import plume.Pair;

import edu.washington.cs.sqlsynth.util.Globals;
import edu.washington.cs.sqlsynth.util.TableUtils;
import edu.washington.cs.sqlsynth.util.Utils;

public class SQLSkeleton {
	
	public static boolean REMOVE_DUPLICATE = false;
	
	//can have repetition
	private List<TableInstance> tables = new LinkedList<TableInstance>();
	
	//FIXME currently only supporting equal join
	private List<Pair<TableColumn, TableColumn>> joinColumns = new LinkedList<Pair<TableColumn, TableColumn>>();
	
	//the projections, also zero based
	//that is directly project the columnName to the output table without aggregation operator
	private Map<Integer, TableColumn> projectColumns = new LinkedHashMap<Integer, TableColumn>();
	
	//the total number of output columns, including column names and the aggregation operators.
	private final int outputColumnNumber;
	
	//the data of input/output tables
	private final List<TableInstance> inputTables;
	private final TableInstance outputTable;
	
	public SQLSkeleton(List<TableInstance> inputTables, TableInstance outputTable) {
		int outputColumnNum = outputTable.getColumnNum();
		Utils.checkTrue(outputColumnNum > 0);
		Utils.checkNotNull(outputTable);
		Utils.checkTrue(inputTables.size() > 0);
		this.outputColumnNumber = outputColumnNum;
		this.outputTable = outputTable;
		this.inputTables = inputTables;
	}
	
	public List<TableInstance> getTables() {
		return tables;
	}
	
	public void addTable(TableInstance table) {
		this.tables.add(table);
	}
	
	public void addTables(Collection<TableInstance> tables) {
		this.tables.addAll(tables);
	}
	
	public int getJoinPairNum() {
		return this.joinColumns.size();
	}
	
	public List<Pair<TableColumn, TableColumn>> getJoinColumns() {
		return joinColumns;
	}
	
	public void addJoinColumns(TableColumn t1, TableColumn t2) {
		Pair<TableColumn, TableColumn> p = new Pair<TableColumn, TableColumn>(t1, t2);
		addJoinColumns(p);
	}
	
	public void addJoinColumns(Pair<TableColumn, TableColumn> p) {
		Utils.checkTrue(!this.joinColumns.contains(p));
		this.joinColumns.add(p);
	}
	
	public Map<Integer, TableColumn> getProjectColumns() {
		return projectColumns;
	}
	
	public void setProjectColumn(int index, TableColumn column) {
		Utils.checkTrue(!this.projectColumns.containsKey(index));
		this.projectColumns.put(index, column);
	}
	
	public TableInstance getOutputTable() {
		return this.outputTable;
	}
	
	//note that this method only returns a shallow copy
	public TableInstance getTableOnlyWithMatchedColumns() {
		return getTableOnlyWithMatchedColumns(this.outputTable);
	}
	
	private TableInstance getTableOnlyWithMatchedColumns(TableInstance t) {
		TableInstance ret = new TableInstance(t.getTableName());
		//add columns that are matched
		for(TableColumn c : t.getColumns()) {
			if(TableUtils.findFirstMatchedColumn(c.getColumnName(), this.inputTables) != null) {
				ret.addColumn(c);
			}
		}
		return ret;
	}
	
	//compute a table after applying the join conditions
	public List<TableInstance> computeJoinTableWithoutUnmatches() {
		System.out.println("size: " + inputTables.size() + " " + joinColumns.size());
		Utils.checkTrue(!this.inputTables.isEmpty());		
		if(this.inputTables.size() == 1) {
			Utils.checkTrue(this.joinColumns.isEmpty());
		} else {
			Utils.checkTrue(!this.joinColumns.isEmpty());
		}
		System.out.println("ret:" );
	
		List<TableInstance> list = TableUtils.joinTables(this.inputTables, this.joinColumns);
		System.out.println("list:" + list.size());

		List<TableInstance> ret = new LinkedList<TableInstance>();
		for(TableInstance t : list) {
			//after joining two tables (before filtering), there would be columns
			//not in the output table, so need to remove such tables.
			ret.add(this.getTableOnlyWithMatchedColumns(t));
//			System.out.println("-------------------");
//			System.out.println(t.toString());
		}
		System.out.println("ret1:" + ret.size());

		if(REMOVE_DUPLICATE) {
			List<TableInstance> removes = new LinkedList<TableInstance>();
			for(TableInstance t : ret) {
				if(t.containDuplicateColumns()) {
					removes.add(t);
				}
			}
			ret.removeAll(removes);
		}
		
//		for(TableInstance t : ret) {
//			System.out.println("-------------------");
//			System.out.println(t.toString());
//		}
//		System.exit(0);
		System.out.println("ret:" + ret.size());
		return ret;
	}
	
	/**
	 * The total number of table columns
	 * */
	public int getOutputColumnNum() {
		return this.outputColumnNumber;
	}
	
	public String getAllJoinConditions() {
		if(this.joinColumns.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		int count = 0;
		for(Pair<TableColumn, TableColumn> p : this.joinColumns) {
			if(TableUtils.USE_SAME_NAME_JOIN) {
				if(!p.a.getColumnName().equals(p.b.getColumnName())) {
					continue;
				}
			}
			if(count != 0) {
				sb.append(" and ");
			}
			sb.append(p.a.getFullName());
			sb.append("=");
			sb.append(p.b.getFullName());
			count++;
		}
		sb.append(")");
		return sb.toString();
	}
	
	public String getJoinCondition(int i) {
		Utils.checkTrue(this.getJoinPairNum() > 0);
		Utils.checkTrue(i >= 0 && i < this.getJoinPairNum());
		int count = 0;
		for(Pair<TableColumn, TableColumn> p : this.joinColumns) {
			if(count == i) {
				return "(" + p.a.getFullName() + " = " + p.b.getFullName() + ")";
			}
			count++;
		}
		throw new Error("unreachable!");
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("tables: ");
		for(TableInstance t : this.getTables()) {
			sb.append(t.getTableName());
			sb.append("\t");
		}
		sb.append(Globals.lineSep);
		sb.append("Join column pairs: (after filtering) ");
		sb.append(this.getAllJoinConditions());
//		for(Pair<TableColumn, TableColumn> p : this.joinColumns) {
//			sb.append(p.a.getFullName() + " join " + p.b.getFullName());
//			sb.append("\t");
//		}
		sb.append(Globals.lineSep);
		sb.append("Output columns: ");
		for(int index : this.getProjectColumns().keySet()) {
			sb.append(index);
			sb.append(":");
			sb.append(this.getProjectColumns().get(index).getFullName());
			sb.append("\t");
		}
		sb.append(Globals.lineSep);
		return sb.toString();
	}
}