package com.model2.mvc.service.wishlist.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.wishlist.WishListService;
import com.model2.mvc.service.wishlist.dao.WishListDAO;

public class WishListServiceImpl implements WishListService {

	private WishListDAO wishListDAO = new WishListDAO();

	@Override
	public void addWishList(WishList wishList) throws Exception {
		System.out.println("여기서출력 " + wishList);
		wishListDAO.insertWishList(wishList);
	}

	@Override
	public WishList getWishList(int prodNo) throws Exception {		
		return wishListDAO.findWishList(prodNo);
	}

	@Override
	public Map<String, Object> getAllWishList(Search search,String userId) throws Exception {
		return wishListDAO.getAllWishList(search,userId);
	}

	@Override
	public void deleteWishList(int wishNo) throws Exception {
		wishListDAO.deleteWishList(wishNo);
		
	}



}
