package horus.datamining.model;

import horus.datamining.model.feature.FeatureVector;
import weka.core.Instances;


abstract class AbstractModel implements Model
{
	@Override
	public Suggestion solve(FeatureVector featureVector) throws Exception
	{
		if (null == featureVector)
			return null;

		Instances instances = featureVector.toWekaInstances();
		return solveInstances(instances);
	}


	abstract protected Suggestion solveInstances(Instances instances);


	@Override
	abstract public FeatureVector createFeatureVector();


	abstract protected Object doubleToSuggestionField(String name, double value);
}
