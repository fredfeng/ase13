package edu.washington.cs.sqlsynth.algorithms;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.sqlsynth.db.DbConnector;
import edu.washington.cs.sqlsynth.entity.AggregateExpr;
import edu.washington.cs.sqlsynth.entity.NotExistStmt;
import edu.washington.cs.sqlsynth.entity.SQLQuery;
import edu.washington.cs.sqlsynth.entity.SQLSkeleton;
import edu.washington.cs.sqlsynth.entity.TableColumn;
import edu.washington.cs.sqlsynth.entity.TableInstance;
import edu.washington.cs.sqlsynth.util.TableInstanceReader;
import edu.washington.cs.sqlsynth.util.TableUtils;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TestSQLCompletor extends TestCase {
	
	public static Test suite() {
		return new TestSuite(TestSQLCompletor.class);
	}
	
	public void test5_1_1()
	{	
		long startTime = System.currentTimeMillis();
		
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_1/class");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_1/enroll");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_1_1/student");
		TableInstance input4 = TableInstanceReader.readTableFromFile("./dat/5_1_1/faculty");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_1/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		inputs.add(input4);
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
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		System.out.println("input 4:");
		System.out.println(input4);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.addInputTable(input4);
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
        System.out.println("[Benchmark 5_1_1]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_1_2()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_2/class");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_2/enroll");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_1_2/student");
		TableInstance input4 = TableInstanceReader.readTableFromFile("./dat/5_1_2/faculty");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_2/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		inputs.add(input4);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
//		if(tables != null) {
//			return;
//		}
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		System.out.println("input 4:");
		System.out.println(input4);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.addInputTable(input4);
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
        System.out.println("[Benchmark 5_1_2]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	
	public void test5_1_4()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_4/class");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_4/enroll");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_1_4/student");

		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_4/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
//		if(tables != null) {
//			return;
//		}
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
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
        System.out.println("[Benchmark 5_1_4]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	
	public void test5_1_6()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_6/class");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_6/enroll");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_1_6/faculty");

		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_6/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
		
//		if(tables != null) {
//			return;
//		}
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
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
        System.out.println("[Benchmark 5_1_6]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test5_1_7()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_7/student");
		
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_7/output");
		
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
		System.out.println("The final output...AAA.");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_7]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_1_8()
	{	
		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_8/student");
		
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_8/output");
		
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
        System.out.println("[Benchmark 5_1_8]It took " + (endTime - startTime) + " milliseconds");
	}
	

	public void test5_1_3()
	{	
		long startTime = System.currentTimeMillis();
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_3/id_class_5_1_3");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_3/id_enroll_5_1_3");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_3/output_5_1_3");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
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
        System.out.println("[Benchmark 5_1_3]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test5_1_5()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_5/id_class_5_1_5");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_5/id_faculty_5_1_5");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_5/output_5_1_5");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_5]It took " + (endTime - startTime) + " milliseconds");
	}
	
	//NOTE this can not be figured by our language subset
	public void test5_1_9()
	{
		long startTime = System.currentTimeMillis();
		SQLQueryCompletor.NESTED_CONDITION = true;
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_9/id_class_5_1_9");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_9/id_faculty_5_1_9");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_9/output_5_1_9");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println("Before validating, number of queries: " + queries.size());
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		this.outputQueries(queries);
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_9]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_1_10()
	{
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_10/student");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_10/enrolled");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_10/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_10]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_1_11()
	{
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_11/student");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_1_11/enroll");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_11/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_11]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test5_1_12()
	{
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_1_12/student");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_1_12/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_1_12]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void testDTree5()
	{
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/single_table_1/provide_relation");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/single_table_1/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
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
	}
	
	public void testDTree6()
	{
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/single_table_2/tbl1");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/single_table_2/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
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
	}
	
	public void testDTree7()
	{
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/two_tables_order_by/vote");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/two_tables_order_by/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		DbConnector.NO_ORDER_MATCHING = true;
		
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
	}
	
	public void testDTree8()
	{
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/four_tables/house");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/four_tables/property");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/four_tables/person");
		TableInstance input4 = TableInstanceReader.readTableFromFile("./dat/four_tables/car");
		
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/four_tables/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		inputs.add(input4);
		
		SQLSkeleton.REMOVE_DUPLICATE = true;
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		System.out.println("input 4:");
		System.out.println(input4);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.addInputTable(input4);
		
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
	}
	
	
	public void test5_2_1()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_1/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_1/catalog");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_1/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		System.out.println("columns: " + skeleton.getAllJoinConditions());
		
		System.out.println("debug1");
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		System.out.println("debug2");

		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
//		
//		if(tables != null) {
//			return;
//		}
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_1]It took " + (endTime - startTime) + " milliseconds");
		//throw new Error();
	}
	
	public void test5_2_2()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_2/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_2/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_2/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_2/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		System.out.println("columns: " + skeleton.getAllJoinConditions());
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
//		
//		if(tables != null) {
//			return;
//		}
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_2]It took " + (endTime - startTime) + " milliseconds");
		//throw new Error();
	}
	
	
	public void test5_2_3()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_3/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_3/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_3/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_3/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		System.out.println("columns: " + skeleton.getAllJoinConditions());
		
		List<TableInstance> tables = skeleton.computeJoinTableWithoutUnmatches();
		
		for(TableInstance t : tables) {
			System.out.println(t.toString());
		}
		System.out.println("Number of tables: " + tables.size());
//		
//		if(tables != null) {
//			return;
//		}
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_3]It took " + (endTime - startTime) + " milliseconds");
		//throw new Error();
	}
	
	
	public void test5_2_4()
	{
		long startTime = System.currentTimeMillis();
		SQLQueryCompletor.NESTED_CONDITION = true;
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_4/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_4/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_4/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_4/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_4]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_2_5()
	{
		long startTime = System.currentTimeMillis();
		
		SQLQueryCompletor.SEC_ORDER_FEA_CONDITION = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_5/suppliers");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_5/catalog");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_5/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println("Number of queries before validation: " + queries.size());
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_5]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	/**
	 * Here is the correct sql:
	 * select distinct c1.part_key, suppliers.sname
	 * from suppliers, catalog c1
	 * where suppliers.sid = c1.sid
	 *     and c1.cost = (select max(catalog.cost) from catalog where catalog.part_key=c1.part_key)
	 * */
	public void test5_2_6()
	{
		long startTime = System.currentTimeMillis();
		
		SQLQueryCompletor.SEC_ORDER_FEA_CONDITION = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_6/suppliers");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_6/catalog");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_6/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		System.out.println("Number of queries before validation: " + queries.size());
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_6]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void test5_2_7()
	{
		long startTime = System.currentTimeMillis();
		SQLQueryCompletor.NESTED_CONDITION = true;
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_7/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_7/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_7/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_7/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_7]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	
	public void test5_2_8()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_8/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_8/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_8/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_8/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_8]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test5_2_9()
	{
		long startTime = System.currentTimeMillis();
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_9/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_9/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_9/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_9/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_9]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void test5_2_10()
	{
		long startTime = System.currentTimeMillis();
		SQLQueryCompletor.NESTED_CONDITION = true;
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/5_2_10/parts");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/5_2_10/catalog");
		TableInstance input3 = TableInstanceReader.readTableFromFile("./dat/5_2_10/suppliers");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/5_2_10/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		inputs.add(input3);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		System.out.println("input 3:");
		System.out.println(input3);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.addInputTable(input3);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark 5_2_10]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void testForum_1() {
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/forum_questions/1/pedon");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/forum_questions/1/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark Forum_1]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void testForum_2() {
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/forum_questions/2/invoices");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/forum_questions/2/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark Forum_2]It took " + (endTime - startTime) + " milliseconds");
	}
	
	
	public void testForum_3() {
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/forum_questions/3/provider");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/forum_questions/3/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark Forum_3]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void testForum_5() {
		long startTime = System.currentTimeMillis();
		
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/forum_questions/5/vote");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/forum_questions/5/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		long endTime = System.currentTimeMillis();
        System.out.println("[Benchmark Forum_5]It took " + (endTime - startTime) + " milliseconds");
	}
	
	public void testExampleForPresentation()
	{
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/example_for_presentation/student");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/example_for_presentation/enroll");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/example_for_presentation/output");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		System.out.println("input 1:");
		System.out.println(input1);
		System.out.println("input 2:");
		System.out.println(input2);
		
		System.out.println("number of join columns: " + skeleton.getJoinPairNum());
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
	}
	
	public void test1() {
		TableInstance input1 = TableInstanceReader.readTableFromFile("./dat/id_name");
		TableInstance input2 = TableInstanceReader.readTableFromFile("./dat/id_salary");
		TableInstance output = TableInstanceReader.readTableFromFile("./dat/id_name_salary_full");
		
		Collection<TableInstance> inputs = new LinkedList<TableInstance>();
		inputs.add(input1);
		inputs.add(input2);
		SQLSkeletonCreator creator = new SQLSkeletonCreator(inputs, output);
		SQLSkeleton skeleton = creator.inferSQLSkeleton();
		
		SQLQueryCompletor completor = new SQLQueryCompletor(skeleton);
		completor.addInputTable(input1);
		completor.addInputTable(input2);
		completor.setOutputTable(output);
		
		
		List<SQLQuery> queries = completor.inferSQLQueries();
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		DbConnector.NO_ORDER_MATCHING = true;
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		//rank it
		queries = SQLQueryRanker.rankSQLQueries(queries);
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		
		//add the not-exist stmt, meaningless code below
		for(SQLQuery q : queries) {
			q.setNotExistStmt(new NotExistStmt(SQLQuery.clone(q)));
			System.out.println(q.toSQLString());
		}
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
		
		AggregateExprInfer aggInfer = new AggregateExprInfer(completor);
		Map<Integer, List<AggregateExpr>> aggrExprs = aggInfer.inferAggregationExprs();
		List<TableColumn> groupbyColumns = aggInfer.inferGroupbyColumns();
		
		//create SQL statements
		
		List<SQLQuery> queries = new LinkedList<SQLQuery>();
		queries.addAll(completor.constructQueries(skeleton, aggrExprs, groupbyColumns));
		
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
		DbConnector.NO_ORDER_MATCHING = true;
		queries = completor.validateQueriesOnDb(queries);
		//after validating on my sql
		System.out.println("The final output....");
		for(SQLQuery q : queries) {
			System.out.println(q.toSQLString());
		}
	}
	
	private void outputQueries(List<SQLQuery> queries) {
		for(SQLQuery q : queries) {
			if(SQLQueryCompletor.NESTED_CONDITION) {
				System.out.println(q.toNestedSQLString());
			} else {
			    System.out.println(q.toSQLString());
			}
		}
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
