package ch.makery.address;

import javafx.fxml.FXML;

public class HomeController {
	private MainApp mainApp;
	
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public HomeController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }
    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    
    /**
     * Called when the user clicks the View the Product button.
     */
    @FXML
    private void handleViewProduct() {
    	mainApp.showProductView(); 
    }
    
    /**
     * Called when the user clicks the Check out Product button.
     */
    @FXML
    private void handleCheckOutProduct() {
    	mainApp.showProductCheckOut();
    }
    
    /**
     * Called when the user clicks the Generate Report button.
     */
    @FXML
    private void handleGenerateReport() {
    	mainApp.showProductGenerateReport();
    }

    
}
