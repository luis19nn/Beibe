/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.LoginBean;
import beibe.beans.Produto;
import beibe.beans.TipoProduto;
import beibe.dao.ConnectionFactory;
import beibe.dao.ProdutoDAO;
import beibe.dao.TipoProdutoDAO;
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
@WebServlet(name = "ProdutoServlet", urlPatterns = {"/ProdutoServlet"})
public class ProdutoServlet extends HttpServlet {

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

        //if(((String)request.getSession().getAttribute("tipousuario")).equals("Funcionário")) {
        switch (acao) {
            case "produtos":
                validaLogado(request, response);
                listarProdutos(request, response);
                break;
            case "cadastroproduto":
                validaLogado(request, response);
                abrirTelaCadastroProduto(request, response);
                break;
            case "cadastrarproduto":
                validaLogado(request, response);
                cadastrarProduto(request, response);
                break;
            case "excluir":
                validaLogado(request, response);
                excluirProduto(request, response);
                break;
            case "visualizar":
                validaLogado(request, response);
                visualizarProduto(request, response);
                break;
            case "alterar":
                validaLogado(request, response);
                aterarProduto(request, response);
            default:
                validaLogado(request, response);
                defaultCase(request, response);
                break;
        }
//        } else {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");;
//            request.setAttribute("mensagem", "Você nao tem acesso a essa página..");
//            request.setAttribute("voltar", request.getContextPath() + "/PessoaServlet?acao=login");
//            dispatcher.forward(request,response);
//        }
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

    private void abrirTelaCadastroProduto(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {

        //buscar todas as categorias
        if (request.getSession().getAttribute("categorias") == null) {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO(con.getConnection());

                List<TipoProduto> categorias = tipoProdutoDAO.buscarTodos();

                request.setAttribute("categorias", categorias);

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao cadastrar protudo...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastroproduto.jsp");
        dispatcher.forward(request, response);
        return;
    }

    private void cadastrarProduto(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {
        String nome = request.getParameter("nome");
        String categoria = request.getParameter("categoria");
        String peso = request.getParameter("peso");
        String descricao = request.getParameter("descricao");

        if (isNullOrEmpty(nome) || isNullOrEmpty(categoria) || isNullOrEmpty(peso) || isNullOrEmpty(descricao)) {
            request.setAttribute("avisoPreenchimento", "Preencha todos os campos.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProdutoServlet?acao=cadastroproduto");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                ProdutoDAO produtoDAO = new ProdutoDAO(con.getConnection());

                TipoProduto c = new TipoProduto();
                c.setId(Integer.parseInt(categoria));

                Produto p = new Produto(c, nome, descricao, Integer.parseInt(peso));

                String id = request.getParameter("id");

                if (id != null && !id.isEmpty()) {
                    p.setId(Integer.parseInt(id));
                    produtoDAO.atualizar(p);
                } else {
                    produtoDAO.inserir(p);
                }

                response.sendRedirect(request.getContextPath() + "/ProdutoServlet?acao=produtos");
                return;

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao cadastrar produto...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void listarProdutos(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            ProdutoDAO dao = new ProdutoDAO(con.getConnection());

            List<Produto> produtos = dao.buscarTodos();
            request.setAttribute("produtos", produtos);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/produtos.jsp");
            dispatcher.forward(request, response);
            return;

        } catch (DAOException ex) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar buscar os produtos...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private void excluirProduto(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao excluir produto...");
            request.setAttribute("voltar", request.getContextPath() + "/ProdutoServlet?acao=produtos");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                ProdutoDAO dao = new ProdutoDAO(con.getConnection());

                dao.remover(Integer.parseInt(id));

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProdutoServlet?acao=produtos");
                dispatcher.forward(request, response);
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar excluir o produto...");
                request.setAttribute("voltar", request.getContextPath() + "/ProdutoServlet?acao=produtos");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void visualizarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao visualizar produto...");
            request.setAttribute("voltar", request.getContextPath() + "/ProdutoServlet?acao=produtos");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO(con.getConnection());

                List<TipoProduto> categorias = tipoProdutoDAO.buscarTodos();

                request.setAttribute("categorias", categorias);

                ProdutoDAO dao = new ProdutoDAO(con.getConnection());

                Produto produto = dao.buscar(Integer.parseInt(id));

                request.setAttribute("produtovisualizar", produto);
                request.setAttribute("permiteAlterar", false);

                String param = "?nome=" + produto.getNome() + "&categoria=" + produto.getTipoProduto().getId() + "&peso=" + produto.getPeso() + "&descricao=" + produto.getDescricao() + "";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastroproduto.jsp" + param);
                dispatcher.forward(request, response);
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar visualizar o produto...");
                request.setAttribute("voltar", request.getContextPath() + "/ProdutoServlet?acao=produtos");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void aterarProduto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao alterar produto...");
            request.setAttribute("voltar", request.getContextPath() + "/ProdutoServlet?acao=produtos");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO tipoProdutoDAO = new TipoProdutoDAO(con.getConnection());

                List<TipoProduto> categorias = tipoProdutoDAO.buscarTodos();

                request.setAttribute("categorias", categorias);

                ProdutoDAO dao = new ProdutoDAO(con.getConnection());

                Produto produto = dao.buscar(Integer.parseInt(id));

                request.setAttribute("produtovisualizar", produto);
                request.setAttribute("permiteAlterar", true);

                String param = "?nome=" + produto.getNome() + "&categoria=" + produto.getTipoProduto().getId() + "&peso=" + produto.getPeso() + "&descricao=" + produto.getDescricao() + "";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastroproduto.jsp" + param);
                dispatcher.forward(request, response);
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar excluir a categoria...");
                request.setAttribute("voltar", request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void validaLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);

        LoginBean login = (LoginBean) session.getAttribute("login");

        if (login == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário deve estar logado para acessar.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
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

}
