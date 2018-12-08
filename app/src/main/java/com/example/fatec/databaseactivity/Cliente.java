package com.example.fatec.databaseactivity;

import java.io.Serializable;

public class Cliente implements Serializable{

    private long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;

    public Cliente(long id, String nome, String cpf, String telefone, String endereco) {

        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public long getIdCli() {
        return id;
    }

    public void setIdCli(long idCli) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return nome +"\nCpf: " + cpf;
    }
}
