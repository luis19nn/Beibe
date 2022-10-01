<%-- 
    Document   : paginainicial
    Created on : 27 de abr de 2022, 18:31:24
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>Página Inicial</title>
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
                            <div>
                                <br>
                                <a href="${pageContext.request.contextPath}/GerenteServlet?acao=listafuncionario">
                                    <button name="listafuncionario" class="btn btn-success btn-md">Lista de Funcionários</button>
                                </a>
                                <a href="${pageContext.request.contextPath}/GerenteServlet?acao=cadastrofuncionario">
                                    <button name="cadastrofuncionario" class="btn btn-success btn-md">Novo Funcionário</button>
                                </a>
                                <a href="${pageContext.request.contextPath}/GerenteServlet?acao=listaatendimentoaberto">
                                    <button name="listaatendimentoaberto" class="btn btn-success btn-md">Atendimentos em Aberto</button>
                                </a>
                                <a href="${pageContext.request.contextPath}/GerenteServlet?acao=listaatendimento">
                                    <button name="listaatendimento" class="btn btn-success btn-md">Todos os Atendimentos</button>
                                </a>
                                <a href="${pageContext.request.contextPath}/GerenteServlet?acao=relatorios">
                                    <button name="relatorios" class="btn btn-success btn-md">Relatórios</button>
                                </a>
                                <hr>
                            </div>
                            <h3 class="text-center text-success">Atendimentos</h3><br>
                            <div>
                                <h5>
                                    Total de Atendimentos:
                                    <small class="text-muted"><c:out value="${requestScope.qntAtendimentosTotal}"/></small>
                                </h5>
                                <h5>
                                    Atendimentos em Aberto:
                                    <c:set var="porcentagemAtendimentosAbertos" scope="request" value="${requestScope.qntAtendimentosAbertos/requestScope.qntAtendimentosTotal}"/>
                                    <small class="text-muted"><c:out value="${requestScope.qntAtendimentosAbertos}"/> (<fmt:formatNumber type = "percent" maxIntegerDigits="3" minFractionDigits = "2" value = "${porcentagemAtendimentosAbertos}"/>)</small>
                                </h5>
                                <table id="login-table" class="table table-bordered mb-0">
                                    <div class="table-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <thead for="categoria">
                                                    <tr>
                                                        <th scope="col" class="table-light align-middle">Categoria</th>
                                                        <th scope="col" class="table-light align-middle">Total de Atendimentos</th>
                                                        <th scope="col" class="table-light align-middle">Atendimentos em Aberto</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${relatorio}" var="categoria">
                                                        <tr>
                                                            <td><c:out value="${categoria.descricaoCategoria}"/></td>
                                                            <td><c:out value="${categoria.totalAtendimentos}"/></td>
                                                            <td><c:out value="${categoria.atendimentosAbertos}"/></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </div>
                                        </div>
                                    </div>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-md-6">
                        <a href="${pageContext.request.contextPath}/PessoaServlet?acao=logout" class="text-info" style="position: absolute; left: 35px;">Logout</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</body>
</html>
