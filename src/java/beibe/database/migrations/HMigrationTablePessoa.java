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
public class HMigrationTablePessoa {
    private static Connection con = null;
    private static final String createTablePessoa = "create table pessoa(\n" +
"	id SERIAL,\n" +
"	nomecompleto varchar(200),\n" +
"	cpf varchar(11),\n" +
"	email varchar(200),\n" +
"	telefone varchar(11),\n" +
"	senha varchar(512),\n" +
"	idEndereco int,\n" +
"	idTipoPessoa int,\n" +
"	constraint pk_cliente primary key(id),\n" +
"	constraint fk_endereco foreign key(idEndereco) references endereco(id),\n" +
"	constraint fk_tipopessoa foreign key(idTipoPessoa) references tipopessoa(id)\n" +
");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTablePessoa) != -1){
                System.out.println("Migration Pessoa criada com sucesso!");
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
