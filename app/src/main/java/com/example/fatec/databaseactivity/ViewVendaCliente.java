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

import java.text.NumberFormat;
import java.util.ArrayList;

public class ViewVendaCliente extends Activity{

    VendaDbHelper helper;
    RecebimentoDbHelper recebimentoDbHelper;
    Cursor cursor;
    ListView listViewVendaCliente;
    SimpleCursorAdapter ad;
    private Spinner spinnerCliente;
    TextView totalCompras;
    TextView totalPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewvendacliente);

        helper = new VendaDbHelper(getApplicationContext());
        recebimentoDbHelper = new RecebimentoDbHelper(getApplicationContext());
        spinnerCliente = (Spinner) findViewById(R.id.spinnerCliente);
        totalCompras = (TextView) findViewById(R.id.compras);
        totalCompras.addTextChangedListener(MaskMoney.monetarioExibir(totalCompras));
        totalPagar = (TextView) findViewById(R.id.pagar);
        totalPagar.addTextChangedListener(MaskMoney.monetarioExibir(totalPagar));
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
                buscarDados((Cliente)parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void buscarDados(Cliente cliente) {

        cursor = helper.consultarCursorVenda(cliente);

        listViewVendaCliente = (ListView)findViewById(R.id.listViewVendaCliente);

        String []from = {"_id", "produtoVenda", "dataVenda", "quantidadeVenda", "precoVenda"};
        int[] to = {R.id.id, R.id.produtoVendaCliente, R.id.dataVendaCliente, R.id.quantidadeVendaCliente,R.id.totalVendaCliente};

        ad = new SimpleCursorAdapter(getApplicationContext(),R.layout.customviewvendacliente,cursor,from,to);

        listViewVendaCliente.setAdapter(ad);

        totalCompras = (TextView)findViewById(R.id.compras);
        totalCompras.setText(String.valueOf(helper.somarVendas(cliente)));
        totalCompras.addTextChangedListener(MaskMoney.monetarioExibir(totalCompras));

        totalPagar = (TextView)findViewById(R.id.pagar);
        totalPagar.setText(String.valueOf(helper.aPagar(cliente)-recebimentoDbHelper.somarRecebimento(cliente)));
        totalPagar.addTextChangedListener(MaskMoney.monetarioExibir(totalPagar));
    }

    public void btcadastrarrecebimento(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoRecebimento.class);
        startActivity(intent);
        finish();
    }

    public void viewRecebimento(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewRecebimento.class);
        startActivity(intent);
    }
}

