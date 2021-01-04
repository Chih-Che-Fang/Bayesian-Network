package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;
import index.Posting;
import index.PostingList;
import retrieval.Dirichlet;
import retrieval.RetrievalModel;

public abstract class ProximityNode implements QueryNode {
	protected Index index;
	protected int docId = -1;
	protected int cur_child = -1;
	protected RetrievalModel model = null;
	static public final int mu = 1500;
	protected PostingList p = null;
	
	public ProximityNode(Index _index){
		this.index = _index;
		model = new Dirichlet(this.index, mu);
		p = new PostingList();
	}
	
	public Double score(int _docId) {
		p.skipTo(_docId);
		Posting post = p.getCurrentPosting();
		int tf = 0;
		if (post != null && post.getDocId() == _docId) tf = post.getTermFreq();
		return model.scoreOccurrence(tf, p.termFrequency(), index.getDocLength(_docId));
	}
	
	
	public void skipTo(int _docId) {
		docId = _docId;
		p.skipTo(_docId);
	}
	
	public int nextCandidate() {
		Posting post = p.getCurrentPosting();
		return (post == null)? -1 : post.getDocId();
	}
	
	public boolean hasMore() {
		return p.hasMore();
	}
	
	public PostingList getPostingList() {
		return p;
	}
}
