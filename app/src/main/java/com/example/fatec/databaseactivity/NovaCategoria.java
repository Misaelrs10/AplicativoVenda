package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovaCategoria extends Activity{

    private CategoriaDbHelper baseCat;
    private EditText nomeCat;
    private long idCat;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_categoria);

        CategoriaDbHelper Categoriadbhelper = new CategoriaDbHelper(getApplicationContext());
        baseCat = new CategoriaDbHelper(getApplicationContext());
        nomeCat = (EditText) findViewById(R.id.NomeCategoria);
        
    }

    public void btcadastrar_Categoria(View view) {
        Categoria Categoria = new Categoria(idCat, nomeCat.getText().toString());
        baseCat.cadastrar_Categoria(Categoria);
        nomeCat.setText("");
        Intent main = new Intent(NovaCategoria.this, NovoProduto.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

        Toast toast = Toast.makeText(this, "Categoria cadastrada com sucesso!", Toast.LENGTH_LONG);
        toast.show();
    }
}
