<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
    <title>Title</title>
</head>
<body>
<p>ERRO</p>
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
<c:out value="${meta2Bean.erro}"/>
<p><s:property value="exceptionStack" /></p>
</body>
</html>
