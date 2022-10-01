/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package beibe.controller;

import beibe.beans.Atendimento;
import beibe.beans.Categoria;
import beibe.beans.Cidade;
import beibe.beans.Endereco;
import beibe.beans.Estado;
import beibe.beans.LoginBean;
import beibe.beans.Pessoa;
import beibe.beans.Produto;
import beibe.beans.SituacaoAtendimento;
import beibe.beans.TipoAtendimento;
import beibe.beans.TipoProduto;
import beibe.dao.AtendimentoDAO;
import beibe.dao.CidadeDAO;
import beibe.dao.ConnectionFactory;
import beibe.dao.EnderecoDAO;
import beibe.dao.EstadoDAO;
import beibe.dao.PessoaDAO;
import beibe.dao.ProdutoDAO;
import beibe.dao.TipoAtendimentoDAO;
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
import java.util.Date;
import java.util.List;

/**
 *
 * @author luis
 */
@WebServlet(name = "ClienteServlet", urlPatterns = {"/ClienteServlet"})
public class ClienteServlet extends HttpServlet {

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
            case "atualizarcadastro":
                validaClienteLogado(request, response);
                AbrirTelaAtualizarCadastro(request, response);
                break;
            case "atualizar":
                validaClienteLogado(request, response);
                AtualizarCadastro(request, response);
                break;
            case "listaatendimento":
                validaClienteLogado(request, response);
                ListarAtendimentos(request, response);
                break;
            case "criaratendimento":
                validaClienteLogado(request, response);
                CriarAtendimento(request, response);
                break;
            case "carregaratendimento":
                validaClienteLogado(request, response);
                AbrirTelaCriarAtendimento(request, response);
                break;
            case "removeratendimento":
                validaClienteLogado(request, response);
                RemoverAtendimento(request, response);
                break;
            case "visualizaratendimento":
                validaClienteLogado(request, response);
                VisualizarAtendimento(request, response);
                break;
            default:
                validaClienteLogado(request, response);
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

    private void AbrirTelaAtualizarCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        Integer id = (Integer) request.getSession().getAttribute("idusuario");

        if (id == null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar atualizar o cadastro...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                PessoaDAO pessoaDAO = new PessoaDAO(con.getConnection());
                EnderecoDAO enderecoDAO = new EnderecoDAO(con.getConnection());
                CidadeDAO cidadeDAO = new CidadeDAO(con.getConnection());

                Pessoa pessoa = pessoaDAO.buscar(id.intValue());
                Endereco endereco = enderecoDAO.buscar(pessoa.getEndereco().getId());
                Cidade cidade = cidadeDAO.buscar(endereco.getCidade().getId());

                endereco.setCidade(cidade);
                pessoa.setEndereco(endereco);

                request.getSession().setAttribute("usuarioedicao", pessoa);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/atualizacaocadastro.jsp");
                dispatcher.forward(request, response);
                return;

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar atualizar o cadastro...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void AtualizarCadastro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("senhaIncoerente", null);
        request.setAttribute("preenchimentoIncompleto", null);
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");
        String senha = request.getParameter("password");
        String senhaconfirma = request.getParameter("passwordconfirm");
        String rua = request.getParameter("endereco");
        String numero = request.getParameter("numero");
        String complemento = request.getParameter("complemento");
        String bairro = request.getParameter("bairro");
        String cep = request.getParameter("cep");
        String cidade = request.getParameter("cidade");
        String estado = request.getParameter("estado");

        if (isNullOrEmpty(nome) || isNullOrEmpty(cpf) || isNullOrEmpty(telefone) || isNullOrEmpty(email)
                || isNullOrEmpty(senha) || isNullOrEmpty(senhaconfirma) || isNullOrEmpty(rua) || isNullOrEmpty(numero)
                || isNullOrEmpty(complemento) || isNullOrEmpty(bairro) || isNullOrEmpty(cep) || isNullOrEmpty(cidade) || isNullOrEmpty(estado)) {

            request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/atualizacaocadastro.jsp");
            dispatcher.forward(request, response);
            return;

        } else if (!senha.equals(senhaconfirma)) {

            request.setAttribute("senhaIncoerente", "As senhas não são iguais.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/atualizacaocadastro.jsp");
            dispatcher.forward(request, response);
            return;

        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {
                EnderecoDAO enderecoDAO = new EnderecoDAO(con.getConnection());

                Cidade cid = new Cidade();
                cid.setId(Integer.parseInt(cidade));
                Estado est = new Estado();
                est.setId(Integer.parseInt(estado));

                int idEndereco = ((Pessoa) request.getSession().getAttribute("usuarioedicao")).getEndereco().getId();
                Endereco endereco = new Endereco(idEndereco, cid, rua, Integer.parseInt(numero), complemento, bairro, cep);

                enderecoDAO.atualizar(endereco);

                Integer id = (Integer) request.getSession().getAttribute("idusuario");
                Pessoa cliente = new Pessoa(id.intValue(), nome, cpf, email, telefone, senha, null, endereco);

                new PessoaDAO(con.getConnection()).atualizar(cliente);

                request.setAttribute("cadastradoSucesso", "Atualizado com sucesso!");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ClienteServlet?acao=atualizarcadastro");
                dispatcher.forward(request, response);
                return;
            } catch (DAOException e) {
                e.printStackTrace();
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro o ao tentar efetuar o cadastro...");
                request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void AbrirTelaCriarAtendimento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("categorias") == null || request.getSession().getAttribute("produtos") == null || request.getSession().getAttribute("tiposatendimento") == null) {

            try (ConnectionFactory con = new ConnectionFactory()) {
                List<TipoProduto> categorias = (new TipoProdutoDAO(con.getConnection())).buscarTodos();
                request.getSession().setAttribute("categorias", categorias);

                List<Produto> produtos = (new ProdutoDAO(con.getConnection())).buscarTodos();
                request.getSession().setAttribute("produtos", produtos);

                List<TipoAtendimento> tiposatendimento = (new TipoAtendimentoDAO(con.getConnection())).buscarTodos();
                request.getSession().setAttribute("tiposatendimento", tiposatendimento);

            } catch (DAOException e) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro o ao tentar acessar a página de criação de atendimento...");
                request.setAttribute("voltar", request.getContextPath() + "/cliente/listaatendimento.jsp");
                dispatcher.forward(request, response);
                return;
            }
        }

        Integer id = (Integer) request.getSession().getAttribute("idusuario");

        if (id == null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar criar atendimento...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/criacaoatendimento.jsp");
        dispatcher.forward(request, response);
        return;
    }

    private void CriarAtendimento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id = (Integer) request.getSession().getAttribute("idusuario");
        String produto = request.getParameter("produto");
        String categoria = request.getParameter("categoria");
        String tipoAtendimento = request.getParameter("tipoatendimento");
        String descricao = request.getParameter("descricao");

        if (isNullOrEmpty(descricao) || isNullOrEmpty(produto) || isNullOrEmpty(categoria) || isNullOrEmpty(tipoAtendimento)) {
            request.setAttribute("preenchimentoIncompleto", "Voce deve preencher todos os campos.");
            response.sendRedirect(request.getContextPath() + "/cliente/criacaoatendimento.jsp");
            return;
        }
        try (ConnectionFactory con = new ConnectionFactory()) {
            Atendimento novoAtendimento = new Atendimento();

            Categoria cat = new Categoria();
            cat.setId(Integer.parseInt(categoria));
            Produto prod = new Produto();
            prod.setId(Integer.parseInt(produto));
            TipoAtendimento tipo = new TipoAtendimento();
            tipo.setId(Integer.parseInt(tipoAtendimento));

            Pessoa cliente = new Pessoa();
            Date date = new Date();
            SituacaoAtendimento atendimento = new SituacaoAtendimento();
            atendimento.setId(1);

            cliente.setId(id);
            novoAtendimento.setCliente(cliente);

            novoAtendimento.setData(date);
            novoAtendimento.setDescricao(descricao);
            novoAtendimento.setProduto(prod);

            novoAtendimento.setSituacaoAtendimento(atendimento);
            novoAtendimento.setTipoAtendimento(tipo);

            AtendimentoDAO atendimentoDAO = new AtendimentoDAO(con.getConnection());
            atendimentoDAO.inserir(novoAtendimento);

            response.sendRedirect(request.getContextPath() + "/ClienteServlet?acao=listaatendimento");
            return;

        } catch (DAOException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro o ao tentar efetuar o cadastro...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

    }

    private void ListarAtendimentos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Integer id = (Integer) request.getSession().getAttribute("idusuario");

        if (id == null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar listar atendimento...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try (ConnectionFactory con = new ConnectionFactory()) {
            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());

            List<Atendimento> atendimentos = dao.buscarTodosDoCliente(id);
            request.setAttribute("atendimentos", atendimentos);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/listaatendimento.jsp");
            dispatcher.forward(request, response);
            return;

        } catch (DAOException ex) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao tentar buscar os atendimentos...");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
    }

    private void RemoverAtendimento(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException, ServletException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao excluir o atendimento...");
            request.setAttribute("voltar", request.getContextPath() + "/ClienteServlet?acao=listaatendimento");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {
                Atendimento atendimento = new Atendimento();
                atendimento.setId(Integer.parseInt(id));
                System.out.println("ID ATENDIMENTO = " + id);
                AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());

                dao.remover(atendimento);

                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ClienteServlet?acao=listaatendimento");
                dispatcher.forward(request, response);
                return;

            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar excluir o atendimento...");
                request.setAttribute("voltar", request.getContextPath() + "/ClienteServlet?acao=listaatendimento");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private void VisualizarAtendimento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        if (id == null || id.isEmpty()) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Houve um erro ao visualizar atendimento...");
            request.setAttribute("voltar", request.getContextPath() + "/ClienteServlet?acao=listaatendimento");
            dispatcher.forward(request, response);
            return;
        } else {
            try (ConnectionFactory con = new ConnectionFactory()) {

                TipoAtendimentoDAO tipoAtendimentoDAO = new TipoAtendimentoDAO(con.getConnection());

                List<TipoAtendimento> categorias = tipoAtendimentoDAO.buscarTodos();

                request.setAttribute("categorias", categorias);

                AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());

                Atendimento atendimento = dao.buscar(Integer.parseInt(id));

                request.setAttribute("atendimentovisualizar", atendimento);
                request.setAttribute("permiteAlterar", false);

                String param = "?situacaoatendimento=" + atendimento.getSituacaoAtendimento().getDescricao() + "&categoria=" + atendimento.getProduto().getTipoProduto().getDescricao() + "&produto=" + atendimento.getProduto().getNome() + "&tipoatendimento=" + atendimento.getTipoAtendimento().getDescricao() + "&descricao=" + atendimento.getDescricao();
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/cliente/visualizaratendimento.jsp" + param);
                dispatcher.forward(request, response);
                return;
            } catch (DAOException ex) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/geral/erro.jsp");
                request.setAttribute("mensagem", "Houve um erro ao tentar visualizar o produto...");
                request.setAttribute("voltar", request.getContextPath() + "/ClienteServlet?acao=listaatendimento");
                dispatcher.forward(request, response);
                return;
            }
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    private void validaClienteLogado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        LoginBean login = (LoginBean) session.getAttribute("login");

        if (session == null) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário deve estar logado para acessar.");
            request.setAttribute("voltar", request.getContextPath() + "/geral/login.jsp");
            rd.forward(request, response);
            return;
        } else if (!"Cliente".equalsIgnoreCase(login.getTipoUsuario())) {
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/geral/erro.jsp");
            request.setAttribute("mensagem", "Erro: " + "Usuário não possui permissão para acessar a página.");
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
