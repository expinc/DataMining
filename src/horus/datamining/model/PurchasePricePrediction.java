package horus.datamining.model;

import java.util.*;
import horus.datamining.Environment;
import horus.datamining.model.feature.FeatureType;
import horus.datamining.model.feature.FeatureVectorImpl;
import horus.datamining.model.feature.FeatureVectorImpl.Feature;
import weka.core.Attribute;


public class PurchasePricePrediction extends AbstractLearningModel
{

	public PurchasePricePrediction(Environment environment) throws Exception
	{
		super(environment);
	}


	@Override
	protected String getName()
	{
		return "PurchasePricePrediction";
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


	@Override
	protected String getModelFile()
	{
		return getName() + ".model";
	}


	@Override
	protected String getFeatureSchemaFile()
	{
		return getName() + ".arff";
	}

}
