/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.database.migrations;

import beibe.dao.ConnectionFactory;
import beibe.exceptions.DAOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class CMigrationTableProduto {
    private static Connection con = null;
    private static final String createTableProduto = "create table produto(\n" +
"	id SERIAL,\n" +
"	idTipoProduto int,\n" +
"	nome varchar(200),\n" +
"	descricao varchar(500),\n" +
"	peso int,\n" +
"	constraint pk_produto primary key(id),\n" +
"	constraint fk_tipoproduto foreign key (idTipoProduto) references tipoproduto(id)\n" +
");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableProduto) != -1){
                System.out.println("Migration Produto criada com sucesso!");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(DAOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            con.close();
            System.out.println("Desconectado!");
        }
    }
}
