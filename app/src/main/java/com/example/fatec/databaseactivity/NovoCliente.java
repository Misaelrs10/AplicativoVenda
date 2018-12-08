package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NovoCliente extends Activity{

    private ClienteDbHelper baseCli;
    private EditText nomeCli, cpf, endereco, telefone;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_cliente);

        ClienteDbHelper clientedbhelper = new ClienteDbHelper(getApplicationContext());
        baseCli = new ClienteDbHelper(getApplicationContext());
        nomeCli = (EditText) findViewById(R.id.idNomeCli);
        telefone = (EditText) findViewById(R.id.idTelefone);
        telefone.addTextChangedListener(Masks.mask(telefone, Masks.FORMAT_FONE));
        endereco = (EditText) findViewById(R.id.idEndereco);
        cpf = (EditText) findViewById(R.id.idCpf);
        cpf.addTextChangedListener(Masks.mask(cpf, Masks.FORMAT_CPF));
    }

    public void btcadastrar_cliente(View view) {
        Cliente cliente = new Cliente(id , nomeCli.getText().toString(), cpf.getText().toString(), telefone.getText().toString(), endereco.getText().toString());
        baseCli.salvarCliente(cliente);
        nomeCli.setText("");
        cpf.setText("");
        telefone.setText("");
        endereco.setText("");
        Intent main = new Intent(NovoCliente.this, ViewCliente.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

        Toast toast = Toast.makeText(this, "Cliente cadastrado com sucesso!", Toast.LENGTH_LONG);
        toast.show();

    }

}
