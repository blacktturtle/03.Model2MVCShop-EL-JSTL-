<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
<title>위시 리스트</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
function fncGetList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}
function allChk(obj) {
	var chkObj = document.getElementsByName("RowCheck");
	var rowCnt = chkObj.length -1;
	var check = obj.checked;
	if(check){
		for (var i = 0; i <= rowCnt; i++) {
			if(chkObj[i].type =="checkbox")
				chkObj[i].checked =true;			
		}
	}else{
		for (var i = 0; i <= rowCnt; i++) {
			if(chkObj[i].type =="checkbox")
				chkObj[i].checked =false;	
	}
}
}

function delWishList() {
	var chk = document.getElementsByName("RowCheck");  //name="pidx1" 값을 모두 가져옴.
	var data = "";
	var chk_check = false;

	 for (i=0;i<chk.length ;i++ )                   //for 문으로 돌리고..
	 {
	 if (chk[i].checked == true )               //checked 된 것만
	 {
	  data = data + "/ " +chk[i].value;            // 값을 가져와서 data 넣는다.
	     if(chk[i].checked) chk_check = true;    // 하나라도 체크 됐다면 chk_check = true 값 반환
	 }
	}
	 if (chk_check)              // chk_check 값이 true 라면
	 {
	    if(confirm("삭제하시겠습니까?")){
	   alert("넘어가는 값은="+data);
	   document.deleteForm.action = "/deleteWishList.do?wishNo2="+data;
	   document.deleteForm.submit();
	    }
	 }else{                         // chk_check 값이 false 라면
	  alert('하나이상을 체크하여 주십시오');
	  return;
	 }
}

</script>

</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/wishList.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">위시 리스트</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage } 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">
		<input id="allCheck" type="checkbox" onclick="allChk(this);"/>
		</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">상품가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>
		<td class="ct_line02"></td>

	</tr>
	<tr>
		<td colspan="20" bgcolor="808285" height="1"></td>
	</tr>
	
		
		<c:set var="i" value="0"/>
		<c:forEach var="list" items="${list}">
		<c:set var="i" value="${i+1}"/>
	
	<tr class="ct_list_pop">
	
	
	<td align="center">
		<input name="RowCheck" type="checkbox" value="${list.wishNo}"/>
		</td>
		<td></td>
		
	<td align="center">
		${i}
		</td>
		<td></td>
		
	<td align="center">
		${list.wishListBuyer.userId}
		</td>
		<td></td>
		
		<td align="center">
		<a href ="/getProduct.do?menu=completeSearch&prodNo=${list.wishListProd.prodNo}">${list.wishListProd.prodName}</a>
		</td>
		<td></td>

		<td align="center">${list.wishListProd.price}</td>
		<td></td>
		
		<td align="center">
		<c:if test="${list.wishIsPurchaseCode=='0' }">
			<a href="addPurchaseView.do?prodNo=${list.wishListProd.prodNo}">구매하러 가기</a>
		</c:if>
		<c:if test="${list.wishIsPurchaseCode!='0' }">
			구매하신 상품입니다.
		</c:if>
		
		</td>
		
		<tr >
		<td colspan="20" bgcolor="D6D7D6" height="1"></td>
		</tr>
	
		</c:forEach>
		
		
	
</table>

		
<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top:10px;">
	<tr>
					
		<td align="center">
		   <input type="hidden" id="currentPage" name="currentPage" value="${resultPage.currentPage}"/>
				<jsp:include page="../common/pageNavigator.jsp"></jsp:include>		
		</td>
	</tr>	
</table>


					</table>
					

<!--  페이지 Navigator 끝 -->
</form>

<form name="deleteForm" method="post">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
						<a href="javascript:delWishList();">삭제</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
					</tr>
					</table>
				</form>	

</div>

</body>
</html>