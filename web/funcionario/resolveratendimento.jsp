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
        <title>Resolver Atendimento</title>
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
                                <form id="login-form" class="form" action="${pageContext.request.contextPath}/FuncionarioServlet?acao=solucionaratendimento" method="post">
                                    <h3 class="text-center text-success">Resolver Atendimento</h3><br>
                                    <div class="form-group">
                                        <label for="idatendimento" class="text-success">Número do Atendimento:</label>
                                        <input type="text" name="idatendimento" class="form-control" readonly="readonly" value="${requestScope.atendimento.id}">
                                    </div>
                                    <div class="form-group">
                                        <label for="status" class="text-success">Status:</label>
                                        <input type="text" name="status" class="form-control" value="${requestScope.atendimento.situacaoAtendimento.descricao}" disabled>
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
                                        <label for="descricao" class="text-success">Descrição:</label><br>
                                        <textarea name="descicao" id="descricao" cols="30" rows="10" class="form-control" disabled>${requestScope.atendimento.descricao}</textarea>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label for="resolucao" class="text-success">Resolver Atendimento:</label><br>
                                        <textarea name="resolucao" id="resolucao" cols="30" rows="10" class="form-control" required></textarea>
                                    </div>
                                    <div class="form-group">
                                        <a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=listatodosatendimentos" class="text-info">Voltar</a>
                                        <input type="submit" name="submit" class="btn btn-success btn-md" value="Resolver" style="position: absolute; right: 15px;">
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
