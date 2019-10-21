package ch.makery.address;

import java.util.ArrayList;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import ch.makery.address.model.Items;
import ch.makery.address.model.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class CheckOutProductController {
	ArrayList al = new ArrayList();
//	ArrayList storage = new ArrayList();
	
//	ArrayList quantity = new ArrayList();
	private Product product;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productNameContent;
    @FXML
    private TableColumn<Product, Double> productPriceContent;
    
    @FXML
    private Label productIdPurchase;
    @FXML
    private Label productNamePurchase;
    @FXML
    private Label productPricePurchase;
    @FXML
    private Label productGstType;

    @FXML
    private TextField productSQ;
    
    @FXML
    private Label totalProductPrice;
	private MainApp mainApp;

	private Items item;

    public CheckOutProductController() {
    }
    
//	public void setProduct(Items item) {
//		this.item = item;
//	    productSQ.setText(Integer.toString(item.getProductSQ()));
//	}
	
    @FXML
    private void initialize() {
    // Initialize the person table
    	productNameContent.setCellValueFactory(new PropertyValueFactory<Product, String>("productName"));
        productPriceContent.setCellValueFactory(new PropertyValueFactory<Product, Double>("productPrice"));
//        productNamePurchase.setCellValueFactory(new PropertyValueFactory<Items,String>("productName"));
//        productPricePurchase.setCellValueFactory(new PropertyValueFactory<Items,Double>("ProductPrice"));
//        productQuantityPurchase.setCellValueFactory(new PropertyValueFactory<Items,Integer>("productSQ"));
       // Auto resize columns
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
//        purchaseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
     // clear person
        showProductDetails(null);
        
        productTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

            @Override
            public void changed(ObservableValue<? extends Product> observable,
                Product oldValue, Product newValue) {
            	showProductDetails(newValue);
            }
          });
        	
    }
    
    private void showProductDetails(Product product) {

    	// use setText(...) on all labels with info from the person object
    	// use setText("") on all labels if the person is null
    	if(product==null){
    		productIdPurchase.setText("");
    		productNamePurchase.setText("");
    		productPricePurchase.setText("");
    		productGstType.setText("");
    		productSQ.setText("");
    	}
    	else
    	{
    		productIdPurchase.setText( product.getProductName());
    		productNamePurchase.setText(product.getProductName() );
    		productPricePurchase.setText(product.getProductPrice() + "");
    		productGstType.setText(product.getProductGstType() + "");
    		productSQ.setText("");
    	}
    }

    public void setMainApp(MainApp mainApp) { 	    	
        this.mainApp = mainApp;
 //       totalProductPrice.textProperty().bindBidirectional(mainApp.amount);
        
        // Add observable list data to the table
        productTable.setItems(mainApp.getProductData());
//        mainApp.getPurchaseData();
        
//        purchaseTable.getItems().addListener(new ListChangeListener(){
//
//			@Override		
//			public void onChanged(Change c) {
//				// TODO Auto-generated method stub
//				double total = 0;
//				for (Object p : purchaseTable.getItems()){
//					Product e = (Product)p;
//					total = total + e.getProductPrice();
//				}
//				
//				mainApp.amount.setValue(total + "");
//			}});  
    }
    
    @FXML
    private void handleCancel() {
    	mainApp.showHome(); 
    }
    
    @FXML
    private void handleAdd(){
    	Product product = (Product) productTable.getSelectionModel().getSelectedItem();
    	if(product != null){
	    	EntityManager em2 = MainApp.emf.createEntityManager();
	    	em2.getTransaction().begin();	
	    	Items items = new Items();
			items.setProductId(product.getProductId());
	        items.setProductName(product.getProductName());
	        items.setProductSQ(Integer.parseInt(productSQ.getText()));
	        items.setProductPrice(product.getProductPrice());
	        items.setProductGstType(product.getProductGstType());
	//		productTable.layout();
	//		productTable.setItems(mainApp.getProductData());
	        em2.persist(items);
//	    	TypedQuery<Items> itemQuery = em2.createQuery("SELECT i FROM Items i",
//					Items.class);+y
//	    	List<Items> itemResults = itemQuery.getResultList();
//	    	for (Items i : itemResults) {
//	    		System.out.print(i);
//	    	}
	        mainApp.getPurchaseData().add(items);
	    	em2.getTransaction().commit();
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
    private void handleCart(){
    	mainApp.showProductCart();
    }
    
//    /**
//     * Returns true if the user clicked OK, false otherwise.
//     * @return
//     */
//    public boolean isOkClicked() {
//        return okClicked;
//    }
    
    @FXML
    private void handleReceipt(){
//    	mainApp.showProductReceipt();
//    	        Product tempProduct = new Product();
    	        boolean okClicked = mainApp.showProductReceipt();
    	        if(okClicked){
    	     	 	  EntityManager em3 = MainApp.emf.createEntityManager();
    	            em3.getTransaction().begin();
			    	TypedQuery<Items> itemQuery = em3.createQuery("SELECT i FROM Items i",
							Items.class);
			    	List<Items> itemResults = itemQuery.getResultList();
			    	for (Items i : itemResults) {
			    		em3.remove(i);
	       				mainApp.getPurchaseData().remove(i);
			    	}
    	            mainApp.getPurchaseData().removeAll(item);
    	            em3.getTransaction().commit();
    	        }
    }
    

//	@FXML
//	public void handleAdd(ActionEvent e){	
//		
//		Product product = (Product) productTable.getSelectionModel().getSelectedItem();
//		Items item = new Items();
//		if(productSQ != null){
//		if (product != null){
//			if (al.size() < 4){
//					EntityManager em2 = MainApp.emf.createEntityManager();
//					em2.getTransaction().begin();
////					purchaseTable.getItems().add(product);
//					item.setProductId(product.getProductId());
//		            item.setProductName(product.getProductName());
//		            item.setProductSQ(Integer.parseInt(productSQ.getText()));
//		            item.setProductPrice(product.getProductPrice());
//		            item.setProductGstType(product.getProductGstType());
//		            em2.persist(item);
//					al.add(purchaseTable.getItems().add(item));
//		            em2.getTransaction().commit();
//			}
//			else { 
//				EntityManager em2 = MainApp.emf.createEntityManager();
//				em2.getTransaction().begin();
//				purchaseTable.setItems(null);
//				purchaseTable.layout();
//				purchaseTable.setItems(mainApp.getPurchaseData());  
//				item.setProductId(product.getProductId());
//	            item.setProductName(product.getProductName()); //get text from quantity box and then and set it to the product Sq in product class
//	            item.setProductSQ(Integer.parseInt(productSQ.getText()));
//	            item.setProductPrice(product.getProductPrice());
//	            item.setProductGstType(product.getProductGstType());
//	            em2.persist(item);
//				em2.getTransaction().commit();
//			}
//		}
//		}
//	}
	
//	@FXML
//	public void handleAdd(ActionEvent e){
//		Product product = (Product) productTable.getSelectionModel().getSelectedItem();
//		
//
//			product.setProductSQ(Integer.parseInt(productSQ.getText()));
//			
//		
//				productTable.setItems(null);
//				productTable.layout();
//				productTable.setItems(mainApp.getProductData());
//				product.setProductSQ(Integer.parseInt(productSQ.getText()));
//			      
//			
//			}
	
//	@FXML
//	public void handleAdd(ActionEvent e){
//		Product product = (Product) productTable.getSelectionModel().getSelectedItem();
//		
//		if (product != null)
//			if (al.indexOf(product) == -1  && al.size() == 0){
//					product.setProductSQ(Integer.parseInt(productSQ.getText()));
//					Integer.parseInt(productSQ.getText());
//					al.add(purchaseTable.getItems().add(product));
//				}
//			else { 
//				purchaseTable.setItems(null);
//				purchaseTable.setItems(mainApp.getProductData());
//				product.setProductSQ(Integer.parseInt(productSQ.getText()));
//			 }
//			}
	
	@FXML
	public void handleOkay(ActionEvent e){
	}
	
	@FXML
	public void handleGenerateReceipt(ActionEvent e){
	}
	
//	@FXML
//	public void handleAdd(ActionEvent e){	
//		Product product = (Product) productTable.getSelectionModel().getSelectedItem();
//		
//		if (product != null)
//			purchaseTable.getItems().add(product);
//		
//	}
	
}
