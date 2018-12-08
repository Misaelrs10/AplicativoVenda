package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovoVendedor extends Activity{

    private VendedorDbHelper baseVen;
    private EditText nomeVen;
    private long idVen;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_vendedor);

        VendedorDbHelper vendedordbhelper = new VendedorDbHelper(getApplicationContext());
        baseVen = new VendedorDbHelper(getApplicationContext());
        nomeVen = (EditText) findViewById(R.id.idNomeVendedor);

    }

    public void btcadastrar_vendedor(View view) {
        Vendedor vendedor = new Vendedor(idVen, nomeVen.getText().toString());
        baseVen.cadastrar_vendedor(vendedor);
        nomeVen.setText("");
        Intent main = new Intent(NovoVendedor.this, ViewVendedor.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

        Toast toast = Toast.makeText(this, "Vendedor cadastrado com sucesso!", Toast.LENGTH_LONG);
        toast.show();
    }

}
