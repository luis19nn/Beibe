<%-- 
    Document   : produtos
    Created on : Apr 22, 2022, 2:45:54 PM
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        <title>Produtos</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <script type="text/javascript">
        function removerProduto(id) {
            excluir = confirm('Tem certeza que deseja excluir a categoria?');
            if (excluir) {
                document.location.href= "${pageContext.request.contextPath}/ProdutoServlet?acao=excluir&id=" + id;
            }
        }
    </script>
    <body>
        <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center table-hover">
                    <div id="login-column" class="col-md-12">
                        <div id="login-box" class="col-md-12">
                            <h3 class="text-center text-success">Produtos</h3><br>
                            <a href="${pageContext.request.contextPath}/ProdutoServlet?acao=cadastroproduto">
                                <button type="button" class="btn btn-primary btn-success" style="margin-bottom: 10px; width: 100%;">Novo Produto</button>
                            </a>
                            <table id="login-table" class="table table-bordered mb-0">
                                <div class="table-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <thead for="categoria">
                                                <tr>
                                                    <th scope="col" class="table-light align-middle">Produto</th>
                                                    <th scope="col" class="table-light align-middle">Categoria</th>
                                                    <th scope="col" class="table-light align-middle">Visualizar</th>
                                                    <th scope="col" class="table-light align-middle">Alterar</th>
                                                    <th scope="col" class="table-light align-middle">Excluir</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${produtos}" var="produto">
                                                    <tr>
                                                        <td>${produto.nome}</td>
                                                        <td>${produto.tipoProduto.descricao}</td>
                                                        <td><a href="${pageContext.request.contextPath}/ProdutoServlet?acao=visualizar&id=${produto.id}" class="btn btn-info" style="width: 100%;">Visualizar</button></td>
                                                        <td><a href="${pageContext.request.contextPath}/ProdutoServlet?acao=alterar&id=${produto.id}" class="btn btn-info" style="width: 100%;">Alterar</a></td>
                                                        <td><button type="button" class="btn btn-danger" onclick="removerProduto(${produto.id})" style="width: 100%;">Excluir</button></td>
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
        <div class="modal" tabindex="-1" role="dialog">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Modal title</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Modal body text goes here.</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary">Save changes</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

