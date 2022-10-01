/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.Atendimento;
import beibe.beans.LoginBean;
import beibe.facade.FuncionarioFacade;
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
@WebServlet(name = "FuncionarioServlet", urlPatterns = {"/FuncionarioServlet"})
public class FuncionarioServlet extends HttpServlet {

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
       
        switch(acao){
            case "listaatendimentoaberto":
                validaFuncionarioLogado(request, response);
                listaAtendimentosAberto(request, response);
                break;
            case "listatodosatendimentos":
                validaFuncionarioLogado(request, response);
                listaTodosAtendimentos(request, response);
                break;
            case "resolveratendimento":
                validaFuncionarioLogado(request, response);
                resolverAtendimento(request, response);
                break;
            case "visualizaratendimento":
                validaFuncionarioLogado(request, response);
                visualizarAtendimento(request, response);
                break;
            case "solucionaratendimento":
                validaFuncionarioLogado(request, response);
                solucionarAtendimento(request, response);
                break;
            default:
                validaFuncionarioLogado(request, response);
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

    public void validaFuncionarioLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
       
        LoginBean login = (LoginBean) session.getAttribute("login");

        if (session == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário deve estar logado para acessar.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        } else if (!"Funcionário".equalsIgnoreCase(login.getTipoUsuario())) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário não possui permissão para acessar a página.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }
    
    private void listaAtendimentosAberto (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Atendimento> atendimentos = FuncionarioFacade.getAtendimentosAbertos();
            request.setAttribute("atendimentos", atendimentos);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionario/listaatendimentoaberto.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }
    
    private void listaTodosAtendimentos (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Atendimento> atendimentos = FuncionarioFacade.getTodosAtendimentos();
            request.setAttribute("atendimentos", atendimentos);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionario/listaatendimento.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }
    
    private void resolverAtendimento (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
            
        try {
            if(id == null || id.isEmpty()){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao resolver atendimento...");
                request.setAttribute("voltar", request.getContextPath() + "/FuncionarioServlet?acao=listaatendimento");
                dispatcher.forward(request,response);
                return;
            }
            Atendimento atendimento = FuncionarioFacade.getAtendimento(Integer.parseInt(id));
            request.setAttribute("atendimento", atendimento);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionario/resolveratendimento.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }
    
    private void visualizarAtendimento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
       
        try {
            if(id == null || id.isEmpty()){
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao visualizar atendimento...");
                request.setAttribute("voltar", request.getContextPath() + "/FuncionarioServlet?acao=listaatendimento");
                dispatcher.forward(request,response);
                return;
            }
            Atendimento atendimento = FuncionarioFacade.getAtendimento(Integer.parseInt(id));
            request.setAttribute("atendimento", atendimento);
            request.setAttribute("permiteAlterar", false);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/funcionario/visualizaratendimento.jsp");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        }
    }    
        
    private void solucionarAtendimento (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("idatendimento");
        String resolucao = request.getParameter("resolucao");
        try {
            FuncionarioFacade.setSolucao(Integer.parseInt(id), resolucao);
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/FuncionarioServlet?acao=listaatendimentoaberto");
            rd.forward(request, response);
            return;
        } catch (Exception ex) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + ex.getMessage());
            request.setAttribute("voltar", request.getContextPath() + "/FuncionarioServlet?acao=listaatendimentoaberto");
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