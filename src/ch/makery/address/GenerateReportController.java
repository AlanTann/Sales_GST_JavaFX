package ch.makery.address;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ch.makery.address.model.Product;

public class GenerateReportController {
	private Product product;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, String> productGstTypeColumn;
    private MainApp mainApp;
    @FXML
    private TextField productTotalQuantity;
    @FXML
    private TextField productTotalPrice;
    
    public void setProduct(Product product) {
        this.product = product;

        productNameColumn.setText(product.getProductName());
        productQuantityColumn.setText(Integer.toString(product.getProductQuantity()));
        productPriceColumn.setText(Double.toString(product.getProductPrice()));
        productGstTypeColumn.setText(String.valueOf(product.getProductGstType()));
        
    }
    
    @FXML
    private void handleReturn() {
    	mainApp.showHome(); 
    }
	
	public void setMainApp(MainApp mainApp) {
		
		this.mainApp = mainApp;
	}
}
