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
public class IMigrationTableGerencia {
    private static Connection con = null;
    private static final String createTableGerencia = "create table gerencia(\n" +
"	id SERIAL,\n" +
"	idGerente int,\n" +
"	idFuncionario int,\n" +
"	constraint pk_gerencia primary key(id),\n" +
"	constraint fk_gerente foreign key(idGerente) references pessoa(id),\n" +
"	constraint fk_funcionario foreign key(idFuncionario) references pessoa(id)	\n" +
");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableGerencia) != -1){
                System.out.println("Migration Gerencia criada com sucesso!");
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
