package horus.datamining.model;

import horus.datamining.env.Environment;
import horus.datamining.model.feature.FeatureVector;


public class SaleQuantityPrediction extends AbstractLearningModel
{

	public SaleQuantityPrediction(Environment environment) throws Exception
	{
		super(environment);
	}


	@Override
	protected String getName()
	{
		return "SaleQuantityPrediction";
	}


	@Override
	protected void amendSuggestion(Suggestion suggestion, FeatureVector featureVector)
	{
		int stockQuantity = (int) featureVector.getValue("StockQuantity");
		double saleQuantity = (double) suggestion.getFieldValue("SalesQuantity");
		if (saleQuantity > stockQuantity)
			suggestion.setFieldValue("SalesQuantity", (double) stockQuantity);
	}
}
