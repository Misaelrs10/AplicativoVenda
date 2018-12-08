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

public class ViewFornecedor extends Activity implements AdapterView.OnItemClickListener{

    Cursor cursor;
    SQLiteDatabase db;
    ListView listView;
    SimpleCursorAdapter ad;
    String id_Fornecedor;
    TextView totalFornecedor;
    FornecedorDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewfornecedor);

        helper = new FornecedorDbHelper(getApplicationContext());
        totalFornecedor = (TextView) findViewById(R.id.totalFornecedores);
        buscarDados();
        criarListagem();
    }

    private void buscarDados() {
        try{
            db = openOrCreateDatabase("Fornecedor.db", Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * from Fornecedor Order by nomefor", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem(){
        listView = (ListView)findViewById(R.id.listViewFornecedor);

        String []from = {"_id", "nomeFor","telefoneFor"};
        int[] to = {R.id.id, R.id.nomeFornecedor, R.id.telefoneFornecedor};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewfornecedor,cursor,from,to);

        listView.setOnItemClickListener(this);

        listView.setAdapter(ad);

        totalFornecedor = (TextView)findViewById(R.id.totalFornecedores);
        totalFornecedor.setText(String.valueOf(helper.contarFornecedores()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);

        final CharSequence[]itens = {getString(R.string.editar) , getString(R.string.excluir)};

        AlertDialog.Builder opcoes = new AlertDialog.Builder(ViewFornecedor.this);
        opcoes.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);

                String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nomeFor"));
                String idFornecedor = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));
                String opcao = (String) itens[which];

                if(opcao.equals(getString(R.string.editar))){
                    Intent AlterarFornecedor = new Intent(getApplicationContext(), EditarFornecedor.class);
                    AlterarFornecedor.putExtra("id_fornecedor", idFornecedor);
                    startActivity(AlterarFornecedor);
                    finish();
                }else if (opcao.equals(getString(R.string.excluir))){
                    mostraSimNao(null);
                }
            }
        });

        opcoes.setTitle(getString(R.string.opcoes));
        AlertDialog alerta = opcoes.create();
        alerta.show();
    }

    private void mostraSimNao (ViewFornecedor viewFornecedor) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(ViewFornecedor.this);
        mensagem.setMessage(getString(R.string.excluir));
        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirFornecedor(null);
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

    public void excluirFornecedor(ViewFornecedor f) {
        id_Fornecedor = ad.getCursor().toString();
            SQLiteDatabase db = openOrCreateDatabase("Fornecedor.db", Context.MODE_PRIVATE, null);
            db.delete("Fornecedor", "_id=?", new String[]{id_Fornecedor});
            db.close();
            Toast.makeText(getApplicationContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void btnovoFornecedor(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoFornecedor.class);
        startActivity(intent);
        db.close();
    }
}
