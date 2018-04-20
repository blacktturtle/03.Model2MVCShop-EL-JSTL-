package com.model2.mvc.view.product;

import java.awt.List;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Search search = new Search();

		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		if(request.getParameter("check") !=null) {
			System.out.println("체크박스 값은? " + request.getParameter("check"));
			if(request.getParameter("check").equals("1")) {
				search.setPriceUpDown(1);
			}
			if(request.getParameter("check").equals("2")) {
				search.setPriceUpDown(2);
			}
		}	

		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		System.out.println("현재 숫자 : " + request.getParameter("searchCondition"));

		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 한 페이지에 몇개씩 보여줄 것인지
		search.setPageSize(pageSize);

		ProductService service = new ProductServiceImpl();
		Map<String, Object> map = service.getProductList(search);

		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::" + resultPage);

		//Trancode 넘기게할라고
		PurchaseService purchaseService = new PurchaseServiceImpl();
		ArrayList<Product> list = (ArrayList<Product>) map.get("list");
		ArrayList<Purchase> list2 = new ArrayList<Purchase>();
		
		System.out.println("리스트 사이즈 : " + list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println("리스트 출력  : " + list.get(i));
			Purchase purchase = purchaseService.getPurchase2(list.get(i).getProdNo());
			System.out.println("트랜코드 넘기기전 확인 : " + purchase.getTranCode());
			
			list2.add(purchase);
		}
		


		request.setAttribute("purchase", list2);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);

	
		if (request.getParameter("menu").equals("manage")) {
			System.out.println("mange로 왔따");
			return "forward:/product/manageProduct.jsp";
		} else if (request.getParameter("menu").equals("search")) {
			System.out.println("서치로왓다");
			return "forward:/product/searchProduct.jsp?menu=search";
		} else {
			return "forward:/product/searchProduct.jsp?menu=completeSearch";
		}
	}

}
