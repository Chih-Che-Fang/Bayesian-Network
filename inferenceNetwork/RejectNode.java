package inferenceNetwork;

public class RejectNode extends FilterNode {
	
	public RejectNode(ProximityNode _pNode, QueryNode _qNode) {
		super(_pNode, _qNode);
	}

	@Override
	public Double score(int _docId) {
		return (pNode.nextCandidate() != qNode.nextCandidate())? qNode.score(_docId) : null;
	}

	@Override
	public int nextCandidate() {
		return qNode.nextCandidate();
	}

	@Override
	public boolean hasMore() {
		while(qNode.hasMore()) {
			int qNode_nextCandidate = qNode.nextCandidate();
			pNode.skipTo(qNode_nextCandidate);
			if(!pNode.hasMore() || (pNode.nextCandidate() != qNode.nextCandidate()) ) break;
			qNode.skipTo(qNode_nextCandidate + 1);
		}
		return qNode.hasMore();
	}

}
