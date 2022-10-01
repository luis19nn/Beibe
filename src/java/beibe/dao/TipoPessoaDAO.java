/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.TipoPessoa;
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
public class TipoPessoaDAO {

    private final String QUERY_CLIENTE = "SELECT id, descricao FROM TipoPessoa where descricao = 'Cliente'";
    private final String QUERY_ID = "SELECT id, descricao FROM TipoPessoa WHERE id = ?";
    private final String QUERY_COLABORADORES = "SELECT id, descricao FROM TipoPessoa WHERE descricao IN ('Funcionário','Gerente')";

    private Connection con = null;

    public TipoPessoaDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar TipoProdutoDAO.");
        }
        this.con = con;
    }

    public TipoPessoa buscarCliente() throws DAOException {
        TipoPessoa tipoPessoaCliente = new TipoPessoa();
        try (PreparedStatement st = con.prepareStatement(QUERY_CLIENTE)) {
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                tipoPessoaCliente.setId(rs.getInt(1));
                tipoPessoaCliente.setDescricao(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao obter Tipo Pessoa: " + QUERY_CLIENTE, e);
        }

        return tipoPessoaCliente;
    }

    public List<TipoPessoa> buscarTipoPessoaColaboradores() throws DAOException {
        List<TipoPessoa> listaTipoPessoaColaborador = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(QUERY_COLABORADORES)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    TipoPessoa tipoPessoaColaborador = new TipoPessoa();
                    tipoPessoaColaborador.setId(rs.getInt(1));
                    tipoPessoaColaborador.setDescricao(rs.getString(2));
                    listaTipoPessoaColaborador.add(tipoPessoaColaborador);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao obter Tipo Pessoa: " + QUERY_COLABORADORES, e);
        }

        return listaTipoPessoaColaborador;
    }

    public TipoPessoa buscarTipoPessoaId(int id) throws DAOException {
        TipoPessoa tipoPessoa = new TipoPessoa();
        try (PreparedStatement st = con.prepareStatement(QUERY_ID)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                tipoPessoa.setId(rs.getInt(1));
                tipoPessoa.setDescricao(rs.getString(2));
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao obter Tipo Pessoa: " + QUERY_ID, e);
        }

        return tipoPessoa;
    }
}
