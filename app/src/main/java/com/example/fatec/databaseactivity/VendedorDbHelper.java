package com.example.fatec.databaseactivity;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.VendedorRegister.VendedorDb;
import java.util.ArrayList;

public class VendedorDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 10;
  public static final String DATABASE_NAMEVEN = "Vendedor.db";

  private static final String CREATEVEN = "create table " + VendedorDb.TABLE_NAME +" ( "
          + VendedorDb._ID +" integer primary key autoincrement, "
          + VendedorDb.COLUMN_NOMEVENDEDOR + " text) ";

    private static final String DELETEVEN = "drop table if exists " + VendedorDb.TABLE_NAME;

    public VendedorDbHelper(Context context){
        super(context, DATABASE_NAMEVEN, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATEVEN);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEVEN);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean cadastrar_vendedor(Vendedor vendedor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VendedorDb.COLUMN_NOMEVENDEDOR, vendedor.getNomeVendedor());
        long id = db.insert(VendedorDb.TABLE_NAME, null, contentValues);
        vendedor.setId(id);
        return true;
    }

    public ArrayList consultarVendedor(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Vendedor Order by NomeVen", null);
        while(cursor.moveToNext()){
            lista.add(new Vendedor(cursor.getLong(cursor.getColumnIndex(VendedorDb._ID)),
                      cursor.getString(cursor.getColumnIndex(VendedorDb.COLUMN_NOMEVENDEDOR))));
        }
        return lista;
    }

    public int contarVendedores() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;
        Cursor cursortotalvendedor = db.rawQuery("SELECT COUNT(" + VendedorDb.COLUMN_NOMEVENDEDOR + ") total FROM " + VendedorRegister.VendedorDb.TABLE_NAME, null);
        if (cursortotalvendedor.moveToFirst()) {
            total = cursortotalvendedor.getInt(0);
        }
        cursortotalvendedor.close();
        db.close();
        return total;
    }

}
