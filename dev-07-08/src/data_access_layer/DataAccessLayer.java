package data_access_layer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
                        if (locDescr.equals("_EMPTY_")) {
                            locDescr = "";
                        }
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
       + "," + asset.getCategory() + "," + asset.getLocation() + "," + asset.getPurchDate() + "," + asset.getDescription() + "," + asset.getPurchVal() + "," + asset.getExpDate();
        
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
                	String[] data = line.split(",");
                
                	if (data.length >= 7) {
                        for (String item : data) {
                            if (item.equals("_EMPTY_")) {
                                item = "";
                            }
                        }
                		tempMap.put(data[0], new Asset(data[0], data[1], data[2], data[3], data[4], data[5], data[6]));
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
        assetsMap = tempMap; 
    }
    
    public ArrayList<Asset> searchAsset(String keyword) {
        ArrayList<Asset> results = new ArrayList<>();
        for (String key : assetsMap.keySet()) {
            if (key.toLowerCase().contains(keyword)) {
                results.add(assetsMap.get(key));
            }
        }
        return results;
    }

    
    public void editAssetData(Asset asset)
    {
    	// initialize the string to store in file
        String dataline = asset.getAssetName()
        + "," + asset.getCategory() + "," + asset.getLocation() + "," + asset.getPurchDate() + "," + 
        		asset.getDescription() + "," + asset.getPurchVal() + "," + asset.getExpDate();
         
    	//implement try catch block for any IOExceptions for writing or accessing assets.csv
    	try 
    	{
        	//store all of the lines for the asset csv into list of Strings to be iterated through
        	List<String> lines = Files.readAllLines(Paths.get(assetFilePath));
        	
        	//code for loop to iterate through the csv lines, start at line 1 because of title
        	for(int i = 1; i < lines.size(); i++)
        	{
        		//store the current line of asset data from the list of Strings into line
        		String line = lines.get(i);
        		//extract each attribute of the asset using a comma as the delimiter
        		String[] data = line.split(",");
        		
        		//if the assetName from the list is equal t
        		if(data[0] == asset.getAssetName())
        		{
        			lines.set(i, dataline);
        		}
        	}
        	
        
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
    
    public void storeAssetsToFile() {
    	// need to implement storing of assets into file from DAL after editing or deleting assets
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