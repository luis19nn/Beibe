<%-- 
    Document   : visualizaratendimento
    Created on : 28 de abr de 2022, 19:24:10
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
        <title>Visualizar Atendimento</title>
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
                                <form id="login-form" class="form" action="" method="">
                                    <h3 class="text-center text-success">Visualizar Atendimento</h3><br>
                                    <div class="form-group">
                                        <label for="descicao" class="text-success">Status:</label>
                                        <input type="text" name="categoria" class="form-control" value="${requestScope.atendimento.situacaoAtendimento.descricao}" disabled>
                                    </div>
                                    <div class="form-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label for="categoria" class="text-success">Categoria do Produto:</label><br>
                                                <input type="text" name="categoria" class="form-control"value="${requestScope.atendimento.produto.tipoProduto.descricao}" disabled>
                                            </div>
                                            <div class="col-md-6">
                                                <label for="produto" class="text-success">Produto:</label><br>
                                                <input type="text" name="produto" class="form-control" value="${requestScope.atendimento.produto.nome}" disabled>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="tipoatendimento" class="text-success">Tipo do Atendimento:</label><br>
                                        <input type="text" name="tipoatendimento" class="form-control" value="${requestScope.atendimento.tipoAtendimento.descricao}" disabled>
                                    </div>
                                    <div class="form-group">
                                        <label for="descicao" class="text-success">Descrição:</label><br>
                                        <textarea name="descicao" id="descicao" cols="30" rows="10" class="form-control" disabled>${requestScope.atendimento.descricao}</textarea>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="resolucao" class="text-success">Resolver Atendimento:</label><br>
                                        <textarea name="resolucao" id="resolucao" cols="30" rows="10" class="form-control" disabled>${requestScope.atendimento.solucao}</textarea>
                                    </div>
                                    
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=listatodosatendimentos" class="text-info">Voltar</a>
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
