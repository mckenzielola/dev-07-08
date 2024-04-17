package application.controller;

import application.Category;
import application.Location;
import application.CreateAsset;

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
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();
		
		List<String> categoryNames = new ArrayList<>(categoriesMap.keySet());
		List<String> locationNames = new ArrayList<>(locationsMap.keySet());
		
		categoryType.setPromptText("Select a Category...");
		locationType.setPromptText("Select a Location..");
		
		categoryType.getItems().addAll(categoryNames);
		locationType.getItems().addAll(locationNames);
		//selectedCategory = categoryType.getValue();
	}

	@FXML public void saveAssetOp() 
	{	
		//get text 
		String asset = asset_name.getText();
		String purchdate = purchase_date.toString();
		String expdate = warranty_date.toString();
		String descr = asset_descr.getText();
		
		
				
		//check if the asset name field is left blank
		if(asset.length() == 0)
		{
			//display error message for blank category name
			result_message.setText("Asset name can not be blank!");
		}
	
		else
		{
			//display message for category being added
			result_message.setText("Asset added successfully!");
					
			//instantiate CreateAsset object and store name
			//CreateAsset Casset = new CreateAsset(asset, purchdate, expdate, descr,  );
					
		
		}
	}

}
