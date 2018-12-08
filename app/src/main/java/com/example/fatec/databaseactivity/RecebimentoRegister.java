package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class RecebimentoRegister {

    public RecebimentoRegister(){
    }

    public static abstract  class RecebimentoDb implements BaseColumns{
        public static final String TABLE_NAME = "Recebimento";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CLIENTE = "cliente";
        public static final String COLUMN_VALOR = "valor";
        public static final String COLUMN_DATA = "data";
    }
}