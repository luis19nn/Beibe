/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.facade;

import beibe.beans.Cidade;
import beibe.beans.Endereco;
import beibe.beans.Estado;
import beibe.beans.Pessoa;
import beibe.beans.TipoPessoa;
import beibe.dao.CidadeDAO;
import beibe.dao.ConnectionFactory;
import beibe.dao.EnderecoDAO;
import beibe.dao.EstadoDAO;
import beibe.dao.PessoaDAO;
import beibe.dao.TipoPessoaDAO;
import beibe.exceptions.DAOException;
import beibe.exceptions.FacadeException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author luis
 */
public class GerenteFacade {

    private static final String MSG_ERRO_LISTAR_FUNCIONARIOS = "Erro ao buscar lista de funcionários.";
    private static final String MSG_ERRO_BUSCAR_FUNCIONARIO_ID = "Erro ao buscar funcionário.";
    private static final String MSG_ERRO_BUSCAR_TIPOPESSOA_ID = "Erro ao buscar tipo pessoa.";
    private static final String MSG_ERRO_BUSCAR_ENDERECO_ID = "Erro ao buscar endereco.";
    private static final String MSG_ERRO_BUSCAR_CIDADE_ID = "Erro ao buscar cidade.";
    private static final String MSG_ERRO_BUSCAR_CIDADE_LISTA = "Erro ao buscar lista de cidades.";
    private static final String MSG_ERRO_BUSCAR_ESTADO_ID = "Erro ao buscar estado.";
    private static final String MSG_ERRO_BUSCAR_ESTADO_LISTA = "Erro ao buscar lista de estados.";
    private static final String MSG_ERRO_ATUALIZAR_ENDERECO = "Erro ao atualizar endereço.";
    private static final String MSG_ERRO_ATUALIZAR_FUNCIONARIO = "Erro ao atualizar informações do funcionário.";
    private static final String MSG_ERRO_REMOVER_FUNCIONARIO = "Erro ao remover o funcionário.";
    private static final String MSG_ERRO_CADASTRAR_ENDERECO = "Erro ao cadastrar endereço.";
    private static final String MSG_ERRO_CADASTRAR_FUNCINARIO = "Erro ao cadastrar funcionário.";

    public static Pessoa getFuncionarioTodasInformacoesById(int id) throws FacadeException, IOException {
        Pessoa funcionario = getFuncionarioById(id);
        funcionario.setTipoPessoa(getTipoPessoaById(funcionario.getTipoPessoa().getId()));
        funcionario.setEndereco(getEnderecoById(funcionario.getEndereco().getId()));
        funcionario.getEndereco().setCidade(getCidadeById(funcionario.getEndereco().getCidade().getId()));
        funcionario.getEndereco().getCidade().setEstado(getEstadoById(funcionario.getEndereco().getCidade().getEstado().getId()));

        return funcionario;
    }

    public static void updateEndereco(Endereco endereco) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EnderecoDAO dao = new EnderecoDAO(con.getConnection());
            dao.atualizar(endereco);

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_ATUALIZAR_ENDERECO, ex);
        }
    }

    public static List<Pessoa> getListarFuncionarios() throws FacadeException, IOException {

        try (ConnectionFactory con = new ConnectionFactory()) {

            PessoaDAO dao = new PessoaDAO(con.getConnection());
            List<Pessoa> listaPessoas = dao.buscarListaFuncionariosGerentes();
            return listaPessoas;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_LISTAR_FUNCIONARIOS, ex);
        }
    }

    public static Pessoa getFuncionarioById(int id) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            PessoaDAO dao = new PessoaDAO(con.getConnection());
            Pessoa funcionario = dao.buscar(id);
            return funcionario;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_FUNCIONARIO_ID, ex);
        }
    }

    public static TipoPessoa getTipoPessoaById(int id) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            TipoPessoaDAO dao = new TipoPessoaDAO(con.getConnection());
            TipoPessoa tipoPessoa = dao.buscarTipoPessoaId(id);
            return tipoPessoa;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_TIPOPESSOA_ID, ex);
        }
    }

    public static Endereco getEnderecoById(int id) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EnderecoDAO dao = new EnderecoDAO(con.getConnection());
            Endereco endereco = dao.buscar(id);
            return endereco;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ENDERECO_ID, ex);
        }
    }

    public static Cidade getCidadeById(int id) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            CidadeDAO dao = new CidadeDAO(con.getConnection());
            Cidade cidade = dao.buscar(id);
            return cidade;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_CIDADE_ID, ex);
        }
    }

    public static List<Cidade> getListaCidades() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            CidadeDAO dao = new CidadeDAO(con.getConnection());
            List<Cidade> listaCidades = dao.buscarTodos();
            return listaCidades;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_CIDADE_LISTA, ex);
        }
    }

    public static Estado getEstadoById(int id) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EstadoDAO dao = new EstadoDAO(con.getConnection());
            Estado estado = dao.buscar(id);
            return estado;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ESTADO_ID, ex);
        }
    }

    public static List<Estado> getListaEstados() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EstadoDAO dao = new EstadoDAO(con.getConnection());
            List<Estado> listaEstados = dao.buscarTodos();
            return listaEstados;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ESTADO_LISTA, ex);
        }
    }

    public static List<TipoPessoa> getListaCargosColaboradores() throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            TipoPessoaDAO dao = new TipoPessoaDAO(con.getConnection());
            List<TipoPessoa> listaCargos = dao.buscarTipoPessoaColaboradores();
            return listaCargos;

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_BUSCAR_ESTADO_LISTA, ex);
        }
    }

    public static void updateFuncionario(Pessoa funcionario) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            PessoaDAO dao = new PessoaDAO(con.getConnection());
            dao.atualizarTipoPessoa(funcionario);

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_ATUALIZAR_FUNCIONARIO, ex);
        }
    }

    public static void deleteFuncionario(Pessoa funcionario) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            PessoaDAO dao = new PessoaDAO(con.getConnection());
            dao.remover(funcionario);
            EnderecoDAO endDao = new EnderecoDAO(con.getConnection());
            endDao.remover(funcionario.getEndereco());

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_REMOVER_FUNCIONARIO, ex);
        }
    }

    public static int cadastrarEndereco(Endereco endereco) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            EnderecoDAO dao = new EnderecoDAO(con.getConnection());
            return dao.inserirReturningId(endereco);

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_CADASTRAR_ENDERECO, ex);
        }
    }

    public static int cadastrarFuncionario(Pessoa funcionario) throws FacadeException, IOException {
        try (ConnectionFactory con = new ConnectionFactory()) {

            PessoaDAO dao = new PessoaDAO(con.getConnection());
            return dao.inserirReturningId(funcionario);

        } catch (DAOException ex) {
            throw new FacadeException(MSG_ERRO_CADASTRAR_FUNCINARIO, ex);
        }
    }
}
