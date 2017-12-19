<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Editar eleição</title>
</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="editarEleicao" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                <label for="reg_nome" class="sr-only">Eleição a editar: </label>
                <input type="text" class="form-control" required="true" id="reg_nome" name="eleiEdit" placeholder="Nome">
                </div>

                <div class="form-group">
                    <label for="reg_novonome" class="sr-only">Novo nome: </label>
                    <input type="text" class="form-control" required="true" id="reg_novonome" name="novoNome" placeholder="Nome">
                </div>

                <div class="form-group">
                    <label for="reg_desc" class="sr-only">Nova descrição: </label>
                    <input type="text" class="form-control" required="true" id="reg_desc" name="descricao" placeholder="Descrição">
                </div>

            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
</div>

</body>
</html>
