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
public class HSeedersTablePessoa {
    private static Connection con = null;
    private static final String insertIntoTablePessoa = "insert into pessoa (nomecompleto, cpf, email, telefone, senha, idEndereco, idTipoPessoa)\n" +
"	values ('Raymond Reddington', '87182791212', 'rrgton@gmail.com', '41999990000', 'r4ymond', 1, 1),\n" +
"			('Neal Caffrey', '91234871624', 'neal@outlook.com', '41992223478', 'n3al', 2, 2),\n" +
"			('Patrick Jane', '67219237190', 'j4ne@mail.com', '41992132641', 'j4ne', 3, 3)";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTablePessoa) != -1){
                System.out.println("Seeder Pessoa executada com sucesso!");
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
