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

public class EditarVendedor extends Activity {

    private VendedorDbHelper baseVend;
    private EditText nomeVendedorAlterar, idVendedor_a;
    Button bt_alterarVendedor, bt_excluirVendedor;
    String id_Vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_vendedor);

        idVendedor_a = (EditText) findViewById(R.id.idVendedor_a);
        nomeVendedorAlterar = (EditText) findViewById(R.id.nomeVendedorAlterar);
        bt_alterarVendedor = (Button) findViewById(R.id.bt_alterarVendedor);
        //bt_excluirVendedor = (Button) findViewById(R.id.bt_excluirVendedor);
       
        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                String idVendedor = params.getString("id_Vendedor");
                buscarVendedor(idVendedor);
            }
        }

        bt_alterarVendedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idVendedor = idVendedor_a.getText().toString();
                int res = alterarVendedor(idVendedor);
                if(res > 0) {
                    Toast.makeText(getApplicationContext(),
                           "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewVendedor.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error ao alterar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected int alterarVendedor(String idVendedor) {
        SQLiteDatabase db = openOrCreateDatabase("Vendedor.db", Context.MODE_PRIVATE, null);

        String nomeVendedor = nomeVendedorAlterar.getText().toString();
       
        ContentValues ctv = new ContentValues();
        ctv.put("nomeVen", nomeVendedor);
        int res = db.update("Vendedor", ctv, "_id=?", new String[]{idVendedor});
        db.close();
        return res;
    }

    private void buscarVendedor(String idVendedor) {
        SQLiteDatabase db = openOrCreateDatabase("Vendedor.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * from Vendedor where _id=?";

        Cursor c = (SQLiteCursor) db.rawQuery(sql, new String[]{idVendedor});

        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndex("_id"));
            String nomeVendedor = c.getString(c.getColumnIndex("nomeVen"));

            idVendedor_a.setText(id.toString());
            nomeVendedorAlterar.setText(nomeVendedor.toString());
        }
        c.close();
        db.close();
    }

    public void bt_excluirVendedor(View v) {

        id_Vendedor = idVendedor_a.getText().toString();

        try {
            SQLiteDatabase db = openOrCreateDatabase("Vendedor.db", Context.MODE_PRIVATE, null);
            db.delete("Vendedor", "_id=?", new String[]{id_Vendedor});
            db.close();
            Toast.makeText(getApplicationContext(), "Excluido com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), ViewVendedor.class));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Erro ao excluir", Toast.LENGTH_SHORT).show();
        }
    }
}
