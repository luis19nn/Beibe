/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

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
public class TipoProdutoDAO {
    
    private final String INSERT = "INSERT INTO TipoProduto (descricao) VALUES(?)";
    private final String QUERYALL = "SELECT id, descricao FROM TipoProduto";
    private final String SELECT = "SELECT id, descricao FROM TipoProduto WHERE id = ?";
    private final String DELETE = "DELETE FROM TipoProduto WHERE id = ?";
    private final String UPDATE = "UPDATE TipoProduto set descricao = ? WHERE id = ?";
    
    private Connection con = null;
    
    public TipoProdutoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar TipoProdutoDAO.");
        }
        this.con = con;
    }
    
    public TipoProduto buscar(int id) throws DAOException {
        TipoProduto tipoProduto;
        
        try (PreparedStatement st = con.prepareStatement(SELECT)) {
          
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                rs.next();

                tipoProduto = new TipoProduto();

                tipoProduto.setId(rs.getInt(1));
                tipoProduto.setDescricao(rs.getString(2));   
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar Categoria: " + SELECT, e);
        }
        
        return tipoProduto;
    }

    public List<TipoProduto> buscarTodos() throws DAOException {
        List<TipoProduto> tipos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(QUERYALL)) {
          
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    
                    TipoProduto tipoProduto = new TipoProduto();
                    
                    tipoProduto.setId(rs.getInt(1));
                    tipoProduto.setDescricao(rs.getString(2));   
                    
                    tipos.add(tipoProduto);
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar Categorias: " + QUERYALL, e);
        }
        
        return tipos;
    }

    public void inserir(TipoProduto tipoProduto) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT)) {
            
            st.setString(1, tipoProduto.getDescricao());
           
            st.executeUpdate();
              
        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Tipo Produto: " + INSERT, e);
        }
    }

    public void atualizar(TipoProduto tipoProduto) throws DAOException {
         try (PreparedStatement st = con.prepareStatement(UPDATE)) {
            
            st.setString(1, tipoProduto.getDescricao());
            st.setInt(2, tipoProduto.getId());
           
            st.executeUpdate();
              
        } catch (SQLException e) {
            throw new DAOException("Erro ao Atualizar Tipo Produto: " + UPDATE, e);
        }
    }

    public void remover(int id) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(DELETE)) {
            st.setInt(1, id);
            
            st.executeUpdate();
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao deletar Categoria: " + DELETE, e);
        }
    }
    
}
