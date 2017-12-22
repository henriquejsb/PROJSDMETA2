<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 21/12/2017
  Time: 20:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Desassociar conta do facebook</title>
</head>
<body>


<div class="register-form-1">
    <form id="create-form" class="text-left" action="desassociarFb" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">

                <div class="form-group">
                    <label for="reg_numero" class="sr-only">Número CC: </label>
                    <input type="number" class="form-control" size="8" required="true" id="reg_numero" name="numerocc" placeholder="Número CC">
                </div>

            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Desassociar:</button>
        </div>
    </form>
</div>
</body>
</html>
