/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.facade;

import beibe.beans.Cidade;
import beibe.beans.Estado;
import beibe.beans.TipoPessoa;
import beibe.dao.CidadeDAO;
import beibe.dao.ConnectionFactory;
import beibe.dao.EstadoDAO;
import beibe.dao.TipoPessoaDAO;
import beibe.exceptions.DAOException;
import beibe.exceptions.FacadeException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author luis
 */
public class GeralFacade {
    
    private static final String MSG_ERRO_BUSCAR_LISTA_CIDADES = "Erro ao buscar lista de cidades.";
    private static final String MSG_ERRO_BUSCAR_LISTA_ESTADOS = "Erro ao buscar lista de estados.";
    private static final String MSG_ERRO_BUSCAR_LISTA_CARGOS = "Erro ao buscar lista de cargos.";

    public static List<Cidade> getListaCidades() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            CidadeDAO dao = new CidadeDAO(con.getConnection());
            List<Cidade> listaCidades = dao.buscarTodos();
            return listaCidades;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_LISTA_CIDADES, ex);
        }
    }
    
    public static List<Estado> getListaEstados() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EstadoDAO dao = new EstadoDAO(con.getConnection());
            List<Estado> listaEstados = dao.buscarTodos();
            return listaEstados;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_LISTA_ESTADOS, ex);
        }
    }
    
    public static List<TipoPessoa> getListaCargos() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            TipoPessoaDAO dao = new TipoPessoaDAO(con.getConnection());
            List<TipoPessoa> listaCargos = dao.buscarTipoPessoaColaboradores();
            return listaCargos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_LISTA_CARGOS, ex);
        }
    }
}
