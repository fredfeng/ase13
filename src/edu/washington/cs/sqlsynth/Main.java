package edu.washington.cs.sqlsynth;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.washington.cs.sqlsynth.algorithms.AggregateExprInfer;
import edu.washington.cs.sqlsynth.algorithms.SQLQueryCompletor;
import edu.washington.cs.sqlsynth.algorithms.SQLSkeletonCreator;
import edu.washington.cs.sqlsynth.db.DbConnector;
import edu.washington.cs.sqlsynth.entity.AggregateExpr;
import edu.washington.cs.sqlsynth.entity.SQLQuery;
import edu.washington.cs.sqlsynth.entity.SQLSkeleton;
import edu.washington.cs.sqlsynth.entity.TableColumn;
import edu.washington.cs.sqlsynth.entity.TableInstance;
import edu.washington.cs.sqlsynth.util.TableInstanceReader;
import edu.washington.cs.sqlsynth.util.TableUtils;

/**
 * The main entry of the SQL synthesis program
 * */
public class Main {
	public static void main(String[] args) {
		//fill in later
		if(args.length != 2) {
			System.out.println("Wrong parameters.");
			return;
		}
		System.out.println("my input:" + args[0]);
		System.out.println("my output:" + args[1]);

		long startTime = System.currentTimeMillis();
		TableUtils.USE_SAME_NAME_JOIN = true;
		TableUtils.JOIN_ALL_TABLES = true;
		DbConnector.NO_ORDER_MATCHING = true;
		
		TableInstance input1 = TableInstanceReader.readTableFromFile(args[0]);
		TableInstance output = TableInstanceReader.readTableFromFile(args[1]);
		
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
        System.out.println("Synthesis results=" + !queries.isEmpty());
	}
}
