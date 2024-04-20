package data_access_layer;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.Category;
import application.Location;
import application.Asset;

public class DataAccessLayer {
	
	//class members
    private String locationFilePath;
    private String categoryFilePath;
    private String assetFilePath;
    //create HashMaps for each object
    private HashMap<String, Location> locationsMap;
    private HashMap<String, Category> categoriesMap;
    private HashMap<String, Asset> assetsMap;
    
	
    
    //DAL constructor
    public DataAccessLayer()
    {
    	//store the names of each objects file path
    	locationFilePath = "resources/csv/locations.csv";
    	categoryFilePath = "resources/csv/categories.csv";
    	assetFilePath = "resources/csv/assets.csv";
    	//instantiate the HashMaps for Location and Category
    	locationsMap = new HashMap<>();
    	categoriesMap = new HashMap<>();
    	assetsMap = new HashMap<>();
    
    }
    
    //addLocation method adds the Location object to its respective HashMap,
    //stores the attributes of the Location into its corresponding csv file 
    //using the appendToFile function, 
    //returns false if Location already exists and true if otherwise
    public int addLocation(Location location){
    	
    	//checks if the following Location object already exists by checking
    	//if the location name is stored as a key
        if(locationsMap.containsKey(location.getLocationName())) {
        	//returns false, if the location already exists in the HashMap
            return 1;
        }
        //if here, Location is new

        // initialize the string to store in file
        String dataline = location.getLocationName() + "," + location.getLocationDescr();
        
        //check if the data contains additional commas
        long count = dataline.chars().filter(ch -> ch == ',').count();
        if (count != 1) {
            return 2;
        }
        //if here, no commas are in the data

        //store the location name as the key, and the Location parameter as
        //the value in the HashMap
        locationsMap.put(location.getLocationName(), location);

        //call the appendToFile function to store location name 
        //and description into  passed file path, using a comma as
        //a delimiter to separate name and description
        appendToFile(locationFilePath, dataline);

        //returns true if the location is added to the HashMap and the 
        //location info is stored in the csv file
        return 0;
    }
    
    //addCategory method adds the Category object to its respective HashMap,
    //stores the attributes of the Category into its corresponding csv file 
    //using the appendToFile function, 
    //returns false if Category already exists and true if otherwise
    public int addCategory(Category category) {
    	
    	//checks if the following Category object already exists by checking
    	//if the category name is stored as a key
        if(categoriesMap.containsKey(category.getCategoryName())) {
            return 1;
        }
        //if here, Category is new

        // initialize the string to store in file
        String dataline = category.getCategoryName();
        
        //check if the data contains additional commas
        long count = dataline.chars().filter(ch -> ch == ',').count();
        if (count != 0) {
            return 2;
        }
        //if here, no commas are in the data
        
        //store the category name as the key, and the Category parameter as
        //the value in the HashMap
        categoriesMap.put(category.getCategoryName(), category);
        
        //call the appendToFile function to store category name 
        //and description into  passed file path
        appendToFile(categoryFilePath, dataline);
        
        //returns true if the Category is added to the HashMap and the 
        //Category name is stored in the csv file
        return 0;
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
                	//Store the category name into an array of Strings
                	String[] data = line.split(",");
                
                	//if there is at least one attribute, then csv is stored properly. continue reading from file
                	if (data.length > 0)
                	{
                		//store category name from the array of Strings
                		String categoryName = data[0].trim();
                		//store category name and Category object into Hashmap
                		tempMap.put(categoryName, new Category(categoryName));
                	}
                }
            }
            //close filereaders
            br.close();
        } 
        //catch block to catch any IOExceptions
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
                	//initialize array of Strings that stores each Location attribute into the array
                	String[] data = line.split(",");
                
                	//check if csv file is saving data properly, should be two elements in array
                	if (data.length >= 2)
                	{
                		//Store Location attributes from the String array
                		String locationName = data[0];
                		String locDescr = data[1];
                		//if the location is empty, store location description as empty string
                        if (locDescr.equals("_EMPTY_")) {
                            locDescr = "";
                        }
                        //store Location attributes into the HashMap of Locations
                		tempMap.put(locationName, new Location(locationName, locDescr));
                	}
                }
            }
            //close filereaders, no more lines to read
            br.close();
        }
        //implement catch block to handle any IoExceptions
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        //store populated hashmap into class location hashmap
        locationsMap = tempMap; 
    }
    
   public int addAsset(Asset asset) {
   	//checks if the following Asset object already exists by checking
   	//if the asset name is stored as a key
       if(assetsMap.containsKey(asset.getAssetName())) {
       	//returns false, if the asset already exists in the HashMap
           return 1;
       }
       //if here, Asset is new

       // initialize the string to store in file
       String dataline = asset.getAssetName()
       + "," + asset.getCategory() + "," + asset.getLocation() + "," + asset.getPurchDate() + "," + asset.getDescription() + "," 
    		   + asset.getPurchVal() + "," + asset.getExpDate();
        
       //check if the data contains additional commas
       long count = dataline.chars().filter(ch -> ch == ',').count();
       if (count != 6) {
           return 2;
       }
       //if here, no commas are in the data
       
       //store the asset name as the key, and the Asset parameter as
       //the value in the HashMap
       assetsMap.put(asset.getAssetName(), asset);
       
       //call the appendToFile function to store asset name
       //and description into  passed file path, using a comma as
       //a delimiter to separate name and description
       appendToFile(assetFilePath, dataline);

       //returns true if the asset is added to the HashMap and the 
       //asset info is stored in the csv file
       return 0;
   }
    
    public void storeAssetsFromFile() {
        //create a temp HashMap so that the current HashMap does not get overwritten
    	HashMap<String, Asset> tempMap = new HashMap<>();

    	//use try-catch block to check if the file path being passed exists,
    	//to catch any IoExceptions
        try 
        {
        	//instantiate BufferReader object and FileReader to read the asset.csv file line by line
        	BufferedReader br = new BufferedReader(new FileReader(assetFilePath));
        	
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
                	//store all Asset attributes into String array
                	String[] data = line.split(",");
               
                	//if the array has 7 items, csv is handling Asset data correctly
                	if (data.length >= 7) 
                	{
                		//check if there are any attributes that are tagged empty, save as empty string
                        for (String item : data) 
                        {
                            if (item.equals("_EMPTY_")) {
                                item = "";
                            }
                            
                        }
                        //store Asset attributes from array into the HashMap
                		tempMap.put(data[0], new Asset(data[0], data[1], data[2], data[3], data[4], data[5], data[6]));
                	}
                }
            }
            //clode filereaders, no more lines to read from
            br.close();
          //store populated hashmap into class location hashmap
            assetsMap = tempMap;   
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

    }
    
    //Checks to see if a particular searched string is a substring of an Asset Name,
    //returns an ArrayList of Assets that contain the following keyword
    public ArrayList<Asset> searchAsset(String keyword) {
    	//initialize an empty ArrayList to save all Assets that contain the keyword
        ArrayList<Asset> results = new ArrayList<>();
        //iterate through the assets HashMap by using the asset name
        for (String key : assetsMap.keySet()) {
        	//if the user's strings matches a substring in assetname, add Asset to ArrayList
            if (key.toLowerCase().contains(keyword)) {
                results.add(assetsMap.get(key));
            }
        }
        //return the ArrayList of all Assets that have the same keyword that was searched for
        return results;
    }

    

    //Takes the original Asset that is being overwritten and the new Asset that will overwrite
    //the original, calls delete to remove Asset from HashMap, returns value to determine
    //result_message
    public int editAssetData(Asset preAsset, Asset newAsset)
    {
    	//call deleteAssetData to remove the original Asset that is being edited
    	deleteAssetData(preAsset);
    	//add the newly edited Asset to the Asset HashMap and into the Asset Csv file
    	int val = addAsset(newAsset);
    	//return integer value to determine result message to be prompted to user
    	return val;
    }
    
    //removes the Asset that is either being deleted or edited from the hashmap,
    //calls storeAssetsToFile to rewrite csv file without the removed Asset
    public void deleteAssetData(Asset asset)
    {
    	//remove the Asset that is being deleted/edited from the Asset Hashmap
    	assetsMap.remove(asset.getAssetName());
    	//call storeAssetsToFile to rewrite csv file without removed Asset
    	storeAssetsToFile();
    }
    
 
    // need to implement storing of assets into file from DAL after editing or deleting assets
    public void storeAssetsToFile() 
    {
    	try
    	{
    		//instantiate BufferWriter object and FileWriter to read the asset.csv file line by line
        	BufferedWriter bw = new BufferedWriter(new FileWriter(assetFilePath));
        	
        	//create a flag to skip the first line of the csv file
        	boolean firstline = true;
        	
        	//flag caught, write titles
    		if(firstline)
    		{
    			//filewriter writes titles of asset's attributes
    			bw.write("Asset Name,Category,Location,Purchase Date,"
    					+ "Description,Purchase Value,Warranty Expiration Date");
    			bw.newLine();
    			//assign firstline to false
    			firstline = false;
    			
    		}
    		
        	//enhanced for loop to iterate through HashMap with removed assets
        	for(Map.Entry<String, Asset> Emap : assetsMap.entrySet())
        	{
        		//write to the file with new asset values
        		bw.write(Emap.getKey() + "," + Emap.getValue().getCategory()+ "," + Emap.getValue().getLocation()+ ","
        				+ Emap.getValue().getPurchDate()+ "," + Emap.getValue().getDescription() + "," + Emap.getValue().getPurchVal() 
        				+ "," + Emap.getValue().getExpDate());
        		//go to new line
        		bw.newLine();	
        	}
        	//close file writers
    		bw.close();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    

    //returns the HashMap for the Location data
    public HashMap<String, Location> getLocationsMap() {
        return locationsMap;
    }

    //returns the HashMap for the Category data
    public HashMap<String, Category> getCategoriesMap() {
        return categoriesMap;
    }
   
    //returns the HashMap for the Asset data
    public HashMap<String, Asset> getAssetsMap() {
        return assetsMap;
    }
}