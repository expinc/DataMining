package horus.datamining;

import java.time.DayOfWeek;
import java.time.LocalDate;
import horus.datamining.env.Environment;
import horus.datamining.env.EnvironmentImpl;
import horus.datamining.model.*;
import horus.datamining.model.feature.FeatureVector;


public final class Test
{
	public static void main(String[] args) throws Exception
	{
		testSaleQuantityPrediction();
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
	
	
	private static void testSaleCommentPrediction() throws Exception
	{
		Environment environment = new EnvironmentImpl();
		environment.setModelPath("D:/my-git/data-mining/DataMining/models/");

		Model model = new SaleCommentsPrediction(environment);

		LocalDate date = LocalDate.of(2017, 4, 1);
		LocalDate endDate = LocalDate.of(2017, 12, 31);
		while (!date.isAfter(endDate))
		{
			FeatureVector featureVector = model.createFeatureVector();
			featureVector.setValue("Year", date.getYear());
			featureVector.setValue("DayOfYear", date.getDayOfYear());

			Suggestion suggestion = model.solve(featureVector);
			System.out.println(suggestion.getFieldValue("Comments"));
			date = date.plusDays(1);
		}
	}
	
	
	private static void testSaleQuantityPrediction() throws Exception
	{
		Environment environment = new EnvironmentImpl();
		environment.setModelPath("D:/my-git/data-mining/DataMining/models/");

		Model commentsModel = new SaleCommentsPrediction(environment);
		Model quantityModel = new SaleQuantityPrediction(environment);

		LocalDate date = LocalDate.of(2017, 4, 1);
		LocalDate endDate = LocalDate.of(2017, 12, 31);
		while (!date.isAfter(endDate))
		{
			FeatureVector featureVector = commentsModel.createFeatureVector();
			featureVector.setValue("Year", date.getYear());
			featureVector.setValue("DayOfYear", date.getDayOfYear());
			Suggestion suggestion = commentsModel.solve(featureVector);
			
			featureVector = quantityModel.createFeatureVector();
			featureVector.setValue("Year", date.getYear());
			featureVector.setValue("Month", date.getMonthValue());
			featureVector.setValue("Day", date.getDayOfMonth());
			int dayOfWeek = date.getDayOfWeek().getValue() % DayOfWeek.SUNDAY.getValue();
			featureVector.setValue("WeekDay", dayOfWeek);
			featureVector.setValue("Customer", 1);	// just C0001
			featureVector.setValue("Comments", suggestion.getFieldValue("Comments"));
			featureVector.setValue("Price", 6.0);	// the major one
			featureVector.setValue("StockQuantity", 3305);	// average of 2017
			
			suggestion = quantityModel.solve(featureVector);
			System.out.println(suggestion.getFieldValue("SalesQuantity"));
			date = date.plusDays(1);
		}
	}
}
