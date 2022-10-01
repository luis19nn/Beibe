/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.facade;

import beibe.beans.RelatorioAtendimento;
import beibe.beans.TipoProduto;
import beibe.dao.AtendimentoDAO;
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
public class RelatorioFacade {

    private static final String MSG_ERRO_BUSCAR_LISTA_CATEGORIAS = "Erro ao buscar lista de categorias de produtos.";
    private static final String MSG_ERRO_BUSCAR_RELATORIO_ATENDIMENTOS = "Erro ao buscar informações de relatório de atendimentos.";
    
    public static List<RelatorioAtendimento> getRelatorioAtendimentosByCategoria(List<TipoProduto> listaCategorias) throws FacadeException, IOException {
        List<RelatorioAtendimento> relatorio = new ArrayList<>();
        int qntAtendimentosTotal = 0;
        int qntAtendimentosAbertos = 0;
        try (ConnectionFactory con = new ConnectionFactory()) {
            for (TipoProduto tipoProduto : listaCategorias){
                RelatorioAtendimento categoria = new RelatorioAtendimento();
                categoria.setIdCategoria(tipoProduto.getId());
                categoria.setDescricaoCategoria(tipoProduto.getDescricao());
                AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
                qntAtendimentosTotal = dao.queryQuantidadeAtendimentosByTipoProdutoId(categoria.getIdCategoria());
                categoria.setTotalAtendimentos(qntAtendimentosTotal);
                qntAtendimentosAbertos = dao.queryQuantidadeAtendimentosAbertosByTipoProdutoId(categoria.getIdCategoria());
                categoria.setAtendimentosAbertos(qntAtendimentosAbertos);
                relatorio.add(categoria);
            }
            return relatorio;
        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_RELATORIO_ATENDIMENTOS, ex);
        }
    }
    
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
