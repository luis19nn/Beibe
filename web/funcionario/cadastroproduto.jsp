<%-- 
    Document   : cadastroproduto
    Created on : Apr 22, 2022, 1:37:42 PM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Cadastrar Produto</title>
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
                                <form id="login-form" class="form" action="${pageContext.request.contextPath}/ProdutoServlet?acao=cadastrarproduto&id=${requestScope.produtovisualizar.id}" method="post">
                                    <h3 class="text-center text-success">Cadastrar Produto</h3><br>
                                    <p class="text-danger">${requestScope.avisoPreenchimento}</p>
                                    <div class="form-group">
                                        <label for="nome" class="text-success">Nome do Produto:</label><br>
                                        <input type="text" class="form-control" name="nome" id="nome" value="${param.nome}" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}>
                                    </div>
                                    <div class="form-group">
                                        <label for="categoria" class="text-success">Categoria:</label><br>
                                        <select name="categoria" id="categoria" class="form-control" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}>
                                            <c:forEach items="${categorias}" var="categoria">
                                                <option value="${categoria.id}" ${param.categoria == categoria.id ? 'selected="true"' : '' }>${categoria.descricao}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="peso" class="text-success">Peso:</label><br>
                                        <input type="number" class="form-control" name="peso" id="peso" value="${param.peso}" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}>
                                    </div>
                                    <div class="form-group">
                                        <label for="descricao" class="text-success">Descrição:</label><br>
                                        <input type="text" name="descricao" id="descricao" class="form-control" value="${param.descricao}" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}></textarea>
                                    </div>
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/ProdutoServlet?acao=produtos" class="text-info">Voltar</a>
                                        <input type="submit" name="submit" class="btn btn-success btn-md" value="Salvar" style="position: absolute; right: 15px;" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}>
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
