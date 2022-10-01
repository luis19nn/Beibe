/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.Cidade;
import beibe.beans.Endereco;
import beibe.beans.Estado;
import beibe.beans.LoginBean;
import beibe.beans.Pessoa;
import beibe.beans.TipoPessoa;
import beibe.dao.CidadeDAO;
import beibe.dao.ConnectionFactory;
import beibe.dao.EnderecoDAO;
import beibe.dao.EstadoDAO;
import beibe.dao.PessoaDAO;
import beibe.dao.TipoPessoaDAO;
import beibe.exceptions.DAOException;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author luis
 */
@WebServlet(name = "PessoaServlet", urlPatterns = {"/PessoaServlet"})
public class PessoaServlet extends HttpServlet {

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
            case "login":
                isLogado(request, response);
                abrirTelaLogin(request, response);
                break;
            case "efetuarlogin":
                isLogado(request, response);
                efetuarLogin(request, response);
                break;
            case "cadastrar":
                cadastrar(request, response);
                break;
            case "autocadastro":
                abrirTelaAutoCadastro(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
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

    private void isLogado(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        if (session != null) {
            LoginBean login = (LoginBean) session.getAttribute("login");
            if (login != null) {
                RequestDispatcher dispatcher;
                switch (login.getTipoUsuario()) {
                    case "Cliente":
                        dispatcher = getServletContext().getRequestDispatcher("/ClienteServlet?acao=listaatendimento");
                        break;
                    case "Funcionário":
                        dispatcher = getServletContext().getRequestDispatcher("/FuncionarioServlet?acao=listaatendimentoaberto");
                        break;
                    case "Gerente":
                        dispatcher = getServletContext().getRequestDispatcher("/GerenteServlet?acao=paginainicial");
                        break;
                    default:
                        dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                        request.setAttribute("mensagem", "Houve um erro ao obter o tipo de usuario...");
                        request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                        break;
                }
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void efetuarLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String email = request.getParameter("username");
        String senha = request.getParameter("password");

        if (isNullOrEmpty(email) || isNullOrEmpty(senha)) {
            request.setAttribute("aviso", "Preencha todos os campos.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                Pessoa pessoa = (new PessoaDAO(con.getConnection())).login(email, senha);

                if (pessoa == null || pessoa.getTipoPessoa() == null) {
                    request.setAttribute("aviso", "Usuário ou senha incorreto");
                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/login.jsp");
                    dispatcher.forward(request, response);
                    return;

                } else {

                    HttpSession session = request.getSession(true);

                    session.setAttribute("idusuario", pessoa.getId());
                    session.setAttribute("tipousuario", pessoa.getTipoPessoa().getDescricao());

                    LoginBean login = new LoginBean();
                    login.setId(pessoa.getId());
                    login.setTipoUsuario(pessoa.getTipoPessoa().getDescricao());
                    session.setAttribute("login", login);

                    RequestDispatcher dispatcher;
                    switch (login.getTipoUsuario()) {
                        case "Cliente":
                            dispatcher = getServletContext().getRequestDispatcher("/ClienteServlet?acao=listaatendimento");
                            break;
                        case "Funcionário":
                            dispatcher = getServletContext().getRequestDispatcher("/FuncionarioServlet?acao=listaatendimentoaberto");
                            break;
                        case "Gerente":
                            dispatcher = getServletContext().getRequestDispatcher("/GerenteServlet?acao=paginainicial");
                            break;
                        default:
                            dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                            request.setAttribute("mensagem", "Houve um erro ao obter o tipo de usuario...");
                            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                            break;
                    }

                    dispatcher.forward(request, response);
                    return;
                }

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar efetuar o login...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void abrirTelaLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("idusuario", null);
        request.getSession().setAttribute("tipousuario", null);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/login.jsp");
        dispatcher.forward(request, response);
        return;
    }

    private void cadastrar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        request.setAttribute("senhaIncoerente", null);
        request.setAttribute("preenchimentoIncompleto", null);
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf").replace(".", "").replace("-", "");
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

        if (isNullOrEmpty(nome) || isNullOrEmpty(cpf) || isNullOrEmpty(telefone) || isNullOrEmpty(email)
                || isNullOrEmpty(senha) || isNullOrEmpty(senhaconfirma) || isNullOrEmpty(rua) || isNullOrEmpty(numero)
                || isNullOrEmpty(complemento) || isNullOrEmpty(bairro) || isNullOrEmpty(cep) || isNullOrEmpty(cidade) || isNullOrEmpty(estado)) {

            request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/autocadastro.jsp");
            dispatcher.forward(request, response);
            return;

        } else if (!senha.equals(senhaconfirma)) {

            request.setAttribute("senhaIncoerente", "As senhas não são iguais.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/autocadastro.jsp");
            dispatcher.forward(request, response);
            return;

        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {
                TipoPessoa tipoPessoaCliente = new TipoPessoaDAO(con.getConnection()).buscarCliente();

                Cidade cid = new Cidade();
                cid.setId(Integer.parseInt(cidade));
                Estado est = new Estado();
                est.setId(Integer.parseInt(estado));
                Endereco endereco = new Endereco(cid, rua, Integer.parseInt(numero), complemento, bairro, cep);

                int idEndereco = new EnderecoDAO(con.getConnection()).inserirReturningId(endereco);

                endereco.setId(idEndereco);

                Pessoa cliente = new Pessoa(nome, cpf, email, telefone, senha, tipoPessoaCliente, endereco);

                new PessoaDAO(con.getConnection()).inserir(cliente);

                response.sendRedirect(request.getContextPath() + "/geral/login.jsp?cadastradoSucesso=Cadastrado%20com%20Sucesso!");
                return;
            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro o ao tentar efetuar o cadastro...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void abrirTelaAutoCadastro(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        if (request.getSession().getAttribute("estados") == null || request.getSession().getAttribute("cidades") == null) {

            try (ConnectionFactory con = new ConnectionFactory()) {
                List<Estado> estados = (new EstadoDAO(con.getConnection())).buscarTodos();
                request.getSession().setAttribute("estados", estados);

                List<Cidade> cidades = (new CidadeDAO(con.getConnection())).buscarTodos();
                request.getSession().setAttribute("cidades", cidades);

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro o ao tentar acessar a página de auto cadastro...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        response.sendRedirect(request.getContextPath() + "/geral/autocadastro.jsp");
        return;
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
            RequestDispatcher rd = request.getRequestDispatcher("/geral/logout.jsp");
            String msg = "Usuário desconectado com sucesso";
            request.setAttribute("msg", msg);
            rd.forward(request, response);
            return;
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }
    
    private void defaultCase(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
        request.setAttribute("mensagem", "Erro: ação inválida.");
        request.setAttribute("voltar", request.getContextPath() + "/PessoaServlet?acao=login");
        rd.forward(request, response);
        return;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
    
}
