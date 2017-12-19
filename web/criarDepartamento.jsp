<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Criar Departamento</title>
</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="criarDepartamento" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                    <label for="reg_nomeF" class="sr-only">Faculdade correspondente: </label>
                    <input type="text" class="form-control" required="true" id="reg_nomeF" name="faculdade" placeholder="Nome da faculdade">
                </div>

                <div class="form-group">
                    <label for="reg_nomeD" class="sr-only">Nome do Departamento: </label>
                    <input type="text" class="form-control" required="true" id="reg_nomeD" name="departamento" placeholder="Novo departamento">
                </div>

            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
</div>
</body>
</html>
