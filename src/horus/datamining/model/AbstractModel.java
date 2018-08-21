package horus.datamining.model;

import java.util.List;
import horus.datamining.env.Environment;
import horus.datamining.model.feature.FeatureVector;
import horus.datamining.model.feature.FeatureVectorImpl;
import horus.datamining.model.feature.FeatureVectorImpl.Feature;
import weka.core.Instances;


abstract class AbstractModel implements Model
{
	Environment environment;


	public AbstractModel(Environment environment)
	{
		this.environment = environment;
	}


	abstract protected String getName();


	@Override
	public FeatureVector createFeatureVector()
	{
		List<Feature> features = getFeatures();
		FeatureVector featureVector = new FeatureVectorImpl(features);
		return featureVector;
	}


	abstract protected List<Feature> getFeatures();


	@Override
	public Suggestion solve(FeatureVector featureVector) throws Exception
	{
		if (null == featureVector)
			return null;

		Instances instances = featureVector.toWekaInstances();
		return solveInstances(instances);
	}


	abstract protected Suggestion solveInstances(Instances instances);


	abstract protected Suggestion createSuggestion();


	abstract protected Object doubleToSuggestionField(String name, double value);
}
