package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class CategoriaRegister {

    public CategoriaRegister(){
    }

    public static abstract  class CategoriaDb implements BaseColumns{
        public static final String TABLE_NAME = "categoria";
        public static final String COLUMN_NOMECAT = "nomeCat";
    }
}