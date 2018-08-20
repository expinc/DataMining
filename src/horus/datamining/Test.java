package horus.datamining;

import horus.datamining.model.*;
import horus.datamining.model.feature.FeatureVector;


public final class Test
{
	public static void main(String[] args) throws Exception
	{
		// Set environment: set path that contains .model and .arff files
		Environment environment = new EnvironmentImpl();
		environment.setModelPath("D:/my-git/data-mining/DataMining/models/");
		
		// Create model accordingly
		Model model = new PurchasePricePrediction(environment);
		
		// Create feature vector
		// Schema could be found in corresponding .arff files
		// Typically, the last feature is the result feature
		FeatureVector featureVector = model.createFeatureVector();
		featureVector.setValue("Year", 2017);
		featureVector.setValue("DayOfYear", 91);
		
		// Solve the feature vector by the model
		// Suggestion contains several fields
		// Typically the last feature of the feature schema
		Suggestion suggestion = model.solve(featureVector);
		System.out.println(suggestion.getFieldValue("Price"));
	}
}
