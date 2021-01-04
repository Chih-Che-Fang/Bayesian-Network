package inferenceNetwork;

import index.Index;

public class NotNode extends BeliefNode {
	public NotNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Double score(int _docId) {
		return Math.log(1 - Math.exp(children.get(0).score(_docId)));
	}
}
