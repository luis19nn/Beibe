/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Cidade;
import beibe.beans.Estado;
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
public class CidadeDAO implements DAO<Cidade> {
    
    private final String QUERY_TODOS = "select id, nome, idestado from cidade";
    private final String SELECT = "select id, nome, idestado from cidade where id = ?";
    
    private Connection con = null;
    
    public CidadeDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar CidadeDAO.");
        }
        this.con = con;
    }
    
    @Override
    public Cidade buscar(int id) throws DAOException {
        Cidade cidade = null;
        try (PreparedStatement st = con.prepareStatement(SELECT)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    
                    cidade = new Cidade();
                    
                    cidade.setId(rs.getInt(1));
                    cidade.setNome(rs.getString(2));
                    Estado estado = new Estado();
                    estado.setId(rs.getInt(3));
                    cidade.setEstado(estado);
                    
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar cidade: " + SELECT, e);
        }
        
        return cidade;
    }

    @Override
    public List<Cidade> buscarTodos() throws DAOException {
        List<Cidade> cidades = new ArrayList<Cidade>();
        try (PreparedStatement st = con.prepareStatement(QUERY_TODOS)) {
          
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    
                    Cidade cidade = new Cidade();
                    
                    cidade.setId(rs.getInt(1));
                    cidade.setNome(rs.getString(2));
                    Estado estado = new Estado();
                    estado.setId(rs.getInt(3));
                    cidade.setEstado(estado);
                    
                    cidades.add(cidade);
                    
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar cidades: " + QUERY_TODOS, e);
        }
        
        return cidades;
    }

    @Override
    public void inserir(Cidade u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Cidade u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Cidade u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
