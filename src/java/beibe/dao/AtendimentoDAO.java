/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Atendimento;
import beibe.beans.Pessoa;
import beibe.beans.Produto;
import beibe.beans.SituacaoAtendimento;
import beibe.beans.TipoAtendimento;
import beibe.beans.TipoProduto;
import beibe.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class AtendimentoDAO implements DAO<Atendimento> {

    private Connection con = null;
    private final String INSERT = "INSERT INTO atendimento "
            + "(idtipoatendimento, idcliente, data, idproduto, descricao, solucao, idsituacaoatendimento) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT = "SELECT a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao, sa.descricao situacaoDescricao, tp.id idtipoproduto, tp.descricao categoria \n"
            + "from atendimento a, tipoatendimento ta, pessoa pe, produto pr, situacaoatendimento sa, tipoproduto tp \n"
            + "where ta.id = a.idtipoatendimento and pe.id = a.idcliente and pr.id = a.idproduto and sa.id = idsituacaoatendimento and tp.id = pr.idtipoproduto and a.id=?";
    private final String SELECTALL = "SELECT a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao, sa.descricao situacaoDescricao, tp.id idtipoproduto, tp.descricao categoria \n"
            + "from atendimento a, tipoatendimento ta, pessoa pe, produto pr, situacaoatendimento sa, tipoproduto tp \n"
            + "where ta.id = a.idtipoatendimento and pe.id = a.idcliente and pr.id = a.idproduto and sa.id = idsituacaoatendimento and tp.id = pr.idtipoproduto ORDER BY a.data";
    private final String SELECTALLDOCLIENTE = "SELECT a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao, sa.descricao situacaoDescricao, tp.id idtipoproduto, tp.descricao categoria \n"
            + "from atendimento a, tipoatendimento ta, pessoa pe, produto pr, situacaoatendimento sa, tipoproduto tp \n"
            + "where ta.id = a.idtipoatendimento and pe.id = a.idcliente and pr.id = a.idproduto and sa.id = idsituacaoatendimento and tp.id = pr.idtipoproduto and pe.id=?";
    private final String DELETE = "DELETE FROM atendimento WHERE id = ?";
    private final String BUSCAR_ATENDIMENTOS_ABERTOS = "SELECT a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao, sa.descricao situacaoDescricao, tp.id idtipoproduto, tp.descricao categoria \n"
            + "FROM atendimento a, tipoatendimento ta, pessoa pe, produto pr, situacaoatendimento sa, tipoproduto tp \n"
            + "WHERE a.idsituacaoatendimento = 1 and ta.id = a.idtipoatendimento and pe.id = a.idcliente and pr.id = a.idproduto and sa.id = idsituacaoatendimento and tp.id = pr.idtipoproduto order by a.data";
    private final String RESOLVE_ATENDIMENTO = "UPDATE atendimento SET solucao = ?,  idsituacaoatendimento = 2 WHERE id = ?";
    private final String QUERY_QNT_ANTEDIMENTOS_TOTAL = "SELECT count(*) as qntAtendimentos FROM atendimento";
    private final String QUERY_QNT_ATENDIMENTOS_ABERTOS = "select count (a.idtipoatendimento) as atendimentosabertos from atendimento a \n" +
            "inner join tipoproduto tp on (a.idproduto = tp.id)\n" +
            "inner join situacaoatendimento sa on (a.idsituacaoatendimento = sa.id) \n" +
            "where sa.descricao = 'Aberto'";
    
    private final String QUERY_QNT_ANTEDIMENTOS_TOTAL_BY_TIPOPRODUTO_ID = "select count (a.idtipoatendimento) as atendimentosabertos from atendimento a\n" +
            "inner join tipoproduto tp on (a.idproduto = tp.id)\n" +
            "inner join situacaoatendimento sa on (a.idsituacaoatendimento = sa.id)\n" +
            "where tp.id = ?";
    
    private final String QUERY_QNT_ANTEDIMENTOS_ABERTOS_BY_TIPOPRODUTO_ID = "select count (a.idtipoatendimento) as atendimentosabertos from atendimento a\n" +
            "inner join tipoproduto tp on (a.idproduto = tp.id)\n" +
            "inner join situacaoatendimento sa on (a.idsituacaoatendimento = sa.id)\n" +
            "where sa.descricao = 'Aberto' and tp.id = ?";

    public AtendimentoDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar AtendimentoDAO.");
        }
        this.con = con;
    }

    @Override
    public Atendimento buscar(int id) throws DAOException {
        Atendimento atendimento = null;
        try (PreparedStatement st = con.prepareStatement(SELECT)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    //a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, 
                    TipoAtendimento tipo = new TipoAtendimento();
                    atendimento = new Atendimento();
                    Pessoa cliente = new Pessoa();
                    Produto produto = new Produto();
                    SituacaoAtendimento situacao = new SituacaoAtendimento();
                    TipoProduto categoria = new TipoProduto();

                    atendimento.setId(rs.getInt(1));

                    tipo.setId(rs.getInt(2));
                    cliente.setId(rs.getInt(3));

                    atendimento.setData(rs.getDate(4));
                    produto.setId(rs.getInt(5));
                    //a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao,
                    atendimento.setDescricao(rs.getString(6));
                    atendimento.setSolucao(rs.getString(7));
                    situacao.setId(rs.getInt(8));
                    produto.setNome(rs.getString(9));
                    tipo.setDescricao(rs.getString(10));
                    //sa.descricao situacaoDescricao tp.id idtipoproduto, tp.descricao categoria
                    situacao.setDescricao(rs.getString(11));
                    categoria.setId(rs.getInt(12));
                    categoria.setDescricao(rs.getString(13));

                    atendimento.setCliente(cliente);
                    produto.setTipoProduto(categoria);
                    atendimento.setProduto(produto);
                    atendimento.setSituacaoAtendimento(situacao);
                    atendimento.setTipoAtendimento(tipo);

                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar atendimento: " + SELECT, e);
        }

        return atendimento;
    }

    @Override
    public List<Atendimento> buscarTodos() throws DAOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SELECTALL)) {

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    //a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, 
                    TipoAtendimento tipo = new TipoAtendimento();
                    Atendimento atendimento = new Atendimento();
                    Pessoa cliente = new Pessoa();
                    Produto produto = new Produto();
                    SituacaoAtendimento situacao = new SituacaoAtendimento();
                    TipoProduto categoria = new TipoProduto();

                    atendimento.setId(rs.getInt(1));

                    tipo.setId(rs.getInt(2));
                    cliente.setId(rs.getInt(3));

                    atendimento.setData(rs.getDate(4));
                    produto.setId(rs.getInt(5));
                    //a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao,
                    atendimento.setDescricao(rs.getString(6));
                    atendimento.setSolucao(rs.getString(7));
                    situacao.setId(rs.getInt(8));
                    produto.setNome(rs.getString(9));
                    tipo.setDescricao(rs.getString(10));
                    //sa.descricao situacaoDescricao tp.id idtipoproduto, tp.descricao categoria
                    situacao.setDescricao(rs.getString(11));
                    categoria.setId(rs.getInt(12));
                    categoria.setDescricao(rs.getString(13));

                    atendimento.setCliente(cliente);
                    produto.setTipoProduto(categoria);
                    atendimento.setProduto(produto);
                    atendimento.setSituacaoAtendimento(situacao);
                    atendimento.setTipoAtendimento(tipo);

                    atendimentos.add(atendimento);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar atendimentos: " + SELECTALL, e);
        }

        return atendimentos;
    }

    public List<Atendimento> buscarTodosDoCliente(int id) throws DAOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SELECTALLDOCLIENTE)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    //a.id idatendimento, ta.id idtipoatendimento, pe.id idpessoa, a.data, pr.id idproduto, 
                    TipoAtendimento tipo = new TipoAtendimento();
                    Atendimento atendimento = new Atendimento();
                    Pessoa cliente = new Pessoa();
                    Produto produto = new Produto();
                    SituacaoAtendimento situacao = new SituacaoAtendimento();
                    TipoProduto categoria = new TipoProduto();

                    atendimento.setId(rs.getInt(1));

                    tipo.setId(rs.getInt(2));
                    cliente.setId(rs.getInt(3));

                    atendimento.setData(rs.getDate(4));
                    produto.setId(rs.getInt(5));
                    //a.descricao, a.solucao, sa.id idsituacaoatendimento, pr.nome, ta.descricao tipoDescricao,
                    atendimento.setDescricao(rs.getString(6));
                    atendimento.setSolucao(rs.getString(7));
                    situacao.setId(rs.getInt(8));
                    produto.setNome(rs.getString(9));
                    tipo.setDescricao(rs.getString(10));
                    //sa.descricao situacaoDescricao tp.id idtipoproduto, tp.descricao categoria
                    situacao.setDescricao(rs.getString(11));
                    categoria.setId(rs.getInt(12));
                    categoria.setDescricao(rs.getString(13));

                    atendimento.setCliente(cliente);
                    produto.setTipoProduto(categoria);
                    atendimento.setProduto(produto);
                    atendimento.setSituacaoAtendimento(situacao);
                    atendimento.setTipoAtendimento(tipo);

                    atendimentos.add(atendimento);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar atendimentos: " + SELECTALL, e);
        }

        return atendimentos;
    }

    public List<Atendimento> buscarAtendimentosEmAberto() throws DAOException {
        List<Atendimento> atendimentos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(BUSCAR_ATENDIMENTOS_ABERTOS)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                TipoAtendimento tipo = new TipoAtendimento();
                Atendimento atendimento = new Atendimento();
                Pessoa cliente = new Pessoa();
                Produto produto = new Produto();
                SituacaoAtendimento situacao = new SituacaoAtendimento();
                TipoProduto categoria = new TipoProduto();

                atendimento.setId(rs.getInt(1));

                tipo.setId(rs.getInt(2));
                cliente.setId(rs.getInt(3));

                atendimento.setData(rs.getDate(4));
                produto.setId(rs.getInt(5));

                atendimento.setDescricao(rs.getString(6));
                atendimento.setSolucao(rs.getString(7));
                situacao.setId(rs.getInt(8));
                produto.setNome(rs.getString(9));
                tipo.setDescricao(rs.getString(10));

                situacao.setDescricao(rs.getString(11));
                categoria.setId(rs.getInt(12));
                categoria.setDescricao(rs.getString(13));

                atendimento.setCliente(cliente);
                produto.setTipoProduto(categoria);
                atendimento.setProduto(produto);
                atendimento.setSituacaoAtendimento(situacao);
                atendimento.setTipoAtendimento(tipo);

                atendimentos.add(atendimento);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar atendimentos: " + BUSCAR_ATENDIMENTOS_ABERTOS, e);
        }

        return atendimentos;
    }

    @Override
    public void inserir(Atendimento u) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT)) {

            java.util.Date dt = u.getData();
            java.sql.Date data = new java.sql.Date(dt.getTime());

            st.setInt(1, u.getTipoAtendimento().getId());
            st.setInt(2, u.getCliente().getId());
            st.setDate(3, data);
            st.setInt(4, u.getProduto().getId());
            st.setString(5, u.getDescricao());
            st.setString(6, u.getSolucao());
            st.setInt(7, u.getSituacaoAtendimento().getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Pessoa: " + INSERT, e);
        }
    }

    @Override
    public void atualizar(Atendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Atendimento u) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(DELETE)) {

            st.setInt(1, u.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir atendimento: " + DELETE, e);
        }
    }

    public void solucionar(int id, String solucao) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(RESOLVE_ATENDIMENTO)) {

            st.setString(1, solucao);
            st.setInt(2, id);

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao resolver atendimento: " + RESOLVE_ATENDIMENTO, e);
        }
    }

    public int queryQuantidadeAtendimentosTotal() throws DAOException {
        try (PreparedStatement st = con.prepareStatement(QUERY_QNT_ANTEDIMENTOS_TOTAL)) {

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar quantidade total de atendimentos: " + QUERY_QNT_ANTEDIMENTOS_TOTAL, e);
        }
    }
    
    public int queryQuantidadeAtendimentosByTipoProdutoId(int id) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(QUERY_QNT_ANTEDIMENTOS_TOTAL_BY_TIPOPRODUTO_ID)) {

            st.setInt(1, id);
            
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar quantidade total de atendimentos: " + QUERY_QNT_ANTEDIMENTOS_TOTAL_BY_TIPOPRODUTO_ID, e);
        }
    }
    
    public int queryQuantidadeAtendimentosAbertos() throws DAOException {
        try (PreparedStatement st = con.prepareStatement(QUERY_QNT_ATENDIMENTOS_ABERTOS)) {

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar quantidade total de atendimentos em aberto: " + QUERY_QNT_ATENDIMENTOS_ABERTOS, e);
        }
    }
    
    public int queryQuantidadeAtendimentosAbertosByTipoProdutoId(int id) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(QUERY_QNT_ANTEDIMENTOS_ABERTOS_BY_TIPOPRODUTO_ID)) {
            
            st.setInt(1, id);
            
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao consultar quantidade total de atendimentos em aberto: " + QUERY_QNT_ANTEDIMENTOS_ABERTOS_BY_TIPOPRODUTO_ID, e);
        }
    }

}
