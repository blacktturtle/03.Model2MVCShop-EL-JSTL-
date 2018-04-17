<%@page import="com.model2.mvc.service.product.impl.ProductServiceImpl"%>
<%@page import="com.model2.mvc.service.product.ProductService"%>
<%@page import="com.model2.mvc.service.product.dao.ProductDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ page import="java.util.List"  %>
<%@page import="com.model2.mvc.service.purchase.impl.PurchaseServiceImpl"%>
<%@page import="com.model2.mvc.service.purchase.PurchaseService"%>
<%@page import="com.model2.mvc.service.purchase.dao.PurchaseDAO"%>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>

<%
	User user = (User)session.getAttribute("user");
	List<Purchase> list= (List<Purchase>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	Search search=(Search)request.getAttribute("search"); 
	//==> null �� ""(nullString)���� ����
	
%>



<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetPurchaseList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listPurchase.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">��ü <%=resultPage.getTotalCount() %> �Ǽ�, ���� <%= resultPage.getCurrentPage() %> ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ǰ����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<% 	
		for(int i=0; i<list.size(); i++) {
			Purchase vo = (Purchase)list.get(i);
	%>
	<%
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		PurchaseService purchaseService = new PurchaseServiceImpl();
				
		Purchase purchase =  purchaseService.getPurchase(vo.getTranNo());
		System.out.println("�ڵ� : " + purchase.getTranCode());
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct2(vo.getTranNo());
		%>
		
	
	<tr class="ct_list_pop">
	<td align="center">
			<a href="/getPurchase.do?tranNo=<%=vo.getTranNo() %>"><%=i+1 %></a>
		</td>
		<td></td>
		<td align="left">
		<%if((purchase.getTranCode().trim().equals("1"))){%>
			<a href="/getUser.do?userId=<%=user.getUserId()%>"><%=user.getUserId() %></a>
			<%}else{ %>
			<%=user.getUserId() %>
			<%} %>
		</td>
		<td></td>
		
		<td align="left"><a href ="/getProduct.do?menu=completeSearch&prodNo=<%=product.getProdNo()%>"><%=product.getProdName() %></a></td>
		<td></td>
		<td align="left"><%=product.getPrice() %></td>
		<td></td>
		
		<td> <%if((purchase.getTranCode().trim().equals("1"))){ %>
		    ���� ���ſϷ� �����Դϴ�.  
			<%}else if(purchase.getTranCode().trim().equals("2")) {%>
			���� ����� �����Դϴ�.
			<%}else if(purchase.getTranCode().trim().equals("3")) {%>
			���� ��ۿϷ� �����Դϴ�.
		<%} %>
		
		</td>
		<td></td>
		<td>
		<%if(purchase.getTranCode().trim().equals("2")){ %>
		<a href="/updateTranCodeByProd.do?tranNo=<%=vo.getTranNo()%>" >��ۿϷ�</a>
		<%} %></td>
		</tr>
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
	
		<%} %>	
		
	
</table>
		
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value=""/>
			<% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					�� ����
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getBeginUnitPage()-1%>')">�� ����</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetPurchaseList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					���� ��
			<% }else{ %>
					<a href="javascript:fncGetPurchaseList('<%=resultPage.getEndUnitPage()+1%>')">���� ��</a>
			<% } %>		
		
		</td>
	</tr>	
</table>


<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>