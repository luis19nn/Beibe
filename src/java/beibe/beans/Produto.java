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
public class Produto implements Serializable {
    
    private int id;
    private TipoProduto tipoProduto;
    private String nome;
    private String descricao;
    private int peso;
    
    public Produto() { }
    
    public Produto(int id, TipoProduto tipoProduto, String nome, String descricao, int peso) {
        this.id = id;
        this.tipoProduto = tipoProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
    }
    
    public Produto(TipoProduto tipoProduto, String nome, String descricao, int peso) {
        this.id = id;
        this.tipoProduto = tipoProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.peso = peso;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }
}
