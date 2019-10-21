package ch.makery.address.model;

import javax.persistence.Entity;

@Entity
public class Items{
	


	private int productId;
    private String productName;
    private double productPrice;
    private int productGstType;
	private int productSQ;
	private double productTotalAmount;
	private double productGstAmount;
	private double productTotalGstAmount;
	
	public Items(){
	}
	
	public Items(int productId, String productName, int productPrice, int productGstType, int productSQ){
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productGstType = productGstType;
		this.productSQ = productSQ;		
	}
	
	public Items(String productName, int productPrice, int productSQ){
		this.productName = productName;
		this.productPrice = productPrice;
		this.productSQ = productSQ;
	}
	
	public int getProductSQ() {
		return productSQ;
	}

	public void setProductSQ(int productSQ) {
		this.productSQ = productSQ;
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
	
	public double getProductTotalAmount() {
		return productTotalAmount = productPrice * productSQ;
	}

	public void setProductTotalAmount(double productTotalAmount) {
		this.productTotalAmount = productTotalAmount;
	}

	public double getProductGstAmount() {
		if(productGstType == 0){
			productGstAmount = 0;
		}else if(productGstType >= 1){
			productGstAmount = productTotalAmount * 6 /100;
		}
		
		return productGstAmount;
	}

	public void setProductGstAmount(double productGstAmount) {
		this.productGstAmount = productGstAmount;
	}
	
	public double getProductTotalGstAmount() {
		return productTotalGstAmount = getProductTotalAmount()+ getProductGstAmount();
	}

	public void setProductTotalGstAmount(double productTotalGstAmount) {
		this.productTotalGstAmount = productTotalGstAmount;
	}

}
