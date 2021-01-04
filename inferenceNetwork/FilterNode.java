package inferenceNetwork;

public abstract class FilterNode implements QueryNode {
	ProximityNode pNode;
	QueryNode qNode;
	int docId = -1;
	
	public FilterNode(ProximityNode _pNode, QueryNode _qNode) {
		pNode = _pNode;
		qNode = _qNode;
	}

	@Override
	public abstract Double score(int _docId);

	@Override
	public void skipTo(int _docId) {
		pNode.skipTo(_docId);
		qNode.skipTo(_docId);
		docId = _docId;
	}

	@Override
	public abstract int nextCandidate();

	@Override
	public abstract boolean hasMore();
	


}
