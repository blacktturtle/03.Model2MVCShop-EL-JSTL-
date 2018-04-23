package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService purchaseService = new PurchaseServiceImpl();
		ProductService productService = new ProductServiceImpl();
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("UpdateTranCode 받아온 tranNo ::" + tranNo);

		Purchase purchase = purchaseService.getPurchase(tranNo);
		System.out.println("UpdateTranCode 받아온 TranCode ::" + purchase.getTranCode());
		System.out.println("UpdateTranCode 받아온 IsPurchaseCode ::" + purchase.getIsPurchaseCode());

		if (purchase.getIsPurchaseCode() == 1) { 
			if (purchase.getTranCode().equals("2")) { // 배송완료 일 경우
				purchase.setTranCode("3");
				purchaseService.updateTranCode(purchase);
			}
			if (purchase.getTranCode().equals("1")) { //구매취소 일 경우
				purchase.setIsPurchaseCode(0); // 0 : 구매취소
				purchaseService.updateTranCode(purchase);
				
				Product product = productService.getProduct2(tranNo);
				product.setQuantity((product.getQuantity()+purchase.getQuantity()));
				productService.updateProduct(product); 

			}

		}

		return "forward:/listPurchase.do";
	}

}
