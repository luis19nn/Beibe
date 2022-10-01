/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Gerencia;
import beibe.exceptions.DAOException;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author luis
 */
public class GerenciaDAO implements DAO<Gerencia> {
    
    private Connection con = null;
    
    public GerenciaDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar GerenciaDAO.");
        }
        this.con = con;
    }
    
    @Override
    public Gerencia buscar(int id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Gerencia> buscarTodos() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inserir(Gerencia u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Gerencia u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remover(Gerencia u) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
