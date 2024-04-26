package application;
import application.controller.MainController;
import javafx.scene.layout.HBox;

public class CommonObjs 
{
	private static CommonObjs commonObjs = new CommonObjs();
	private HBox mainBox;
	private MainController mainController;
	
	private CommonObjs()
	{
		
	}
	
	public static CommonObjs getInstance()
	{
		return commonObjs;
	}

	public HBox getMainBox() {
		return mainBox;
	}

	public void setMainBox(HBox mainBox) {
		this.mainBox = mainBox;
	}
	
	public MainController getMainController() {
		return mainController;
	}
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	
	
}
