<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 17/12/2017
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu Admin</title>
</head>
<body>
<center>iVotas - Menu</center>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
                <ul class="nav navbar-nav">
                    <li><a href="register.jsp">Registar pessoa</a></li>
                    <li><a href="criarFaculdade.jsp">Criar faculdade</a></li>
                    <li><a href="criarDepartamento.jsp">Criar departamento</a></li>
                    <li><a href="criarEleicao.jsp">Criar eleição</a></li>
                    <li><a href="editarEleicao.jsp">Editar eleição</a></li>
                    <li><a href="criarLista.jsp">Criar lista de candidatos</a></li>
                    <li><a href="associarCandidato.jsp">Associar candidato a uma lista</a></li>
                    <li><a href="adicionarMesaVoto.jsp">Adicionar mesa de voto a uma eleição</a></li>
                    <li><a href="consultarDetalhesEleicao.jsp">Consultar detalhes de eleições passadas</a></li>
                    <li><a href="modoLive.jsp">Modo live</a></li>
                    <li><a href="pessoaVoto.jsp">Ver onde votou uma pessoa para uma eleição</a></li>

                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <form action="logout" method="post">
                        <button type="submit"><span class="glyphicon glyphicon-log-in"></span><font size="6">  Logout   </font></button>
                    </form>
                </ul>

    </div>
</nav>


</body>
</html>
