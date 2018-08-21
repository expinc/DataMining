package horus.datamining.model;

import java.util.*;
import horus.datamining.env.Environment;
import horus.datamining.model.feature.FeatureType;
import horus.datamining.model.feature.FeatureVectorImpl;
import horus.datamining.model.feature.FeatureVectorImpl.Feature;
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
	
	
	@Override
	protected List<Feature> getFeatures()
	{
		List<Feature> features = new LinkedList<Feature>();
		for (int i = 0; i < featureSchema.numAttributes(); ++i)
		{
			Attribute attribute = featureSchema.attribute(i);
			FeatureVectorImpl.Feature feature = new FeatureVectorImpl.Feature();

			feature.name = attribute.name();
			int attributeType = attribute.type();
			if (Attribute.NUMERIC == attributeType)
			{
				feature.type = FeatureType.NUMERIC;
				feature.value = 0.0;
			}
			else if (Attribute.NOMINAL == attributeType)
			{
				feature.type = FeatureType.NOMINAL;
				feature.validValues = new ArrayList<String>();
				for (int j = 0; j < attribute.numValues(); ++i)
				{
					feature.validValues.add(attribute.value(j));
				}
				feature.value = feature.validValues.get(0);
			}

			features.add(feature);
		}
		return features;
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
