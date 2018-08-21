package horus.datamining;

import java.time.DayOfWeek;
import java.time.LocalDate;
import horus.datamining.env.Environment;
import horus.datamining.env.EnvironmentImpl;
import horus.datamining.model.*;
import horus.datamining.model.feature.FeatureVector;


public final class Sample
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
		LocalDate date = LocalDate.of(2017, 4, 1);
		featureVector.setValue("Year", date.getYear());
		featureVector.setValue("Month", date.getMonthValue());
		featureVector.setValue("Day", date.getDayOfMonth());
		int dayOfWeek = date.getDayOfWeek().getValue() % DayOfWeek.SUNDAY.getValue();
		featureVector.setValue("WeekDay", dayOfWeek);
		System.out.println("Feature Vector" +
				": Year - " + date.getYear() +
				", Month - " + date.getMonthValue() +
				", Day - " + date.getDayOfMonth() +
				", WeekDay - " + dayOfWeek);
		
		
		// Solve the feature vector by the model
		// Suggestion contains several fields
		// Typically the last feature of the feature schema
		Suggestion suggestion = model.solve(featureVector);
		System.out.println("Predicted price: " + suggestion.getFieldValue("Price"));
	}
}
