package horus.datamining;

import horus.datamining.model.Model;
import horus.datamining.model.PurchasePricePrediction;
import horus.datamining.model.Suggestion;
import horus.datamining.model.feature.FeatureVector;


public final class Test
{
	public static void main(String[] args) throws Exception
	{
		Environment environment = new EnvironmentImpl();
		environment.setModelPath("D:/my-git/data-mining/DataMining/models/");
		Model model = new PurchasePricePrediction(environment);
		FeatureVector featureVector = model.createFeatureVector();
		featureVector.setValue("Year", 2017);
		featureVector.setValue("DayOfYear", 91);
		Suggestion suggestion = model.solve(featureVector);
		System.out.println(suggestion.getFieldValue("Price"));
	}
}
