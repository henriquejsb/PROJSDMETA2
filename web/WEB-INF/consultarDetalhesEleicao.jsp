<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 18/12/2017
  Time: 00:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <script src="../js/jquery.js"></script>
    <script src="../js/detalhesEleicao.js"></script>
    <title>Title</title>



</head>
<body>


<input type="text" class="form-control" required="true" id="reg_eleicao" name="eleicao" placeholder="Nome da eleição">

<button id="pesquisa">Consultar detalhes</button>

<p id="resultados"></p>


</body>
</html>
