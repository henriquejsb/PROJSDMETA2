<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 16/12/2017
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Registo</title>
</head>
<body>

<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>
<div class="text-center" style="padding:50px 0">
    <div class="logo">Registo</div>
    <!-- Main Form -->
    <div class="register-form-1">
        <form id="register-form" class="text-left" action="register" method="post">
            <div class="login-form-main-message"></div>
            <div class="main-login-form">
                <div class="login-group">

                    Tipo de Pessoa
                    <s:select id="tipo" name="tipo"
                              list="{'Alunos','Docentes','Funcionários'}" label="Tipo de Pessoa" required="true"  />
                    <div class="form-group">
                        <label for="reg_nome" class="sr-only">Nome:: </label>
                        <input type="text" class="form-control" required="true" id="reg_nome" name="nome" placeholder="Nome">
                    </div>

                    <div class="form-group">
                        <label for="reg_numero" class="sr-only">Número CC: </label>
                        <input type="number" class="form-control" size="8" required="true" id="reg_numero" name="numerocc" placeholder="Número CC">
                    </div>

                    <div class="form-group">
                        <label for="reg_password" class="sr-only">Password: </label>
                        <input type="password" class="form-control" required="true" id="reg_password" name="password" placeholder="Password">
                    </div>

                    <div class="form-group">
                        <input type="password" class="form-control" required="true" id="reg_password_confirm" name="cpassword" placeholder="Confirm password">
                    </div>

                    <div class="form-group">
                        <label for="reg_morada" class="sr-only">Morada: </label>
                        <input type="text" class="form-control" required="true" id="reg_morada" name="morada" placeholder="morada">
                    </div>


                    <div class="form-group">
                        <label for="reg_numeroT" class="sr-only">Número de Telefone: </label>
                        <input type="number" class="form-control" size="9" required="true" id="reg_numeroT" name="telefone" placeholder="Número de Telefone">
                    </div>

                    Data de validade do CC:

                    <div class="form-group">
                        <label for="reg_ano" class="sr-only">Ano: </label>
                        <input type="number" min="2018"  class="form-control" required="true" id="reg_ano" name="ano" placeholder="Ano">
                    </div>

                    <div class="form-group">
                        <label for="reg_mes" class="sr-only">Mês: </label>
                        <input type="number" min="1" max="12" class="form-control" required="true" id="reg_mes" name="mes" placeholder="Mês">
                    </div>

                    <div class="form-group">
                        <label for="reg_dia" class="sr-only">Dia: </label>
                        <input type="number" min="1" max="31" class="form-control" required="true" id="reg_dia" name="dia" placeholder="Dia">
                    </div>

                    <div class="form-group">
                        <label for="reg_dep" class="sr-only">Departamento: </label>
                        <input type="text" class="form-control" required="true" id="reg_dep" name="departamento" placeholder="Departamento">
                    </div>



                </div>
                <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Registar</button>
            </div>
        </form>
    </div>
</div>
<!-- end:Main Form -->
</body>
</html>