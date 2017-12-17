<%--
  Created by IntelliJ IDEA.
  User: hb
  Date: 15/12/17
  Time: 23:03
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<html>
  <head>
    <title>Login</title>
  </head>
  <body>
<form id = "loginForm" action="login" method="post">
  <div class="form-group">
    <label for="lg_username" class="sr-only">Username: </label>
    <input type="number" class="form-control" required="true" id="lg_username" name="cc" placeholder="cc">
  </div>
  <div class="form-group">
    <label for="lg_password" class="sr-only">Password: </label>
    <input type="password" class="form-control" required="true" id="lg_password" name="password" placeholder="password">
  </div>
  <a href="register.jsp">Registar um maninho</a>
<button action="submit">Login</button>
</form>
  </body>
</html>
