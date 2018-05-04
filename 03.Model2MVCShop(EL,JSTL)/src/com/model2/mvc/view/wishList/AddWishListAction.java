package com.model2.mvc.view.wishList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.*;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.WishList;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.dao.UserDao;
import com.model2.mvc.service.wishlist.WishListService;
import com.model2.mvc.service.wishlist.impl.WishListServiceImpl;


public class AddWishListAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();		
		User user = (User) session.getAttribute("user");		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		
		System.out.println("AddWishListAction.java :: 받아온 prodNo " + prodNo);
		
		WishList wishList = new WishList();
		wishList.setWishListBuyer(user);
		wishList.setWishListProd(new ProductDAO().findProduct(prodNo));

		
		System.out.println("AddWishListAction.java :: 등록된 wishList 정보 : " +wishList);
		
		WishListService service = new WishListServiceImpl();
		
		request.setAttribute("wishList", wishList);
		
		if(service.getWishList(prodNo).getWishListProd()!=null) {			
			return "forward:/wishList/notAddWishList.jsp";
		}else {
			service.addWishList(wishList);
			return "forward:/wishList/addWishList.jsp";	
		}	
	}
}
