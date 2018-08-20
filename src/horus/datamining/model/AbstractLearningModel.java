package horus.datamining.model;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;


abstract class AbstractLearningModel extends AbstractModel
{
	Classifier classifier;


	public AbstractLearningModel(String modelPath) throws Exception
	{
		classifier = (Classifier) weka.core.SerializationHelper.read(modelPath + getModelFile());
	}


	@Override
	protected Suggestion solveInstances(Instances instances)
	{
		Suggestion suggestion = null;

		try
		{
			double predict = classifier.classifyInstance(instances.get(0));
			suggestion = new SuggestionImpl();
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
