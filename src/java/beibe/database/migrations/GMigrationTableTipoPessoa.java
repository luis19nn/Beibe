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
public class GMigrationTableTipoPessoa {
    private static Connection con = null;
    private static final String createTableTipoPessoa = "create table tipopessoa(\n" +
"	id SERIAL,--define se é funcionario ou cliente, assim sabemos o perfil de acesso\n" +
"	descricao varchar(200),\n" +
"	constraint fk_tipopessoa primary key(id)\n" +
");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableTipoPessoa) != -1){
                System.out.println("Migration TipoPessoa criada com sucesso!");
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
