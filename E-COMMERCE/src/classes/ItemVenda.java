package classes;


import java.util.List;

public class ItemVenda{
    private int id;
    private Venda venda;
    private Produto produto;
    private int quantidade;
    private double subTotal;
    private double valorUnitario;

    public ItemVenda(){}

    public ItemVenda(Venda venda, int quantidade, Produto produto) {
        this.venda = venda;
        this.quantidade = quantidade;
        this.produto = produto;
        this.valorUnitario = produto.getPreco();
        calcularSubTotal();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public double getSubTotal() {
        return subTotal;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
       if(this.quantidade < 0 ){
           System.out.println("Quantidade insuficiente");
       } 
        this.quantidade = quantidade;
        calcularSubTotal();
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valor) {
        this.valorUnitario = valor;
    }


    public void calcularSubTotal() {
        this.subTotal = this.valorUnitario * this.quantidade;
        if(this.subTotal < 0){ // se o subTotal for menor que zero, ele retorna 0
            this.subTotal = 0;
        }

    }


}
