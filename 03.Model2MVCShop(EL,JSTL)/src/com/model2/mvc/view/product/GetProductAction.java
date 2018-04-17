package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int prodNo=Integer.parseInt( request.getParameter("prodNo"));
		System.out.println("�޾ƿ� ��ȣ��?" + prodNo);

		////////////��Ű�߰�
		Cookie[] cookies = request.getCookies();
		String coo = null;
		
		for(int i = 0; i<cookies.length; i++) {
			
			if(cookies[i].getName().equals("history")){
				coo= cookies[i].getValue();
				System.out.println("���� ����Ű" + coo);
		
			}
		}
		Cookie cookie = new Cookie("history", coo +"," +prodNo);
		response.addCookie(cookie);
		
		/////////��Ű�߰�

		ProductService service = new ProductServiceImpl();
		Product vo = service.getProduct(prodNo);
		
		

		request.setAttribute("vo", vo);
		
		if(request.getParameter("menu").equals("search")) {
			return "forward:/product/getProduct.jsp?menu=search";	
		}else if(request.getParameter("menu").equals("completeSearch")) {
			return "forward:/product/getProduct.jsp?menu=completeSearch";			
		}else {
			return "forward:/product/getProduct.jsp";	
		}


	}

}
