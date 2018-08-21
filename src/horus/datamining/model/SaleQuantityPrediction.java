package horus.datamining.model;

import horus.datamining.env.Environment;


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

}
