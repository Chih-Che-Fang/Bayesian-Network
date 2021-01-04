package inferenceNetwork;

import index.Index;

public class OrNode extends BeliefNode {
	public OrNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Double score(int _docId) {
		double score = 0.0;
		for(QueryNode node : children) {
			score += Math.log(1 - Math.exp(node.score(_docId)));
		}
		return Math.log(1 - Math.exp(score));
	}
}
