package inferenceNetwork;

import index.Index;
import index.Posting;
import index.PostingList;

public class TermNode extends ProximityNode {
	public String term = "";
	
	public TermNode(Index index, String _term) {
		super(index);
		term = _term;
		p = index.getPostings(_term);
		p.startIteration();
	}
}
