<%-- 
    Document   : criacaoatendimento
    Created on : 27 de abr de 2022, 17:17:29
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
        <title>Criar Atendimento</title>
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
                                <form id="login-form" class="form" action="${pageContext.request.contextPath}/ClienteServlet?acao=criaratendimento" method="post">
                                    <h3 class="text-center text-success">Criar Atendimento</h3><br>
                                    <p class="text-danger">${requestScope.preenchimentoIncompleto}</p>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="categoria" class="text-success">Categoria do Produto:</label><br>
                                                <select name="categoria" id="categoria" class="form-control" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'} required>
                                                    <option style="display:none" selected disabled>Selecione</option>
                                                    <c:forEach items="${categorias}" var="categoria">
                                                        <option value="${categoria.id}" ${param.categoria == categoria.id ? 'selected="true"' : '' }>${categoria.descricao}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="produto" class="text-success">Produto:</label><br>
                                                <select name="produto" id="categoria" class="form-control" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'} required>
                                                    <option style="display:none" selected disabled>Selecione</option>
                                                    <c:forEach items="${produtos}" var="produto">
                                                        <option value="${produto.id}" ${param.produto == produto.id ? 'selected="true"' : '' }>${produto.nome}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="tipoatendimento" class="text-success">Tipo do Atendimento:</label><br>
                                        <select name="tipoatendimento" id="tipoatendimento" class="form-control" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'} required>
                                            <option style="display:none" selected disabled>Selecione</option>
                                            <c:forEach items="${tiposatendimento}" var="tipoatendimento">
                                                <option value="${tipoatendimento.id}" ${param.tipoatendimento == tipoatendimento.id ? 'selected="true"' : '' }>${tipoatendimento.descricao}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label for="descricao" class="text-success">Descrição:</label><br>
                                        <textarea name="descricao" id="descricao" cols="30" rows="10" class="form-control" value="${param.descricao}" ${(requestScope.permiteAlterar || requestScope.permiteAlterar == null)  ? '' : 'disabled="disabled"'} recquired></textarea>
                                    </div>
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/ClienteServlet?acao=listaatendimento" class="text-info">Voltar</a>
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
