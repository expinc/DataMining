package horus.datamining;

public interface Model
{
	public Suggestion solve(Environment environment, FeatureVector featureVector) throws Exception;


	public FeatureVector createFeatureVector();
}
