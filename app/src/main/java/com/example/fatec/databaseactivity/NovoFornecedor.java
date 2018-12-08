package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovoFornecedor extends Activity{

    private FornecedorDbHelper baseFor;
    private EditText nomeFor, telefoneFor;
    private long idFor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_fornecedor);

        FornecedorDbHelper fornecedordbhelper = new FornecedorDbHelper(getApplicationContext());
        baseFor = new FornecedorDbHelper(getApplicationContext());
        nomeFor = (EditText) findViewById(R.id.NomeFornecedor);
        telefoneFor = (EditText) findViewById(R.id.telFornecedor);
        telefoneFor.addTextChangedListener(Masks.mask(telefoneFor, Masks.FORMAT_FONE));
    }

    public void btcadastrar_fornecedor(View view) {
        Fornecedor fornecedor = new Fornecedor(idFor, nomeFor.getText().toString(), telefoneFor.getText().toString());
        baseFor.cadastrar_fornecedor(fornecedor);
        nomeFor.setText("");
        telefoneFor.setText("");
        Intent main = new Intent(NovoFornecedor.this, ViewFornecedor.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

        Toast toast = Toast.makeText(this, "Fornecedor cadastrado com sucesso!", Toast.LENGTH_LONG);
        toast.show();
    }

}
