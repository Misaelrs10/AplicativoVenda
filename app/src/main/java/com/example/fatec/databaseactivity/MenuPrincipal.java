package com.example.fatec.databaseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);
    }

    public void btVenda(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewVenda.class);
        startActivity(intent);
    }

    public void btCliente(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewCliente.class);
        startActivity(intent);
    }

    public void btFornecedor(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewFornecedor.class);
        startActivity(intent);
    }

    public void btProduto(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewProduto.class);
        startActivity(intent);
    }

    public void btVendedor(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewVendedor.class);
        startActivity(intent);
    }

    public void btRecebimento(View v) {
        Intent intent = new Intent(getApplicationContext(), ViewVendaCliente.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
