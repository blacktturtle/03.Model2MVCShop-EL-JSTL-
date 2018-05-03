package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("UpdateTranCode 받아온 tranNo ::" + tranNo);
		
		Purchase purchase =  purchaseService.getPurchase(tranNo); 
		//나중에 반드시 수정할것 여러사람이 한 상품번호의 상품을 구매할 경우 겹친다
		//SQL 조건문에 prodNo로만 검사하는데 여기 userId 검색도 조건에 추가해서 하면 될듯
		System.out.println("UpdateTranCode 받아온 TranCode ::" + purchase.getTranCode());
		
		purchase.setTranCode("2"); // 배송중으로 변경		
		
		purchaseService.updateTranCode(purchase);
		
		
		return "forward:/delivery.do?prodNo="+purchase.getPurchaseProd().getProdNo();
	}

}
