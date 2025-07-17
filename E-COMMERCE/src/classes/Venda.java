package classes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Venda{

    private int id; // o id vai ser gerado pelo banco de dados
    private Cliente cliente;// aqui vai trazer o cliente da classe cliente
    private List<ItemVenda> itens = new ArrayList<>();// vou criar dps uma lista de item, pq agora só está calculando 1 produto só
    private double quantidade;
    private double valor;
    private Date dataVenda = new Date();

    public Venda(){}

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidadeTotal(double quantidade) {
        this.quantidade= quantidade;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }


    public void realizaVenda(int quantidade, Produto produto){
        if(produto == null || this.quantidade < 0){
            System.out.println("Quantidade insuficiente");
        }

        ItemVenda novoItem = new ItemVenda(this, quantidade, produto);
        itens.add(novoItem);

        produto.reduzirEstoque(quantidade);
    }

    public void mostrarVenda(){
        ItemVenda item = itens.get(0);
        System.out.println("Produto: " + item.getProduto().getNome());
        System.out.println("Cliente: " + this.cliente.getNome());
        System.out.println("Data: " + this.getDataVenda());
        System.out.println("Quantidade: " + item.getQuantidade());
        System.out.println("valor total " + item.getSubTotal());

    }

    // lista vendas feitas


}
