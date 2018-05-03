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
		System.out.println("UpdateTranCode �޾ƿ� tranNo ::" + tranNo);
		
		Purchase purchase =  purchaseService.getPurchase(tranNo); 
		//���߿� �ݵ�� �����Ұ� ��������� �� ��ǰ��ȣ�� ��ǰ�� ������ ��� ��ģ��
		//SQL ���ǹ��� prodNo�θ� �˻��ϴµ� ���� userId �˻��� ���ǿ� �߰��ؼ� �ϸ� �ɵ�
		System.out.println("UpdateTranCode �޾ƿ� TranCode ::" + purchase.getTranCode());
		
		purchase.setTranCode("2"); // ��������� ����		
		
		purchaseService.updateTranCode(purchase);
		
		
		return "forward:/delivery.do?prodNo="+purchase.getPurchaseProd().getProdNo();
	}

}
