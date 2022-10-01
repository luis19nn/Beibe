/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package beibe.dao;

import beibe.exceptions.DAOException;
import java.util.List;

/**
 *
 * @author luis
 */
public interface DAO<T> {
    T buscar(int id) throws DAOException;
    List<T> buscarTodos() throws DAOException;
    void inserir(T u) throws DAOException;
    void atualizar(T u) throws DAOException;
    void remover(T u) throws DAOException;
}
