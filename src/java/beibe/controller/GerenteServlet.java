/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.Atendimento;
import beibe.beans.Cidade;
import beibe.beans.Endereco;
import beibe.beans.LoginBean;
import beibe.beans.Pessoa;
import beibe.beans.RelatorioAtendimento;
import beibe.beans.TipoPessoa;
import beibe.beans.TipoProduto;
import beibe.dao.ConnectionFactory;
import beibe.exceptions.DAOException;
import beibe.exceptions.FacadeException;
import beibe.facade.AtendimentoFacade;
import beibe.facade.GerenteFacade;
import beibe.facade.ProdutoFacade;
import beibe.facade.RelatorioFacade;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author luis
 */
@WebServlet(name = "GerenteServlet", urlPatterns = {"/GerenteServlet"})
public class GerenteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");
        acao = acao == null ? "" : acao;

        switch (acao) {
            case "paginainicial":
                validaGerenteLogado(request, response);
                paginaInicial(request, response);
                break;
            case "listafuncionario":
                validaGerenteLogado(request, response);
                listarFuncionarios(request, response);
                break;
            case "cadastrofuncionario":
                validaGerenteLogado(request, response);
                cadastrarFuncionario(request, response);
                break;
            case "salvarcadastrofuncionario":
                validaGerenteLogado(request, response);
                salvarCadastroFuncionario(request, response);
                break;
            case "alterafuncionario":
                validaGerenteLogado(request, response);
                alterarFuncionario(request, response);
                break;
            case "salvaralteracaofuncionario":
                validaGerenteLogado(request, response);
                salvarAlteracaoFuncionario(request, response);
                break;
            case "visualizafuncionario":
                validaGerenteLogado(request, response);
                visualizarFuncionario(request, response);
                break;
            case "removefuncionario":
                validaGerenteLogado(request, response);
                removerFuncionario(request, response);
                break;
            case "listaatendimentoaberto":
                validaGerenteLogado(request, response);
                listarAtendimentosEmAberto(request, response);
                break;
            case "listaatendimento":
                validaGerenteLogado(request, response);
                listarAtendimentos(request, response);
                break;
            case "relatorios":
                validaGerenteLogado(request, response);
                relatorios(request, response);
                break;
            case "relatoriofuncionarios":
                validaGerenteLogado(request, response);
                relatorioFuncionarios(request, response);
                break;
            case "relatorioprodutosmaisreclamados":
                validaGerenteLogado(request, response);
                relatorioProdutosMaisReclamados(request, response);
                break;
            case "relatorioatendimentosemaberto":
                validaGerenteLogado(request, response);
                relatorioAtendimentosEmAberto(request, response);
                break;
            case "relatorioreclamacoes":
                validaGerenteLogado(request, response);
                relatorioReclamacoes(request, response);
                break;
            default:
                validaGerenteLogado(request, response);
                defaultCase(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void validaGerenteLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        LoginBean login = (LoginBean) session.getAttribute("login");

        if (login == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário deve estar logado para acessar.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        } else if (!"Gerente".equalsIgnoreCase(login.getTipoUsuario())) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário não possui permissão para acessar a página.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }

    private void paginaInicial(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            int qntAtendimentosTotal = AtendimentoFacade.getQuantidadeAtendimentosTodos();
            int qntAtendimentosAbertos = AtendimentoFacade.getQuantidadeAtendimentosAbertos();

            request.setAttribute("qntAtendimentosTotal", qntAtendimentosTotal);
            request.setAttribute("qntAtendimentosAbertos", qntAtendimentosAbertos);

            List<TipoProduto> categorias = ProdutoFacade.getListaCategoriaProdutos();

            List<RelatorioAtendimento> relatorio = RelatorioFacade.getRelatorioAtendimentosByCategoria(categorias);
            request.setAttribute("relatorio", relatorio);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/gerente/paginainicial.jsp");
            dispatcher.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }

    }

    private void listarFuncionarios(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        try {
            List<Pessoa> funcionarios = GerenteFacade.getListarFuncionarios();
            request.setAttribute("funcionarios", funcionarios);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/listafuncionario.jsp");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }

    }

    private void cadastrarFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/gerente/cadastrofuncionario.jsp");
        dispatcher.forward(request, response);
        return;
    }

    private void alterarFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String funcionarioStrId = request.getParameter("id");
        if (funcionarioStrId == null) {
            request.setAttribute("mensagem", "Erro: " + "Invocação inválida: id é nulo");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
        try {
            int funcionarioId = Integer.parseInt(funcionarioStrId);

            Pessoa funcionario = GerenteFacade.getFuncionarioTodasInformacoesById(funcionarioId);

            request.setAttribute("funcionario", funcionario);
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/alterafuncionario.jsp");
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: " + "Id para remoção inválido: " + funcionarioStrId);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        }
    }

    private void salvarAlteracaoFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("senhaIncoerente", null);
        request.setAttribute("preenchimentoIncompleto", null);
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        String cargo = request.getParameter("cargo");
        String telefone = request.getParameter("telefone").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
        String email = request.getParameter("email");
        String senha = request.getParameter("password");
        String senhaconfirma = request.getParameter("passwordconfirm");
        String rua = request.getParameter("endereco");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep").replace("-", "");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");

        String funcionarioStrId = request.getParameter("id");
        if (funcionarioStrId == null) {
            request.setAttribute("mensagem", "Erro: " + "Invocação inválida: id é nulo");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?action=listafuncionario");
            rd.forward(request, response);
            return;
        }
        try {
            RequestDispatcher rd = null;
            int funcionarioId = Integer.parseInt(funcionarioStrId);
            Pessoa funcionario = GerenteFacade.getFuncionarioTodasInformacoesById(funcionarioId);
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            if (cidade == null) {
                cidade = Integer.toString(funcionario.getEndereco().getCidade().getId());
            }
            if (estado == null) {
                estado = Integer.toString(funcionario.getEndereco().getCidade().getEstado().getId());
            }
            if (cargo == null) {
                cargo = Integer.toString(funcionario.getTipoPessoa().getId());
            }
            if (senha == null && senhaconfirma == null) {
                senha = funcionario.getSenha();

            } else if (isNullOrEmpty(senha) || isNullOrEmpty(senhaconfirma)) {
                request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
                rd.forward(request, response);
                return;
            } else if (!senha.equals(senhaconfirma)) {
                request.setAttribute("senhaIncoerente", "As senhas não conheferem.");
                rd = getServletContext().getRequestDispatcher("/gerente/alterafuncionario.jsp");
                rd.forward(request, response);
                return;
            }
            if (isNullOrEmpty(nome) || isNullOrEmpty(cpf) || isNullOrEmpty(telefone) || isNullOrEmpty(email)
                    || isNullOrEmpty(rua) || isNullOrEmpty(numero)
                    || isNullOrEmpty(complemento) || isNullOrEmpty(bairro) || isNullOrEmpty(cep) || isNullOrEmpty(cidade) || isNullOrEmpty(estado) || isNullOrEmpty(cargo)) {

                rd = getServletContext().getRequestDispatcher("/gerente/alterafuncionario.jsp");
                request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
                rd.forward(request, response);
                return;
            }
            int cidadeId = Integer.parseInt(cidade);
            funcionario.getEndereco().getCidade().setId(cidadeId);
            funcionario.getEndereco().setBairro(bairro);
            funcionario.getEndereco().setCep(cep);
            funcionario.getEndereco().setComplemento(complemento);
            int numeroInt = Integer.parseInt(numero);
            funcionario.getEndereco().setNumero(numeroInt);
            funcionario.getEndereco().setRua(rua);
            GerenteFacade.updateEndereco(funcionario.getEndereco());
            funcionario.setCpf(cpf);
            funcionario.setEmail(email);
            funcionario.setNomeCompleto(nome);
            funcionario.setTelefone(telefone);
            int tipoPessoaId = Integer.parseInt(cargo);
            funcionario.getTipoPessoa().setId(tipoPessoaId);
            funcionario.setSenha(senha);
            GerenteFacade.updateFuncionario(funcionario);
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=paginainicial");
            rd = getServletContext().getRequestDispatcher("/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: " + "Id para remoção inválido: " + funcionarioStrId);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        }
    }

    private void visualizarFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String funcionarioStrId = request.getParameter("id");
        if (funcionarioStrId == null) {
            request.setAttribute("mensagem", "Erro: " + "Invocação inválida: id é nulo");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
        try {
            int funcionarioId = Integer.parseInt(funcionarioStrId);
            Pessoa funcionario = GerenteFacade.getFuncionarioTodasInformacoesById(funcionarioId);
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/visualizafuncionario.jsp");
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: " + "Id para remoção inválido: " + funcionarioStrId);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }

    private void removerFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String funcionarioStrId = request.getParameter("id");
        if (funcionarioStrId == null) {
            request.setAttribute("mensagem", "Erro: " + "Invocação inválida: id é nulo");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
        try {
            int funcionarioId = Integer.parseInt(funcionarioStrId);

            Pessoa funcionario = GerenteFacade.getFuncionarioTodasInformacoesById(funcionarioId);

            GerenteFacade.deleteFuncionario(funcionario);

            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=paginainicial");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: " + "Id para remoção inválido: " + funcionarioStrId);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        }
    }

    private void listarAtendimentosEmAberto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Atendimento> atendimentos = AtendimentoFacade.getAtendimentosAbertos();
            request.setAttribute("atendimentos", atendimentos);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/listaatendimentoaberto.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?action=paginainicial");
            rd.forward(request, response);
            return;
        }
    }

    private void listarAtendimentos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Atendimento> atendimentos = AtendimentoFacade.getAtendimentosTodos();
            request.setAttribute("atendimentos", atendimentos);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/listaatendimento.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?action=paginainicial");
            rd.forward(request, response);
            return;
        }
    }

    private void relatorios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/gerente/relatorios.jsp");
        rd.forward(request, response);
        return;
    }

    private void salvarCadastroFuncionario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("senhaIncoerente", null);
        request.setAttribute("preenchimentoIncompleto", null);
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
        String cargo = request.getParameter("cargo");
        String telefone = request.getParameter("telefone").replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
        String email = request.getParameter("email");
        String senha = request.getParameter("password");
        String senhaconfirma = request.getParameter("passwordconfirm");
        String rua = request.getParameter("endereco");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep").replace("-", "");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");

        try {
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            RequestDispatcher rd = null;
            if (isNullOrEmpty(nome) || isNullOrEmpty(cpf) || isNullOrEmpty(telefone) || isNullOrEmpty(email)
                    || isNullOrEmpty(rua) || isNullOrEmpty(numero) || isNullOrEmpty(senha) || isNullOrEmpty(senhaconfirma)
                    || isNullOrEmpty(complemento) || isNullOrEmpty(bairro) || isNullOrEmpty(cep) || isNullOrEmpty(cidade) || isNullOrEmpty(estado) || isNullOrEmpty(cargo)) {

                request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
                rd = getServletContext().getRequestDispatcher("/gerente/cadastrofuncionario.jsp");
                rd.forward(request, response);
                return;
            } else if (!senha.equals(senhaconfirma)) {

                request.setAttribute("senhaIncoerente", "As senhas não conheferem.");
                rd = getServletContext().getRequestDispatcher("/gerente/cadastrofuncionario.jsp");
                rd.forward(request, response);

            }
            Pessoa funcionario = new Pessoa();
            Endereco endereco = new Endereco();
            int cidadeId = Integer.parseInt(cidade);
            Cidade cidadeObj = GerenteFacade.getCidadeById(cidadeId);
            endereco.setCidade(cidadeObj);
            endereco.setBairro(bairro);
            endereco.setCep(cep);
            endereco.setComplemento(complemento);
            endereco.setRua(rua);
            int numeroInt = Integer.parseInt(numero);
            endereco.setNumero(numeroInt);
            endereco.setId(GerenteFacade.cadastrarEndereco(endereco));
            funcionario.setEndereco(endereco);
            funcionario.setCpf(cpf);
            funcionario.setEmail(email);
            funcionario.setNomeCompleto(nome);
            funcionario.setTelefone(telefone);
            funcionario.setSenha(senha);
            int tipoPessoaId = Integer.parseInt(cargo);
            TipoPessoa tipoPessoa = GerenteFacade.getTipoPessoaById(tipoPessoaId);
            funcionario.setTipoPessoa(tipoPessoa);
            funcionario.setId(GerenteFacade.cadastrarFuncionario(funcionario));
            rd = getServletContext().getRequestDispatcher("/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (NumberFormatException e) {
            request.setAttribute("mensagem", "Erro: " + "Id para remoção inválido: ");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        } catch (FacadeException ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=listafuncionario");
            rd.forward(request, response);
            return;
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void defaultCase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
        request.setAttribute("mensagem", "Erro: ação inválida.");
        request.setAttribute("voltar", request.getContextPath() + "/PessoaServlet?acao=login");
        rd.forward(request, response);
        return;
    }

    private void relatorioFuncionarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            // Host onde o servlet esta executando
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath() + "/relatorioFuncionarios.jasper";
            // URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
            // Parâmetros do relatório
            HashMap params = new HashMap();
            // Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, factory.getConnection());
            if (bytes != null) {
                // A página será mostrada em PDF
                response.setContentType("application/pdf");
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } catch (DAOException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (JRException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (Exception e){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        }
    }

    private void relatorioProdutosMaisReclamados(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            // Host onde o servlet esta executando
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath() + "/relatorioProdutosMaisReclamados.jasper";
            // URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
            // Parâmetros do relatório
            HashMap params = new HashMap();
            // Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, factory.getConnection());
            if (bytes != null) {
                // A página será mostrada em PDF
                response.setContentType("application/pdf");
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } catch (DAOException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (JRException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (Exception e){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        }
    }

    private void relatorioAtendimentosEmAberto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            // Host onde o servlet esta executando
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath() + "/relatorioAtendimentosEmAberto.jasper";
            // URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
            // Parâmetros do relatório
            HashMap params = new HashMap();
            // Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, factory.getConnection());
            if (bytes != null) {
                // A página será mostrada em PDF
                response.setContentType("application/pdf");
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } catch (DAOException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (JRException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (Exception e){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        }
    }

    private void relatorioReclamacoes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ConnectionFactory factory = new ConnectionFactory()) {
            // Host onde o servlet esta executando
            String host = "http://" + request.getServerName() + ":" + request.getServerPort();
            // Caminho contextualizado do relatório compilado
            String jasper = request.getContextPath() + "/relatorioReclamacoes.jasper";
            // URL para acesso ao relatório
            URL jasperURL = new URL(host + jasper);
            // Parâmetros do relatório
            HashMap params = new HashMap();
            // Geração do relatório
            byte[] bytes = JasperRunManager.runReportToPdf(jasperURL.openStream(), params, factory.getConnection());
            if (bytes != null) {
                // A página será mostrada em PDF
                response.setContentType("application/pdf");
                // Envia o PDF para o Cliente
                OutputStream ops = response.getOutputStream();
                ops.write(bytes);
            }
        } catch (DAOException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (JRException e) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        } catch (Exception e){
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + e.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/GerenteServlet?acao=relatorios");
            rd.forward(request, response);
        }
    }

}
