<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 20/12/2017
  Time: 03:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ver onde uma pessoa votou</title>
    <script src="../js/jquery.js"></script>
    <script src="../js/pessoaVoto.js"></script>

</head>
<body>
<script>
    var user = <c:out value="${meta2Bean.user}"></c:out>;
    var admin = <c:out value="${admin}"></c:out>;
</script>
<c:if test="${!admin}">
    <form id="listas" class="text-left" action="listarEleicoes" method="post">
        <button>Menu</button>
    </form>
</c:if>

<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>

<input type="number" class="form-control"  id="reg_cc1" name="cc" placeholder="CC da pessoa">
<input type="text" class="form-control" required="true" id="reg_nome1" name="eleicao" placeholder="Nome da eleição">

<button id="pesquisa">Ver onde votou</button>

<p id="resultados"></p>


</body>
</html>
