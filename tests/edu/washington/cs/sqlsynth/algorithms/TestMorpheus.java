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
import edu.washington.cs.sqlsynth.util.TableInstanceReader;
import edu.washington.cs.sqlsynth.util.TableUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestMorpheus extends TestCase {
	
	public static Test suite() {
		return new TestSuite(TestMorpheus.class);
	}
	
	public void test_morpheus_12()
	{	
		long startTime = System.currentTimeMillis();
		
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/12/p12_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/12/p12_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		System.out.println("The skeleton: ");
		System.out.println(skeleton);
		
//		if(tables != null) {
//			return;
//		}
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test_morpheus_16()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/16/p16_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/16/p16_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test_morpheus_73() {
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/73/p73_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/73/p73_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output...." + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test_morpheus_77() {
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/77/p77_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/77/p77_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test_morpheus_84() {
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/84/p84_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/84/p84_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions**************:");
		System.out.println(aggrExprs.size());
		System.out.println(aggrExprs);
		
		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);
		
//		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test_morpheus_94() {
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/Morpheus/94/p94_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/Morpheus/94/p94_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		System.out.println("aggregate expressions**************:");
		System.out.println(aggrExprs.size());
		System.out.println(aggrExprs);
		System.out.println("group by columns:");
		System.out.println(groupbyColumns);
		System.out.println("all join conditions:");
		System.out.println(skeleton.getAllJoinConditions());
		System.out.println("projection columns:");
		System.out.println(skeleton.getProjectColumns());
		
		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);

//		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test_morpheus_100() {
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./morpheus/p73_input1");
		TableInstance output = TableInstanceReader.readTableFromFile("./morpheus/p73_output1");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
//		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
//		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
//		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
//		
//		System.out.println("aggregate expressions**************:");
//		System.out.println(aggrExprs.size());
//		System.out.println(aggrExprs);
		
//		List<SQLQuery> queries = completor.constructQueries(skeleton, aggrExprs, groupbyColumns);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		System.out.println("number of inferred queries: " + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println();
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output...." + queries.size());
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime - startTime) + " milliseconds");
        assertTrue(!queries.isEmpty());
	}
	
	@Override
	public void tearDown() {
		DbConnector.NO_ORDER_MATCHING = false;
		SQLSkeleton.REMOVE_DUPLICATE = false;
		SQLQueryCompletor.NESTED_CONDITION = false;
		SQLQueryCompletor.SEC_ORDER_FEA_CONDITION = false;
		SQLQueryCompletor.extra_cond = null;
		SQLQueryCompletor.out_column = null;
		SQLQueryCompletor.out_table = null;
	}
	
}
