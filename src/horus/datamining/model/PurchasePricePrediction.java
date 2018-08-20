package horus.datamining.model;

import java.util.*;
import horus.datamining.Environment;
import horus.datamining.model.feature.FeatureType;
import horus.datamining.model.feature.FeatureVectorImpl;
import horus.datamining.model.feature.FeatureVectorImpl.Feature;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;


public class PurchasePricePrediction extends AbstractLearningModel
{

	public PurchasePricePrediction(Environment environment) throws Exception
	{
		super(environment);
	}


	@Override
	protected String getModelFile()
	{
		return getName() + ".model";
	}


	@Override
	protected List<Feature> getFeatures()
	{
		Instances schema = null;
		try
		{
			schema = DataSource.read(environment.getModelPath() + getName() + ".arff");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}

		List<Feature> features = new LinkedList<Feature>();
		for (int i = 0; i < schema.numAttributes(); ++i)
		{
			Attribute attribute = schema.attribute(i);
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


	@Override
	protected String getName()
	{
		return "PurchasePricePrediction";
	}
	
	
	@Override
	protected Object doubleToSuggestionField(String name, double value)
	{
		if (name.equals("Price"))
			return new Double(value);
		else
			return null;
	}


	@Override
	protected Suggestion createSuggestion()
	{
		SuggestionImpl suggestion = new SuggestionImpl();
		suggestion.addField("Price");
		return suggestion;
	}

}
