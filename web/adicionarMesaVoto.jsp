<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adicionar Mesa de Voto</title>
</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="adicionarMesaVoto" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                    <label for="reg_nome" class="sr-only">Eleição: </label>
                    <input type="text" class="form-control" required="true" id="reg_nome" name="eleicao" placeholder="Nome">
                </div>

                <div class="form-group">
                    <label for="reg_dep" class="sr-only">Departamento a adicionar: </label>
                    <input type="text" class="form-control" required="true" id="reg_dep" name="departamento" placeholder="Nome do departamento">
                </div>


            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
</div>

</body>
</html>
