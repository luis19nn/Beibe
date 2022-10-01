/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luis
 */
public class ConfigBean implements Serializable {
    private String emailAdministrador;
    private List<TipoPessoa> listaCargos = new ArrayList<>();
    private List<Cidade> listaCidades = new ArrayList<>();
    private List<Estado> listaEstados = new ArrayList<>();
    
    public ConfigBean(){
    }

    public String getEmailAdministrador() {
        return emailAdministrador;
    }

    public void setEmailAdministrador(String emailAdministrador) {
        this.emailAdministrador = emailAdministrador;
    }

    public List<TipoPessoa> getListaCargos() {
        return listaCargos;
    }

    public void setListaCargos(List<TipoPessoa> listaCargos) {
        this.listaCargos = listaCargos;
    }

    public List<Cidade> getListaCidades() {
        return listaCidades;
    }

    public void setListaCidades(List<Cidade> listaCidades) {
        this.listaCidades = listaCidades;
    }

    public List<Estado> getListaEstados() {
        return listaEstados;
    }

    public void setListaEstados(List<Estado> listaEstados) {
        this.listaEstados = listaEstados;
    }
    
}
