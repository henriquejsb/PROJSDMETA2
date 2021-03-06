<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 23:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
    <title>Criar Lista</title>
</head>
<body>
<c:if test="${admin}">
    <form id="menu" class="text-left" action="menu" method="post">
        <button>Menu</button>
    </form>
</c:if>
        <form id="create-form" class="text-left" action="criarLista" method="post">

                    Tipo de Lista:
                    <s:select id="tipoLista" name="tipoLista"
                              list="{'Alunos','Docentes','Funcionários'}" label="Tipo de lista" required="true"  />



                    <div class="form-group">
                        <label for="reg_eleicao" class="sr-only">Eleição a associar: </label>
                        <input type="text" class="form-control" required="true" id="reg_eleicao" name="eleicao" placeholder="Nome da eleição">
                    </div>

                    <div class="form-group">
                        <label for="reg_lista" class="sr-only">Nome: </label>
                        <input type="text" class="form-control" required="true" id="reg_lista" name="lista" placeholder="Nome da lista">
                    </div>

                </div>
                <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
            </div>
        </form>
    </div>
</body>
</html>
