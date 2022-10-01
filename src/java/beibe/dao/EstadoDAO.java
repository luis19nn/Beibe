/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

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
public class EstadoDAO implements DAO<Estado>{
    
    private final String QUERY_TODOS = "select id, nome, sigla from estado";
    private final String QUERY_ESTADO_ID = "SELECT id, nome, sigla FROM estado WHERE id = ?";
    private Connection con = null;
    
    public EstadoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar EstadoDAO.");
        }
        this.con = con;
    }
    
    @Override
    public Estado buscar(int id) throws DAOException {
        Estado estado = null;
        try (PreparedStatement st = con.prepareStatement(QUERY_ESTADO_ID)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    estado = new Estado();
                    estado.setId(rs.getInt(1));
                    estado.setNome(rs.getString(2));
                    estado.setSigla(rs.getString(3)); 
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar estados: " + QUERY_TODOS, e);
        }
        return estado;        
    }

    @Override
    public List<Estado> buscarTodos() throws DAOException {
        List<Estado> estados = new ArrayList<Estado>();
        try (PreparedStatement st = con.prepareStatement(QUERY_TODOS)) {
          
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    
                    Estado estado = new Estado();
                    
                    estado.setId(rs.getInt(1));
                    estado.setNome(rs.getString(2));
                    estado.setSigla(rs.getString(3));
                    
                    estados.add(estado);
                    
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar estados: " + QUERY_TODOS, e);
        }
        
        return estados;
    }

    @Override
    public void inserir(Estado u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Estado u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Estado u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
