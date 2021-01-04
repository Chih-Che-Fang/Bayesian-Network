package inferenceNetwork;

import index.Index;

public class MaxNode extends BeliefNode {
	public MaxNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Double score(int _docId) {
		double score = -Double.MAX_VALUE;
		for(QueryNode node : children) {
			//System.out.print(node.score(_docId) + " ");
			score = Math.max(score, node.score(_docId));
		}
		System.out.println("Score:" + score);
		return Math.log(Math.exp(score));
	}
}
