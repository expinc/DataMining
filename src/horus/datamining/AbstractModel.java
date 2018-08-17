package horus.datamining;

import weka.core.Instances;


abstract class AbstractModel implements Model
{
	private String modelFile;
	// TODO: abstract initialization

	@Override
	public Suggestion solve(Environment environment, FeatureVector featureVector) throws Exception
	{
		if (null == environment || null == featureVector)
			return null;

		Instances instances = featureVector.toWekaInstances();
		return solveInstances(environment, instances);
	}


	abstract protected Suggestion solveInstances(Environment environment, Instances instances);


	@Override
	abstract public FeatureVector createFeatureVector();


	abstract protected Object doubleToSuggestionField(String name, double value);
}
