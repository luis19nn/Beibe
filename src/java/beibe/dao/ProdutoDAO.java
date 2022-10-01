/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.dao;

import beibe.beans.Produto;
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
public class ProdutoDAO {
    
    private final String INSERT = "INSERT INTO Produto(idTipoProduto, nome, descricao, peso) VALUES(?, ?, ?, ?)";
    private final String SELECTALL = "SELECT p.id, p.idTipoProduto, t.descricao, p.nome, p.descricao, p.peso FROM Produto p INNER JOIN TipoProduto t on(t.id = p.idTipoProduto)";
    private final String DELETE = "DELETE FROM Produto WHERE id = ?";
    private final String SELECT = "SELECT id, idTipoProduto, nome, descricao, peso FROM Produto WHERE id = ?";
    private final String UPDATE = "UPDATE Produto SET idTipoProduto = ?, nome = ?, descricao = ?, peso =? WHERE id = ?";
    
    private Connection con = null;
    
    public ProdutoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar ProdutoDAO.");
        }
        this.con = con;
    }
    
   
    public Produto buscar(int id) throws DAOException {
        Produto produto = null;
        try (PreparedStatement st = con.prepareStatement(SELECT)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    TipoProduto tipo = new TipoProduto();
                    produto = new Produto();
                    
                    produto.setId(rs.getInt(1));
                    
                    tipo.setId(rs.getInt(2));
                    
                    produto.setTipoProduto(tipo);
                    produto.setNome(rs.getString(3));
                    produto.setDescricao(rs.getString(4));
                    produto.setPeso(rs.getInt(5));
                    
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produtos: " + SELECTALL, e);
        }
        
        return produto;
    }

    
    public List<Produto> buscarTodos() throws DAOException {
       List<Produto> produtos = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(SELECTALL)) {
          
            try (ResultSet rs = st.executeQuery()) {
                while(rs.next()){
                    TipoProduto tipo = new TipoProduto();
                    Produto produto = new Produto();
                    
                    produto.setId(rs.getInt(1));
                    
                    tipo.setId(rs.getInt(2));
                    tipo.setDescricao(rs.getString(3));
                    
                    produto.setTipoProduto(tipo);
                    produto.setNome(rs.getString(4));
                    produto.setDescricao(rs.getString(5));
                    produto.setPeso(rs.getInt(6));
                    
                    produtos.add(produto);
                }
            }
             
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar produtos: " + SELECTALL, e);
        }
        
        return produtos;
    }

    
    public void inserir(Produto produto) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(INSERT)) {
            
            st.setInt(1, produto.getTipoProduto().getId());
            st.setString(2, produto.getNome());
            st.setString(3, produto.getDescricao());
            st.setInt(4, produto.getPeso());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao Inserir Produto: " + INSERT, e);
        }
    }

    
    public void atualizar(Produto produto) throws DAOException {
        try (PreparedStatement st = con.prepareStatement(UPDATE)) {
            
            st.setInt(1, produto.getTipoProduto().getId());
            st.setString(2, produto.getNome());
            st.setString(3, produto.getDescricao());
            st.setInt(4, produto.getPeso());
            st.setInt(5, produto.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao Atualizar Produto: " + UPDATE, e);
        }
    }

    
    public void remover(int id) throws DAOException {
       try (PreparedStatement st = con.prepareStatement(DELETE)) {
            
            st.setInt(1, id);
           
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao excluir produto: " + DELETE, e);
        }
    }
    
}
