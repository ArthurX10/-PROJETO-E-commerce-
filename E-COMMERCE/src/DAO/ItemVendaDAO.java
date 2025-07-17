package DAO;

import classes.ItemVenda;
import conexao.Conexao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemVendaDAO {
    public void salvar(ItemVenda itemVenda){
       try{
            String sql = "INSERT INTO ItemVenda (venda_id, produto_id, quantidade,preco_unitario, subTotal VALUES(?,?,?,?,?)";
           PreparedStatement ps = null;
           ps = Conexao.getConexao().prepareStatement(sql);
           ps.setInt(1, itemVenda.getVenda().getId());
           ps.setInt(2, itemVenda.getProduto().getId());
           ps.setInt(3, itemVenda.getQuantidade());
           ps.setDouble(4, itemVenda.getValorUnitario());
           ps.execute();
           ps.close();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
    }


}


