<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <script src="../js/jquery.js"></script>
    <script src="../js/WebSocket.js"></script>
    <title>iVotas</title>
</head>
<body>
<a href="menu">Menu</a>
<p id="stats">
    <c:out value="${meta2Bean.liveStats}"></c:out>
</p>

<p id="cenas">

</p>

</body>
</html>
