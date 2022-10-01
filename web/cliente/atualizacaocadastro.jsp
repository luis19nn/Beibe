<%-- 
    Document   : atualizacaocadastro
    Created on : Apr 21, 2022, 12:05:24 PM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.login == null }" >
    <c:set var="aviso" value="Usuário deve se autenticar para acessar o sistema." scope="request" />
    <jsp:forward page="/geral/login.jsp" />
</c:if>
<c:if test="${ sessionScope.login != null }" >
    <c:if test="${ sessionScope.login.tipoUsuario != 'Cliente' }" >
        <c:set var="mensagem" value="Erro: Usuário não possui permissão para acessar essa página." scope="request" />
        <c:set var="voltar" value="${pageContext.request.contextPath}/geral/login.jsp" scope="request" />
        <jsp:forward page="/geral/erro.jsp" />
    </c:if>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Atualização de Cadastro</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <body>
            <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
                <div class="container">
                    <div id="login-row" class="row justify-content-center align-items-center">
                        <div id="login-column" class="col-md-6">
                            <div id="login-box" class="col-md-12">
                                <form id="login-form" class="form" action="${pageContext.request.contextPath}/ClienteServlet?acao=atualizar" method="post">
                                    <h3 class="text-center text-success">Atualização de Cadastro</h3>
                                    <p class="text-danger">${requestScope.preenchimentoIncompleto}</p>
                                    <div class="form-group">
                                        <label for="username" class="text-success">Nome:</label><br>
                                        <input type="text" name="nome" id="username" class="form-control" value="${sessionScope.usuarioedicao.nomeCompleto}">
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="cpf" class="text-success">CPF:</label><br>
                                                <input type="text" name="cpf" id="cpf" class="form-control" value="${sessionScope.usuarioedicao.cpf}">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="telefone" class="text-success">Telefone:</label><br>
                                                <input type="text" name="telefone" id="telefone" class="form-control" value="${sessionScope.usuarioedicao.telefone}">
                                            </div>
                                        </div>

                                    </div>
                                    <div class="form-group">

                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="text-success">Email:</label><br>
                                        <input type="text" name="email" id="email" class="form-control" value="${sessionScope.usuarioedicao.email}">
                                    </div>
                                    <div class="form-group">
                                        <label for="password" class="text-success">Senha:</label><br>
                                        <input type="password" name="password" id="password" class="form-control" value="${sessionScope.usuarioedicao.senha}">
                                    </div>
                                    <div class="form-group">
                                        <label for="password-confirm" class="text-success">Confirmar Senha:</label><br>
                                        <input type="password" name="passwordconfirm" id="password-confirm" class="form-control" value="${sessionScope.usuarioedicao.senha}">
                                        <p class="text-danger">${requestScope.senhaIncoerente}</p>
                                    </div>
                                    <div class="form-group">
                                        <label for="endereco" class="text-success">Endereco:</label><br>
                                        <input type="text" name="endereco" id="endereco" class="form-control" value="${sessionScope.usuarioedicao.endereco.rua}">
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="numero" class="text-success">Numero:</label><br>
                                                <input type="text" name="numero" id="numero" class="form-control" value="${sessionScope.usuarioedicao.endereco.numero}">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="complementp" class="text-success">Complemento:</label><br>
                                                <input type="text" name="complemento" id="complementp" class="form-control" value="${sessionScope.usuarioedicao.endereco.complemento}">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="bairro" class="text-success">Bairro:</label><br>
                                                <input type="text" name="bairro" id="bairro" class="form-control" value="${sessionScope.usuarioedicao.endereco.bairro}">
                                            </div>
                                            <div class="col-md-6">
                                                <label for="cep" class="text-success">CEP:</label><br>
                                                <input type="text" name="cep" id="cep" class="form-control" value="${sessionScope.usuarioedicao.endereco.cep}">
                                            </div>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <label for="estado" class="text-success">Estado:</label><br>
                                        <select name="estado" id="estado" class="form-control">
                                            <c:forEach items="${estados}" var="estado">
                                                <option value="${estado.id}" ${sessionScope.usuarioedicao.endereco.cidade.estado.id == estado.id ? 'selected="true"' : '' } >${estado.nome} - ${estado.sigla}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="cidade" class="text-success">Cidade:</label><br>
                                        <select name="cidade" id="cidade" class="form-control">
                                            <c:forEach items="${cidades}" var="cidade">
                                                <option value="${cidade.id}" ${sessionScope.usuarioedicao.endereco.cidade.id == cidade.id ? 'selected="true"' : '' }>${cidade.nome}</option>   
                                            </c:forEach>
                                        </select>
                                    </div>  
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/ClienteServlet?acao=listaatendimento"" class="text-info">Voltar</a>
                                        <input type="submit" name="submit" class="btn btn-success btn-md" value="Salvar" style="position: absolute; right: 15px;">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </body>
</html>
