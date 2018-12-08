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

public class ViewCliente extends Activity implements AdapterView.OnItemClickListener  {

    Cursor cursor;
    SQLiteDatabase db;
    ListView listViewCliente;
    SimpleCursorAdapter ad;
    String id_Cliente;
    TextView totalCliente;
    ClienteDbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcliente);

        helper = new ClienteDbHelper(getApplicationContext());
        buscarDados();
        criarListagem();
        totalCliente = (TextView) findViewById(R.id.totalClientes);

    }

     private void buscarDados() {
        try{
            db = openOrCreateDatabase("Cliente.db", Context.MODE_PRIVATE,null);
            cursor = db.rawQuery("SELECT * from Cliente Order by Nome", null);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Erro ao carregar os dados",Toast.LENGTH_SHORT).show();
        }
    }

    public void criarListagem() {
        listViewCliente = (ListView) findViewById(R.id.listViewCliente);

        String[] from = {"_id", "nome", "cpf", "telefone", "endereco"};
        int[] to = {R.id.id, R.id.nomeCliente, R.id.cpfCliente, R.id.telefoneCliente, R.id.precoVenda};

        ad = new SimpleCursorAdapter(getApplicationContext(), R.layout.customviewcliente, cursor, from, to);

        listViewCliente.setOnItemClickListener(this);

        listViewCliente.setAdapter(ad);

        totalCliente = (TextView) findViewById(R.id.totalClientes);
        totalCliente.setText(String.valueOf(helper.contarClientes()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
             final SQLiteCursor sqlCursor = (SQLiteCursor) ad.getItem(position);

             final CharSequence[]itens = {getString(R.string.editar), getString(R.string.excluir)};

             AlertDialog.Builder opcoes = new AlertDialog.Builder(ViewCliente.this);
             opcoes.setItems(itens, new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     SQLiteCursor sqlCursor = (SQLiteCursor) ad.getItem(position);

                     String opcao = (String) itens[which];
                     String nome = sqlCursor.getString(sqlCursor.getColumnIndex("nome"));
                     String idCliente = sqlCursor.getString(sqlCursor.getColumnIndex("_id"));

                     if (opcao.equals(getString(R.string.editar))) {
                         Intent AlterarCliente = new Intent(getApplicationContext(), EditarCliente.class);
                         AlterarCliente.putExtra("id_cliente", idCliente);
                         startActivity(AlterarCliente);
                         finish();
                     } else if (opcao.equals(getString(R.string.excluir))) {
                         mostraSimNao(null);

                     }
                 }
             });

             opcoes.setTitle(getString(R.string.opcoes));
             AlertDialog alerta = opcoes.create();
             alerta.show();
         }

    private void mostraSimNao (final ViewCliente viewCliente) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(ViewCliente.this);
        mensagem.setMessage(getString(R.string.excluir));

        mensagem.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirCliente(null);
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

    public void excluirCliente(ViewCliente cliente) {
        id_Cliente = ad.getCursor().toString();
            SQLiteDatabase db = openOrCreateDatabase("Cliente.db", Context.MODE_PRIVATE, null);
            db.delete("Cliente", "_id=?", new String[]{id_Cliente});
            db.close();
            Toast.makeText(getApplicationContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void btnovoCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoCliente.class);
        startActivity(intent);
        db.close();
    }
}


