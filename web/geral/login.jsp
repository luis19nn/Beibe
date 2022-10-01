<%-- 
    Document   : login
    Created on : Apr 17, 2022, 8:55:47 AM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Login</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <div id="login" style="margin-top: 13%;">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <form id="login-form" class="form" action="${pageContext.request.contextPath}/PessoaServlet?acao=efetuarlogin" method="post">
                                <h3 class="text-center text-success">Login</h3>
                                <p class="text-danger">${param.cadastradoSucesso}</p>
                                <div class="form-group">
                                    <label for="username" class="text-success">Email:</label><br>
                                    <input type="text" name="username" id="username" class="form-control">
                                </div>
                                <div class="form-group">
                                    <label for="password" class="text-success">Senha:</label><br>
                                    <input type="password" name="password" id="password" class="form-control">
                                </div>
                                <div class="form-group">
                                    <a href="${pageContext.request.contextPath}/PessoaServlet?acao=autocadastro" class="text-info">Criar conta</a>
                                    <input id="submit" type="submit" name="submit" class="btn btn-success btn-md" value="Entrar" style="position: absolute; right: 15px;">
                                </div>
                                <p class="text-danger">${requestScope.aviso}</p>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
