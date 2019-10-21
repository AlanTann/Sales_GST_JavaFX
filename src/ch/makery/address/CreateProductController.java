package ch.makery.address;


import ch.makery.address.model.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateProductController {
	
	@FXML
    private TextField productIdField;
	
	@FXML
    private TextField productNameField;
	
	@FXML
    private TextField productQuantityField;
	
	@FXML
    private TextField productPriceField;
	
	@FXML
    private TextField productGstTypeField;
	
	private Stage dialogStage;
	private Product product;
	private boolean okClicked = false;
	
	private MainApp mainApp;
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

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
    public void setProduct(Product product) {
        this.product = product;

        productIdField.setText(Integer.toString(product.getProductId()));
        productNameField.setText(product.getProductName());
        productQuantityField.setText(Integer.toString(product.getProductQuantity()));
        productPriceField.setText(Double.toString(product.getProductPrice()));
        productGstTypeField.setText(Integer.toString(product.getProductGstType()));
    }
    

    /**
     * Returns true if the user clicked OK, false otherwise.
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid() == true) {
        	product.setProductId(Integer.parseInt(productIdField.getText()));
            product.setProductName(productNameField.getText());
            product.setProductQuantity(Integer.parseInt(productQuantityField.getText()));
            product.setProductPrice(Double.parseDouble(productPriceField.getText()));
            product.setProductGstType(Integer.parseInt(productGstTypeField.getText()));
            
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */


    @FXML
    private void handleBack() {
    	dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (productIdField.getText() == null || productIdField.getText().length() == 0) {
            errorMessage += "No valid product id!\n"; 
        } else {
            // try to parse the postal code into an int
            try {
                Integer.parseInt(productIdField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product id (must be an integer)!\n"; 
            }
        }
        if (productNameField.getText() == null || productNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (productQuantityField.getText() == null || productQuantityField.getText().length() == 0) {
            errorMessage += "No valid product quantity!\n"; 
        } else {
            // try to parse the postal code into an int
            try {
            	Integer.parseInt(productQuantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product quantity (must be an integer)!\n"; 
            }
        }
        if (productPriceField.getText() == null || productPriceField.getText().length() == 0) {
            errorMessage += "No valid product Price!\n"; 
        } else {
            // try to parse the product price into an double
            try {
                Double.parseDouble(productPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product price(must be an double)!\n"; 
            }
        }
        if (productGstTypeField.getText() == null || productGstTypeField.getText().length() == 0) {
            errorMessage += "No valid Gst Type!\n"; 
        }else {
            // try to parse the postal code into an int
            try {
            	Integer.parseInt(productQuantityField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product quantity (must be an integer)!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message
        	Alert alert = new Alert(AlertType.ERROR);
    	    alert.initOwner(mainApp.getPrimaryStage());
    	    alert.setTitle("No Selection");
    	    alert.setHeaderText("No Person Selected");
    	    alert.setContentText("Please select a person in the table");
    	    alert.showAndWait();
      
            return false;
        }
    }

}
