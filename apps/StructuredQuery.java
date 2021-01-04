package apps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import index.Index;
import index.InvertedIndex;
import inferenceNetwork.AndNode;
import inferenceNetwork.BeliefNode;
import inferenceNetwork.InferenceNetwork;
import inferenceNetwork.MaxNode;
import inferenceNetwork.OrNode;
import inferenceNetwork.OrderedWindowNode;
import inferenceNetwork.ProximityNode;
import inferenceNetwork.QueryNode;
import inferenceNetwork.SumNode;
import inferenceNetwork.TermNode;
import inferenceNetwork.UnorderedWindowNode;
import inferenceNetwork.WindowNode;

public class StructuredQuery {

	public static void main(String[] args) {
		try {
			Index index = new InvertedIndex();
			int k = Integer.parseInt(args[0]);
			boolean compressed = Boolean.parseBoolean(args[1]);
			String querysFile = args[2];
			index.load(compressed);
			List<Map.Entry<Integer, Double>> results;
			PrintWriter outputWriter;
			BufferedReader reader = new BufferedReader(new FileReader(querysFile));
			String line;
			List <String> queries = new ArrayList<String>();
			while ((line = reader.readLine()) != null) {
				queries.add(line);
			}
			reader.close();
			
			//SUM operator
			String runid = "ccfang-sum-dir-1500";
			String outputFile = "sum.trecrun";
			int qNum = 1;
			InferenceNetwork iNtework = new InferenceNetwork();
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new SumNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
			//AND operator
			runid = "ccfang-and-dir-1500";
			outputFile = "and.trecrun";
			qNum = 1;
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new AndNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
			//Or operator
			runid = "ccfang-or-dir-1500";
			outputFile = "or.trecrun";
			qNum = 1;
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new OrNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
			//Max operator
			runid = "ccfang-max-dir-1500";
			outputFile = "max.trecrun";
			qNum = 1;
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new MaxNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
			//Ordered window operator
			runid = "ccfang-od1-dir-1500";
			outputFile = "od1.trecrun";
			qNum = 1;
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new OrderedWindowNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
			//Unordered window operator
			runid = "ccfang-uw-dir-1500";
			outputFile = "uw.trecrun";
			qNum = 1;
			outputWriter = new PrintWriter(outputFile);
			
			for (String query: queries) {
				results = iNtework.retrieveQuery(new UnorderedWindowNode(query, index), k);
				int rank = 1;
				for (Map.Entry<Integer, Double> res : results) {
					outputWriter.println("Q" + qNum + "\tskip\t" + index.getDocName(res.getKey()) +
							"\t" + rank + "\t" + res.getValue() + "\t" + runid);
					rank++;
				}
				qNum++;
			}
			outputWriter.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
