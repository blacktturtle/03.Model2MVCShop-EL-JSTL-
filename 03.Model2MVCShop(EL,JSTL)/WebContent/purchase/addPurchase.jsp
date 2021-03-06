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

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${vo.purchaseProd.prodNo}</td>
<%-- 		<td><%=vo.getPurchaseProd().getProdNo() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${vo.buyer.userId}</td>
<%-- 		<td><%=vo.getBuyer().getUserId() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>구매수량</td>
		<td>${vo.quantity} 개</td>
<%-- 		<td><%=vo.getBuyer().getUserId() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		
		<c:if test="${vo.paymentOption =='1' }">
		${"현금구매"}		
		</c:if>
		<c:if test="${vo.paymentOption !='1' }">
		${"신용구매"}		
		</c:if>
<%-- 		<%	if(vo.getPaymentOption().equals(1)){%>
			<%="현금구매" %>
			<%}else{ %>
					<%="신용구매" %>
			<%} %> --%>
	
		
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${vo.receiverName}</td>
<%-- 		<td><%=vo.getReceiverName() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${vo.receiverPhone}</td>
<%-- 		<td><%=vo.getReceiverPhone() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${vo.divyAddr}</td>
<%-- 		<td><%=vo.getDivyAddr() %></td> --%>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${vo.divyRequest}</td>
<%-- 		<td><%=vo.getDivyRequest() %></td> --%>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${vo.divyDate}</td>
<%-- 		<td><%=vo.getDivyDate() %></td> --%>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>