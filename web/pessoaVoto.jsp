<%--
  Created by IntelliJ IDEA.
  User: Elsa Diogo
  Date: 20/12/2017
  Time: 03:07
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ver onde uma pessoa votou</title>
    <script src="js/jquery.min.js"></script>
    <script  type="text/javascript">
        $.ajax ({
            url: '<s:url action="callAction"/>',
            type: 'POST',
            dataType: 'text',
            success: function (data) {
                $("#msgid").html(data);
                console.log(data);
            }
        });

    </script>
</head>
<body>
<div class="register-form-1">
    <form id="create-form" class="text-left" action="callAction" method="post">
        <div class="create-form-main-message"></div>
        <div class="main-create-form">
            <div class="create-group">
                <div class="form-group">
                    <label for="reg_nome" class="sr-only">CC: </label>
                    <input type="number" class="form-control" required="true" id="reg_nome" name="cc" placeholder="CC da pessoa">
                </div>

                <div class="form-group">
                    <label for="reg_nome1" class="sr-only">Eleição: </label>
                    <input type="text" class="form-control" required="true" id="reg_nome1" name="eleicao" placeholder="Nome da eleição">
                </div>

            </div>
            <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i>Criar</button>
        </div>
    </form>
    <div id="msgid">
    </div>
</div>

</body>
</html>
