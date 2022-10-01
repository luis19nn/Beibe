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
public class MigrationTableUsuario {
    
    private static Connection con = null;
    private static String createTableUsuario = "CREATE TABLE public.tb_usuario (\n" +
                                                "	id_usuario serial4 NOT NULL,\n" +
                                                "	login_usuario varchar(50) NULL,\n" +
                                                "	senha_usuario varchar(50) NULL,\n" +
                                                "	nome_usuario varchar(100) NULL,\n" +
                                                "	CONSTRAINT pkusuario PRIMARY KEY (id_usuario),\n" +
                                                "	CONSTRAINT ukusuariologin UNIQUE (login_usuario)\n" +
                                                ");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableUsuario) != -1){
                System.out.println("Migration Usuario criada com sucesso!");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(DAOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            if(con != null)
            con.close();
            System.out.println("Desconectado!");
        }
    }
}
