/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.TipoAtendimento;
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
public class TipoAtendimentoDAO implements DAO<TipoAtendimento>{
    
    private Connection con = null;
    
    public TipoAtendimentoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar TipoAtendimentoDAO.");
        }
        this.con = con;
    }
    
    @Override
    public TipoAtendimento buscar(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TipoAtendimento> buscarTodos() throws DAOException {
        List<TipoAtendimento> tipos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement("SELECT id, descricao from tipoatendimento")) {
          
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    TipoAtendimento tipo = new TipoAtendimento();
                    
                    tipo.setId(rs.getInt(1));
                    tipo.setDescricao(rs.getString(2));
                    
                    tipos.add(tipo);
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produtos: " + e);
        }
        
        return tipos;
    }

    @Override
    public void inserir(TipoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(TipoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(TipoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
