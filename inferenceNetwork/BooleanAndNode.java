package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;
import index.Posting;
import index.PostingList;

public class BooleanAndNode extends UnorderedWindowNode {

	public BooleanAndNode(String query, Index index) {
		super(query, index);
	}
	
	@Override
	public Posting selectWindow(int docId, Posting[] posts) {
		int minOccurance = 0;
		for(int i = 0; i < children.size(); ++i) {
			Integer[] posArray = posts[i].getPositionsArray();
			minOccurance = Math.min(minOccurance, posArray.length);
		}
		return new Posting(docId, minOccurance);
	}
}
