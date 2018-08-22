package horus.datamining.model;

import horus.datamining.env.Environment;


public class ProfitPrediction extends AbstractLearningModel
{

	public ProfitPrediction(Environment environment) throws Exception
	{
		super(environment);
	}


	@Override
	protected String getName()
	{
		return "ProfitPrediction";
	}

}
