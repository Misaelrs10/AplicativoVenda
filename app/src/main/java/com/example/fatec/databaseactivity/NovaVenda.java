package com.example.fatec.databaseactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class NovaVenda extends Activity implements AdapterView.OnItemSelectedListener {

    private Spinner spn_cliente, spn_vendedor, spn_produto;
    private EditText dataVenda, quantidadeVenda, precoVenda;
    private long idVenda;
    private VendaDbHelper baseVenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nova_venda);

        VendaDbHelper vendadbhelper = new VendaDbHelper(getApplicationContext());
        baseVenda = new VendaDbHelper(getApplicationContext());

        spn_cliente = (Spinner) findViewById(R.id.spn_clienteVenda);
        loadSpinnerCliente();
        spn_produto = (Spinner) findViewById(R.id.spn_produtoVenda);
        loadSpinnerProduto();
        spn_vendedor = (Spinner) findViewById(R.id.spn_vendedorVenda);
        loadSpinnerVendedor();
        quantidadeVenda = (EditText) findViewById(R.id.quantidadeVenda);
        dataVenda = (EditText) findViewById(R.id.dataVenda);
        dataVenda.addTextChangedListener(Masks.mask(dataVenda, Masks.FORMAT_DATE));
        precoVenda = (EditText) findViewById(R.id.precoVenda);
        precoVenda.addTextChangedListener(MaskMoney.monetario(precoVenda));
    }

    private void loadSpinnerCliente() {
        ClienteDbHelper db = new ClienteDbHelper(getApplicationContext());
        ArrayList<Cliente> consultarCliente = db.consultarCliente();
        ArrayAdapter<Cliente> dataAdapter = new ArrayAdapter<Cliente>(this, android.R.layout.simple_list_item_1, consultarCliente);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn_cliente.setAdapter(dataAdapter);
    }

    private void loadSpinnerVendedor() {
        VendedorDbHelper db = new VendedorDbHelper(getApplicationContext());
        ArrayList<Vendedor> consultarVendedor = db.consultarVendedor();
        ArrayAdapter<Vendedor> dataAdapter = new ArrayAdapter<Vendedor>(this, android.R.layout.simple_list_item_1, consultarVendedor);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn_vendedor.setAdapter(dataAdapter);
    }

    private void loadSpinnerProduto() {
        ProdutoDbHelper db = new ProdutoDbHelper(getApplicationContext());
        ArrayList<Produto> consultarProduto = db.consultarProduto();
        ArrayAdapter<Produto> dataAdapter = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1, consultarProduto);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn_produto.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String cliente = spn_cliente.getSelectedItem().toString();
            String vendedor = spn_vendedor.getSelectedItem().toString();
            String produto = spn_produto.getSelectedItem().toString();
    }

    public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
    }

    public void btcadastrar_venda(View view) {
        Venda venda = new Venda(idVenda, spn_cliente.getSelectedItem().toString(),spn_produto.getSelectedItem().toString(),spn_vendedor.getSelectedItem().toString(),
                dataVenda.getText().toString(), quantidadeVenda.getText().toString(), precoVenda.getText().toString());

        baseVenda.cadastrarVenda(venda);
        spn_cliente.setOnItemSelectedListener(this);
        spn_produto.setOnItemSelectedListener(this);
        spn_vendedor.setOnItemSelectedListener(this);
        dataVenda.setText("");
        quantidadeVenda.setText("");
        precoVenda.setText("");


        Intent intent = new Intent(this, ViewVenda.class);
        startActivity(intent);

        Toast toast = Toast.makeText(this, "Venda cadastrada com sucesso!", Toast.LENGTH_LONG);
        toast.show();
        finish();
    }

}