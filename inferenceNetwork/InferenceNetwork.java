package inferenceNetwork;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import index.Posting;
import index.PostingList;

public class InferenceNetwork {
	public InferenceNetwork() {
		
	}
	//Retrieve top-k documents
	public List<Map.Entry<Integer, Double>> retrieveQuery(QueryNode qNode, int k) {
		PriorityQueue<Map.Entry<Integer, Double>> result = 
				new PriorityQueue<>(Map.Entry.<Integer, Double>comparingByValue());

		while(qNode.hasMore()) {
			int docId = qNode.nextCandidate();
			qNode.skipTo(docId);
			Double score = qNode.score(docId);
			result.add(new AbstractMap.SimpleEntry<Integer, Double>(docId, score));
			// trim the queue if necessary
			if (result.size() > k) result.poll();
			qNode.skipTo(docId + 1);
		}
		
		// reverse the queue
		ArrayList<Map.Entry<Integer, Double>> scores = new ArrayList<Map.Entry<Integer, Double>>();
		scores.addAll(result);
		scores.sort(Map.Entry.<Integer, Double>comparingByValue(Comparator.reverseOrder()));
		return scores;
	}
}
