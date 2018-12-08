package com.example.fatec.databaseactivity;

import android.provider.BaseColumns;

public class VendaRegister {

    public static abstract  class VendaDb implements BaseColumns{
        public static final String TABLE_NAME = "venda";
        public static final String COLUMN_CLIENTEVENDA = "clienteVenda";
        public static final String COLUMN_PRODUTOVENDA = "produtoVenda";
        public static final String COLUMN_VENDEDORVENDA = "vendedorVenda";
        public static final String COLUMN_DATAVENDA = "dataVenda";
        public static final String COLUMN_QUANTIDADEVENDA = "quantidadeVenda";
        public static final String COLUMN_PRECOVENDA = "precoVenda";
    }
}