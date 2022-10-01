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
public class SeedersTableUsuario {
    
    private static Connection con = null;
    private static final String insertIntoTableUsuario = "INSERT INTO public.tb_usuario\n" +
                                                    "(id_usuario, login_usuario, senha_usuario, nome_usuario)\n" +
                                                    "VALUES(1, 'luis', 'luis', 'Luis');\n" +
                                                    "INSERT INTO public.tb_usuario\n" +
                                                    "(id_usuario, login_usuario, senha_usuario, nome_usuario)\n" +
                                                    "VALUES(2, 'miguel', 'miguel', 'Miguel');\n" +
                                                    "INSERT INTO public.tb_usuario\n" +
                                                    "(id_usuario, login_usuario, senha_usuario, nome_usuario)\n" +
                                                    "VALUES(3, 'ferreira', 'ferreira', 'Ferreira');";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableUsuario) != -1){
                System.out.println("Seeder Usuario executada com sucesso!");
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
