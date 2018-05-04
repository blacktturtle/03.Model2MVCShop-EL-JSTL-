package com.model2.mvc.service.wishlist;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.*;

public interface WishListService {

	public void addWishList(WishList wishList) throws Exception;

	public WishList getWishList(int prodNo) throws Exception;

	public Map<String, Object> getAllWishList(Search search,String userId) throws Exception;
	
	public void deleteWishList(int wishNo) throws Exception;

}