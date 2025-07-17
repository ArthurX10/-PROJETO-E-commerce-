package DAO;

import classes.Cliente;
import conexao.Conexao;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClienteDAO {

    public void cadastrarCliente(Cliente cliente){

        try {
            String sql = "INSERT INTO Clientes (NOME, EMAIL, ENDERECO, TELEFONE) VALUES(?,?,?,?)";

            PreparedStatement ps = null;

            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, cliente.getTelefone());

            ps.execute();
            ps.close();
        }catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    //Editar um cliente

    public void editarCliente(Cliente cliente){

        try {
            String sql = "UPDATE Clientes set nome = ?, email = ?, endereco = ?, telefone = ? where id = ?";

            PreparedStatement ps = null;

            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2,cliente.getEmail());
            ps.setString(3,cliente.getEndereco());
            ps.setString(4,cliente.getTelefone());
            ps.setInt(5,cliente.getId());

            ps.execute();
            ps.close();

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    // criar um buscar cliente
    public void buscarCliente(String nome){


        try {
            String sql = "SELECT * FROM Clientes WHERE nome = ?";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet result = ps.executeQuery();
            Cliente cliente = new Cliente();
            if(result.next()){
                cliente.setId(result.getInt("cliente_id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEmail(result.getString("email"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEndereco(result.getString("endereco"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // criar um deletar cliente pelo id e pelo nome usando o polimofismo de sobrecarga
    public void deletarCliente(String nome){
        String sql = "DELETE  FROM Clientes where nome = ?";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, nome);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deletarCliente(int id){
        String sql = "DELETE  FROM Clientes where id = ?";
        PreparedStatement ps = null;


        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // criar um metodo de listar Clientes
    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Clientes";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            while (result.next()){
                Cliente cliente = new Cliente();
                cliente.setId(result.getInt("cliente_id"));
                cliente.setNome(result.getString("nome"));
                cliente.setEmail(result.getString("email"));
                cliente.setTelefone(result.getString("telefone"));
                cliente.setEndereco(result.getString("endereco"));
                lista.add(cliente);
            }
            return lista;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao criar a lista " + e);
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
