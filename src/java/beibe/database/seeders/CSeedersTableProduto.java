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
public class CSeedersTableProduto {
    private static Connection con = null;
    private static final String insertIntoTableProduto = "insert into produto (idTipoProduto, nome, descricao, peso) \n" +
"	values (1, 'Shampoo', 'O melhor shampoo para calvos', 350), \n" +
"			(2, 'Sabonete', 'O melhor sabonete para acnes', 125), \n" +
"			(3, 'Esmalte', 'O melhor esmalte para unhas encravadas', 90)";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableProduto) != -1){
                System.out.println("Seeder Produto executada com sucesso!");
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
