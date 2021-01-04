package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;

public class WeightedAndNode extends BeliefNode {
	protected List<Double> weights;
	public WeightedAndNode(String query, Index index) {
		super(query, index);
		weights = new ArrayList();
		createWeight();
	}
	
	public void createWeight() {
		for(int i = 0; i < children.size(); ++i) weights.add(1.0);
	}
	
	@Override
	public Double score(int _docId) {
		double score = 0.0;
		for(int i = 0; i < children.size(); ++i) {
			score +=  (weights.get(i) * children.get(i).score(_docId));
		}
		return Math.log(Math.exp(score));
	}
}
