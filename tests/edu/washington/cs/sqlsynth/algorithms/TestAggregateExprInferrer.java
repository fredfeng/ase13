package edu.washington.cs.sqlsynth.algorithms;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.sqlsynth.db.DbConnector;
import edu.washington.cs.sqlsynth.entity.AggregateExpr;
import edu.washington.cs.sqlsynth.entity.SQLQuery;
import edu.washington.cs.sqlsynth.entity.SQLSkeleton;
import edu.washington.cs.sqlsynth.entity.TableColumn;
import edu.washington.cs.sqlsynth.entity.TableInstance;
import edu.washington.cs.sqlsynth.util.Log;
import edu.washington.cs.sqlsynth.util.TableInstanceReader;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestAggregateExprInferrer extends TestCase {
	
	public static Test suite() {
		return new TestSuite(TestAggregateExprInferrer.class);
	}

	public void test1() {
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/id_name");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/id_salary");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/id_name_salary");
		SQLSkeleton skeleton = TestSQLSkeletonCreator.createSampleSkeleton();
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		//create the inferrer
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println(aggrExprs);
		System.out.println(groupbyColumns);
		System.out.println(skeleton.getAllJoinConditions());
		System.out.println(skeleton.getProjectColumns());
		
		assertTrue(aggrExprs.isEmpty());
		assertTrue(groupbyColumns.isEmpty());
	}
	
	public void test2() {
		TableInstance input = TableInstanceReader.readTableFromFile("./dat/groupby/name_salary");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/groupby/name_salary_count");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input);
		completor.setOutputTable(output);
		
		//create the inferrer
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions:");
		System.out.println(aggrExprs);
		System.out.println("group by columns:");
		System.out.println(groupbyColumns);
		System.out.println("all join conditions:");
		System.out.println(skeleton.getAllJoinConditions());
		System.out.println("projection columns:");
		System.out.println(skeleton.getProjectColumns());
		
		assertTrue(aggrExprs.size() == 1);
		assertTrue(groupbyColumns.size() == 1);
	}
	
	public void test3() {
		TableInstance input = TableInstanceReader.readTableFromFile("./dat/multigroupby/id_salary_age");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/multigroupby/id_max_all");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input);
		completor.setOutputTable(output);
		
		//create the inferrer
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions:");
		System.out.println(aggrExprs.size());
		System.out.println(aggrExprs);
		System.out.println("group by columns:");
		System.out.println(groupbyColumns);
		System.out.println("all join conditions:");
		System.out.println(skeleton.getAllJoinConditions());
		System.out.println("projection columns:");
		System.out.println(skeleton.getProjectColumns());
		
		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);
		
		System.out.println("Num of queries: " + queries.size());
		for(SQLQuery sql : queries) {
			System.out.println(sql.toSQLString());
		}
		
		queries = completor.validateQueriesOnDb(queries);
		System.out.println("After validation: ");
		for(SQLQuery sql : queries) {
			System.out.println(sql.toSQLString());
		}
	}
	
	public void test4() {
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/jointhengroupby/id_name");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/jointhengroupby/name_salary");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/jointhengroupby/id_maxsalary");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		//create the inferrer
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions:");
		System.out.println(aggrExprs.size());
		System.out.println("****************" + aggrExprs);
//		System.out.println("group by columns:");
//		System.out.println(groupbyColumns);
//		System.out.println("all join conditions:");
//		System.out.println(skeleton.getAllJoinConditions());
//		System.out.println("projection columns:");
//		System.out.println(skeleton.getProjectColumns());
		
		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);
		
		System.out.println("Num of queries: " + queries.size());
		for(SQLQuery sql : queries) {
			System.out.println(sql.toSQLString());
		}
		
		queries = completor.validateQueriesOnDb(queries);
		System.out.println("After validation: ");
		for(SQLQuery sql : queries) {
			System.out.println(sql.toSQLString());
		}
	}
	
	public void removedtest5() {
		AggregateExpr.moreStringOp = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		Log.logConfig("./log.txt");
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/proposalexample/table1");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/proposalexample/table2");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/proposalexample/table3");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/proposalexample/output_nocond");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		//create the inferrer
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions:");
		System.out.println(aggrExprs.size());
		System.out.println(aggrExprs);
		System.out.println("group by columns:");
		System.out.println(groupbyColumns);
		System.out.println("all join conditions:");
		System.out.println(skeleton.getAllJoinConditions());
		System.out.println("projection columns:");
		System.out.println(skeleton.getProjectColumns());
		
		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);
		
		System.out.println("Num of queries: " + queries.size());
		List<SQLQuery> filtered = queries; 
		filtered = new LinkedList<SQLQuery>();
		for(SQLQuery sql : queries) {
			//if(sql.toSQLString().trim().startsWith("select min(table1.T1Column1), table2.T2Column3, min(table1.T1Column4), min(table3.T3Column2)")) {
			    System.out.println(sql.toSQLString());
			    filtered.add(sql);
			//}
		}
		
		filtered = completor.validateQueriesOnDb(filtered);
		System.out.println("After validation: size of filtered: " + filtered.size());
		for(SQLQuery sql : filtered) {
			System.out.println(sql.toSQLString());
		}
	}
	
	@Override
	public void tearDown() {
		AggregateExpr.moreStringOp = false;
		DbConnector.NO_ORDER_MATCHING = false;
		Log.removeLog();
	}
}
