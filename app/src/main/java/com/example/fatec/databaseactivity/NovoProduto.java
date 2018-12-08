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

public class NovoProduto extends Activity implements AdapterView.OnItemSelectedListener {

    private ProdutoDbHelper basePro;
    private EditText nomePro, preco;
    private Spinner spn_categoria, spn_fornecedor;
    private long idPro;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo_produto);

        ProdutoDbHelper produtodbhelper = new ProdutoDbHelper(getApplicationContext());
        basePro = new ProdutoDbHelper(getApplicationContext());
        nomePro = (EditText) findViewById(R.id.idNomePro);
        spn_categoria = (Spinner) findViewById(R.id.spn_categoria);
        loadSpinnerCategoria();
        spn_fornecedor = (Spinner) findViewById(R.id.spn_fornecedor);
        loadSpinnerFornecedor();
        preco = (EditText) findViewById(R.id.idPreco);
        preco.addTextChangedListener(MaskMoney.monetario(preco));

    }

    private void loadSpinnerCategoria() {
        CategoriaDbHelper db = new CategoriaDbHelper(getApplicationContext());
        ArrayList<Categoria> consultarCategoria = db.consultarCategoria();
        ArrayAdapter<Categoria> dataAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, consultarCategoria);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn_categoria.setAdapter(dataAdapter);
    }

    private void loadSpinnerFornecedor() {
        FornecedorDbHelper db = new FornecedorDbHelper(getApplicationContext());
        ArrayList<Fornecedor> consultarFornecedor = db.consultarFornecedor();
        ArrayAdapter<Fornecedor> dataAdapter = new ArrayAdapter<Fornecedor>(this, android.R.layout.simple_list_item_1, consultarFornecedor);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spn_fornecedor.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoria = spn_categoria.getSelectedItem().toString();
        String fornecedor = spn_fornecedor.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void btCategoria(View v) {
        Intent intent = new Intent(getApplicationContext(), NovaCategoria.class);
        startActivity(intent);
    }

    public void btnovoFornecedor(View v) {
        Intent intent = new Intent(getApplicationContext(), NovoFornecedor.class);
        startActivity(intent);
    }

    public void btcadastrar_produto(View view) {
        Produto produto = new Produto(idPro, nomePro.getText().toString(), spn_categoria.getSelectedItem().toString(), spn_fornecedor.getSelectedItem().toString(), preco.getText().toString());
        basePro.salvar(produto);
        nomePro.setText("");
        spn_categoria.setOnItemSelectedListener(this);
        spn_fornecedor.setOnItemSelectedListener(this);
        preco.setText("");
        Intent main = new Intent(NovoProduto.this, ViewProduto.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main);

        Toast toast = Toast.makeText(this, "Produto cadastrado com sucesso!", Toast.LENGTH_LONG);
        toast.show();
    }

}
