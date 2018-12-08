package com.example.fatec.databaseactivity;

public class Recebimento {

    private long id;
    private String cliente;
    private String valor;
    private String data;

    public Recebimento(long id, String cliente, String valor, String data){
        this.id = id;
        this.cliente = cliente;
        this.valor = valor;
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

       @Override
    public String toString() {
        return  "Cliente: " + cliente + "\nValor: R$ " + valor+ "\nData: " + data;
    }
}
