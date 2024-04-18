package application.controller;

import application.Asset;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class SearchAssetController
{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField search_term;
	@FXML private Label result_message;
	private DataAccessLayer DAL = new DataAccessLayer();

	public void initialize() {
		DAL.storeAssetsFromFile();
	}

	@FXML public void searchAssetOp() 
	{	
		String result = DAL.searchAsset(search_term.getText());
		if (result.equals(""))
		{
			result_message.setText("No results found!");
		}
		else {
			result_message.setText(result + " found!");
		}
	}
}
