<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%--  <%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>
<%@ page import="com.model2.mvc.common.util.*" %>
<%@page import="com.model2.mvc.service.purchase.dao.PurchaseDAO"%>
<%@page import="com.model2.mvc.service.purchase.impl.PurchaseServiceImpl"%>
<%@page import="com.model2.mvc.service.purchase.PurchaseService"%>
<%	
	List<Product> list= (List<Product>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	Search search=(Search)request.getAttribute("search"); 
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
%> --%>


<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
</script>




</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-center:10px;">


<form name="detailForm" action="/listProduct.do?menu=search"method="post">
<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-center:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01" align="center">상품 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-center:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					
					<td width="90%" class="ct_ttl01" align="right">높은가격순(<input type="radio" name="check"  value="1"  ${search.priceUpDown=='1' ?"checked":"" }/>)</td>
					<td width="10%" class="ct_ttl01" align="right">낮은가격순(<input type="radio" name="check" value="2"  ${search.priceUpDown=='2' ?"checked":"" }/>)</td>
				</tr>
				
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>

		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			
			<c:if test="${user.role =='admin'}">
				<option value="0" ${search.searchCondition =='0'?"selected":"" }>상품번호</option>
				<option value="1" ${search.searchCondition =='1'?"selected":"" }>상품명</option>
				<option value="2" ${search.searchCondition =='2'?"selected":"" }>상품가격</option>
			</c:if>
			<c:if test="${user.role =='user'}">
				<option value="1" ${search.searchCondition =='1'?"selected":"" }>상품명</option>
				<option value="2" ${search.searchCondition =='2'?"selected":"" }>상품가격</option>
			</c:if>
		
			
			<%-- <%User user = (User)session.getAttribute("user"); 
			if(user.getRole().trim().equals("admin")){%>
				<option value="0"<%=searchCondition.equals("0")? "selected":""%>>상품번호</option>
				<option value="1"<%=searchCondition.equals("1")? "selected":""%>>상품명</option>
				<option value="2"<%=searchCondition.equals("2")? "selected":""%>>상품가격</option>
				<%}else{ %>
				<option value="1"<%=searchCondition.equals("1")? "selected":""%>>상품명</option>
				<option value="2"<%=searchCondition.equals("2")? "selected":""%>>상품가격</option>
				<%} %> --%>
						
			</select>
			<%-- <input type="text" name="searchKeyword"  value ="<%=searchKeyword %>"class="ct_input_g" style="width:200px; height:19px"/> --%>
			
			
	<input type="text" name="searchKeyword"  value ="${search.searchKeyword}"class="ct_input_g" style="width:200px; height:19px"/>
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
<%-- 		<td colspan="11" >전체 <%=resultPage.getTotalCount() %> 건수, 현재 <%=resultPage.getCurrentPage() %> 페이지</td> --%>
		<td colspan="11" >전체 ${resultPage.totalCount} 건수, 현재 ${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">수량</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<%-- <% 	
		for(int i=0; i<list.size(); i++) {
			Product vo = (Product)list.get(i);
	%>
		
			<%		
		PurchaseDAO purchaseDAO = new PurchaseDAO();
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		System.out.println("Prod_no: " +vo.getProdNo());
		Purchase purchase =  purchaseService.getPurchase2(vo.getProdNo());
		System.out.println("코드 : " + purchase.getTranCode());		
		%> --%>
	<c:set var="i" value="0"/>
	<c:forEach var="product" items="${list}">
	<c:set var="i" value="${i+1}"/>
	<tr class="ct_list_pop">
		<td align="center">${i}</td>
		<td></td>
				<td align="center">
				<c:if test="${product.quantity!='0'}">
					<a href="/getProduct.do?prodNo=${product.prodNo}&menu=search">${product.prodName}</a>
				</c:if>
			
				<c:if test="${product.quantity=='0' }">
					<a href="/getProduct.do?prodNo=${product.prodNo}&menu=completeSearch">${product.prodName}</a>
					</c:if>
					<%-- <%} %> --%>
				</td>
		
		<td></td>
		<td align="center">${product.price}</td>
		<td></td>
		<td align="center">${product.quantity}</td>
		<td></td>
		<td align="center">${product.regDate}</td>
		<td></td>
		<td align="center">
		
		<c:if test="${product.quantity=='0' }">
		 	재고없음
		 </c:if>
		<c:if test="${product.quantity !='0' }">
		 	판매중
		 </c:if>
		
		
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<%-- <%} %>	 --%>
</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
			<%-- <% if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
					◀ 이전
			<% }else{ %>
					<a href="javascript:fncGetUserList('<%=resultPage.getBeginUnitPage()-1%>')">◀ 이전</a>
			<% } %>

			<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
					<a href="javascript:fncGetUserList('<%=i %>');"><%=i %></a>
			<% 	}  %>
	
			<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
					이후 ▶
			<% }else{ %>
					<a href="javascript:fncGetUserList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
			<% } %>
		 --%>
		 <jsp:include page="../common/pageNavigator.jsp"></jsp:include>
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
