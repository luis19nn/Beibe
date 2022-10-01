/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.beans;

import java.io.Serializable;

/**
 *
 * @author luis
 */
public class RelatorioAtendimento implements Serializable {
    private int idCategoria;
    private String descricaoCategoria;
    private int totalAtendimentos;
    private int atendimentosAbertos;
    
    public RelatorioAtendimento(){}

    public RelatorioAtendimento(int idCategoria, String descricaoCategoria, int totalAtendimentos, int atendimentosAbertos) {
        this.idCategoria = idCategoria;
        this.descricaoCategoria = descricaoCategoria;
        this.totalAtendimentos = totalAtendimentos;
        this.atendimentosAbertos = atendimentosAbertos;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescricaoCategoria() {
        return descricaoCategoria;
    }

    public void setDescricaoCategoria(String descricaoCategoria) {
        this.descricaoCategoria = descricaoCategoria;
    }

    public int getTotalAtendimentos() {
        return totalAtendimentos;
    }

    public void setTotalAtendimentos(int totalAtendimentos) {
        this.totalAtendimentos = totalAtendimentos;
    }

    public int getAtendimentosAbertos() {
        return atendimentosAbertos;
    }

    public void setAtendimentosAbertos(int atendimentosAbertos) {
        this.atendimentosAbertos = atendimentosAbertos;
    }
    
    
    
}
