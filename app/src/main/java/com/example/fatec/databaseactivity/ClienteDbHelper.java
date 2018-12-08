package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.ClienteRegister.ClienteDb;
import java.util.ArrayList;

public class ClienteDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 10;
  public static final String DATABASE_NAMECLI = "Cliente.db";

  private static final String CREATECLI = "create table " + ClienteDb.TABLE_NAME +" ( "
          +ClienteDb._ID +" integer primary key autoincrement, "
          +ClienteDb.COLUMN_NOME + " text, "
          +ClienteDb.COLUMN_CPF + " text, "
          +ClienteDb.COLUMN_ENDERECO + " text, "
          +ClienteDb.COLUMN_TELEFONE + " text)";

    private static final String DELETECLI = "drop table if exists " + ClienteDb.TABLE_NAME;

    public ClienteDbHelper(Context context){
        super(context, DATABASE_NAMECLI, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATECLI);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETECLI);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean salvarCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClienteDb.COLUMN_NOME, cliente.getNome());
        contentValues.put(ClienteDb.COLUMN_CPF, cliente.getCpf());
        contentValues.put(ClienteDb.COLUMN_ENDERECO, cliente.getEndereco());
        contentValues.put(ClienteDb.COLUMN_TELEFONE, cliente.getTelefone());
        long idCli = db.insert(ClienteDb.TABLE_NAME, null, contentValues);
        cliente.setIdCli(idCli);
        return true;
    }

    public ArrayList consultarCliente(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Cliente Order by nome", null);
        while(cursor.moveToNext()){
            lista.add(new Cliente(cursor.getLong(cursor.getColumnIndex(ClienteDb._ID)),
                      cursor.getString(cursor.getColumnIndex(ClienteDb.COLUMN_NOME)),
                      cursor.getString(cursor.getColumnIndex(ClienteDb.COLUMN_CPF)),
                      cursor.getString(cursor.getColumnIndex(ClienteDb.COLUMN_ENDERECO)),
                      cursor.getString(cursor.getColumnIndex(ClienteDb.COLUMN_TELEFONE))));
        }
        return lista;
    }

    public int contarClientes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        Cursor cursortotal = db.rawQuery("SELECT COUNT(" + ClienteDb.COLUMN_NOME + ") total FROM " + ClienteRegister.ClienteDb.TABLE_NAME, null);
        if (cursortotal.moveToFirst()) {
            total = cursortotal.getInt(0);
        }
        cursortotal.close();
        db.close();
        return total;
    }
}