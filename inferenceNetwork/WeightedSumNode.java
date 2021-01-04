package inferenceNetwork;

import java.util.ArrayList;
import java.util.List;

import index.Index;

public class WeightedSumNode extends BeliefNode {
	protected List<Double> weights;
	public WeightedSumNode(String query, Index index) {
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
		double total_weight = 0.0;
		for(int i = 0; i < children.size(); ++i) {
			score += (weights.get(i)* Math.exp(children.get(i).score(_docId)));
			total_weight += weights.get(i);
		}
		return  Math.log(score / total_weight);
	}
}
