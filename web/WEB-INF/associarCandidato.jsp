<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 23:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="associarCandidato" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                    <label for="reg_nome" class="sr-only">Eleicao a associar: </label>
                    <input type="text" class="form-control" required="true" id="reg_nome" name="eleicao" placeholder="Nome">
                </div>

                <div class="form-group">
                    <label for="reg_lista" class="sr-only">Nome da lista: </label>
                    <input type="text" class="form-control" required="true" id="reg_lista" name="lista" placeholder="Nome">
                </div>

                <div class="form-group">
                    <label for="reg_cc" class="sr-only">CC da pessoa: </label>
                    <input type="number" class="form-control" required="true" id="reg_cc" name="cc" placeholder="cc">
                </div>


            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
</div>

</body>
</html>
