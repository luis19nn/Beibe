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
public class DSeedersTableEstado {
    private static Connection con = null;
    private static final String insertIntoTableEstado = "INSERT INTO estado (id, nome, sigla) VALUES\n" +
"(1, 'Acre', 'AC'),\n" +
"(2, 'Alagoas', 'AL'),\n" +
"(3, 'Amazonas', 'AM'),\n" +
"(4, 'Amapá', 'AP'),\n" +
"(5, 'Bahia', 'BA'),\n" +
"(6, 'Ceará', 'CE'),\n" +
"(7, 'Distrito Federal', 'DF'),\n" +
"(8, 'Espírito Santo', 'ES'),\n" +
"(9, 'Goiás', 'GO'),\n" +
"(10, 'Maranhão', 'MA'),\n" +
"(11, 'Minas Gerais', 'MG'),\n" +
"(12, 'Mato Grosso do Sul', 'MS'),\n" +
"(13, 'Mato Grosso', 'MT'),\n" +
"(14, 'Pará', 'PA'),\n" +
"(15, 'Paraíba', 'PB'),\n" +
"(16, 'Pernambuco', 'PE'),\n" +
"(17, 'Piauí', 'PI'),\n" +
"(18, 'Paraná', 'PR'),\n" +
"(19, 'Rio de Janeiro', 'RJ'),\n" +
"(20, 'Rio Grande do Norte', 'RN'),\n" +
"(21, 'Rondônia', 'RO'),\n" +
"(22, 'Roraima', 'RR'),\n" +
"(23, 'Rio Grande do Sul', 'RS'),\n" +
"(24, 'Santa Catarina', 'SC'),\n" +
"(25, 'Sergipe', 'SE'),\n" +
"(26, 'São Paulo', 'SP'),\n" +
"(27, 'Tocantins', 'TO')";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableEstado) != -1){
                System.out.println("Seeder Estado executada com sucesso!");
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
