<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%--
  Created by IntelliJ IDEA.
  User: hb
  Date: 17/12/17
  Time: 01:15
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <script src="../js/jquery.js"></script>
    <title>iVotas</title>
</head>
<body>

<c:if test="${!admin}">
<center>iVotas - Menu</center>
</c:if>


<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>
<script>
    var conta = 0;
</script>
 <c:forEach var="eleicao" items= "${meta2Bean.eleicoes}">
     <script>conta += 1;</script>
    <a href="eleicoes?eleicao=${eleicao}">
        <c:out value="${eleicao}"></c:out>
    </a><br>
</c:forEach>

<p id="nha"></p>
<script>
    if(conta == 0){
        $("#nha").html("Não há eleições disponíveis!");
    }
</script>



<c:if test="${!admin}">

<form id = "loginForm" action="addFb" method="post">
    <button action="submit"><img src="http://www.nnnever.com/images/facebook-login-button.png" width="200px" height="auto"></button>


</form>


<li><a href="verVoto">Consultar seu voto</a></li>


<ul class="nav navbar-nav navbar-right">
    <form action="logout" method="post">
        <button type="submit"><span class="glyphicon glyphicon-log-in"></span><font size="6">  Logout   </font></button>
    </form>
</ul>

</c:if>

</body>
</html>
