/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.trash;

import beibe.dao.ConnectionFactory;
import beibe.exceptions.DAOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class MigrationTableCliente {
    
    private static Connection con = null;
    private static final String createTableCliente = "CREATE TABLE public.cliente (\n" +
                                                    "	id_cliente serial4 NOT NULL,\n" +
                                                    "	cpf_cliente bpchar(11) NULL,\n" +
                                                    "	nome_cliente varchar(100) NULL,\n" +
                                                    "	email_cliente varchar(100) NULL,\n" +
                                                    "	data_cliente date NULL,\n" +
                                                    "	rua_cliente varchar(100) NULL,\n" +
                                                    "	nr_cliente int4 NULL,\n" +
                                                    "	cep_cliente bpchar(8) NULL,\n" +
                                                    "	cidade_cliente varchar(100) NULL,\n" +
                                                    "	uf_cliente bpchar(2) NULL\n" +
                                                    ");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableCliente) != -1){
                System.out.println("Migration Cliente criada com sucesso!");
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
