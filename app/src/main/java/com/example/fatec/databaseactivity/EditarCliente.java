package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarCliente extends Activity{

    private ClienteDbHelper baseCli;
    private EditText nomeClienteAlterar, cpfClienteAlterar, telefoneClienteAlterar, enderecoClienteAlterar;
    private EditText idCliente_a;
    Button bt_alterarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cliente);

        idCliente_a = (EditText) findViewById(R.id.idCliente_a);
        nomeClienteAlterar = (EditText) findViewById(R.id.nomeClienteAlterar);
        cpfClienteAlterar = (EditText) findViewById(R.id.cpfClienteAlterar);
        cpfClienteAlterar.addTextChangedListener(Masks.mask(cpfClienteAlterar, Masks.FORMAT_CPF));
        telefoneClienteAlterar= (EditText) findViewById(R.id.telefoneClienteAlterar);
        telefoneClienteAlterar.addTextChangedListener(Masks.mask(telefoneClienteAlterar, Masks.FORMAT_FONE));
        enderecoClienteAlterar = (EditText) findViewById(R.id.enderecoClienteAlterar);
        bt_alterarCliente = (Button)findViewById(R.id.bt_alterarCliente);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                String idCli = params.getString("id_cliente");
                buscarCliente(idCli);
            }
        }

        bt_alterarCliente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String idCliente = idCliente_a.getText().toString();
                int res = alterarCliente(idCliente);
                if (res > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewCliente.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error ao alterar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected int alterarCliente(String idCliente){
        SQLiteDatabase db = openOrCreateDatabase("Cliente.db", Context.MODE_PRIVATE, null);

        String nome = nomeClienteAlterar.getText().toString();
        String telefone = telefoneClienteAlterar.getText().toString();
        String endereco = enderecoClienteAlterar.getText().toString();
        String cpf = cpfClienteAlterar.getText().toString();

        ContentValues ctv = new ContentValues();
        ctv.put("nome",nome);
        ctv.put("telefone",telefone);
        ctv.put("endereco",endereco);
        ctv.put("cpf", cpf);
        int res = db.update("cliente", ctv, "_id=?", new String[] {idCliente});
        db.close();
        return res;
    }

    private void buscarCliente(String idCliente) {
        SQLiteDatabase db = openOrCreateDatabase("Cliente.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * from Cliente where _id=?";

        Cursor c = (SQLiteCursor) db.rawQuery(sql,new String[] {idCliente});

        if (c.moveToFirst()){
            String id = c.getString(c.getColumnIndex("_id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String telefone = c.getString(c.getColumnIndex("telefone"));
            String endereco = c.getString(c.getColumnIndex("endereco"));
            String cpf = c.getString(c.getColumnIndex("cpf"));

            idCliente_a.setText(id.toString());
            nomeClienteAlterar.setText(nome.toString());
            telefoneClienteAlterar.setText(telefone.toString());
            enderecoClienteAlterar.setText(endereco.toString());
            cpfClienteAlterar.setText(cpf.toString());
        }
        c.close();
        db.close();
    }
}
