package classes;

import DAO.ClienteDAO;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Cliente c = new Cliente();
        Produto p = new Produto();

        // criar o cliente
        c.setNome("João");
        c.setTelefone("889811237254");
        c.setEndereco("Sitio");
        c.setEmail("TUTU@gmail.com");

        // criar o produto
        p.setNome("Air Force ONE");
        p.setQuantidade(4);
        p.setPreco(200.50);
        p.setDescricao("Tenis branco");

        System.out.println(c.toString());
        System.out.println(p.toString());

        System.out.println("");
        Venda v = new Venda();
        v.setCliente(c);
        v.realizaVenda(2, p);

        v.mostrarVenda();




    }
}
