<%--
  Created by IntelliJ IDEA.
  User: hb
  Date: 17/12/17
  Time: 01:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>iVotas</title>
</head>
<body>

<c:forEach var="eleicao" items="${meta2Bean.eleicoes}">
    <c:out value="${eleicao.nome}"></c:out>
</c:forEach>



</body>
</html>
