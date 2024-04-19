package application.controller;

import application.Asset;
import application.controller.EditAssetController;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;

import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SearchAssetController
{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField search_term;
	@FXML private Label result_message;
	@FXML private Label asset_name;
	@FXML private Label asset_category;
	@FXML private Label asset_location;
	@FXML private Button edit_button;
	@FXML private Button delete_button;
	@FXML private Button prev_button;
	@FXML private Label result_number;
	@FXML private Button next_button;

	private DataAccessLayer DAL = new DataAccessLayer();
	private ArrayList<Asset> results;
	private int resultsCount;
	private Asset currentAsset;
	private int currentAssetIndex;

	public void initialize() {
		DAL.storeAssetsFromFile();
		edit_button.setDisable(true);
		delete_button.setDisable(true);
		prev_button.setDisable(true);
		next_button.setDisable(true);
	}

	private void loadPageData() {
		currentAsset = results.get(currentAssetIndex);
		asset_name.setText(currentAsset.getAssetName());
		asset_category.setText(currentAsset.getCategory());
		asset_location.setText(currentAsset.getLocation());
		result_number.setText(Integer.toString(currentAssetIndex + 1));

		if (currentAssetIndex == 0)
		{
			prev_button.setDisable(true);
		}
		else
		{
			prev_button.setDisable(false);
		}

		if (resultsCount == currentAssetIndex + 1)
		{
			next_button.setDisable(true);
		}
		else
		{
			next_button.setDisable(false);
		}
	}

	@FXML public void searchAssetOp() 
	{	
		results = DAL.searchAsset(search_term.getText().toLowerCase());
		resultsCount = results.size();
		if (search_term.getText().length() == 0)
		{
			result_message.setText("Please type in a keyword.");
		}
		else if (resultsCount == 0)
		{
			result_message.setText("No results found!");
		}
		else {
			result_message.setText(resultsCount + " results found!");
			currentAssetIndex = 0;
			loadPageData();
			edit_button.setDisable(false);
			delete_button.setDisable(false);
		}
	}

	@FXML public void prevAssetOp() 
	{
		currentAssetIndex -= 1;
		loadPageData();
	}
	
	@FXML public void nextAssetOp() 
	{
		currentAssetIndex += 1;
		loadPageData();
	}

	@FXML public void editAssetOp() 
	{
		//implement try block in case of file exceptions for the EditAsset FXML file
		try 
		{
			//save the current Asset object into currentAsset
			currentAsset = results.get(currentAssetIndex);
		   
			//get the loader for the EditAsset file to prompt the UI
		    FXMLLoader editLoader = new FXMLLoader(getClass().getClassLoader().getResource("view/EditAsset.fxml"));
		    
		    //create AnchorPane for the EditAsset FXML file
		    AnchorPane editPane = editLoader.load();
		    
		    //instantiate EditAssetController object 
			EditAssetController editController = editLoader.getController();
			//save the Asset object
			editController.saveAssetObject(currentAsset);

			//instantiate a Scene object to be used to create a window for the Edit page
			Scene editScene = new Scene(editPane);
			
			//instantiate Stage object to load the window and prompt the user 
		    Stage primaryStage = new Stage();
		    primaryStage.setScene(editScene);
		    
		    //call the show function to display the editAsset window
		    primaryStage.show();
		} 
		//catch block to catch any exceptions if the file directory is not found
		catch (IOException e) {
		    e.printStackTrace();
		}

	}

	@FXML public void deleteAssetOp() 
	{
		// need to implement deletion of asset
		// can use currentAsset to get asset's data
	}
}
