<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@page import="com.model2.mvc.service.domain.*"%>
<%Purchase vo = (Purchase)request.getAttribute("vo"); %> --%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${vo.purchaseProd.prodNo}</td>
<%-- 		<td><%=vo.getPurchaseProd().getProdNo() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${vo.buyer.userId}</td>
<%-- 		<td><%=vo.getBuyer().getUserId() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<td>
		
		<c:if test="${vo.paymentOption =='1' }">
		${"���ݱ���"}		
		</c:if>
		<c:if test="${vo.paymentOption !='1' }">
		${"�ſ뱸��"}		
		</c:if>
<%-- 		<%	if(vo.getPaymentOption().equals(1)){%>
			<%="���ݱ���" %>
			<%}else{ %>
					<%="�ſ뱸��" %>
			<%} %> --%>
	
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${vo.receiverName}</td>
<%-- 		<td><%=vo.getReceiverName() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${vo.receiverPhone}</td>
<%-- 		<td><%=vo.getReceiverPhone() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${vo.divyAddr}</td>
<%-- 		<td><%=vo.getDivyAddr() %></td> --%>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${vo.divyRequest}</td>
<%-- 		<td><%=vo.getDivyRequest() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${vo.divyDate}</td>
<%-- 		<td><%=vo.getDivyDate() %></td> --%>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>