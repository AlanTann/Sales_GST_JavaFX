package ch.makery.address;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import ch.makery.address.model.Items;
import ch.makery.address.model.Product;
import ch.makery.address.model.Receipt;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	public static EntityManagerFactory emf;
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    // ...

    /**
     * The data as an observable list of Products.
     */
    private ObservableList<Product> productData = FXCollections.observableArrayList();
    private ObservableList<Items> purchaseData = FXCollections.observableArrayList();
    private ObservableList<Receipt> receiptData = FXCollections.observableArrayList();
    
//    public SimpleStringProperty amount = new SimpleStringProperty();
    
    /**
     * Constructor
     */
    public MainApp() {
    	
    	// productData.add(new Product(11, "Mueller",11,11.11,"ds"));
    	// Open a database connection
    	// (create a new database if it doesn't exist yet):
    	emf = Persistence.createEntityManagerFactory("productGstDatabase2.odb");
    	EntityManager em = emf.createEntityManager();
    	EntityManager em2 = emf.createEntityManager();
    	EntityManager em3 = emf.createEntityManager();

    	em.getTransaction().begin();
    	em2.getTransaction().begin();
    	em3.getTransaction().begin();
    	// Retrieve all the Point objects from the database:
    	TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p",
    				Product.class);
    	List<Product> results = query.getResultList();
    	for (Product x : results) {
    		productData.add(x);
    	}
    	
    	TypedQuery<Items> itemQuery = em2.createQuery("SELECT i FROM Items i",
				Items.class);
    	List<Items> itemResults = itemQuery.getResultList();
    	for (Items i : itemResults) {
 //   		purchaseData.add(i);
    	}
    	
    	TypedQuery<Receipt> receiptQuery = em3.createQuery("SELECT r FROM Receipt r",
				Receipt.class);
    	List<Receipt> receiptResults = receiptQuery.getResultList();
    	for (Receipt r : receiptResults) {
    		receiptData.add(r);
    	}
    	em.getTransaction().commit();
    	em2.getTransaction().commit();
    	em3.getTransaction().commit();
    }
  
	@Override
	public void start(Stage primaryStage) {
		//EntityManager em = emf.createEntityManager();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Tanjung Sdn Bhd");
        
        try {
            // Load the root layout from the fxml file
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();   
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
 //       showProductCheckOut();
//        showProductCart();
        showHome();
        //showProductView();

	}
	
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
	
	public void showHome(){
        try {
        	// Load the root layout from the fxml file
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Home.fxml"));
            AnchorPane Home = (AnchorPane) loader.load();
            rootLayout.setCenter(Home);
            
            // Give the controller access to the main app
            HomeController controller = loader.getController();
            controller.setMainApp(this);           
        } catch (IOException e) {
            // Exception gets thrown if the fxml file could not be loaded
            e.printStackTrace();
        }
	}
	
    public void showProductView() {
        try {
          FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/ProductView.fxml"));
          AnchorPane productView = (AnchorPane) loader.load();
          rootLayout.setCenter(productView);
         
          ProductViewController controller = loader.getController();
       // Give the controller access to the main app    
          controller.setMainApp(this);
        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
        }
      }
    
    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Product> getProductData() {
        return productData;
    }
    
    public ObservableList<Items> getPurchaseData() {
        return purchaseData;
    }
    
    public ObservableList<Receipt> getReceiptData() {
        return receiptData;
    }

    
    public boolean showProductCreate(Product product) {
        try {
          // Load the fxml file and create a new stage for the popup
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CreateProduct.fxml"));
            AnchorPane productCreate = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Peroduct");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(productCreate);
            dialogStage.setScene(scene);

          // Set the person into the controller
            CreateProductController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct(product); 
            
            dialogStage.showAndWait();

          return controller.isOkClicked();

        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
          return false;
        }
      }
    
    public void showProductCheckOut() {
        try {
          FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/CheckOutProduct.fxml"));
          AnchorPane productCheckOut = (AnchorPane) loader.load();
          rootLayout.setCenter(productCheckOut);
         
          CheckOutProductController controller = loader.getController();
       // Give the controller access to the main app    
          controller.setMainApp(this);
          
 //         return controller.isOkClicked;
          
        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
 //         return false;
        }
      }
    
    public void showProductCart() {
        try {
          // Load the fxml file and create a new stage for the popup
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Cart.fxml"));
            AnchorPane productCart = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Product Cart");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(productCart);
            dialogStage.setScene(scene);

          // Set the person into the controller
            CartController controller = loader.getController();
            controller.setDialogStage(dialogStage);// controller.setProduct(product); 
            controller.setMainApp(this);
//            controller.setProduct(items); 
            
            dialogStage.showAndWait();

//          return controller.isOkClicked();

        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
//          return false;
        }
      }
    
    public boolean showProductReceipt() {
        try {
          // Load the fxml file and create a new stage for the popup
        	FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/Receipt.fxml"));
            AnchorPane productReceipt = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Receipt");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(productReceipt);
            dialogStage.setScene(scene);

          // Set the person into the controller
            ReceiptController controller = loader.getController();
            controller.setDialogStage(dialogStage);// controller.setProduct(product); 
            controller.setMainApp(this);
//            controller.setProduct(items); 
            
            dialogStage.showAndWait();

          return controller.isOkClicked();

        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
          return false;
        }
      }
    
    public void showProductGenerateReport() {
        try {
          FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("view/SecReport.fxml"));
          AnchorPane generateReport = (AnchorPane) loader.load();
          rootLayout.setCenter(generateReport);
         
          ReportController controller = loader.getController();
       // Give the controller access to the main app    
          controller.setMainApp(this);
        } catch (IOException e) {
          // Exception gets thrown if the fxml file could not be loaded
          e.printStackTrace();
        }
      }

	public static void main(String[] args) {
		launch(args);
	}
}
