package ch.makery.address;

import javax.persistence.EntityManager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import ch.makery.address.model.Product;

public class ProductViewController {
    
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, String> productGstTypeColumn;
    
	
	private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public ProductViewController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    // Initialize the person table
    	productIdColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("productQuantity"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
        productGstTypeColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("productGstType"));
        
        // Auto resize columns
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
/*
        // Listen for selection changes
        productTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

		@Override
		public void changed(ObservableValue<? extends Product> observable,
				Product oldValue, Product newValue) {
			// TODO Auto-generated method stub
			showProductDetails(newValue);
		}
        });
*/
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        productTable.setItems(mainApp.getProductData());
    }
    
    private void showProductDetails(Product product) {

    	// use setText(...) on all labels with info from the person object
    	// use setText("") on all labels if the person is null
    	if(product==null){
    		productIdColumn.setText("");
    		productNameColumn.setText("");
    		productQuantityColumn.setText("");
    		productPriceColumn.setText("");
    		productGstTypeColumn.setText("");
    	}
    	else
    	{
    		productIdColumn.setText(product.getProductId() + "");
    		productNameColumn.setText(product.getProductName());
    		productQuantityColumn.setText(product.getProductQuantity() + "");
    		productPriceColumn.setText(product.getProductPrice() + "");
    		productGstTypeColumn.setText(product.getProductGstType() + "");
    	}
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteProduct() {
      int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
      Product p =  productTable.getSelectionModel().getSelectedItem();
      EntityManager em = MainApp.emf.createEntityManager();
      //Product removeProduct = em.find(Product.class,p);
      if (selectedIndex >= 0) {
      em.getTransaction().begin();
      em.remove(p);
      em.getTransaction().commit();
      productTable.getItems().remove(selectedIndex);
      }
      else {
  	    // Nothing selected
  	    Alert alert = new Alert(AlertType.ERROR);
  	    alert.initOwner(mainApp.getPrimaryStage());
  	    alert.setTitle("No Select Product Selected");
  	    alert.setContentText("Please select a person in the table to delete");
  	    alert.showAndWait();
  	  }
    }
    
    /**
     * Called when the user clicks the new button.
     * Opens a dialog to edit details for a new person.
     */
    @FXML
    private void handleCreateProduct() {
      Product tempProduct = new Product();
      boolean okClicked = mainApp.showProductCreate(tempProduct);
      if(okClicked){
    	  EntityManager em = MainApp.emf.createEntityManager();
          em.getTransaction().begin();
          em.persist(tempProduct);
          mainApp.getProductData().add(tempProduct);
          em.getTransaction().commit();
          //mainApp.getProductData().add(tempProduct);
      }
    }
    
    @FXML
    private void handleBack() {
    	mainApp.showHome(); 
    }

    /**
     * Called when the user clicks the edit button.
     * Opens a dialog to edit details for the selected product.
     */
    @FXML
    private void handleEditProduct() {
      	EntityManager em = MainApp.emf.createEntityManager();
    	em.getTransaction().begin();
    	Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
    	if (selectedProduct != null) {
        boolean okClicked = mainApp.showProductCreate(selectedProduct);
        if (okClicked) {
 //       	selectedProduct.setProductId(13);
        	refreshProductTable();
         // showProductDetails(selectedProduct);       
        }

      } else {
        // Nothing selected
//        Dialogs.showWarningDialog(mainApp.getPrimaryStage(),
//            "Please select a person in the table.",
//            "No Person Selected", "No Selection");
  	    
  	    Alert alert = new Alert(AlertType.WARNING);
  	    alert.initOwner(mainApp.getPrimaryStage());
  	    alert.setTitle("No Selection");
  	    alert.setHeaderText("No Product Selected");
  	    alert.setContentText("Please select a product in the table to edit");
  	    alert.showAndWait();
  	    
      }
      em.getTransaction().commit();
    }

    /**
     * Refreshes the table. This is only necessary if an item that is already in
     * the table is changed. New and deleted items are refreshed automatically.
     * 
     * This is a workaround because otherwise we would need to use property
     * bindings in the model class and add a *property() method for each
     * property. Maybe this will not be necessary in future versions of JavaFX
     * (see http://javafx-jira.kenai.com/browse/RT-22599)
     */
    private void refreshProductTable() {
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        productTable.setItems(null);
        productTable.layout();
        productTable.setItems(mainApp.getProductData());
        // Must set the selected index again (see http://javafx-jira.kenai.com/browse/RT-26291)
        productTable.getSelectionModel().select(selectedIndex);
      }
}
