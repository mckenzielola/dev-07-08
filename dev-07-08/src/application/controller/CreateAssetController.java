package application.controller;

import application.Category;
import application.Location;
import application.Asset;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class CreateAssetController {
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField asset_name;
	@FXML private TextField cost;
	@FXML private ComboBox<String> categoryType;
	@FXML private ComboBox<String> locationType;
	@FXML private DatePicker purchase_date;
	@FXML private DatePicker warranty_date;
	@FXML private TextArea asset_descr;
	@FXML private Label result_message;
	private DataAccessLayer DAL = new DataAccessLayer();
	//private String selectedCategory;
	
	
	public void initialize()
	{
		//store HashMaps with existing data from csv files
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		DAL.storeAssetsFromFile();
		
		//initialize Hashmaps for Categories and Locations
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();
		
		//set the HashMaps as Lists to populate the combobox
		List<String> categoryNames = new ArrayList<>(categoriesMap.keySet());
		List<String> locationNames = new ArrayList<>(locationsMap.keySet());
		
		//prompt Categories and Locations combobox with message
		categoryType.setPromptText("Select a Category...");
		locationType.setPromptText("Select a Location..");
		
		//populate Categories and Locations combobox with existing data
		categoryType.getItems().addAll(categoryNames);
		locationType.getItems().addAll(locationNames);
	}

	@FXML public void saveAssetOp() 
	{	
		//get text 
		String assetName = asset_name.getText();
		String category = "_EMPTY_";
		String location = "_EMPTY_";
		String purchdate = "_EMPTY_";
		String descr = "_EMPTY_";
		String purchval = "_EMPTY_";
		String expdate = "_EMPTY_";

		// if user filled out category
		if (categoryType.getValue() != null) {
			// set category to the value of the category
			category = categoryType.getValue();
		}
		// if user filled out location
		if (locationType.getValue() != null) {
			// set location to the value of the location
			location = locationType.getValue();
		}
		// if user filled out purchase date
		if (purchase_date.getValue() != null) {
			// set purchase date to the value of the purchase date
			purchdate = purchase_date.getValue().toString();
		}
		// if user filled out description
		if (!asset_descr.getText().equals("")) {
			// set description to the value of the description
			descr = asset_descr.getText();
		}
		// if user filled out cost
		if (!cost.getText().equals("")) {
			// set purchase value to the value of the cost
			purchval = cost.getText();
		}
		// if user filled out warranty date
		if (warranty_date.getValue() != null) {
			// set warranty date to the value of the warranty date
			expdate = warranty_date.getValue().toString();
		}
		
		//check if the asset name field is left blank
		if (assetName.length() == 0)
		{
			//display error message for blank category name
			result_message.setText("Asset name can not be blank!");
		}
		//check if the category name field is left blank
		else if (category.equals("_EMPTY_"))
		{
			//display error message for blank category name
			result_message.setText("Category can not be blank!");
		}
		//check if the location name field is left blank
		else if (location.equals("_EMPTY_"))
		{
			//display error message for blank location name
			result_message.setText("Location can not be blank!");
		}
		else
		{
			//create asset object and store name
			Asset newAsset = new Asset(assetName, category, location, purchdate, descr, purchval, expdate);
			
			//called addAsset function using location object and DAL object
			int result = DAL.addAsset(newAsset);
			
			// check the result of addAsset
			switch (result) {
				case 0:
				// if addAsset returns 0, display success message
				result_message.setText("Asset added successfully!");
				break;

				case 1:
				// if addAsset returns 1, display asset exists error
				result_message.setText("Asset " + assetName + " already exists!");
				break;

				case 2:
				// if addAsset returns 2, display no commas error
				result_message.setText("Commas (,) are not allowed in any field!");
				break;
			}
		}
	}

}
