package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class VendedorRegister {

    public VendedorRegister(){
    }

    public static abstract  class VendedorDb implements BaseColumns{
        public static final String TABLE_NAME = "vendedor";
        public static final String COLUMN_NOMEVENDEDOR = "nomeVen";
    }
}