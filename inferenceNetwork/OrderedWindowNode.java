package inferenceNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import index.Index;
import index.Posting;
import index.PostingList;

public class OrderedWindowNode extends WindowNode {
	public OrderedWindowNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Posting selectWindow(int docId, Posting[] posts) {
		window = 1;
		int num_children = children.size();
		Integer [] aPos = posts[0].getPositionsArray();
		Posting newPost = null;
		for(Integer posA : aPos) {
			List<Integer> candidate = new ArrayList();
			candidate.add(posA);
			
			for(int i = 1; i < num_children; ++i) {
				boolean findPair = false;
				int lastPos = candidate.get(candidate.size() - 1);
				for(Integer posB : posts[i].getPositionsArray()) {
					if(posB == (lastPos + this.window) ) {
						candidate.add(posB);
						findPair = true;
						break;
					}
				}
				if(!findPair) break;
			}
			
			if(candidate.size() == num_children) {
				if(newPost == null) {
					newPost = new Posting(docId, posA);
				}else {
					newPost.add(posA);
				}
			}
		}
		return newPost;
	}
	
}
