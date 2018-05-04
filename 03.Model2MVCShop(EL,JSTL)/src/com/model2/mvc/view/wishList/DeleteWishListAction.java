package com.model2.mvc.view.wishList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Manager;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.wishlist.WishListService;
import com.model2.mvc.service.wishlist.impl.WishListServiceImpl;

public class DeleteWishListAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WishListService service = new WishListServiceImpl();
		//int wishNo = Integer.parseInt(request.getParameter("wishNo"));
		String[] wishNo = request.getParameter("wishNo2").split("/");
		
		for (String deleteList : wishNo) {
			if(deleteList.trim().equals("")) {
				
			}else {
				System.out.println("지워야할 List : " + deleteList);
				service.deleteWishList(Integer.parseInt(deleteList.trim()));
			}
			
		}
		
		
		
		
		
		
		
				
		return "forward:/wishList.do";
	}

}
