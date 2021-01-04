package inferenceNetwork;
import index.Index;

public class AndNode extends BeliefNode {
	public AndNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Double score(int _docId) {
		double score = 0.0;
		for(QueryNode node : children) {
			score += node.score(_docId);
		}
		return Math.log(Math.exp(score));
	}
}
