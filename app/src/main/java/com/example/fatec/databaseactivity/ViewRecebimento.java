package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class ViewRecebimento extends Activity{

    RecebimentoDbHelper helper;
    Cursor cursor;
    SimpleCursorAdapter ad;
    ListView listViewRecebimento;
    private Spinner spinnerCliente;
    TextView totalRecebido;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewrecebimento);

        helper = new RecebimentoDbHelper(getApplicationContext());
        spinnerCliente = (Spinner) findViewById(R.id.spinnerCliente);
        totalRecebido = (TextView) findViewById(R.id.recebido);
        totalRecebido.addTextChangedListener(MaskMoney.monetarioExibir(totalRecebido));
        loadSpinnerCliente();

    }

    private void loadSpinnerCliente() {
        ClienteDbHelper db = new ClienteDbHelper(getApplicationContext());
        ArrayList<Cliente> consultarCliente = db.consultarCliente();
        ArrayAdapter<Cliente> dataAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, consultarCliente);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerCliente.setAdapter(dataAdapter);
        spinnerCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                buscarDados((Cliente) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buscarDados(Cliente cliente) {

        cursor = helper.consultarCursorRecebimento(cliente);

        listViewRecebimento = (ListView)findViewById(R.id.listViewRecebimento);

        String []from = {"_id", "data", "valor"};
        int[] to = {R.id.id, R.id.dataRecebimento, R.id.valorRecebimento};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewrecebimento,cursor,from,to);

        listViewRecebimento.setAdapter(ad);

        totalRecebido = (TextView)findViewById(R.id.recebido);
        totalRecebido.setText(String.valueOf(helper.somarRecebimento(cliente)));
    }

    public void btnovoRecebimento(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoRecebimento.class);
        startActivity(intent);
        finish();
    }

}

