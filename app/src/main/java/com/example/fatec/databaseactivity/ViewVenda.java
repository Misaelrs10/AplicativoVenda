package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewVenda extends Activity implements AdapterView.OnItemClickListener{

    Cursor cursor;
    SQLiteDatabase db;
    ListView listViewVenda;
    SimpleCursorAdapter ad;
    TextView totalVendas;
    VendaDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewvenda);

        helper = new VendaDbHelper(getApplicationContext());
        buscarDados();
        criarListagem();
        totalVendas = (TextView) findViewById(R.id.totalTodasVendas);
        //totalVendas.addTextChangedListener(MaskMoney.monetario(totalVendas));
    }

    private void buscarDados() {
        try{
            db = openOrCreateDatabase("Venda.db", Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * from Venda Order by _id desc", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem(){

        listViewVenda = (ListView)findViewById(R.id.listViewVenda);

        String []from = {"_id", "clienteVenda", "produtoVenda", "vendedorVenda", "dataVenda", "quantidadeVenda", "precoVenda"};
        int[] to = {R.id.id, R.id.clienteVenda, R.id.produtoVenda, R.id.vendedorVenda, R.id.dataVenda, R.id.quantidadeVenda,R.id.totalVenda};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewvenda,cursor,from,to);

        listViewVenda.setAdapter(ad);

        totalVendas = (TextView)findViewById(R.id.totalTodasVendas);
        //totalVendas.addTextChangedListener(MaskMoney.monetarioTextView(totalVendas));
        totalVendas.setText(String.valueOf(helper.somarTodasVendas()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long idVen) {
        SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);
        String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nome"));

        String id_venda = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));

        Intent altera = new Intent(getApplicationContext(), EditarCliente.class);
        altera.putExtra("id_venda", idVen);
        startActivity(altera);
    }

    public void btnovaVenda(View v) {
        Intent intent = new Intent(getApplicationContext(), NovaVenda.class);
        startActivity(intent);
    }
}

