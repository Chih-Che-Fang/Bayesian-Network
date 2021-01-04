package inferenceNetwork;

public class RequireNode extends FilterNode {

	public RequireNode(ProximityNode _pNode, QueryNode _qNode) {
		super(_pNode, _qNode);
		
	}
	
	@Override
	public Double score(int _docId) {
		return (pNode.nextCandidate() == qNode.nextCandidate())? qNode.score(_docId) : null;
	}

	@Override
	public int nextCandidate() {
		return pNode.nextCandidate();
	}

	@Override
	public boolean hasMore() {
		return pNode.hasMore();
	}
}
