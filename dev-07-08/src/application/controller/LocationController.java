package application.controller;

import application.Location;
import data_access_layer.DataAccessLayer;
import application.CommonObjs;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

public class LocationController {
	
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField location_name;
	@FXML private TextArea location_description;
	@FXML private Label result_message;
	private DataAccessLayer DAL = new DataAccessLayer();
	
	@FXML public void saveLocationOp() 
	{	
		//get text
		String location = location_name.getText();
		String description = "_EMPTY_";
		
		if (!location_description.getText().equals(""))
		{
			description = location_description.getText();
		}
		
		//check if the location name field is left blank or not
		if(location.length() == 0)
		{
			//if field left blank, display error message
			result_message.setText("Location can not be blank!");
		}
		//if field is filled correctly
		else
		{
			//create location object and store name
			Location loc = new Location(location, description);
			
			//called addLocation function using location object and DAL object
			int result = DAL.addLocation(loc);
			
			// check the result of addLocation
			switch (result) {
				case 0:
				// if addLocation returns 0, display success message
				result_message.setText("Location added successfully!");
				break;

				case 1:
				// if addLocation returns 1, display location exists error
				result_message.setText("Location " + location + " already exists!");
				break;

				case 2:
				// if addLocation returns 2, display no commas error
				result_message.setText("Commas (,) are not allowed in any field!");
				break;
			}
		}
	}

}
