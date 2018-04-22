package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.*;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		System.out.println(user.getUserName());
		ProductService productService = new ProductServiceImpl();
		//Product product = new Product();
		

		//product.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); // 다시해볼것 productVO따오는방법 생각
		//purchase.setPurchaseProd(product);
	
		// 구매한만큼 수량 -해서 업데이트
		Product product = productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		product.setQuantity((product.getQuantity()-Integer.parseInt(request.getParameter("quantity"))));
		productService.updateProduct(product); 
		
		
		Purchase purchase = new Purchase();
		purchase.setBuyer(user);
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setTranCode("1"); // 구매상태코드??
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setIsPurchaseCode(1);
		purchase.setQuantity(Integer.parseInt(request.getParameter("quantity"))); //구매수량

		PurchaseService service = new PurchaseServiceImpl();

		service.addPurchase(purchase);

		request.setAttribute("vo", purchase);

		return "forward:/purchase/addPurchase.jsp";
	}

}
