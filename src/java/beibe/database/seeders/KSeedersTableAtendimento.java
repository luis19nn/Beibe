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
public class KSeedersTableAtendimento {
    private static Connection con = null;
    private static final String insertIntoTableAtendimento = "insert into atendimento (idTipoAtendimento, idCliente, data, idProduto, \n" +
"						descricao, solucao, idSituacaoAtendimento)\n" +
"	values (1, 1, (select current_date), 1, 'A embalagem poderia ser verde', null, 1),\n" +
"			(2, 1, (select current_date-2), 2, 'Gostaria de trocar por um esmalte', 'Solicitação atendida', 2),\n" +
"			(3, 1, (select current_date-5), 3, 'A cor do esmalte é feia', null, 1)";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableAtendimento) != -1){
                System.out.println("Seeder Atendimento executada com sucesso!");
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
