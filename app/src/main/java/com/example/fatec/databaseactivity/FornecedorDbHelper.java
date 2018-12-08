package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.FornecedorRegister.FornecedorDb;
import java.util.ArrayList;

public class FornecedorDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 10;
  public static final String DATABASE_NAMEFOR = "Fornecedor.db";

  private static final String CREATEFOR = "create table " + FornecedorDb.TABLE_NAME +" ( "
          + FornecedorDb._ID +" integer primary key autoincrement, "
          + FornecedorDb.COLUMN_NOMEFOR + " text, "
          + FornecedorDb.COLUMN_TELEFONEFOR + " text)";

    private static final String DELETEFOR = "drop table if exists " + FornecedorDb.TABLE_NAME;

    public FornecedorDbHelper(Context context){
        super(context, DATABASE_NAMEFOR, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATEFOR);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEFOR);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean cadastrar_fornecedor(Fornecedor fornecedor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FornecedorDb.COLUMN_NOMEFOR, fornecedor.getNome());
        contentValues.put(FornecedorDb.COLUMN_TELEFONEFOR, fornecedor.getTelefone());
        long id = db.insert(FornecedorDb.TABLE_NAME, null, contentValues);
        fornecedor.setId(id);
        return true;
    }

    public ArrayList consultarFornecedor(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Fornecedor Order by nomeFor", null);
        while(cursor.moveToNext()){
            lista.add(new Fornecedor(cursor.getLong(cursor.getColumnIndex(FornecedorDb._ID)),
                        cursor.getString(cursor.getColumnIndex(FornecedorDb.COLUMN_NOMEFOR)),
                        cursor.getString(cursor.getColumnIndex(FornecedorDb.COLUMN_TELEFONEFOR))));
        }
        return lista;
    }

    public int contarFornecedores() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        Cursor cursortotalfornecedor = db.rawQuery("SELECT COUNT(" + FornecedorDb.COLUMN_NOMEFOR + ") total FROM " + FornecedorRegister.FornecedorDb.TABLE_NAME, null);
        if (cursortotalfornecedor.moveToFirst()) {
            total = cursortotalfornecedor.getInt(0);
        }
        cursortotalfornecedor.close();
        db.close();
        return total;
    }

}
