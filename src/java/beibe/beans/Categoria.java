/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.beans;

/**
 *
 * @author luis
 */
public class Categoria {
    private int id;
    private String descricao;
    
    public Categoria() { }

    public Categoria(String descricao) {
        this.descricao = descricao;
    }
    
    public Categoria(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
