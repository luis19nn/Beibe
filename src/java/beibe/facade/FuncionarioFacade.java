/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author gsebold
 */
public class FuncionarioFacade {
    private static final String MSG_ERRO_BUSCAR_ATENDIMENTOS = "Erro ao buscar atendimentos.";
    private static final String MSG_ERRO_BUSCAR_ATENDIMENTO = "Erro ao buscar atendimento.";
    private static final String MSG_ERRO_SOLUCIONAR_ATENDIMENTO = "Erro ao solucionar atendimento.";

    public static List<Atendimento> getAtendimentosAbertos() throws FacadeException, IOException {

        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            List<Atendimento> listaAtendimentosAbertos = dao.buscarAtendimentosEmAberto();
            return listaAtendimentosAbertos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ATENDIMENTOS, ex);
        }
    }
    
    public static List<Atendimento> getTodosAtendimentos() throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            List<Atendimento> listaAtendimentos = dao.buscarTodos();
            return listaAtendimentos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ATENDIMENTOS, ex);
        }
    }
    
    public static Atendimento getAtendimento(int id) throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            Atendimento atendimento = dao.buscar(id);
            return atendimento;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ATENDIMENTO, ex);
        }
    }

    public static void setSolucao(int id, String solucao) throws IOException, FacadeException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            AtendimentoDAO dao = new AtendimentoDAO(con.getConnection());
            dao.solucionar(id, solucao);

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_SOLUCIONAR_ATENDIMENTO, ex);
        }
    }
}