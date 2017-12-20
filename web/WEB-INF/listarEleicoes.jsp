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
    <title>iVotas</title>
</head>
<body>
<p>OI</p>


<% int conta = 0 ;%>
 <c:forEach var="eleicao" items= "${meta2Bean.eleicoes}">
     <% conta+= 1 ;%>
    <a href="eleicoes?eleicao=${eleicao}">
        <c:out value="${eleicao}"></c:out>
    </a><br>
</c:forEach>

${conta > 0 ? "" : "Não há eleições disponíveis"}

</body>
</html>
