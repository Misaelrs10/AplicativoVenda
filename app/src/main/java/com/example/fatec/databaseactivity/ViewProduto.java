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

public class ViewProduto extends Activity implements AdapterView.OnItemClickListener{

    Cursor cursor;
    SQLiteDatabase db;
    ListView listViewProduto;
    SimpleCursorAdapter ad;
    String id_Produto;
    TextView totalProduto;
    ProdutoDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewproduto);

        helper = new ProdutoDbHelper(getApplicationContext());
        buscarDados();
        criarListagem();
        totalProduto = (TextView) findViewById(R.id.totalProdutos);
    }

    private void buscarDados() {
        try{
            db = openOrCreateDatabase("Produto.db", Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * from Produto Order by Nome", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao carregar os dados", Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem(){
        listViewProduto = (ListView)findViewById(R.id.listViewProduto);

        String []from = {"_id", "nome", "categoria", "fornecedor", "preco"};
        int[] to = {R.id.id, R.id.nomeProduto, R.id.categoriaProduto, R.id.fornecedorProduto, R.id.precoProduto};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewproduto,cursor,from,to);

        listViewProduto.setOnItemClickListener(this);

        listViewProduto.setAdapter(ad);

        totalProduto = (TextView)findViewById(R.id.totalProdutos);
        totalProduto.setText(String.valueOf(helper.contarProdutos()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);

        final CharSequence[]itens = {getString(R.string.editar) , getString(R.string.excluir)};

        AlertDialog.Builder opcoes = new AlertDialog.Builder(ViewProduto.this);
        opcoes.setItems(itens, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteCursor sqlCursor = (SQLiteCursor)ad.getItem(position);

                String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nome"));
                String idProduto = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));
                String opcao = (String) itens[which];

                if(opcao.equals(getString(R.string.editar))){
                    Intent altera = new Intent(getApplicationContext(), EditarProduto.class);
                    altera.putExtra("id_produto", idProduto);
                    startActivity(altera);
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

    private void mostraSimNao (ViewProduto viewProduto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(ViewProduto.this);
        mensagem.setMessage(getString(R.string.excluir));
        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirProduto(null);
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

    public void excluirProduto(ViewProduto p) {
        id_Produto = ad.getCursor().toString();
            SQLiteDatabase db = openOrCreateDatabase("Produto.db", Context.MODE_PRIVATE, null);
            db.delete("Produto", "_id=?", new String[]{id_Produto});
            db.close();
            Toast.makeText(getApplicationContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void btnovoProduto(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoProduto.class);
        startActivity(intent);
        db.close();
    }


}
