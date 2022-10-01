/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.facade;

import beibe.beans.Atendimento;
import beibe.dao.AtendimentoDAO;
import beibe.dao.ConnectionFactory;
import beibe.exceptions.DAOException;
import beibe.exceptions.FacadeException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author luis
 */
public class AtendimentoFacade {
    
    private static final String MSG_ERRO_GET_ATENDIMENTOS_ABERTOS = "Erro ao buscar atendimentos em aberto.";
    private static final String MSG_ERRO_GET_ATENDIMENTOS_TODOS = "Erro ao buscar atendimentos.";
    private static final String MSG_ERRO_GET_QUANTIDADE_ATENDIMENTOS_TODOS = "Erro ao buscar quantidade total de atendimentos.";
    private static final String MSG_ERRO_GET_QUANTIDADE_ATENDIMENTOS_ABERTOS = "Erro ao buscar quantidade total de atendimentos em aberto.";

    public static List<Atendimento> getAtendimentosAbertos() throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            List<Atendimento> listaAtendimentosAbertos = dao.buscarAtendimentosEmAberto();
            return listaAtendimentosAbertos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_GET_ATENDIMENTOS_ABERTOS, ex);
        }
    }
    
    public static List<Atendimento> getAtendimentosTodos() throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            List<Atendimento> listaAtendimentos = dao.buscarTodos();
            return listaAtendimentos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_GET_ATENDIMENTOS_TODOS, ex);
        }
    }
    
    public static int getQuantidadeAtendimentosTodos() throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            int qntAtendimentos = dao.queryQuantidadeAtendimentosTotal();
            return qntAtendimentos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_GET_QUANTIDADE_ATENDIMENTOS_TODOS, ex);
        }
    }
    
    public static int getQuantidadeAtendimentosAbertos() throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            int qntAtendimentosAbertos = dao.queryQuantidadeAtendimentosAbertos();
            return qntAtendimentosAbertos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_GET_QUANTIDADE_ATENDIMENTOS_ABERTOS, ex);
        }
    }

}
