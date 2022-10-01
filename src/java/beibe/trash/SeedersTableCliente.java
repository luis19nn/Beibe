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
public class SeedersTableCliente {
    private static Connection con = null;
    private static final String insertIntoTableCliente = "INSERT INTO public.tb_cliente\n" +
"(id_cliente, cpf_cliente, nome_cliente, email_cliente, data_cliente, rua_cliente, nr_cliente, cep_cliente, cidade_cliente, uf_cliente)\n" +
"values\n" +
"(1, 11111111111, 'Cliente Numero Um', 'clienteum@mail.com', '2022-01-01', 'Rua do cliente Um', 1, '11111111', 'Cidade Um', 'UM'),\n" +
"(2, 22222222222, 'Afonso Afonsinho', 'afonso@mail.com', '2022-01-02', 'Rua do Afonso', 2, '22222222', 'Cidade do Afonso', 'RS'),\n" +
"(3, 33333333333, 'Bruno Bruninho', 'bruno@mail.com', '2022-01-03', 'Rua do Bruno', 3, '33333333', 'Cidade do Bruno', 'SC'),\n" +
"(4, 44444444444, 'Claudio Claudinho', 'claudio@mail.com', '2022-01-04', 'Rua do Claudio', 4, '44444444', 'Cidade do Claudio', 'PR'),\n" +
"(5, 55555555555, 'Dalton Daltinho', 'dalton@mail.com', '2022-01-05', 'Rua do Dalton', 5, '55555555', 'Cidade do Dalton', 'SP'),\n" +
"(6, 66666666666, 'Elton Eltinho', 'elton@mail.com', '2022-01-06', 'Rua do Elton', 6, '66666666', 'Cidade do Elton', 'RJ'),\n" +
"(7, 77777777777, 'Felipe Felipinho', 'felipe@mail.com', '2022-01-07', 'Rua do Felipe', 7, '77777777', 'Cidade do Felipe', 'MG'),\n" +
"(8, 88888888888, 'Geraldo Geraldinho', 'geraldo@mail.com', '2022-01-08', 'Rua do Geraldo', 8, '88888888', 'Cidade do Geraldo', 'GO'),\n" +
"(9, 99999999999, 'Hercules Herculinho', 'hercules@mail.com', '2022-01-09', 'Rua do Hercules', 9, '99999999', 'Cidade do Hercules', 'MS'),\n" +
"(10, 10110110110, 'Irculano Irculaninho', 'irculano@mail.com', '2022-01-10', 'Rua do Irculano', 10, '10101101', 'Cidade do Irculano', 'MT'),\n" +
"(11, 12312312312, 'Joao Joaozinho', 'joao@mail.com', '2022-01-11', 'Rua do Joao', 11, '12123123', 'Cidade do Joao', 'ES');";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(insertIntoTableCliente) != -1){
                System.out.println("Seeder Cliente executada com sucesso!");
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
