<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.login == null }" >
    <c:set var="aviso" value="Usuário deve se autenticar para acessar o sistema." scope="request" />
    <jsp:forward page="/geral/login.jsp" />
</c:if>
<c:if test="${ sessionScope.login != null }" >
    <c:if test="${ sessionScope.login.tipoUsuario != 'Funcionário' }" >
        <c:set var="mensagem" value="Erro: Usuário não possui permissão para acessar essa página." scope="request" />
        <c:set var="voltar" value="${pageContext.request.contextPath}/geral/login.jsp" scope="request" />
        <jsp:forward page="/geral/erro.jsp" />
    </c:if>
</c:if>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Atendimentos em Aberto</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <jsp:useBean id="dataBean" class="java.util.Date"/>
    <body>
        <body>
            <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
                <div class="container">
                    <div id="login-row" class="row justify-content-center align-items-center">
                        <div id="login-column" class="col-md-12">
                            <div id="login-box" class="col-md-12">
                                <h3 class="text-center text-success">Atendimentos em Aberto</h3><br>
                                <div>
                                    <a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=listatodosatendimentos">
                                        <button name="listaatendimento" class="btn btn-success btn-md">Todos Atendimentos</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/TipoProdutoServlet?acao=categorias">
                                        <button name="visualizarcategorias" class="btn btn-success btn-md">Categorias</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/ProdutoServlet?acao=produtos">
                                        <button name="visualizarprodutos" class="btn btn-success btn-md">Produtos</button>
                                    </a>
                                </div>
                                <br>
                                <table id="login-table" class="table table-bordered mb-0 table-hover">
                                    <div class="table-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <thead for="categoria">
                                                    <tr>
                                                        <th scope="col" class="table-light align-middle">Data do Atendimento</th>
                                                        <th scope="col" class="table-light align-middle">Categoria do Produto</th>
                                                        <th scope="col" class="table-light align-middle">Produto</th>
                                                        <th scope="col" class="table-light align-middle">Tipo do Atendimento</th>
                                                        <th scope="col" class="table-light align-middle">Status do Atendimento</th>
                                                        <th scope="col" class="table-light align-middle">Descrição</th>
                                                        <th scope="col" class="table-light align-middle">Ver Atendimento</th>
                                                        <th scope="col" class="table-light align-middle">Resolver Atendimento</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="atendimento" items="${requestScope.atendimentos}" varStatus="status">
                                                        <c:set var="data" scope="request" value="${atendimento.data}"/>
                                                            <c:choose>
                                                                <c:when test="${(((dataBean.getTime() - data.getTime()) / (1000 * 60 * 60 * 24)) % 365) ge 7}">
                                                                    <tr id="status.count" class="table-danger">
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
                                                            <td><a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=visualizaratendimento&id=${atendimento.id}">Ver</a></td>
                                                            <td><a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=resolveratendimento&id=${atendimento.id}">Resolver</a></td>
                                                            
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
                            <a href="${pageContext.request.contextPath}/PessoaServlet?acao=logout" class="text-info" style="position: absolute; left: 35px;">Logout</a>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </body>
</html>
