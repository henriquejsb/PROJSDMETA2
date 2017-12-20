<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 21:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Criar Eleição</title>


</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="criarEleicao" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">

                Tipo de Eleição:
                <s:select id="tipoElei" name="tipoElei"
                          list="{'Núcleo','Conselho Geral'}" label="Tipo de eleição" required="true"  />


                <div class="form-group">
                    <label for="reg_nome" class="sr-only">Nome da Eleição:</label>
                    <input type="text" class="form-control" required="true" id="reg_nome" name="eleicao" placeholder="nome">
                </div>
                <div class="form-group">
                    <label for="reg_descricao" class="sr-only">Descrição: </label>
                    <input type="text" class="form-control" required="true" id="reg_descricao" name="descricao" placeholder="Descrição breve">
                </div>
                <div class="form-group">
                    <label for="reg_departamento" class="sr-only">Departamento: </label>
                    <input type="text" class="form-control" required="true" id="reg_departamento" name="departamento" placeholder="Departamento(eleiçoes cg não precisam editr">
                </div>

                Data de ínicio:
                <div class="form-group">
                    <label for="reg_anoI" class="sr-only">Ano: </label>
                    <input type="number" min="2018"  class="form-control" required="true" id="reg_anoI" name="anoInicio" placeholder="Ano">
                </div>

                <div class="form-group">
                    <label for="reg_mesI" class="sr-only">Mês: </label>
                    <input type="number" min="1" max="12" class="form-control" required="true" id="reg_mesI" name="mesInicio" placeholder="Mês">
                </div>

                <div class="form-group">
                    <label for="reg_diaI" class="sr-only">Dia: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_diaI" name="diaInicio" placeholder="Dia">
                </div>
                <div class="form-group">
                    <label for="reg_horas" class="sr-only">Hora: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_horas" name="horaInicio" placeholder="Hora">
                </div>
                <div class="form-group">
                    <label for="reg_minutos" class="sr-only">Dia: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_minutos" name="minutosInicio" placeholder="Minuto">
                </div>

                Data de fim:
                <div class="form-group">
                    <label for="reg_anoF" class="sr-only">Ano: </label>
                    <input type="number" min="2018"  class="form-control" required="true" id="reg_anoF" name="anoFim" placeholder="Ano">
                </div>

                <div class="form-group">
                    <label for="reg_mesF" class="sr-only">Mês: </label>
                    <input type="number" min="1" max="12" class="form-control" required="true" id="reg_mesF" name="mesFim" placeholder="Mês">
                </div>

                <div class="form-group">
                    <label for="reg_diaF" class="sr-only">Dia: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_diaF" name="diaFim" placeholder="Dia">
                </div>
                <div class="form-group">
                    <label for="reg_horasF" class="sr-only">Hora: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_horasF" name="horaFim" placeholder="Hora">
                </div>
                <div class="form-group">
                    <label for="reg_minutosF" class="sr-only">Dia: </label>
                    <input type="number" min="1" max="31" class="form-control" required="true" id="reg_minutosF" name="minutosFim" placeholder="Minuto">
                </div>
            </div>
            <button type="submit" class="submit-button"><i class="fa fa-chevron-right"></i>Criar</button>


        </div>
    </form>
</div>

</body>
</html>
