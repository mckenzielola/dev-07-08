package data_access_layer;

import java.io.*;
import java.util.HashMap;

import application.Category;
import application.Location;

public class DataAccessLayer {
	
	//class members
    private String locationFilePath;
    private String categoryFilePath;
    //create HashMaps for each object
    private HashMap<String, Location> locationsMap;
    private HashMap<String, Category> categoriesMap;
    
    //DAL constructor
    public DataAccessLayer()
    {
    	//store the names of each objects file path
    	locationFilePath = "resources/csv/locations.csv";
    	categoryFilePath = "resources/csv/categories.csv";
    	//instantiate the HashMaps for Location and Category
    	locationsMap = new HashMap<>();
    	categoriesMap = new HashMap<>();
    }
    
    //addLocation method adds the Location object to its respective HashMap,
    //stores the attributes of the Location into its corresponding csv file 
    //using the appendToFile function, 
    //returns false if Location already exists and true if otherwise
    public boolean addLocation(Location location){
    	
    	//checks if the following Location object already exists by checking
    	//if the location name is stored as a key
        if(locationsMap.containsKey(location.getLocationName())) {
        	//returns false, if the location already exists in the HashMap
            return false;
        }
        //if here, Location is new
        
        //store the location name as the key, and the Location parameter as
        //the value in the HashMap
        locationsMap.put(location.getLocationName(), location);
        
        //call the appendToFile function to store location name 
        //and description into  passed file path, using a comma as
        //a delimiter to separate name and description
        appendToFile(locationFilePath, location.getLocationName()
        		+ "," + location.getLocationDescr());

        //returns true if the location is added to the HashMap and the 
        //location info is stored in the csv file
        return true;
    }
    
    //addCategory method adds the Category object to its respective HashMap,
    //stores the attributes of the Category into its corresponding csv file 
    //using the appendToFile function, 
    //returns false if Category already exists and true if otherwise
    public boolean addCategory(Category category) {
    	
    	//checks if the following Category object already exists by checking
    	//if the category name is stored as a key
        if(categoriesMap.containsKey(category.getCategoryName())) {
            return false;
        }
        //if here, Category is new
        
        //store the category name as the key, and the Category parameter as
        //the value in the HashMap
        categoriesMap.put(category.getCategoryName(), category);
        
        //call the appendToFile function to store category name 
        //and description into  passed file path
        appendToFile(categoryFilePath, category.getCategoryName());
        
        //returns true if the Category is added to the HashMap and the 
        //Category name is stored in the csv file
        return true;
    }
    
    //write the content of the Category and Location objects into the csv
    //file whose file path is passed
    private void appendToFile(String filePath, String content) {
    	
    	//use try-catch block to check if the file path being passed exists,
    	//to catch any IoExceptions
    	try {
    		//instantiate FileWriter object by passing the filepath parameter
    		//to open the file to write to
        	FileWriter fw = new FileWriter(filePath, true);
        	//instantiate BufferedWriter object by passing the FileWrite object 
        	BufferedWriter bw = new BufferedWriter(fw);
        	//instantiate PrintWriter object by passing the BufferedWriter object
        	PrintWriter out = new PrintWriter(bw);
        	//using the PrintWriter object, store the Category or Location's data
        	//into the csv file
            out.println(content);
            //close the csv file that was written to
            out.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //reads and stores the Categories from the csv file into HashMap
    public void storeCategoriesFromFile()
    {
    	//create a temp HashMap so that the current HashMap does not get overwritten
    	HashMap<String, Category> tempMap = new HashMap<>();

    	//use try-catch block to check if the file path being passed exists,
    	//to catch any IoExceptions
        try 
        {
        	//instantiate BufferReader object and FileReader to read the category.csv file line by line
        	BufferedReader br = new BufferedReader(new FileReader(categoryFilePath));
        	
        	//declare string value to hold the line data
            String line;
            
            //declare local variable for lineCounter that will skip first line in csv
            int lineCounter = 0;
            
            //while loop runs until the current line is empty, no more lines to read
            while ((line = br.readLine()) != null) 
            {
            	//if the lineCounter, title is being read, skip
                if (lineCounter == 0) 
                {
                	lineCounter++;
                    continue;    
                }
                else
                {
                	String[] data = line.split(",");
                
                	if (data.length > 0)
                	{
                		String categoryName = data[0].trim();
                		tempMap.put(categoryName, new Category(categoryName));
                	}
                }
            }
            br.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
  
        }

        //store populated hashmap into class hashmap
        categoriesMap = tempMap; 
    }
    
  //reads and stores the Location from the csv file into HashMap
    public void storeLocationsFromFile()
    {
    	//create a temp HashMap so that the current HashMap does not get overwritten
    	HashMap<String, Location> tempMap = new HashMap<>();

    	//use try-catch block to check if the file path being passed exists,
    	//to catch any IoExceptions
        try 
        {
        	//instantiate BufferReader object and FileReader to read the category.csv file line by line
        	BufferedReader br = new BufferedReader(new FileReader(locationFilePath));
        	
        	//declare string value to hold the line data
            String line;
            
            //declare local variable for lineCounter that will skip first line in csv
            int lineCounter = 0;
            
            //while loop runs until the current line is empty, no more lines to read
            while ((line = br.readLine()) != null) 
            {
            	//if the lineCounter, title is being read, skip
                if (lineCounter == 0) 
                {
                	lineCounter++;
                    continue;    
                }
                else
                {
                	String[] data = line.split(",");
                
                	if (data.length >= 2)
                	{
                		String locationName = data[0];
                		String locDescr = data[1];
                		tempMap.put(locationName, new Location(locationName, locDescr));
                	}
                }
            }
            br.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
  
        }

        //store populated hashmap into class location hashmap
        locationsMap = tempMap; 
    }
    
    //returns the HashMap for the Location data
    public HashMap<String, Location> getLocationsMap() {
        return locationsMap;
    }

    //returns the HashMap for the Category data
    public HashMap<String, Category> getCategoriesMap() {
        return categoriesMap;
    }
   
}