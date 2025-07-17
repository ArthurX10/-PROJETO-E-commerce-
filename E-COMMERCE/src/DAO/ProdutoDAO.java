package DAO;


import classes.Produto;
import conexao.Conexao;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public void salvarProdutos(Produto produto){


        try {
            String sql = "INSERT INTO Produtos (nome_produto, descricao, preco, estoque) values(?, ?, ?, ?)";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setInt(4, produto.getQuantidade());

            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editarProdutos(Produto produto){
        try {
        String sql = "UPDATE Produtos set nome = ?, descricao = ?, preco = ?, quantidade = ? where id = ?";
        PreparedStatement ps = null;
        ps = Conexao.getConexao().prepareStatement(sql);
        ps.setString(1, produto.getNome());
        ps.setString(2, produto.getDescricao());
        ps.setDouble(3, produto.getPreco());
        ps.setInt(4, produto.getQuantidade());
        ps.setInt(5, produto.getId());

        ps.execute();
        ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Produto> listar(){
        List<Produto> lista = new ArrayList<>();
         try{
             String sql = "SELECT * FROM Produtos";
             PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
             ResultSet result = ps.executeQuery();

             while(result.next()){
                 Produto produto = new Produto();
                 produto.setId(result.getInt("produto_id"));
                 produto.setNome(result.getString("nome_produto"));
                 produto.setDescricao((result.getString("descrisao")));
                 produto.setPreco(result.getDouble("preco"));
                 produto.setQuantidade(result.getInt("estoque"));
                 lista.add(produto);
             }
             return lista;
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
    }

    public void deletarProdutos(int id){
        try{
            String sql = "DELETE FROM Produtos where id = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void buscarProduto(int id){

        try{
            String sql = "SELECT * FROM Produtos where id = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            Produto produto = new Produto();
            if(result.next()){
                produto.setId(result.getInt("produto_id"));
                produto.setNome(result.getString("nome_produto"));
                produto.setDescricao(result.getString("descricao"));
                produto.setPreco(result.getDouble("preco"));
                produto.setQuantidade(result.getInt("estoque"));
            }

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void adicionarEstoque(int qtde_nova , int id){
        try{
             String sql = "UPDATE Produtos set estoque = ? where id = ?";
             PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
             ps.setInt(1, id);
             ps.setInt(2, qtde_nova);
             ps.execute();
             ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void diminueEstoque(int qtde_nova , int id){
        try{
            String sql = "UPDATE Produtos set estoque = ? where id = ?";
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, qtde_nova);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public int retornaQTDAtualEstoque(int id){
        try{
            int qtd_Atual_estoque = 0;
            String sql = "SELECT estoque FROM Produto where id = ?";
            PreparedStatement ps = null;
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet result = ps.executeQuery();
            if(result.next()){
                qtd_Atual_estoque = (result.getInt("estoque"));
            }
            return qtd_Atual_estoque;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }


}
