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



}
