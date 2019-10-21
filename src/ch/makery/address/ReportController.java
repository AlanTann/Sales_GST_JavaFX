package ch.makery.address;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import ch.makery.address.model.Items;
import ch.makery.address.model.Product;
import ch.makery.address.model.Receipt;

public class ReportController {
	private Items items;
    @FXML
    private TableView<Receipt> reportTable;
    @FXML
    private TableColumn<Receipt, Integer> receiptID;
    @FXML
    private TableColumn<Receipt, Double> SubCostColumn;
    @FXML
    private TableColumn<Receipt, Double> GstChargedColumn;
    @FXML
    private TableColumn<Receipt, Double> totalCostColumn;
//     @FXML
//    private TableColumn<Items, Date> reportDate;
    
    @FXML
    private Label subCostLabel;
    @FXML
    private Label gstChargedLabel;
    @FXML
    private Label totalCostLabel;
    
    private MainApp mainApp;
    private Receipt receipt;
    
    private double SubCostMonthLabel;
    private double GstChargedMonthLabel;
    private double totalCostMonthLabel;
    
    
    
    public ReportController() {
    }
    
    @FXML
    private void initialize() {
    // Initialize the person table
    	receiptID.setCellValueFactory(new PropertyValueFactory<Receipt, Integer>("receiptId"));
    	SubCostColumn.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("subTotal"));
    	GstChargedColumn.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("gstCharge"));
    	totalCostColumn.setCellValueFactory(new PropertyValueFactory<Receipt, Double>("totalSubGst"));
        
    	showReceiptDetail();
    	
        // Auto resize columns
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
    }

//    public void setProduct(Product product) {
//        this.items = items;
        
     // Add observable list data to the table
//        reportTable.setItems(mainApp.getReceiptData());

     //   reportID.setText(Integer.toString(product.getProductPrice()));
//        reportGST.setText(Double.toString(product.getProductPrice()));
//        reportDate.setText(product.getProductGstType());
//    }
    
    public void showReceiptDetail(){
    	
    	EntityManager em3 = MainApp.emf.createEntityManager();
    	em3.getTransaction().begin();
    	TypedQuery<Receipt> receiptQuery = em3.createQuery("SELECT r FROM Receipt r",
				Receipt.class);
    	List<Receipt> receiptResults = receiptQuery.getResultList();
    	for (Receipt r : receiptResults) {
    		SubCostMonthLabel = SubCostMonthLabel + r.getSubTotal();
    		GstChargedMonthLabel = GstChargedMonthLabel + r.getGstCharge();
    		totalCostMonthLabel = totalCostMonthLabel + r.getTotalSubGst();
    	}
    	em3.getTransaction().commit();
    	
    	subCostLabel.setText(Double.toString(SubCostMonthLabel));
    	gstChargedLabel.setText(Double.toString(GstChargedMonthLabel));
    	totalCostLabel.setText(Double.toString(totalCostMonthLabel));
    	
    }
	 
	public void setMainApp(MainApp mainApp) {
		
		this.mainApp = mainApp;
		reportTable.setItems(mainApp.getReceiptData());
	}
	
    @FXML
    private void handleReturn() {
    	mainApp.showHome(); 
    }

}
