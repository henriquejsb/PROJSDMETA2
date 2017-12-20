<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--
  Created by IntelliJ IDEA.
  User: hb
  Date: 19/12/17
  Time: 02:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Votar</title>
    <%--
    Guardar cc da pessoa q ta logada
    Metodo novo no RMI para receber as eleiçoes
    AJAX para receber as listas?
    Enviar voto
    Adicionar WebSockets para dar update às pessoas que votaram até agora


    --%>
    <hi>LISTAS</hi>
    <form id="voto-form" action="votar" method="post">
    <select id="voto" name="voto">

        <c:forEach var="l" items="${meta2Bean.listas}">
            <option value="${l}">
                <c:out value="${l}" />
            </option>
        </c:forEach>
        <option value="BRANCO">BRANCO</option>
        <option value="NULO" selected="selected">NULO</option>
    </select>
        <button type="submit">VOTAR</button>
    </form>

</head>
<body>

</body>
</html>
