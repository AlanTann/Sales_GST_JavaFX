package ch.makery.address.model;

import javax.persistence.Entity;

/**
 * Model class for a Product.
 */
@Entity
public class Product{

    private int productId;
    private String productName;
    private int productQuantity;
    private double productPrice;
    private int productGstType;
    /**
     * Default constructor.
     */
    public Product() {
    }
    
    public Product(String productName, int productPrice) { //for item class
    	this.productName = productName;
        this.productPrice = productPrice;
    }
    
    public Product(int productId, String productName, int productQuantity, double productPrice) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }
    
    public Product(int productId, String productName, int productQuantity, double productPrice, int productGstType) {
        this.productId = productId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
        this.productGstType = productGstType;

        
    }
    public Product(String productName, double productPrice){
    	this.productName = productName;
    	this.productPrice = productPrice;
    }
    
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductGstType() {
        return productGstType;
    }

    public void setProductGstType(int productGstType) {
        this.productGstType = productGstType;
    }
}