package horus.datamining.model;

import horus.datamining.Environment;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;


abstract class AbstractLearningModel extends AbstractModel
{
	Classifier classifier;


	public AbstractLearningModel(Environment environment) throws Exception
	{
		super(environment);
		classifier = (Classifier) weka.core.SerializationHelper.read(environment.getModelPath() + getModelFile());
	}


	@Override
	protected Suggestion solveInstances(Instances instances)
	{
		Suggestion suggestion = null;

		try
		{
			double predict = classifier.classifyInstance(instances.get(0));
			suggestion = createSuggestion();
			Attribute classAttribute = instances.attribute(instances.numAttributes() - 1);
			String classAttributeName = classAttribute.name();
			suggestion.setFieldValue(classAttributeName, doubleToSuggestionField(classAttributeName, predict));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return suggestion;
	}


	abstract protected String getModelFile();
}
