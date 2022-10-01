<%-- 
    Document   : listaatendimento
    Created on : 30 de abr de 2022, 11:08:37
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<jsp:useBean id="dataBean" class="java.util.Date"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Lista Atendimentos</title>
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
                            <h3 class="text-center text-success">Lista de Atendimentos</h3><br>
                            <div>
                            </div>
                            <br>
                            <table id="login-table" class="table table-bordered mb-0 table-hover" id="tabela">
                                <div class="table-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <thead for="categoria">
                                                <tr>
                                                    <th scope="col" class="table-light align-middle date" onclick="sortTable(0)">Data do Atendimento</th>
                                                    <th scope="col" class="table-light align-middle" onclick="sortTable(1)">Categoria do Produto</th>
                                                    <th scope="col" class="table-light align-middle">Produto</th>
                                                    <th scope="col" class="table-light align-middle">Tipo do Atendimento</th>
                                                    <th scope="col" class="table-light align-middle">Status do Atendimento</th>
                                                    <th scope="col" class="table-light align-middle">Descrição do Atendimento</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="atendimento" items="${requestScope.atendimentos}" varStatus="status">
                                                    <c:set var="data" scope="request" value="${atendimento.data}"/>
                                                    <c:set var="situacao" scope="request" value="${atendimento.situacaoAtendimento.descricao}"/>
                                                    <c:choose>
                                                        <c:when test="${((((dataBean.getTime() - data.getTime()) / (1000 * 60 * 60 * 24)) % 365) ge 7) and situacao eq 'Aberto'}">
                                                            <tr id="status.count" class="table-danger">
                                                        </c:when>
                                                        <c:when test="${((((dataBean.getTime() - data.getTime()) / (1000 * 60 * 60 * 24)) % 365) lt 7) and situacao eq 'Aberto'}">
                                                            <tr id="status.count" class="table-warning">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <tr id="status.count">
                                                        </c:otherwise>
                                                    </c:choose> 
                                                        <td><fmt:formatDate value="${atendimento.data}" pattern="dd/MM/yyyy"/></td>
                                                        <td><c:out value="${atendimento.produto.tipoProduto.descricao}"/></td>
                                                        <td><c:out value="${atendimento.produto.nome}"/></td>
                                                        <td><c:out value="${atendimento.tipoAtendimento.descricao}"/></td>
                                                        <td><c:out value="${atendimento.situacaoAtendimento.descricao}"/></td>
                                                        <td><c:out value="${atendimento.descricao}"/></td>
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
