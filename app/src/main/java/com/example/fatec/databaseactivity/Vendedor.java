package com.example.fatec.databaseactivity;

public class Vendedor {

        private long id;
        private String nomeVendedor;

        public Vendedor (long id, String nomeVendedor){
            this.id = id;
            this.nomeVendedor = nomeVendedor;
        }

        public Vendedor (String nomeVendedor){
             this.nomeVendedor = nomeVendedor;

    }

    public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getNomeVendedor() {
            return nomeVendedor;
        }

        public void setNomeVendedor(String nomeVendedor) {
            this.nomeVendedor = nomeVendedor;
        }

        @Override
        public String toString() {
            return nomeVendedor ;
        }
}

