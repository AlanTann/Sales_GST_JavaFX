package ch.makery.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ch.makery.address.model.Items;
import ch.makery.address.model.Product;

public class CartController {
	
    @FXML
    private TableView<Items> productCartTable;
    @FXML
    private TableColumn<Items, String> productNameCart;
    @FXML
    private TableColumn<Items, Double> productPriceCart;
    @FXML
    private TableColumn<Items, Integer> productQuantityCart;
    
	private Stage dialogStage;
	private boolean okClicked = false;
	private Items item;
	private MainApp mainApp;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	// Initialize the person table
        productNameCart.setCellValueFactory(new PropertyValueFactory<Items, String>("productName"));
        productQuantityCart.setCellValueFactory(new PropertyValueFactory<Items, Integer>("productSQ"));
        productPriceCart.setCellValueFactory(new PropertyValueFactory<Items, Double>("productPrice"));
        
      // showProductPurchaseDetails(item);
        
        // Auto resize columns
        productCartTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        
       //productCartTable.setItems(null);
       //productCartTable.layout();

        // Add observable list data to the table
        productCartTable.setItems(mainApp.getPurchaseData());
    }
    
//    private void showProductPurchaseDetails(Items item) {
//
//    		productNameCart.setText(item.getProductName());
//    		productQuantityCart.setText(Integer.toString(item.getProductSQ()));
//    		productPriceCart.setText(Double.toString(item.getProductPrice()));
//    }

    /**
     * Sets the stage of this dialog.
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the person to be edited in the dialog.
     * 
     * @param person
     */
    public void setProduct(Items item) {
        this.item = item;

        productNameCart.setText(item.getProductName());
        productQuantityCart.setText(Integer.toString(item.getProductSQ()));
        productPriceCart.setText(Double.toString(item.getProductPrice()));

    }
    
    @FXML
    private void handleDelete() {
        int selectedIndex = productCartTable.getSelectionModel().getSelectedIndex();
        Items i =  productCartTable.getSelectionModel().getSelectedItem();
        EntityManager em = MainApp.emf.createEntityManager();
        //Product removeProduct = em.find(Product.class,p);
        if (selectedIndex >= 0){
        em.getTransaction().begin();
        em.remove(i);
        em.getTransaction().commit();
        productCartTable.getItems().remove(selectedIndex);
        }
        	else {
        	    // Nothing selected
        	    Alert alert = new Alert(AlertType.ERROR);
        	    alert.initOwner(mainApp.getPrimaryStage());
        	    alert.setTitle("No Selection");
        	    alert.setHeaderText("No Product Selected");
        	    alert.setContentText("Please select a product in the table");
        	    alert.showAndWait();
        	  }
    }

    
    @FXML
    private void handleOkay() {
            dialogStage.close();
    }

}
