package application;

public class Asset 
{
	//initialize class members
	private String assetName;
	private String category, location;
	private String purchaseDate, description;
	private String purchaseVal, warrantyDate;
	
	//default constructor, assign class members with default values
	public Asset(String name, String cat, String loc, String purDate, String descr, String cost, String expDate)
	{
		this.assetName = name;
		this.category = cat;
		this.location = loc;
		this.purchaseDate = purDate;
		this.description = descr;
		this.purchaseVal = cost;
		this.warrantyDate = expDate;
	}
	
	//create mutators and accessors for all class members
	public String getAssetName()
	{
		return this.assetName;
	}
	
	public void setAssetName(String name)
	{
		this.assetName = name;
	}

	public String getCategory()
	{
		return this.category;
	}

	public void setCategory(String cat)
	{
		this.category = cat;
	}

	public String getLocation()
	{
		return this.location;
	}
	
	public void setLocation(String loc)
	{
		this.location = loc;
	}
	
	public String getPurchDate()
	{
		return this.purchaseDate;
	}

	public void setPurchVal(String cost)
	{
		this.purchaseVal = cost;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String descr)
	{
		this.description = descr;
	}

	public String getPurchVal()
	{
		return this.purchaseVal;
	}

	public void setPurchDate(String purDate)
	{
		this.purchaseDate = purDate;
	}

	public String getExpDate()
	{
		return this.warrantyDate;
	}

	public void setExpDate(String expDate)
	{
		this.warrantyDate = expDate;
	}
}
