<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <script src="../js/jquery.js"></script>
    <script src="../js/WebSocket.js"></script>
</head>
<body>

<center>iVotas - Menu</center>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">
                <ul class="nav navbar-nav">

                    <li><a href="paginaRegisto">Registar pessoa</a></li>
                    <li><a href="paginaFaculdade">Criar faculdade</a></li>
                    <li><a href="paginaDepartamento">Criar departamento</a></li>
                    <li><a href="novaEleicao">Criar eleição</a></li>
                    <li><a href="editEleicao">Editar eleição</a></li>
                    <li><a href="novaLista">Criar lista de candidatos</a></li>
                    <li><a href="associaCandidato">Associar candidato a uma lista</a></li>
                    <li><a href="novaMesa">Adicionar mesa de voto a uma eleição</a></li>
                    <li><a href="details">Consultar detalhes de eleições passadas</a></li>
                    <li><a href="verVoto">Ver onde votou uma pessoa para uma eleição</a></li>
                    <li><a href="listarEleicoes">Listar eleições</a></li>


                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <form action="logout" method="post">
                        <button type="submit"><span class="glyphicon glyphicon-log-in"></span><font size="6">  Logout   </font></button>
                    </form>
                </ul>

    </div>
    <p id="stats">
        <c:out value="${meta2Bean.liveStats}"></c:out>
    </p>

</nav>



<form id = "loginForm" action="addFb" method="post">
    <button action="submit"><img src="http://www.nnnever.com/images/facebook-login-button.png" width="200px" height="auto"></button>

</form>


<form id = "dessFb" action="desassociarFbAd" method="post">
    <button action="submit">Desassociar conta de Facebook</button>
</form>


</body>
</html>
