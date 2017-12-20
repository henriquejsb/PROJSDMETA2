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
<script>
    // This is called with the results from from FB.getLoginStatus().
    function statusChangeCallback(response) {
        console.log('statusChangeCallback');
        console.log(response);
        // The response object is returned with a status field that lets the
        // app know the current login status of the person.
        // Full docs on the response object can be found in the documentation
        // for FB.getLoginStatus().
        if (response.status === 'connected') {
            // Logged into your app and Facebook.
            testAPI();
        } else {
            // The person is not logged into your app or we are unable to tell.
            document.getElementById('status').innerHTML = 'Please log ' +
                'into this app.';
        }
    }

    // This function is called when someone finishes with the Login
    // Button.  See the onlogin handler attached to it in the sample
    // code below.
    function checkLoginState() {
        FB.getLoginStatus(function(response) {
            statusChangeCallback(response);
        });
    }

    window.fbAsyncInit = function() {
        FB.init({
            appId      : '403906196694892',
            cookie     : true,  // enable cookies to allow the server to access
                                // the session
            xfbml      : true,  // parse social plugins on this page
            version    : 'v2.8' // use graph api version 2.8
        });

        // Now that we've initialized the JavaScript SDK, we call
        // FB.getLoginStatus().  This function gets the state of the
        // person visiting this page and can return one of three states to
        // the callback you provide.  They can be:
        //
        // 1. Logged into your app ('connected')
        // 2. Logged into Facebook, but not your app ('not_authorized')
        // 3. Not logged into Facebook and can't tell if they are logged into
        //    your app or not.
        //
        // These three cases are handled in the callback function.

        FB.getLoginStatus(function(response) {
            statusChangeCallback(response);
        });

    };

    // Load the SDK asynchronously
    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = "https://connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));

    // Here we run a very simple test of the Graph API after login is
    // successful.  See statusChangeCallback() for when this call is made.
    function testAPI() {
        console.log('Welcome!  Fetching your information.... ');
        FB.api('/me', function(response) {
            console.log('Successful login for: ' + response.name);
            document.getElementById('status').innerHTML =
                'Thanks for logging in, ' + response.name + '!';
        });
    }
</script>

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

<fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
</fb:login-button>

<div id="status">
</div>

</body>
</html>
