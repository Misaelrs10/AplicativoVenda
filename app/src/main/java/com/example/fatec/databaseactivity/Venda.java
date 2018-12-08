package com.example.fatec.databaseactivity;

public class Venda {

        private long id;
        private String clienteVenda;
        private String produtoVenda;
        private String vendedorVenda;
        private String precoVenda;
        private String quantidadeVenda;
        private String dataVenda;

        public Venda(long id, String clienteVenda, String produtoVenda, String vendedorVenda, String dataVenda, String quantidadeVenda, String precoVenda){
            this.id = id;
            this.clienteVenda = clienteVenda;
            this.produtoVenda = produtoVenda;
            this.vendedorVenda = vendedorVenda;
            this.dataVenda = dataVenda;
            this.quantidadeVenda = quantidadeVenda;
            this.precoVenda = precoVenda;
        }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClienteVenda() {
        return clienteVenda;
    }

    public void setClienteVenda(String clienteVenda) {
        this.clienteVenda = clienteVenda;
    }

    public String getProdutoVenda() {
        return produtoVenda;
    }

    public void setProdutoVenda(String produtoVenda) {
        this.produtoVenda = produtoVenda;
    }

    public String getVendedorVenda() {
        return vendedorVenda;
    }

    public void setVendedorVenda(String vendedorVenda) {
        this.vendedorVenda = vendedorVenda;
    }

    public String getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(String precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getQuantidadeVenda() {
        return quantidadeVenda;
    }

    public void setQuantidadeVenda(String quantidadeVenda) {
        this.quantidadeVenda = quantidadeVenda;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }

    @Override
        public String toString() {
            return clienteVenda + produtoVenda + vendedorVenda + dataVenda + quantidadeVenda + precoVenda;
        }
}

