package horus.datamining.model;

import horus.datamining.env.Environment;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


abstract class AbstractLearningModel extends AbstractModel
{
	Classifier classifier;
	Instances featureSchema;


	public AbstractLearningModel(Environment environment) throws Exception
	{
		super(environment);
		classifier = (Classifier) weka.core.SerializationHelper.read(environment.getModelPath() + getModelFile());
		featureSchema = DataSource.read(environment.getModelPath() + getFeatureSchemaFile());
	}


	protected String getModelFile()
	{
		return getName() + ".model";
	}


	protected String getFeatureSchemaFile()
	{
		return getName() + ".arff";
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


	@Override
	protected Object doubleToSuggestionField(String name, double value)
	{
		Attribute classAttribute = featureSchema.attribute(featureSchema.numAttributes() - 1);
		if (name.equals(classAttribute.name()))
			return new Double(value);
		else
			return null;
	}


	@Override
	protected Suggestion createSuggestion()
	{
		Attribute classAttribute = featureSchema.attribute(featureSchema.numAttributes() - 1);
		SuggestionImpl suggestion = new SuggestionImpl();
		suggestion.addField(classAttribute.name());
		return suggestion;
	}
}
