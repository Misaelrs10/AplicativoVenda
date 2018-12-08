package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class ProdutoRegister {

    public ProdutoRegister(){
    }

    public static abstract  class ProdutoDb implements BaseColumns{
        public static final String TABLE_NAME = "produto";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_CATEGORIA = "categoria";
        public static final String COLUMN_FORNECEDOR = "fornecedor";
        public static final String COLUMN_PRECO = "preco";
    }
}