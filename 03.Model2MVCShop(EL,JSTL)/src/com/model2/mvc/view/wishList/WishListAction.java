package com.model2.mvc.view.wishList;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.WishList;
import com.model2.mvc.service.wishlist.WishListService;
import com.model2.mvc.service.wishlist.impl.WishListServiceImpl;

public class WishListAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession httpSession = request.getSession();		
		Search search = new Search();
		
		User user = (User)httpSession.getAttribute("user");
		

		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 한 페이지에 몇개씩 보여줄 것인지
		search.setPageSize(pageSize);
		
		WishListService service = new WishListServiceImpl();
		Map<String, Object> map = service.getAllWishList(search,user.getUserId());
		
		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::" + resultPage);
		
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
		
		return "forward:/wishList/wishList.jsp";
	}

}
