package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService purchaseService = new PurchaseServiceImpl();

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("UpdateTranCode 받아온 tranNo ::" + tranNo);

		Purchase purchase = purchaseService.getPurchase(tranNo);
		System.out.println("UpdateTranCode 받아온 TranCode ::" + purchase.getTranCode());
		System.out.println("UpdateTranCode 받아온 IsPurchaseCode ::" + purchase.getIsPurchaseCode());

		if (purchase.getIsPurchaseCode() == 1) { // 구매취소 눌렀을 경우
			if (purchase.getTranCode().equals("2")) {
				purchase.setTranCode("3");
				purchaseService.updateTranCode(purchase);
			}
			if (purchase.getTranCode().equals("1")) {
				purchase.setIsPurchaseCode(0);
				purchase.setTranCode("0");
				purchaseService.updateTranCode(purchase);

			}

		}

		return "forward:/listPurchase.do";
	}

}
