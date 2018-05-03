package com.model2.mvc.service.domain;

public class WishList {
	private User wishListBuyer;
	private Product wishListProd;
	private int wishIsPurchaseCode;
	private int wishNo;
	
	public WishList() {
	
	}

	public User getWishListBuyer() {
		return wishListBuyer;
	}

	public void setWishListBuyer(User wishListBuyer) {
		this.wishListBuyer = wishListBuyer;
	}

	public Product getWishListProd() {
		return wishListProd;
	}

	public void setWishListProd(Product wishListProd) {
		this.wishListProd = wishListProd;
	}

	public int getWishNo() {
		return wishNo;
	}

	public void setWishNo(int wishNo) {
		this.wishNo = wishNo;
	}

	public int getWishIsPurchaseCode() {
		return wishIsPurchaseCode;
	}

	public void setWishIsPurchaseCode(int wishIsPurchaseCode) {
		this.wishIsPurchaseCode = wishIsPurchaseCode;
	}

	@Override
	public String toString() {
		return "WishList [wishListBuyer=" + wishListBuyer + ", wishListProd=" + wishListProd + ", wishIsPurchaseCode="
				+ wishIsPurchaseCode + ", wishNo=" + wishNo + "]";
	}




}
