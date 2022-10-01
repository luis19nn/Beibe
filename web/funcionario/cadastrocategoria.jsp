<%-- 
    Document   : cadastroproduto
    Created on : Apr 18, 2022, 7:32:59 PM
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
        <title>Cadastrar Categoria</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
                                <form id="login-form" class="form" action="${pageContext.request.contextPath}/TipoProdutoServlet?acao=cadastrocategoria&id=${requestScope.categoriavisualizar.id}" method="post">
                                    <h3 class="text-center text-success">Cadastrar Categoria</h3><br>
                                    <div class="form-group">
                                        <label for="nome" class="text-success">Nome da Categoria:</label><br>
                                        <input type="text" class="form-control" name="nome" id="nome" value="${requestScope.categoriavisualizar.descricao}" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'}>
                                        <p class="text-danger">${requestScope.avisoPreenchimento}</p>
                                    </div>
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/TipoProdutoServlet?acao=categorias" class="text-info">Voltar</a>
                                        <input ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null) ? '' : 'disabled="disabled"'} type="submit" name="submit" value="Cadastrar" class="btn btn-success btn-md" style="position: absolute; right: 15px;">
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
