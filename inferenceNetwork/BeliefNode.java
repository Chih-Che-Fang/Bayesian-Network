package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;

public abstract class BeliefNode implements QueryNode{
	protected Index index;
	protected List<QueryNode> children;
	protected int docId = -1;
	protected int cur_child = -1;
	//String query;
	public BeliefNode(String query, Index _index){
		//this.query = _query;
		this.index = _index;
		children = new ArrayList();
		for(String term : query.split("\\s+")) {
			children.add(new TermNode(index, term));
		}
	}
	
	public abstract Double score(int _docId);
	
	public void skipTo(int _docId) {
		this.docId = _docId;
		//System.out.println("Skip TO");
		for(QueryNode node : children) {
			node.skipTo(_docId);
		}
	}
	
	public int nextCandidate() {
		int candidate = Integer.MAX_VALUE;
		for(QueryNode node : children) {
			int cand = node.nextCandidate();
			if(cand != -1) candidate = Math.min(candidate, node.nextCandidate());
		}
		return candidate;
	}
	
	public boolean hasMore() {
		for(QueryNode node : children) {
			if(node.hasMore()) return true;
		}
		return false;
	}
}
