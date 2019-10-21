package ch.makery.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import ch.makery.address.MainApp;
import ch.makery.address.model.Items;
import ch.makery.address.model.Product;
import ch.makery.address.model.Receipt;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ReceiptController{

    @FXML
    private TableView<Items> receiptProductTable;
    @FXML
    private TableColumn<Items, String> receiptProNameColumn;
    @FXML
    private TableColumn<Items, Double> receiptProPriceColumn;
    @FXML
    private TableColumn<Items, Integer> receiptProQuantityColumn;
    @FXML
    private Label gstIDLabel;
    @FXML
    private Label subTotalLabel;
    @FXML
    private Label gstChargeLabel;
    @FXML
    private Label totalSubGstLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private TextField totalAmountPaidField;
    
    
    private double retrieveTotalPrice;
    private double retrieveGstAmount;
    private double retrieveTotalGstAmount;
    
	private Stage dialogStage;
	private boolean okClicked = false;
	private Items item;
	private MainApp mainApp;
	private Receipt receipt;


//    @FXML
//    private Label totalProductPrice;

	@FXML
    private void initialize() {
    // Initialize the person table
    
		receiptProNameColumn.setCellValueFactory(new PropertyValueFactory<Items,String>("productName"));
		receiptProPriceColumn.setCellValueFactory(new PropertyValueFactory<Items,Double>("productPrice"));
		receiptProQuantityColumn.setCellValueFactory(new PropertyValueFactory<Items,Integer>("productSQ"));

//        EntityManager em2 = MainApp.emf.createEntityManager();
    	
//        TypedQuery<Items> itemQuery = em2.createQuery("SELECT i FROM Items i",
//				Items.class);
        
//        em2.getTransaction().begin();	
        
//        Items item = em2.find(Items.class, 1);
    	
//        Query q1 = em2.createQuery("SELECT SUM(productSQ) FROM Items i");
//        System.out.println("Total Points: " + q1.getSingleResult());
        
//        String query="select sum(itemprice) as 'totalbill' from purchases where customer_id ="+ subTotal;
//        
//        ResultSet rs= stmt.executeQuery(query);
//        while(rs.next())
//        {
//        totalbill= rs.getInt("totalbill");
//                     
//        }[/b]  
         
//    }
        
//    	List<Items> itemResults = itemQuery.getResultList();
//    	for (Items i : itemResults) {
//    		subTotal = subTotal + i.getProductSQ() ;
//    	}
//        
//        
//        	  for (Point p : results) {
//        	      if (p.getX() >= 100) {
//        	          em.remove(p); // delete entity
//        	      }
//        	      else {
//        	          p.setX(p.getX() + 100); // update entity
//        	      }
//        	  }
		showReceiptDetail();
    	// Auto resize columns
        receiptProductTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	
	
	public void showReceiptDetail(){
		
//		if(items==null){
//			subTotal.setText("");
//			gstCharge.setText("");
//			totalSubGst.setText("");
//			totalAmountPaid.setText("");
//    		balance.setText("");
//    	}
//    	else
//    	{
    		
    		EntityManager em2 = MainApp.emf.createEntityManager();
        	em2.getTransaction().begin();
        	TypedQuery<Items> itemQuery = em2.createQuery("SELECT i FROM Items i",
    				Items.class);
        	List<Items> itemResults = itemQuery.getResultList();
        	for (Items i : itemResults) {
        		retrieveTotalPrice = retrieveTotalPrice + i.getProductTotalAmount();
        		retrieveGstAmount = retrieveGstAmount + i.getProductGstAmount();
        		retrieveTotalGstAmount = retrieveTotalGstAmount + i.getProductTotalGstAmount();
        	}
        	em2.getTransaction().commit();
        	
        	subTotalLabel.setText(Double.toString(retrieveTotalPrice));
        	gstChargeLabel.setText(Double.toString(retrieveGstAmount));
        	totalSubGstLabel.setText(Double.toString(retrieveTotalGstAmount));
        	

//    		subTotal.setText(items.getProductId() + "");
//    		gstCharge.setText(items.getProductName());
//    		totalSubGst.setText(items.getProductQuantity() + "");
//    		totalAmountPaid.setText(items.getProductPrice() + "");
//    		balance.setText(items.getProductGstType() + "");
//    	}
		
	}
	
	
	public void setDialogStage(Stage dialogStage) {
	       this.dialogStage = dialogStage;
	    }

    public void setMainApp(MainApp mainApp){ 	    	
            this.mainApp = mainApp;
            
            // Add observable list data to the table
            receiptProductTable.setItems(mainApp.getPurchaseData());
            mainApp.getReceiptData();
//            
//            
//            receiptTable.getItems().addListener(new ListChangeListener(){
//
//    			@Override
//    			public void onChanged(Change c) {
//    				// TODO Auto-generated method stub
//    				double total = 0;
//    				for (Object p : receiptTable.getItems()){
//    					Product e = (Product)p;
//    					total = total + e.getProductPrice();
//    					}
//    				
//    				mainApp.amount.setValue(total + "");
//    			}});
            
        }
 
        
//        @FXML
//        public boolean isOkClicked() {
//            return okClicked;
//        }
        
        @FXML
        private void handleCalculate() {   			
//        	 okClicked = true;
        	Receipt receipt = new Receipt();
        	
        	balanceLabel.setText(Double.toString(receipt.getBalance()));
        	EntityManager em3 = MainApp.emf.createEntityManager();
        	em3.getTransaction().begin();
        	receipt.setReceiptId(1);
        	receipt.setSubTotal(retrieveTotalPrice);
        	receipt.setGstCharge(retrieveGstAmount);
        	receipt.setTotalSubGst(retrieveTotalGstAmount);
        	receipt.setTotalAmountPaid(Double.parseDouble(totalAmountPaidField.getText()));  //imprt
        	if(Double.parseDouble(totalAmountPaidField.getText()) >= retrieveTotalGstAmount && totalAmountPaidField.getText() != null)
        	{
            	balanceLabel.setText(Double.toString(receipt.getBalance()));
            	receipt.setBalance(Double.parseDouble(balanceLabel.getText()));
            	mainApp.getReceiptData().add(receipt);
        		em3.persist(receipt);
        	}
        	else {
        	    // Invalid Amount
        	    Alert alert = new Alert(AlertType.ERROR);
        	    alert.initOwner(mainApp.getPrimaryStage());
        	    alert.setTitle("Invalid Amount");
        	    alert.setHeaderText("Amount entered is insufficient");
        	    alert.setContentText("Please enter a valid amount to proceed the payment process");
        	    alert.showAndWait();
        	  }	
        	em3.getTransaction().commit();
        }
        
	      /**
	      * Returns true if the user clicked OK, false otherwise.
	      * @return
	      */
	     public boolean isOkClicked() {
	         return okClicked;
	     }
        
        @FXML
        private void handleOkay() {   			
        	EntityManager em2 = MainApp.emf.createEntityManager();
        	em2.getTransaction().begin();
        	TypedQuery<Items> itemQuery = em2.createQuery("SELECT i FROM Items i",
    				Items.class);
        	List<Items> itemResults = itemQuery.getResultList();
        	for (Items i : itemResults) {
        		mainApp.getPurchaseData().remove(i);
        		em2.remove(i);
//    	        mainApp.getPurchaseData().remove(i);
        	}
//        	mainApp.getPurchaseData().removeAll(item);
        	em2.getTransaction().commit();
//        	mainApp.getPurchaseData().removeAll(item);
        	okClicked = true;
          dialogStage.close();
        }
}


    


