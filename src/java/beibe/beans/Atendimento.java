/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.beans;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author luis
 */
public class Atendimento implements Serializable {
    private int id;
    private TipoAtendimento tipoAtendimento;
    private Pessoa cliente;
    private Date data;
    private Produto produto;
    private String descricao;
    private String solucao;
    private SituacaoAtendimento situacaoAtendimento;
    
    public Atendimento() { }

    public Atendimento(int id, TipoAtendimento tipoAtendimento, Pessoa cliente, Date data, Produto produto, String descricao, String solucao, SituacaoAtendimento situacaoAtendimento) {
        this.id = id;
        this.tipoAtendimento = tipoAtendimento;
        this.cliente = cliente;
        this.data = data;
        this.produto = produto;
        this.descricao = descricao;
        this.solucao = solucao;
        this.situacaoAtendimento = situacaoAtendimento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoAtendimento getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(TipoAtendimento tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public Pessoa getCliente() {
        return cliente;
    }

    public void setCliente(Pessoa cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public SituacaoAtendimento getSituacaoAtendimento() {
        return situacaoAtendimento;
    }

    public void setSituacaoAtendimento(SituacaoAtendimento situacaoAtendimento) {
        this.situacaoAtendimento = situacaoAtendimento;
    }
}
