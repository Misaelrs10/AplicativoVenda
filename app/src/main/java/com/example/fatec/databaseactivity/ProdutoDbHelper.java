package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.ProdutoRegister.ProdutoDb;

import java.util.ArrayList;

public class ProdutoDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAMEPRO = "Produto.db";


  private static final String CREATEPRO = "create table " + ProdutoDb.TABLE_NAME +" ( "
          + ProdutoDb._ID +" integer primary key autoincrement, "
          + ProdutoDb.COLUMN_NOME + " text, "
          + ProdutoDb.COLUMN_CATEGORIA + " text, "
          + ProdutoDb.COLUMN_FORNECEDOR + " text, "
          + ProdutoDb.COLUMN_PRECO + " text )";

    private static final String DELETEPRO = "drop table if exists " + ProdutoDb.TABLE_NAME;


    public ProdutoDbHelper(Context context){
        super(context, DATABASE_NAMEPRO, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATEPRO);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEPRO);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean salvar(Produto produto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ProdutoDb.COLUMN_NOME, produto.getNome());
        contentValues.put(ProdutoDb.COLUMN_CATEGORIA, produto.getCategoria());
        contentValues.put(ProdutoDb.COLUMN_FORNECEDOR, produto.getFornecedor());
        contentValues.put(ProdutoDb.COLUMN_PRECO, produto.getPreco());

        long id = db.insert(ProdutoDb.TABLE_NAME, null, contentValues);
        produto.setId(id);
        return true;
    }

    public ArrayList consultarProduto(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Produto Order by nome", null);
        while(cursor.moveToNext()){
            lista.add(new Produto(cursor.getLong(cursor.getColumnIndex(ProdutoRegister.ProdutoDb._ID)),
                    cursor.getString(cursor.getColumnIndex(ProdutoRegister.ProdutoDb.COLUMN_NOME)),
                    cursor.getString(cursor.getColumnIndex(ProdutoRegister.ProdutoDb.COLUMN_CATEGORIA)),
                    cursor.getString(cursor.getColumnIndex(ProdutoRegister.ProdutoDb.COLUMN_FORNECEDOR)),
                    cursor.getString(cursor.getColumnIndex(ProdutoRegister.ProdutoDb.COLUMN_PRECO))));
        }
        return lista;
    }

    public int contarProdutos() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        Cursor cursortotalprodutos = db.rawQuery("SELECT COUNT(" + ProdutoDb.COLUMN_NOME + ") total FROM " + ProdutoRegister.ProdutoDb.TABLE_NAME, null);
        if (cursortotalprodutos.moveToFirst()) {
            total = cursortotalprodutos.getInt(0);
        }
        cursortotalprodutos.close();
        db.close();
        return total;
    }

}
