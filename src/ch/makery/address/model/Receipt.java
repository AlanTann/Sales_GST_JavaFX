package ch.makery.address.model;

import javax.persistence.Entity;

@Entity
public class Receipt {
	
	private double subTotal;
	private double gstCharge;
	private double totalSubGst;
	private double totalAmountPaid;
	private double balance;
	private Integer receiptId;

	
	public Receipt(){
	}
	
	public Receipt(int receiptId, double gstCharge, double totalSubGst, double totalAmountPaid, double balance){
		this.receiptId = receiptId;
		this.subTotal = subTotal;
		this.gstCharge = gstCharge;
		this.totalSubGst = totalSubGst;
		this.totalAmountPaid = totalAmountPaid;
		this.balance = balance;
	}
	
	
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	public double getGstCharge() {
		return gstCharge;
	}
	public void setGstCharge(double gstCharge) {
		this.gstCharge = gstCharge;
	}
	public double getTotalSubGst() {
		return totalSubGst = getSubTotal() + getGstCharge();
	}
	public void setTotalSubGst(double totalSubGst) {
		this.totalSubGst = totalSubGst;
	}
	public double getTotalAmountPaid() {
		return totalAmountPaid;
	}
	public void setTotalAmountPaid(double totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}
	public double getBalance() {
		return balance = getTotalAmountPaid() - getTotalSubGst();
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}
}
