package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class FornecedorRegister {

    public FornecedorRegister(){
    }

    public static abstract  class FornecedorDb implements BaseColumns{
        public static final String TABLE_NAME = "fornecedor";
        public static final String COLUMN_NOMEFOR = "nomeFor";
        public static final String COLUMN_TELEFONEFOR = "telefoneFor";
    }
}