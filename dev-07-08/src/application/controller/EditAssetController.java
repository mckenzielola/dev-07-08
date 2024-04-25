package application.controller;

import application.Category;
import application.Location;
import application.Asset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class EditAssetController 
{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField asset_name;
	@FXML private TextField cost;
	@FXML private ComboBox<String> categoryType;
	@FXML private ComboBox<String> locationType;
	@FXML private DatePicker purchase_date;
	@FXML private DatePicker warranty_date;
	@FXML private TextArea asset_descr;
	@FXML private Label result_message;
	
	//initialize id for the EditAsset.fxml's AnchorPane //use for visibility
	@FXML private AnchorPane editContainer;
	private DataAccessLayer DAL = new DataAccessLayer();
	//create pre-existing Asset
	private Asset preAsset;
	
	
	
	public void initialize()
	{
		//populate hashmaps with existing data from csv files
		DAL.storeAssetsFromFile();
		DAL.storeCategoriesFromFile();
		DAL.storeLocationsFromFile();
		
		//store Category and Location hashmaps into their own variables
		HashMap<String, Category> categoriesMap = DAL.getCategoriesMap();
		HashMap<String, Location> locationsMap = DAL.getLocationsMap();


		//initialize Lists and store the hashmaps into them to be used for comboboxes
		List<String> categoryNames = new ArrayList<>(categoriesMap.keySet());
		List<String> locationNames = new ArrayList<>(locationsMap.keySet());
		
		//populate comboboxes with Categories and Locations
		categoryType.getItems().addAll(categoryNames);
		locationType.getItems().addAll(locationNames);
	
	}
	
	
	//changes the visibility of EditAsset.fxml's AnchorPane
	public void displayEditContainer()
	{
		//set the visibility of the EditAsset.fxml's AnchorPane to be visible
		editContainer.setVisible(!editContainer.isVisible());	
    } 
	
	void storeAssetToEdit(Asset asset)
	{
		//initialize and store pre-existing Asset that will be edited
		preAsset = asset;
		
		//Prompt all established Asset values 
		categoryType.setPromptText(asset.getCategory());
		locationType.setPromptText(asset.getLocation());
		purchase_date.setPromptText(asset.getPurchDate());
		warranty_date.setPromptText(asset.getExpDate());
		asset_descr.setPromptText(asset.getDescription());
		asset_name.setPromptText(asset.getAssetName());
		cost.setPromptText(asset.getPurchVal());
		
	}
	
	@FXML public void saveAssetOp()
	{
		//get text from the original Asset, use this data if user does not edit attributes
		String assetName = preAsset.getAssetName();
		String category = preAsset.getCategory();
		String location = preAsset.getLocation();
		String purchdate = preAsset.getPurchDate();
		String descr = preAsset.getDescription();
		String purchval = preAsset.getPurchVal();
		String expdate = preAsset.getExpDate();

		//if user filled out the asset name
		if(!asset_name.getText().equals(""))
		{
			assetName = asset_name.getText();
		}
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
		
		else
		{
			//create asset object and store name
			Asset newAsset = new Asset(assetName, category, location, purchdate, descr, purchval, expdate);
					

			//call editAssetData to delete the pre-existing Asset and add the newAsset
			int result = DAL.editAssetData(preAsset, newAsset);
			
			// check the result of addAsset
			switch (result) 
			{
				case 0:
					// if addAsset returns 0, display success message
					result_message.setText("Asset edited and added successfully!");
					break;

				case 1:
					// if addAsset returns 1, display Asset exists error
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
