package com.example.fatec.databaseactivity;

public class Fornecedor {

        private long id;
        private String nome;
        private String telefoneFor;

        public Fornecedor(long id, String nome, String telefoneFor){
            this.id = id;
            this.nome = nome;
            this.telefoneFor = telefoneFor;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getTelefone() {
        return telefoneFor;
    }

        public void setTelefone(String Telefone) {
        this.telefoneFor = telefoneFor;
    }

        @Override
        public String toString() {
            return  nome + "\nTelefone: " + telefoneFor;
        }
}

