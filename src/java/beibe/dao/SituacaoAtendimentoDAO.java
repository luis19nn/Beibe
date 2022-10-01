/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.SituacaoAtendimento;
import beibe.exceptions.DAOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author luis
 */
public class SituacaoAtendimentoDAO implements DAO<SituacaoAtendimento>{
    
    private Connection con = null;
    
    public SituacaoAtendimentoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar SituacaoAtendimentoDAO.");
        }
        this.con = con;
    }
    
    @Override
    public SituacaoAtendimento buscar(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SituacaoAtendimento> buscarTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inserir(SituacaoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(SituacaoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(SituacaoAtendimento u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
