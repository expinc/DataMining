package horus.datamining;

import java.time.DayOfWeek;
import java.time.LocalDate;
import horus.datamining.env.Environment;
import horus.datamining.env.EnvironmentImpl;
import horus.datamining.model.Model;
import horus.datamining.model.PurchasePricePrediction;
import horus.datamining.model.Suggestion;
import horus.datamining.model.feature.FeatureVector;
import horus.datamining.wrapper.PurchasePricePredictionWrapper;


public final class Test
{
	public static void main(String[] args) throws Exception
	{
		testPurchasePricePrediction();
	}
	
	
	private static void testPurchasePricePrediction() throws Exception
	{
		Environment environment = new EnvironmentImpl();
		environment.setModelPath("D:/my-git/data-mining/DataMining/models/");

		Model model = new PurchasePricePrediction(environment);

		LocalDate date = LocalDate.of(2017, 4, 1);
		LocalDate endDate = LocalDate.of(2017, 12, 31);
		while (!date.isAfter(endDate))
		{
			FeatureVector featureVector = model.createFeatureVector();
			featureVector.setValue("Year", date.getYear());
			featureVector.setValue("Month", date.getMonthValue());
			featureVector.setValue("Day", date.getDayOfMonth());
			int dayOfWeek = date.getDayOfWeek().getValue() % DayOfWeek.SUNDAY.getValue();
			featureVector.setValue("WeekDay", dayOfWeek);

			Suggestion suggestion = model.solve(featureVector);
			System.out.println(suggestion.getFieldValue("Price"));
			date = date.plusDays(1);
		}
	}
}
