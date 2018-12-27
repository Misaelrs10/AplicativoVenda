package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

public class NovoRecebimento extends Activity implements AdapterView.OnItemSelectedListener {

    private RecebimentoDbHelper baseRec;
    private Spinner clienteRecebimento;
    private EditText valRec, dataRec;
    private long idRec;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_recebimento);

        RecebimentoDbHelper Recebimentodbhelper = new RecebimentoDbHelper(getApplicationContext());
        baseRec = new RecebimentoDbHelper(getApplicationContext());

        clienteRecebimento = (Spinner) findViewById(R.id.clienteRecebimento);
        loadSpinnerCliente();

        valRec = (EditText) findViewById(R.id.valor_recebimento);
        valRec.addTextChangedListener(new MaskMoney.MascaraMonetaria(valRec));
        dataRec = (EditText) findViewById(R.id.data_recebimento);
        dataRec.addTextChangedListener(Masks.mask(dataRec, Masks.FORMAT_DATE));
    }

    private void loadSpinnerCliente() {
        ClienteDbHelper db = new ClienteDbHelper(getApplicationContext());
        ArrayList<Cliente> consultarCliente = db.consultarCliente();
        ArrayAdapter<Cliente> dataAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, consultarCliente);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        clienteRecebimento.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String cliente = clienteRecebimento.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    public void btcadastrar_recebimento(View view) {
        Recebimento Recebimento = new Recebimento(idRec, clienteRecebimento.getSelectedItem().toString(), valRec.getText().toString(),dataRec.getText().toString());
        baseRec.cadastrarRecebimento(Recebimento);
        clienteRecebimento.setOnItemSelectedListener(this);
        valRec.setText("");
        dataRec.setText("");

        Toast toast = Toast.makeText(this, "Recebimento de cliente cadastrado com sucesso!", Toast.LENGTH_SHORT);
        toast.show();

        Intent intent = new Intent(getApplicationContext(), ViewRecebimento.class);
        startActivity(intent);
        finish();
    }

}
