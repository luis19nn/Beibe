<%-- 
    Document   : categorias
    Created on : Apr 19, 2022, 4:22:25 PM
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
        <title>Categorias</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <script type="text/javascript">
        function removerCategoria(id) {
            excluir = confirm('Tem certeza que deseja excluir a categoria?');
            if (excluir) {
                document.location.href= "${pageContext.request.contextPath}/TipoProdutoServlet?acao=excluir&id=" + id;
            }
        }
    </script>
</head>
    <body>
        <body>
            <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
                <div class="container">
                    <div id="login-row" class="row justify-content-center align-items-center table-hover">
                        <div id="login-column" class="col-md-12">
                            <div id="login-box" class="col-md-12">
                                <h3 class="text-center text-success">Categoria</h3><br>
                                <a href="${pageContext.request.contextPath}/funcionario/cadastrocategoria.jsp">
                                    <button type="button" class="btn btn-primary btn-success" style="margin-bottom: 10px; width: 100%;">Nova categoria</button>
                                </a>
                                <table id="login-table" class="table table-bordered mb-0">
                                    <div class="table-group">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <thead for="categoria">
                                                    <tr>
                                                        <th scope="col" class="table-light align-middle">Categoria do Produto</th>
                                                        <th scope="col" class="table-light align-middle">Visualizar</th>
                                                        <th scope="col" class="table-light align-middle">Alterar</th>
                                                        <th scope="col" class="table-light align-middle">Excluir</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${categorias}" var="categoria">
                                                        <tr>
                                                            <td>${categoria.descricao}</td>
                                                            <td><a href="${pageContext.request.contextPath}/TipoProdutoServlet?acao=visualizar&id=${categoria.id}" class="btn btn-info" style="width: 100%;">Visualizar</button></td>
                                                            <td><a href="${pageContext.request.contextPath}/TipoProdutoServlet?acao=alterar&id=${categoria.id}" class="btn btn-info" style="width: 100%;">Alterar</a></td>
                                                            <td><button type="button" class="btn btn-danger" onclick="removerCategoria(${categoria.id})" style="width: 100%;">Excluir</button></td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </div>
                                        </div>
                                    </div>
                                </table>
                                <div class="form-group">
                                    <br>
                                    <a href="${pageContext.request.contextPath}/FuncionarioServlet?acao=listaatendimentoaberto" class="text-info">Voltar</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>
    </body>
</html>

