package inferenceNetwork;

import index.Index;

public class SumNode extends BeliefNode {
	public SumNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Double score(int _docId) {
		double score = 0.0;
		for(QueryNode node : children) {
			score += Math.exp(node.score(_docId));
		}
		return Math.log(score / children.size());
	}
}
