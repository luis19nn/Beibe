<%-- 
    Document   : visualizafuncionario
    Created on : 28 de abr de 2022, 12:03:55
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${sessionScope.login == null }" >
    <c:set var="aviso" value="Usuário deve se autenticar para acessar o sistema." scope="request" />
    <jsp:forward page="/geral/login.jsp" />
</c:if>
<c:if test="${ sessionScope.login != null }" >
    <c:if test="${ sessionScope.login.tipoUsuario != 'Gerente' }" >
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
        <title>Visualizar Funcionário</title>
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
                                <form id="login-form" class="form" action="" method="post">
                                    <h3 class="text-center text-success">Visualizar Funcionário</h3>
                                    <div class="form-group">
                                        <label for="username" class="text-success">Nome:</label><br>
                                        <input type="text" name="nome" id="username" class="form-control" value="${funcionario.nomeCompleto}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="cargo" class="text-success">Cargo:</label><br>
                                        <input type="text" name="cargo" id="cargo" class="form-control" value="${funcionario.tipoPessoa.descricao}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="cpf" class="text-success">CPF:</label><br>
                                                <input type="text" name="cpf" id="cpf" class="form-control" value="${funcionario.cpf}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="telefone" class="text-success">Telefone:</label><br>
                                                <input type="text" name="telefone" id="telefone" class="form-control" value="${funcionario.telefone}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                    </div>
                                    <div class="form-group">
                                        <label for="email" class="text-success">Email:</label><br>
                                        <input type="text" name="email" id="email" class="form-control" value="${funcionario.email}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="endereco" class="text-success">Endereco:</label><br>
                                        <input type="text" name="endereco" id="endereco" class="form-control" value="${funcionario.endereco.rua}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="numero" class="text-success">Numero:</label><br>
                                                <input type="text" name="numero" id="numero" class="form-control" value="${funcionario.endereco.numero}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="complementp" class="text-success">Complemento:</label><br>
                                                <input type="text" name="complementp" id="complementp" class="form-control" value="${funcionario.endereco.complemento}" readonly>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="bairro" class="text-success">Bairro:</label><br>
                                                <input type="text" name="bairro" id="bairro" class="form-control" value="${funcionario.endereco.bairro}" readonly>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="cep" class="text-success">CEP:</label><br>
                                                <input type="text" name="cep" id="cep" class="form-control" value="${funcionario.endereco.cep}" readonly>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="form-group">
                                        <label for="cidade" class="text-success">Cidade:</label><br>
                                        <input type="text" name="cidade" id="cidade" class="form-control" value="${funcionario.endereco.cidade.nome}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="estado" class="text-success">Estado:</label><br>
                                        <input type="text" name="estado" id="estado" class="form-control" value="${funcionario.endereco.cidade.estado.sigla}" readonly>
                                    </div>
                                    <div class="form-group">
                                        <a href="${requestScope.voltar}" class="text-info">Voltar</a>
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
