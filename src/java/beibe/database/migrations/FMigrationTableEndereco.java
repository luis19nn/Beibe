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
public class FMigrationTableEndereco {
    private static Connection con = null;
    private static final String createTableEndereco = "create table endereco(\n" +
"	id SERIAL,\n" +
"	idCidade int,\n" +
"       rua varchar(100),\n" +
"	numero int,\n" +
"	complemento varchar(20),\n" +
"	bairro varchar(20),\n" +
"	cep varchar(10),\n" +
"	constraint pk_endereco primary key(id),\n" +
"	constraint fk_cidadeEndereco foreign key(idCidade) references cidade(id)\n" +
")";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableEndereco) != -1){
                System.out.println("Migration Endereço criada com sucesso!");
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
