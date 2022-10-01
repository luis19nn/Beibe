<%-- 
    Document   : alterafuncionario.jsp
    Created on : 28 de abr de 2022, 14:54:52
    Author     : luis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Editar Funcionário</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
    </head>
    <body>
        <div id="login" style="margin-top: 6%; margin-bottom: 6%;">
            <div class="container">
                <div id="login-row" class="row justify-content-center align-items-center">
                    <div id="login-column" class="col-md-6">
                        <div id="login-box" class="col-md-12">
                            <!-- TO-DO: Revisar o action para implementar usando tag de contexto -->
                            <form id="login-form" class="form" action="${pageContext.request.contextPath}/GerenteServlet?acao=salvaralteracaofuncionario&id=${funcionario.id}" method="post">
                                <h3 class="text-center text-success">Editar Funcionário</h3>
                                <p class="text-danger">${requestScope.preenchimentoIncompleto}</p>
                                <div class="form-group">
                                    <label for="username" class="text-success">Nome:</label><br>
                                    <input type="text" name="nome" id="username" class="form-control" value="${funcionario.nomeCompleto}">
                                </div>
                                <div class="form-group">
                                    <label for="cargo" class="text-success">Cargo:</label><br>
                                    <select name="cargo" id="cargo" class="form-control">
                                        <option value="" disabled selected>${funcionario.tipoPessoa.descricao}</option>
                                        <c:forEach items="${configuracao.listaCargos}" var="cargo">
                                            <option value="${cargo.id}" ${param.cargo == cargo.id ? 'selected="true"' : '' }>${cargo.descricao}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="cpf" class="text-success">CPF:</label><br>
                                            <input type="text" name="cpf" id="cpf" class="form-control" value="${funcionario.cpf}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="telefone" class="text-success">Telefone:</label><br>
                                            <input type="tel" name="telefone" id="telefone" class="form-control" value="${funcionario.telefone}">
                                        </div>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="email" class="text-success">Email:</label><br>
                                    <input type="text" name="email" id="email" class="form-control" value="${funcionario.email}">
                                </div>
                                <div class="form-group">
                                    <label for="password" class="text-success">Senha:</label><br>
                                    <input type="password" name="password" id="password" class="form-control" value="${param.password}">
                                </div>
                                <div class="form-group">
                                    <label for="password-confirm" class="text-success">Confirmar Senha:</label><br>
                                    <input type="password" name="passwordconfirm" id="password-confirm" class="form-control" value="${param.passwordconfirm}">
                                    <p class="text-danger">${requestScope.senhaIncoerente}</p>
                                </div>
                                <div class="form-group">
                                    <label for="endereco" class="text-success">Endereco:</label><br>
                                    <input type="text" name="endereco" id="endereco" class="form-control" value="${funcionario.endereco.rua}">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="numero" class="text-success">Numero:</label><br>
                                            <input type="number" name="numero" id="numero" class="form-control" value="${funcionario.endereco.numero}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="complemento" class="text-success">Complemento:</label><br>
                                            <input type="text" name="complemento" id="complementp" class="form-control" value="${funcionario.endereco.complemento}">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="bairro" class="text-success">Bairro:</label><br>
                                            <input type="text" name="bairro" id="bairro" class="form-control" value="${funcionario.endereco.bairro}">
                                        </div>
                                        <div class="col-md-6">
                                            <label for="cep" class="text-success">CEP:</label><br>
                                            <input type="text" name="cep" id="cep" class="form-control" value="${funcionario.endereco.cep}">
                                        </div>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="estado" class="text-success">Estado:</label><br>
                                    <select name="estado" id="estado" class="form-control">
                                        <option value="" disabled selected>${funcionario.endereco.cidade.estado.nome} - ${funcionario.endereco.cidade.estado.sigla}</option>
                                        <c:forEach items="${configuracao.listaEstados}" var="estado">
                                            <option value="${estado.id}"  ${param.estado == estado.id ? 'selected="true"' : '' } >${estado.nome} - ${estado.sigla}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="cidade" class="text-success">Cidade:</label><br>
                                    <select name="cidade" id="cidade" class="form-control">
                                        <option value="" disabled selected>${funcionario.endereco.cidade.nome}</option>
                                        <c:forEach items="${configuracao.listaCidades}" var="cidade">
                                            <option value="${cidade.id}" ${param.cidade == cidade.id ? 'selected="true"' : '' }>${cidade.nome}</option>
                                        </c:forEach>
                                    </select>
                                </div>  
                                <div class="form-group">
                                    <a href="${pageContext.request.contextPath}/GerenteServlet?acao=listafuncionario" class="text-info">Voltar</a>
                                    <input type="submit" name="submit" class="btn btn-success btn-md" value="Salvar Alterações" style="position: absolute; right: 15px;">
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
<script>
    $("#telefone")
            .mask("(99) 9999-9999?9")
            .focusout(function (event) {
                var target, phone, element;
                target = (event.currentTarget) ? event.currentTarget : event.srcElement;
                phone = target.value.replace(/\D/g, '');
                element = $(target);
                element.unmask();
                if (phone.length > 10) {
                    element.mask("(99) 99999-999?9");
                } else {
                    element.mask("(99) 9999-9999?9");
                }
            });

    $("#cep").mask("99999-999");

    $("#cpf").mask("999.999.999-99");

</script>