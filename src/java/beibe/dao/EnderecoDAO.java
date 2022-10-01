/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Cidade;
import beibe.beans.Endereco;
import beibe.exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author luis
 */
public class EnderecoDAO implements DAO<Endereco> {

    private final String INSERT = "INSERT INTO Endereco(idCidade, rua, numero, complemento, bairro, cep) VALUES (?, ?, ?, ?, ?, ?) returning id";
    private final String SELECT = "SELECT id, idCidade, rua, numero, complemento, bairro, cep FROM Endereco WHERE id = ?";
    private final String UPDATE = "UPDATE Endereco SET idCidade = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cep = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM Endereco WHERE id = ?";

    private Connection con = null;

    public EnderecoDAO(Connection con) throws DAOException {
        if (con == null) {
            throw new DAOException("Conexão nula ao criar EnderecoDAO.");
        }
        this.con = con;
    }

    @Override
    public Endereco buscar(int id) throws DAOException {
        Endereco endereco = null;
        Cidade cidade = null;

        try (PreparedStatement st = con.prepareStatement(SELECT)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    endereco = new Endereco();
                    cidade = new Cidade();

                    endereco.setId(rs.getInt(1));
                    endereco.setRua(rs.getString(3));
                    endereco.setNumero(rs.getInt(4));
                    endereco.setComplemento(rs.getString(5));
                    endereco.setBairro(rs.getString(6));
                    endereco.setCep(rs.getString(7));

                    cidade.setId(rs.getInt(2));
                    endereco.setCidade(cidade);
                }
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar usuario: " + SELECT, e);
        }

        return endereco;
    }

    @Override
    public List<Endereco> buscarTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int inserirReturningId(Endereco endereco) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT)) {
            st.setInt(1, endereco.getCidade().getId());
            st.setString(2, endereco.getRua());
            st.setInt(3, endereco.getNumero());
            st.setString(4, endereco.getComplemento());
            st.setString(5, endereco.getBairro());
            st.setString(6, endereco.getCep());

            try (ResultSet rs = st.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Endereco: " + INSERT, e);
        }
    }

    @Override
    public void inserir(Endereco endereco) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Endereco endereco) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE)) {

            st.setInt(1, endereco.getCidade().getId());
            st.setString(2, endereco.getRua());
            st.setInt(3, endereco.getNumero());
            st.setString(4, endereco.getComplemento());
            st.setString(5, endereco.getBairro());
            st.setString(6, endereco.getCep());
            st.setInt(7, endereco.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Erro ao Atualizar Endereco: " + UPDATE, e);
        }
    }

    @Override
    public void remover(Endereco endereco) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(DELETE)) {

            st.setInt(1, endereco.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir endereco: " + DELETE, e);
        }
    }

}
