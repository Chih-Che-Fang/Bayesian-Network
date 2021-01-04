package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;
import index.Posting;
import index.PostingList;

public class UnorderedWindowNode extends WindowNode {

	public UnorderedWindowNode(String query, Index index) {
		super(query, index);
	}

	@Override
	public Posting selectWindow(int docId, Posting[] posts) {
		window = 3 * children.size();
		int num_children = children.size();
		int[] termIndex = new int[num_children];
		Posting newPost = null;
		
		while(true) {
			boolean hasMore = true;
			int chosenTerm = -1;
			int minPos = Integer.MAX_VALUE;
			for(int i = 0; i < num_children; ++i) {
				Integer[] posArray = posts[i].getPositionsArray();
				if(termIndex[i] < posArray.length) { 
					if(posArray[termIndex[i]] < minPos) {						
						minPos = posArray[termIndex[i]];
						chosenTerm = i;
					}
				}else {
					hasMore = false;
					break;
				}
			}
			
			
			if(!hasMore) break;
			++termIndex[chosenTerm];
			
			List<Integer> candidate = new ArrayList();
			candidate.add(minPos);
			
			boolean foundPair = true;
			for(int i = 0; i < num_children; ++i) {
				if(i != chosenTerm) {
					Integer[] posArray = posts[i].getPositionsArray();
					if(posArray[termIndex[i]] >= (minPos + this.window)) {
						foundPair = false;
						break;
					}else {
						++termIndex[i];
					}
				}	
			}
			
			if(foundPair) {
				if(newPost == null) {
					newPost = new Posting(docId, minPos);
				}else {
					newPost.add(minPos);
				}
			}
		}
		return newPost;
	}

}
