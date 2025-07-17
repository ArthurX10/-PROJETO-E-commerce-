package DAO;

import classes.Cliente;
import classes.Venda;
import conexao.Conexao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public void salvarVenda(Venda venda, Cliente cliente){
        try{
            String sql = "INSERT INTO Venda (cliente_id, data_venda, total)  VALUES(?,?,?)";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1,cliente.getId());
            ps.setString(2, String.valueOf(venda.getDataVenda()));
            ps.setDouble(3, venda.getValor());

            ps.execute();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int retornaUltimoIdVenda(){
        try{
            int ultimoId = 0;
            String sql = "SELECT MAX(id) id from Venda";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while(result.next()){
                Venda venda = new Venda();
                venda.setId(result.getInt("id"));
                ultimoId = venda.getId();
            }
            return ultimoId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Venda> listar(){
        List<Venda> lista = new ArrayList<>();

        try{
            String sql = "SELECT * FROM Venda";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                Venda venda = new Venda();
                Cliente cliente = new Cliente();
                venda.setId(result.getInt("venda_id"));
                cliente.setId(result.getInt("cliente_id"));
                venda.getDataVenda();
                venda.setValor(result.getDouble("Valor total"));
                lista.add(venda);

            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
