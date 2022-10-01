/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.facade;

import beibe.beans.TipoProduto;
import beibe.dao.ConnectionFactory;
import beibe.dao.TipoProdutoDAO;
import beibe.exceptions.DAOException;
import beibe.exceptions.FacadeException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ProdutoFacade {
    
    private static final String MSG_ERRO_BUSCAR_LISTA_CATEGORIAS = "Erro ao buscar lista de categorias de produtos.";
    
    public static List<TipoProduto> getListaCategoriaProdutos() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {
            List<TipoProduto> listaCategorias = new ArrayList<>();
            TipoProdutoDAO dao = new TipoProdutoDAO(con.getConnection());
            listaCategorias = dao.buscarTodos();
            return listaCategorias;
        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_LISTA_CATEGORIAS, ex);
        }
    }
    
}
