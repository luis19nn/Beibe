/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Endereco;
import beibe.beans.Pessoa;
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
public class PessoaDAO {

    private final String INSERT = "INSERT INTO Pessoa(nomecompleto, cpf, email, telefone, senha, idTipoPessoa, idEndereco) "
            + "VALUES(?, ?, ?, ?, md5(?), ?, ?)";
    private final String INSERT_RETURNING_ID = "INSERT INTO Pessoa(nomecompleto, cpf, email, telefone, senha, idTipoPessoa, idEndereco) "
            + "VALUES(?, ?, ?, ?, md5(?), ?, ?) returning id";
    private final String LOGIN = "SELECT p.id, tp.descricao FROM Pessoa p INNER JOIN TipoPessoa tp on (tp.id = p.idTipoPessoa) WHERE p.email = ? and p.senha = md5(?)";
    private final String SELECT = "SELECT id, nomecompleto, cpf, email, telefone, idEndereco, idTipoPessoa FROM Pessoa WHERE id = ?";
    private final String UPDATE = "UPDATE PESSOA SET nomecompleto = ?, cpf = ?, email = ?, telefone = ?, senha = md5(?) WHERE id = ?";
    private final String DELETE = "DELETE FROM pessoa WHERE id = ?";
    private final String BUSCAR_FUNCIONARIO_GERENTE = "SELECT p.id, p.nomecompleto, p.cpf, p.email, p.telefone, p.idEndereco, p.idTipoPessoa, tp.descricao "
            + "FROM Pessoa p "
            + "INNER JOIN TipoPessoa tp on (tp.id = p.idTipoPessoa)"
            + "WHERE p.idTipoPessoa IN (2, 3)";
    private final String QUERY_UPDATE_TIPO_PESSOA = "UPDATE PESSOA SET nomecompleto = ?, cpf = ?, email = ?, telefone = ?, senha = md5(?), idtipopessoa = ? WHERE id = ?";

    private Connection con = null;

    public PessoaDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar PessoaDAO.");
        }
        this.con = con;
    }

    public Pessoa buscar(int id) throws DAOException {
        Pessoa pessoa = null;
        Endereco endereco = null;
        TipoPessoa tipoPessoa = null;

        try (PreparedStatement st = con.prepareStatement(SELECT)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pessoa = new Pessoa();
                    endereco = new Endereco();
                    tipoPessoa = new TipoPessoa();

                    pessoa.setId(rs.getInt(1));
                    pessoa.setNomeCompleto(rs.getString(2));
                    pessoa.setCpf(rs.getString(3));
                    pessoa.setEmail(rs.getString(4));
                    pessoa.setTelefone(rs.getString(5));

                    endereco.setId(rs.getInt(6));
                    pessoa.setEndereco(endereco);

                    tipoPessoa.setId(rs.getInt(7));
                    pessoa.setTipoPessoa(tipoPessoa);

                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuario: " + SELECT, e);
        }

        return pessoa;
    }

    public List<Pessoa> buscarTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Pessoa> buscarListaFuncionariosGerentes() throws DAOException {
        Pessoa pessoa = null;
        Endereco endereco = null;
        TipoPessoa tipoPessoa = null;
        List<Pessoa> listaPessoas = new ArrayList<>();

        try (PreparedStatement st = con.prepareStatement(BUSCAR_FUNCIONARIO_GERENTE)) {

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pessoa = new Pessoa();
                    endereco = new Endereco();
                    tipoPessoa = new TipoPessoa();

                    pessoa.setId(rs.getInt(1));
                    pessoa.setNomeCompleto(rs.getString(2));
                    pessoa.setCpf(rs.getString(3));
                    pessoa.setEmail(rs.getString(4));
                    pessoa.setTelefone(rs.getString(5));

                    endereco.setId(rs.getInt(6));
                    pessoa.setEndereco(endereco);

                    tipoPessoa.setId(rs.getInt(7));
                    tipoPessoa.setDescricao(rs.getString(8));
                    pessoa.setTipoPessoa(tipoPessoa);

                    listaPessoas.add(pessoa);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar funcionários e gerentes: " + BUSCAR_FUNCIONARIO_GERENTE, e);
        }

        return listaPessoas;
    }

    public void inserir(Pessoa pessoa) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT)) {

            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getCpf());
            st.setString(3, pessoa.getEmail());
            st.setString(4, pessoa.getTelefone());
            st.setString(5, pessoa.getSenha());
            st.setInt(6, pessoa.getTipoPessoa().getId());
            st.setInt(7, pessoa.getEndereco().getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Pessoa: " + INSERT, e);
        }
    }

    public void atualizar(Pessoa pessoa) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE)) {

            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getCpf());
            st.setString(3, pessoa.getEmail());
            st.setString(4, pessoa.getTelefone());
            st.setString(5, pessoa.getSenha());

            st.setInt(6, pessoa.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao Atualizar Pessoa: " + UPDATE, e);
        }
    }

    public void atualizarTipoPessoa(Pessoa pessoa) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(QUERY_UPDATE_TIPO_PESSOA)) {

            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getCpf());
            st.setString(3, pessoa.getEmail());
            st.setString(4, pessoa.getTelefone());
            st.setString(5, pessoa.getSenha());
            st.setInt(6, pessoa.getTipoPessoa().getId());

            st.setInt(7, pessoa.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao Atualizar Pessoa: " + UPDATE, e);
        }
    }

    public void remover(Pessoa pessoa) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(DELETE)) {

            st.setInt(1, pessoa.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir pessoa: " + DELETE, e);
        }
    }

    public Pessoa login(String email, String senha) throws DAOException {
        Pessoa pessoa = null;
        TipoPessoa tipo = null;

        try (PreparedStatement st = con.prepareStatement(LOGIN)) {

            st.setString(1, email);
            st.setString(2, senha);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pessoa = new Pessoa();
                    tipo = new TipoPessoa();
                    pessoa.setId(rs.getInt(1));
                    tipo.setDescricao(rs.getString(2));
                    pessoa.setTipoPessoa(tipo);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar informações do usuario: " + LOGIN, e);
        }

        return pessoa;
    }

    public int inserirReturningId(Pessoa pessoa) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT_RETURNING_ID)) {
            st.setString(1, pessoa.getNomeCompleto());
            st.setString(2, pessoa.getCpf());
            st.setString(3, pessoa.getEmail());
            st.setString(4, pessoa.getTelefone());
            st.setString(5, pessoa.getSenha());
            st.setInt(6, pessoa.getTipoPessoa().getId());
            st.setInt(7, pessoa.getEndereco().getId());

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Endereco: " + INSERT_RETURNING_ID, e);
        }
    }
}
