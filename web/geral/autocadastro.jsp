<%-- 
    Document   : autocadastro
    Created on : Apr 11, 2022, 7:49:30 PM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Cadastro</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
    </head>
    <body>
        <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <!-- TO-DO: Revisar o action para implementar usando tag de contexto -->
                            <form id="login-form" class="form" action="${pageContext.request.contextPath}/PessoaServlet?acao=cadastrar&id=id=${requestScope.clienteeditar.id}" method="post">
                                <h3 class="text-center text-success">Cadastro</h3>
                                <p class="text-danger">${requestScope.preenchimentoIncompleto}</p>
                                <div class="form-group">
                                    <label for="username" class="text-success">Nome:</label><br>
                                    <input type="text" name="nome" id="username" class="form-control" value="${param.nome}">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="cpf" class="text-success">CPF:</label><br>
                                            <input type="text" name="cpf" id="cpf" class="form-control" value="${param.cpf}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="telefone" class="text-success">Telefone:</label><br>
                                            <input type="tel" name="telefone" id="telefone" class="form-control" value="${param.telefone}">
                                        </div>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="email" class="text-success">Email:</label><br>
                                    <input type="text" name="email" id="email" class="form-control" value="${param.email}">
                                </div>
                                <div class="form-group">
                                    <label for="password" class="text-success">Senha:</label><br>
                                    <input type="password" name="password" id="password" class="form-control" value="${param.password}">
                                </div>
                                <div class="form-group">
                                    <label for="password-confirm" class="text-success">Confirmar Senha:</label><br>
                                    <input type="password" name="passwordconfirm" id="password-confirm" class="form-control" value="${param.passwordconfirm}">
                                    <p class="text-danger">${requestScope.senhaIncoerente}</p>
                                </div>
                                <div class="form-group">
                                    <label for="endereco" class="text-success">Endereco:</label><br>
                                    <input type="text" name="endereco" id="endereco" class="form-control" value="${param.endereco}">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="numero" class="text-success">Numero:</label><br>
                                            <input type="number" name="numero" id="numero" class="form-control" value="${param.numero}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="complemento" class="text-success">Complemento:</label><br>
                                            <input type="text" name="complemento" id="complementp" class="form-control" value="${param.complemento}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="bairro" class="text-success">Bairro:</label><br>
                                            <input type="text" name="bairro" id="bairro" class="form-control" value="${param.bairro}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="cep" class="text-success">CEP:</label><br>
                                            <input type="text" name="cep" id="cep" class="form-control" value="${param.cep}">
                                        </div>
                                    </div>

                                </div>
                                 <div class="form-group">
                                    <label for="estado" class="text-success">Estado:</label><br>
                                    <select name="estado" id="estado" class="form-control" value="${param.estado}">
                                        <c:forEach items="${estados}" var="estado">
                                            <option value="${estado.id}"  ${param.estado == estado.id ? 'selected="true"' : '' } >${estado.nome} - ${estado.sigla}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="cidade" class="text-success">Cidade:</label><br>
                                    <select name="cidade" id="cidade" class="form-control">
                                        <c:forEach items="${cidades}" var="cidade">
                                            <option value="${cidade.id}" ${param.cidade == cidade.id ? 'selected="true"' : '' }>${cidade.nome}</option>   
                                        </c:forEach>
                                    </select>
                                </div>  
                                <div class="form-group">
                                    <a href="${pageContext.request.contextPath}/PessoaServlet?acao=login" class="text-info">Voltar</a>
                                    <input type="submit" name="submit" class="btn btn-success btn-md" value="Criar Conta" style="position: absolute; right: 15px;">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<script>
    $("#telefone")
        .mask("(99) 9999-9999?9")
        .focusout(function (event) {  
            var target, phone, element;  
            target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
            phone = target.value.replace(/\D/g, '');
            element = $(target);  
            element.unmask();  
            if(phone.length > 10) {  
                element.mask("(99) 99999-999?9");  
            } else {  
                element.mask("(99) 9999-9999?9");  
            }  
        });
        
    $("#cep").mask("99999-999");
    
    $("#cpf").mask("999.999.999-99");
        
</script>

