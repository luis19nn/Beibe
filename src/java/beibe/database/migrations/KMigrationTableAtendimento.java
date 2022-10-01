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
public class KMigrationTableAtendimento {
    private static Connection con = null;
    private static final String createTableAtendimento = "create table atendimento(\n" +
"	id SERIAL,\n" +
"	idTipoAtendimento int,\n" +
"	idCliente int,\n" +
"	data date,\n" +
"	idProduto int,\n" +
"	descricao varchar(200),\n" +
"	solucao varchar(200),\n" +
"	idSituacaoAtendimento int,\n" +
"	constraint pk_atendimento primary key(id),\n" +
"	constraint fk_tipoatendimento foreign key(idTipoAtendimento) references tipoatendimento(id),\n" +
"	constraint fk_cliente foreign key(idCliente) references pessoa(id),\n" +
"	constraint fk_produto foreign key(idProduto) references produto(id),\n" +
"	constraint fk_situacaoatendimento foreign key(idsituacaoatendimento) references situacaoatendimento(id)\n" +
");";
    
    public static void main(String[] args) throws SQLException, IOException {
        try{
            con = new ConnectionFactory().getConnection();
            System.out.println("Conectado!");
            if(con.createStatement().executeUpdate(createTableAtendimento) != -1){
                System.out.println("Migration Atendimento criada com sucesso!");
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
