<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<center>
ID : ${wishList.wishListBuyer.userId}<br/>
상품명 : ${wishList.wishListProd.prodName}<br/>
상품가격 : ${wishList.wishListProd.price}<br/>

위시리스트에 추가되었습니다.<br/>
위시리스트로 이동하시겠습니까?<br/>

<a href="/wishList.do">위시리스트로 이동</a>
<a href="/listProduct.do?menu=search"> 계속 쇼핑하기 </a>

</center>

</body>
</html>