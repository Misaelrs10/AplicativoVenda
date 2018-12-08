package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ViewVendedor extends Activity implements AdapterView.OnItemClickListener{

    Cursor cursor;
    SQLiteDatabase db;
    ListView listViewVendedor;
    SimpleCursorAdapter ad;
    String id_Vendedor;
    TextView totalVendedor;
    VendedorDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewvendedor);

        helper = new VendedorDbHelper(getApplicationContext());
        totalVendedor = (TextView) findViewById(R.id.totalVendedores);
        buscarDados();
        criarListagem();
    }

    private void buscarDados() {
        try{
            db = openOrCreateDatabase("Vendedor.db", Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * from Vendedor Order by nomeven", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem(){
        listViewVendedor = (ListView)findViewById(R.id.listViewVendedor);

        String []from = {"_id", "nomeVen"};
        int[] to = {R.id.id, R.id.nomeVendedor};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewvendedor,cursor,from,to);

        listViewVendedor.setOnItemClickListener(this);

        listViewVendedor.setAdapter(ad);

        totalVendedor = (TextView)findViewById(R.id.totalVendedores);
        totalVendedor.setText(String.valueOf(helper.contarVendedores()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);

        final CharSequence[]itens = {getString(R.string.editar) , getString(R.string.excluir)};

        AlertDialog.Builder opcoes = new AlertDialog.Builder(ViewVendedor.this);
        opcoes.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteCursor sqlCursor = (SQLiteCursor) ad.getItem(position);

                String opcao = (String) itens[which];
                String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nomeVen"));
                String idVendedor = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));

                if (opcao.equals(getString(R.string.editar))) {
                    Intent AlterarVendedor = new Intent(getApplicationContext(), EditarVendedor.class);
                    AlterarVendedor.putExtra("id_Vendedor", idVendedor);
                    startActivity(AlterarVendedor);
                    finish();
                } else if (opcao.equals(getString(R.string.excluir))) {
                    mostraSimNao(id_Vendedor);
                }
            }
        });

        opcoes.setTitle(getString(R.string.opcoes));
        AlertDialog alerta = opcoes.create();
        alerta.show();
    }

    private void mostraSimNao (final String viewVendedor) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(ViewVendedor.this);
        mensagem.setMessage(getString(R.string.excluir));

        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirVendedor(null);
            }
        });

        mensagem.setNegativeButton(getString(R.string.nao), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        mensagem.create().show();
    }

    public void excluirVendedor(ViewVendedor viewVendedor) {
        id_Vendedor = ad.getCursor().toString();
            SQLiteDatabase db = openOrCreateDatabase("Vendedor.db", Context.MODE_PRIVATE, null);
            db.delete("Vendedor", "_id=?", new String[]{id_Vendedor});
            db.close();
            Toast.makeText(getApplicationContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void btnovoVendedor(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoVendedor.class);
        startActivity(intent);
        db.close();
    }
}
