package inferenceNetwork;

public interface QueryNode {
	public Double score(int _docId);
	
	public void skipTo(int _docId);
	
	public int nextCandidate();
	
	public boolean hasMore();
}
