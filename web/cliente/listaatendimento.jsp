<%-- 
    Document   : listaatendimento
    Created on : 28 de abr de 2022, 14:46:26
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
        <title>Atendimentos</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <script type="text/javascript">
        function removerAtendimento(id) {
            excluir = confirm('Tem certeza que deseja excluir o atendimento?');
            if (excluir) {
                document.location.href= "/web2_beibe/ClienteServlet?acao=removeratendimento&id=" + id;
            }
        }
    </script>
    <body>
        <body>
            <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
                <div class="container">
                    <div id="login-row" class="row justify-content-center align-items-center">
                        <div id="login-column" class="col-md-12">
                            <div id="login-box" class="col-md-12">
                                <h3 class="text-center text-success">Atendimentos</h3><br>
                                <div>
                                    <a href="${pageContext.request.contextPath}/ClienteServlet?acao=carregaratendimento">
                                        <button name="criarAtendimento" class="btn btn-success btn-md">Novo Atendimento</button>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/ClienteServlet?acao=atualizarcadastro">
                                        <button name="alterarDados" class="btn btn-success btn-md">Alterar Dados</button>
                                    </a>
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
                                                        <th scope="col" class="table-light align-middle">Visualizar Atendimento</th>
                                                        <th scope="col" class="table-light align-middle">Remover Atendimento</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="atendimento" items="${requestScope.atendimentos}" varStatus="status">
                                                        <tr id="status.count">
                                                            <td><fmt:formatDate value="${atendimento.data}" pattern="dd/MM/yyyy"/></td>
                                                            <td><c:out value="${atendimento.produto.tipoProduto.descricao}"/></td>
                                                            <td><c:out value="${atendimento.produto.nome}"/></td>
                                                            <td><c:out value="${atendimento.tipoAtendimento.descricao}"/></td>
                                                            <td><c:out value="${atendimento.situacaoAtendimento.descricao}"/></td>
                                                            <td><a href="${pageContext.request.contextPath}/ClienteServlet?acao=visualizaratendimento&id=${atendimento.id}">Ver</a></td>
                                                            <c:set var="situacao" scope="request" value="${atendimento.situacaoAtendimento.descricao}"/>
                                                            <c:choose>
                                                                <c:when test="${situacao eq 'Fechado'}">
                                                                    <td><button type="button" class="btn btn-danger" style="width: 100%;" onclick="alert('Não é possível remover atendimento fechado.')" >Remover</button></td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td><button type="button" class="btn btn-danger" onclick="removerAtendimento(${atendimento.id})" <a href="${pageContext.request.contextPath}/ClienteServlet?acao=removeratendimento&id=${atendimento.id}" style="width: 100%;">Remover</button></td>
                                                                </c:otherwise>
                                                            </c:choose> 
                                                            
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

