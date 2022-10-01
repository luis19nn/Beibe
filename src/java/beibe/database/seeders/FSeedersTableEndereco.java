/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beibe.database.seeders;

import beibe.dao.ConnectionFactory;
import beibe.exceptions.DAOException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author luis
 */
public class FSeedersTableEndereco {
    private static Connection con = null;
    private static final String insertIntoTableEndereco = "insert into endereco (idCidade, rua, numero, complemento, bairro, cep) \n" +
"	values (2878, 'Rua Mauá', 39, null, 'Alto da Glória', '80030-200'),\n" +
"			(2878, 'Avenida Cândido de Abreu', 526, null, 'Centro Cívico', '80530-000'),\n" +
"			(3135, 'Rua Veríssimo Marques', 320, null, 'Centro', '83005-410')";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableEndereco) != -1){
                System.out.println("Seeder Endereco executada com sucesso!");
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
