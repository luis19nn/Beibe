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
public class ClienteFacade {
    private static final String MSG_ERRO_BUSCAR_ATENDIMENTOS = "Erro ao buscar atendimentos.";
    private static final String MSG_ERRO_BUSCAR_ATENDIMENTO = "Erro ao buscar atendimento.";
    
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
}
