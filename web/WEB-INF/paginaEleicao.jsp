<%--
  Created by IntelliJ IDEA.
  User: hb
  Date: 20/12/17
  Time: 19:04
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Eleição</title>
    <script src="../js/jquery.js"></script>
    <script src="../js/WebSocketEleicao.js"></script>
</head>
<body>

<script>
    var elei = "<c:out value="${meta2Bean.eleicao}"></c:out>";

</script>
<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>
<c:if test="${!admin}">
    <form id="listas" class="text-left" action="listarEleicoes" method="post">
        <button>Menu</button>
    </form>
</c:if>


<c:forEach var="campo" items= "${meta2Bean.infoEleicao}">


        <c:out value="${campo}"></c:out>
    <br>
</c:forEach>

<p id="stats">

</p>



<c:if test="${!admin}">
    <form id = "loginForm" action="postFb" method="post">
        <button action="submit">Partilhar página da eleição do facebook</button>
    </form>

    <form id="votar" class="text-left" action="paginaVoto" method="post">
        <button>Votar</button>
    </form>
</c:if>

</body>
</html>
