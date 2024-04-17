package application.controller;

import application.Category;
import application.CommonObjs;
import data_access_layer.DataAccessLayer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CategoryController 
{
	private CommonObjs commonObjs = CommonObjs.getInstance();
	@FXML private TextField category_name;
	@FXML private Label result_message;
	private DataAccessLayer DAL = new DataAccessLayer();
	
	@FXML public void saveCategoryOp() 
	{	
		//get text 
		String category = category_name.getText();
		
		//check if the category field is left blank
		if(category.length() == 0)
		{
			//display error message for blank category name
			result_message.setText("Category can not be blank!");
		}
		else
		{
			//display message for category being added
			result_message.setText("Category added successfully!");
			
			//create category object and store name
			Category cat = new Category(category);
			
			//call DAL class to store Category names to csv files
			int result = DAL.addCategory(cat);
			
			// check the result of addCategory
			switch (result) {
				case 0:
				// if addCategory returns 0, display success message
				result_message.setText("Category added successfully!");
				break;

				case 1:
				// if addCategory returns 1, display category exists error
				result_message.setText("Category " + category + " already exists!");
				break;

				case 2:
				// if addCategory returns 2, display no commas error
				result_message.setText("Commas (,) are not allowed in any field!");
				break;
			}
		}
	}
}
