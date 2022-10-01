<%-- 
    Document   : erro
    Created on : Apr 16, 2022, 8:05:35 AM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page isErrorPage="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Erro</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <div class="row">
            <div class="col-md-12">
                <h3 class="text-center" style="margin-top: 5%;">Ops... algo de errado aconteceu :(</h3>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
              <p class="text-center" style="margin-top: 10%;">${requestScope.mensagem}</p>  
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <a href="${requestScope.voltar}" class="text-info" style="margin-left: 70%;">Voltar</a>
            </div>
        </div>
    </body>
</html>
