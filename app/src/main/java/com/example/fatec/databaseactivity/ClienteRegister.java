package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class ClienteRegister {

    public ClienteRegister() {
    }

    public static abstract class ClienteDb implements BaseColumns {
        public static final String TABLE_NAME = "cliente";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_CPF = "cpf";
        public static final String COLUMN_ENDERECO = "endereco";
        public static final String COLUMN_TELEFONE = "telefone";

    }

}
