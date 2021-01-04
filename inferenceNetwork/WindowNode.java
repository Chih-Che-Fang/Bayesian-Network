package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;
import index.Posting;
import index.PostingList;

public abstract class WindowNode extends ProximityNode {
	protected List<ProximityNode> children;
	int collectionFrequency = 0;
	int window;
	
	public WindowNode(String query, Index index) {
		super(index);
		children = new ArrayList();
		for(String term : query.split("\\s+")) {
			children.add(new TermNode(index, term));
		}
		buildPostingList();
		p.startIteration();
	}
	
	public void buildPostingList() {
		int num_children = children.size();
		PostingList[] lists = new PostingList[num_children];
		for(int i = 0; i < num_children; ++i) {
			lists[i] = children.get(i).getPostingList();
			lists[i].startIteration();
		}
		
		while(lists[0].hasMore()) {
			Posting[] posts = new Posting[num_children]; 
			Posting a = lists[0].getCurrentPosting();
					
			int docId = a.getDocId();
			boolean hasNextDoc = true;
			posts[0] = a;
					
			for(int i = 1; i < num_children; ++i) {
	    		lists[i].skipTo(docId);
	    		Posting b = lists[i].getCurrentPosting();
	    		if(b == null || b.getDocId() != docId) {
	    			hasNextDoc = false;
	    			break;
	    		}
	    		posts[i] = b;
			}

			if(hasNextDoc) {
				Posting newPost = selectWindow(docId, posts);
				if(newPost != null) {
					p.add(newPost);
					collectionFrequency += newPost.getTermFreq();
				}
			}
			lists[0].skipTo(docId + 1);
		}
	}
	
	public abstract Posting selectWindow(int docId, Posting[] posts);
}
