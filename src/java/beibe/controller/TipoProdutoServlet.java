/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.LoginBean;
import beibe.beans.TipoProduto;
import beibe.dao.ConnectionFactory;
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
@WebServlet(name = "TipoProdutoServlet", urlPatterns = {"/TipoProdutoServlet"})
public class TipoProdutoServlet extends HttpServlet {

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
                case "categorias":
                    validaLogado(request, response);
                    ListarCategorias(request, response);
                    break;
                case "cadastrocategoria":
                    validaLogado(request, response);
                    CadastrarCategoria(request, response);
                    break;
                case "visualizar":
                    validaLogado(request, response);
                    VisualizarCategoria(request, response);
                    break;
                case "excluir":
                    validaLogado(request, response);
                    ExcluirCategoria(request, response);
                    break;
                case "alterar":
                    validaLogado(request, response);
                    AlterarCategoria(request, response);
                default:
                    validaLogado(request, response);
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

    private void ListarCategorias(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());

            List<TipoProduto> tiposProdutos = dao.buscarTodos();
            request.setAttribute("categorias", tiposProdutos);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/categorias.jsp");
            dispatcher.forward(request, response);
            return;

        } catch (DAOException ex) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar buscar as categorias...");
            request.setAttribute("voltar", request.getContextPath() + "/funcionario/cadastrocategoria.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private void CadastrarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoria = request.getParameter("nome");

        if (categoria == null || categoria.isEmpty()) {
            request.setAttribute("avisoPreenchimento", "Preencha todos os campos.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastrocategoria.jsp");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {
                TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());

                TipoProduto tipoProduto = new TipoProduto();

                String id = request.getParameter("id");
                tipoProduto.setDescricao(categoria);
                if (id != null && !id.isEmpty()) {
                    tipoProduto.setId(Integer.parseInt(id));
                    dao.atualizar(tipoProduto);
                } else {
                    dao.inserir(tipoProduto);
                }

                response.sendRedirect(request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar manipular a categoria...");
                request.setAttribute("voltar", request.getContextPath() + "/funcionario/cadastrocategoria.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void VisualizarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao visualizar categoria...");
            request.setAttribute("voltar", request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());

                TipoProduto tipoProduto = dao.buscar(Integer.parseInt(id));

                request.setAttribute("categoriavisualizar", tipoProduto);
                request.setAttribute("permiteAlterar", false);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastrocategoria.jsp");
                dispatcher.forward(request, response);
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar visualizar a categoria...");
                request.setAttribute("voltar", request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void ExcluirCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao excluir categoria...");
            request.setAttribute("voltar", request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());

                dao.remover(Integer.parseInt(id));

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/TipoProdutoServlet?acao=categorias");
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

    private void AlterarCategoria(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao alterar categoria...");
            request.setAttribute("voltar", request.getContextPath() + "/TipoProdutoServlet?acao=categorias");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());

                TipoProduto tipoProduto = dao.buscar(Integer.parseInt(id));

                request.setAttribute("categoriavisualizar", tipoProduto);
                request.setAttribute("permiteAlterar", true);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/funcionario/cadastrocategoria.jsp");
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
