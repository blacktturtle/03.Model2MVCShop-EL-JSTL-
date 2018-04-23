package com.model2.mvc.view.purchase;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class DeliveryAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("넘어온 배송번호 : " + prodNo);

		Search search = new Search();

		int currentPage = 1;
		if (request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		search.setCurrentPage(currentPage);

		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit")); // 한 페이지에 몇개씩 보여줄 것인지
		search.setPageSize(pageSize);
		
		PurchaseService service = new PurchaseServiceImpl();
		Map<String, Object> map = service.getDeliveryList(search, prodNo);
		
		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListUserAction ::" + resultPage);
					
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);

		return "forward:/purchase/delivery.jsp";
	}

}
