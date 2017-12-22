<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 19:27
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Criar Faculdade</title>
</head>
<body>

<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="criarFaculdade" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                    <label for="reg_nome" class="sr-only">Nome da Faculdade: </label>
                    <input type="text" class="form-control" required="true" id="reg_nome" name="faculdade" placeholder="Nome">
                </div>

            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
</div>
</body>
</html>
