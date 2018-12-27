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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class EditarProduto extends Activity implements AdapterView.OnItemSelectedListener{

    private ProdutoDbHelper basePro;
    private EditText nomeProdutoAlterar, precoProdutoAlterar;
    private EditText id_produto_a;
    Spinner categoriaProdutoAlterar, fornecedorProdutoAlterar;
    Button bt_alterarProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_produto);

        id_produto_a = (EditText) findViewById(R.id.idProduto_a);
        nomeProdutoAlterar = (EditText) findViewById(R.id.nomeProdutoAlterar);
        categoriaProdutoAlterar = (Spinner) findViewById(R.id.categoriaProdutoAlterar);
        loadSpinnerCategoria();
        fornecedorProdutoAlterar = (Spinner) findViewById(R.id.fornecedorProdutoAlterar);
        loadSpinnerFornecedor();
        precoProdutoAlterar = (EditText) findViewById(R.id.precoProdutoAlterar);
        precoProdutoAlterar.addTextChangedListener(new MaskMoney.MascaraMonetaria(precoProdutoAlterar));
        bt_alterarProduto = (Button) findViewById(R.id.bt_alterarProduto);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle params = intent.getExtras();
            if (params != null) {
                String idProduto = params.getString("id_produto");
                buscarproduto(idProduto);
            }
        }

        bt_alterarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idProduto = nomeProdutoAlterar.getText().toString();
                int res = alterarproduto(idProduto);
                if (res > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Alterado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), ViewProduto.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error ao alterar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadSpinnerCategoria() {
        CategoriaDbHelper db = new CategoriaDbHelper(getApplicationContext());
        ArrayList<Categoria> consultarCategoria = db.consultarCategoria();
        ArrayAdapter<Categoria> dataAdapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_list_item_1, consultarCategoria);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        categoriaProdutoAlterar.setAdapter(dataAdapter);
    }

    private void loadSpinnerFornecedor() {
        FornecedorDbHelper db = new FornecedorDbHelper(getApplicationContext());
        ArrayList<Fornecedor> consultarFornecedor = db.consultarFornecedor();
        ArrayAdapter<Fornecedor> dataAdapter = new ArrayAdapter<Fornecedor>(this, android.R.layout.simple_list_item_1, consultarFornecedor);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        fornecedorProdutoAlterar.setAdapter(dataAdapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoria = categoriaProdutoAlterar.getSelectedItem().toString();
        String fornecedor = fornecedorProdutoAlterar.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    protected int alterarproduto(String idProduto) {
        SQLiteDatabase db = openOrCreateDatabase("Produto.db", Context.MODE_PRIVATE, null);

        String nome = nomeProdutoAlterar.getText().toString();
        String categoria = categoriaProdutoAlterar.getSelectedItem().toString();
        String fornecedor = fornecedorProdutoAlterar.getSelectedItem().toString();
        String preco = precoProdutoAlterar.getText().toString();

        ContentValues ctv = new ContentValues();
        ctv.put("nome", nome);
        ctv.put("categoria", categoria);
        ctv.put("fornecedor", fornecedor);
        ctv.put("preco", preco);
        int res = db.update("produto", ctv, "nome=?", new String[]{idProduto});
        db.close();
        return res;
    }

    private void buscarproduto(String idProduto) {
        SQLiteDatabase db = openOrCreateDatabase("Produto.db", Context.MODE_PRIVATE, null);

        String sql = "SELECT * from Produto where _id=?";

        Cursor c = (SQLiteCursor) db.rawQuery(sql, new String[]{idProduto});

        if (c.moveToFirst()) {
            String id = c.getString(c.getColumnIndex("_id"));
            String nome = c.getString(c.getColumnIndex("nome"));
            String categoria = c.getString(c.getColumnIndex("categoria"));
            String fornecedor = c.getString(c.getColumnIndex("fornecedor"));
            String preco = c.getString(c.getColumnIndex("preco"));

            id_produto_a.setText(id.toString());
            nomeProdutoAlterar.setText(nome.toString());
            categoriaProdutoAlterar.setOnItemSelectedListener(onItemSelectedListener(categoria));
            fornecedorProdutoAlterar.setOnItemSelectedListener(onItemSelectedListener(fornecedor));
            precoProdutoAlterar.setText(preco.toString());
        }
        c.close();
        db.close();
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener(String categoria) {
        return null;
    }

    public void btCategoria(View v) {
        Intent intent = new Intent(getApplicationContext(), NovaCategoria.class);
        startActivity(intent);
    }

}
