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


  
public List<Venda> listarVendasCompletas() {
    List<Venda> lista = new ArrayList<>();

    String sql = """
        SELECT 
            v.id AS venda_id,
            v.data_venda,
            v.total,
            c.id AS cliente_id,
            c.nome AS cliente_nome,
            c.email,
            c.endereco,
            c.telefone,
            p.id AS produto_id,
            p.nome_produto,
            p.descricao,
            p.preco,
            iv.quantidade,
            iv.preco_unitario,
            iv.subtotal
        FROM Venda v
        INNER JOIN Clientes c ON v.cliente_id = c.id
        INNER JOIN ItemVenda iv ON v.id = iv.venda_id
        INNER JOIN Produtos p ON iv.produto_id = p.id
    """;

    try (PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
         ResultSet result = ps.executeQuery()) {

        while (result.next()) {
            // Cliente
            Cliente cliente = new Cliente();
            cliente.setId(result.getInt("cliente_id"));
            cliente.setNome(result.getString("cliente_nome"));
            cliente.setEmail(result.getString("email"));
            cliente.setEndereco(result.getString("endereco"));
            cliente.setTelefone(result.getString("telefone"));

            // Produto
            Produto produto = new Produto();
            produto.setId(result.getInt("produto_id"));
            produto.setNome(result.getString("nome_produto"));
            produto.setDescricao(result.getString("descricao"));
            produto.setPreco(result.getDouble("preco"));

            // ItemVenda
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(result.getInt("quantidade"));
            item.setValorUnitario(result.getDouble("preco_unitario"));
            item.setSubTotal(result.getDouble("subtotal"));

            // Venda
            Venda venda = new Venda();
            venda.setId(result.getInt("venda_id"));
            venda.setDataVenda(result.getDate("data_venda").toLocalDate());
            venda.setValor(result.getDouble("total"));
            venda.setCliente(cliente);
            venda.getItens().add(item);

            lista.add(venda);
        }

    } catch (SQLException e) {
        throw new RuntimeException("Erro ao listar vendas completas", e);
    }

    return lista;
}

}
