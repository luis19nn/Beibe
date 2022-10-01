<%-- 
    Document   : relatorios
    Created on : 30 de abr de 2022, 13:20:28
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
        <title>Relatórios</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <body>
            <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
                <div class="container">
                    <div id="login-row" class="row justify-content-center align-items-center">
                        <div id="login-column" class="col-md-12">
                            <div id="login-box" class="col-md-12">
                                <h3 class="text-center text-success">Relatórios</h3><br>
                                <div>
                                    <br>
                                    <a href="${pageContext.request.contextPath}/GerenteServlet?acao=relatoriofuncionarios">
                                        <button name="relatoriofuncionarios" class="btn btn-success btn-md">Funcionarios</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/GerenteServlet?acao=relatorioprodutosmaisreclamados">
                                        <button name="relatorioprodutosmaisreclamados" class="btn btn-success btn-md">Produtos Mais Reclamados</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/GerenteServlet?acao=relatorioatendimentosemaberto">
                                        <button name="relatorioatendimentosemaberto" class="btn btn-success btn-md">Atendimentos em Aberto</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/GerenteServlet?acao=relatorioreclamacoes">
                                        <button name="relatorioreclamacoes" class="btn btn-success btn-md">Reclamações</button>
                                    </a>
                                </div>
                                <br>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-md-6">
                            <a href="${pageContext.request.contextPath}/GerenteServlet?acao=paginainicial" class="text-info" style="position: absolute; left: 35px;">Voltar</a>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </body>
</html>
