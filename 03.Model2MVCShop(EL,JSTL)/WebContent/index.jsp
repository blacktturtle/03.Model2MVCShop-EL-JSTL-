<%@ page contentType="text/html; charset=euc-kr" %>

<html>
<head>

<style>

frame{
    background: url("/images/kappa.jpg") no-repeat center center fixed; 
    -webkit-background-size: 500;
    -moz-background-size: 500;
    -o-background-size: 500;
    background-size: 1000;  
}
body{
    background: url("/images/kappa.jpg") no-repeat center center fixed; 
    -webkit-background-size: 500;
    -moz-background-size: 500;
    -o-background-size: 500;
    background-size: 1000;     
} 
</style>
<title>Model2 MVC Shop</title>
</head>


<frameset rows="80,*" cols="*" frameborder="NO" border="0" framespacing="0">
  
  <frame src="/layout/top.jsp" name="topFrame" scrolling="NO" noresize >
  
  <frameset rows="*" cols="160,*" framespacing="0" frameborder="NO" border="0">
    <frame src="/layout/left.jsp" name="leftFrame" scrolling="NO" noresize>
    <frame src="/images/kappa.jpg" name="rightFrame"  scrolling="auto" value = "" >
  </frameset>

</frameset>

<noframes>
<body>

</body>
</noframes>

</html>