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

public class EditarFornecedor extends Activity {

    private FornecedorDbHelper baseFor;
    private EditText nomeFornecedorAlterar, telefoneFornecedorAlterar;
    private EditText idFornecedor_a;
    Button bt_alterarFornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_fornecedor);

        idFornecedor_a = (EditText) findViewById(R.id.idFornecedor_a);
        nomeFornecedorAlterar = (EditText) findViewById(R.id.nomeFornecedorAlterar);
        telefoneFornecedorAlterar = (EditText) findViewById(R.id.telefoneFornecedorAlterar);
        telefoneFornecedorAlterar.addTextChangedListener(Masks.mask(telefoneFornecedorAlterar, Masks.FORMAT_FONE));
        bt_alterarFornecedor = (Button) findViewById(R.id.bt_alterarFornecedor);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                String idFornecedor = params.getString("id_fornecedor");
                buscarFornecedor(idFornecedor);
            }
        }

        bt_alterarFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idFornecedor = idFornecedor_a.getText().toString();
                int res = alterarFornecedor(idFornecedor);
                if (res > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewFornecedor.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error ao alterar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected int alterarFornecedor(String idFornecedor) {
        SQLiteDatabase db = openOrCreateDatabase("Fornecedor.db", Context.MODE_PRIVATE, null);

        String nomeFornecedor = nomeFornecedorAlterar.getText().toString();
        String telefoneFornecedor = telefoneFornecedorAlterar.getText().toString();

        ContentValues ctv = new ContentValues();
        ctv.put("nomeFor", nomeFornecedor);
        ctv.put("telefoneFor", telefoneFornecedor);
        int res = db.update("Fornecedor", ctv, "_id=?", new String[]{idFornecedor});
        db.close();
        return res;
    }

    private void buscarFornecedor(String idFornecedor) {
        SQLiteDatabase db = openOrCreateDatabase("Fornecedor.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * from Fornecedor where _id=?";

        Cursor c = (SQLiteCursor) db.rawQuery(sql, new String[]{idFornecedor});

        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndex("_id"));
            String nomeFornecedor = c.getString(c.getColumnIndex("nomeFor"));
            String telefoneFornecedor = c.getString(c.getColumnIndex("telefoneFor"));

            idFornecedor_a.setText(id.toString());
            nomeFornecedorAlterar.setText(nomeFornecedor.toString());
            telefoneFornecedorAlterar.setText(telefoneFornecedor.toString());
        }
        c.close();
        db.close();
    }
}
