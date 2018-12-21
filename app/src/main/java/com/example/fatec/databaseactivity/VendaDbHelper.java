package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.VendaRegister.VendaDb;

import java.text.Format;
import java.util.ArrayList;

public class VendaDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAMEVEN = "Venda.db";

  private static final String CREATEVEN = "create table " + VendaDb.TABLE_NAME +" ( "
          + VendaDb._ID +" integer primary key autoincrement, "
          + VendaDb.COLUMN_CLIENTEVENDA + " text, "
          + VendaDb.COLUMN_PRODUTOVENDA + " text, "
          + VendaDb.COLUMN_VENDEDORVENDA + " text, "
          + VendaDb.COLUMN_DATAVENDA + " text, "
          + VendaDb.COLUMN_QUANTIDADEVENDA + " text, "
          + VendaDb.COLUMN_PRECOVENDA + " text) ";

    private static final String DELETEVEN = "drop table if exists " + VendaDb.TABLE_NAME;

    public VendaDbHelper(Context context){
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

    public boolean cadastrarVenda(Venda venda) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VendaDb.COLUMN_CLIENTEVENDA, venda.getClienteVenda());
        contentValues.put(VendaDb.COLUMN_PRODUTOVENDA, venda.getProdutoVenda());
        contentValues.put(VendaDb.COLUMN_VENDEDORVENDA, venda.getVendedorVenda());
        contentValues.put(VendaDb.COLUMN_DATAVENDA, venda.getDataVenda());
        contentValues.put(VendaDb.COLUMN_QUANTIDADEVENDA, venda.getQuantidadeVenda());
        contentValues.put(VendaDb.COLUMN_PRECOVENDA, venda.getPrecoVenda());
        long id = db.insert(VendaDb.TABLE_NAME, null, contentValues);
        venda.setId(id);
        return true;
    }

    public ArrayList consultarVenda(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + VendaDb.TABLE_NAME, null);
        while(cursor.moveToNext()){
            lista.add(new Venda(cursor.getLong(cursor.getColumnIndex(VendaDb._ID)),
                      cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_CLIENTEVENDA)),
                    cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_PRODUTOVENDA)),
                    cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_VENDEDORVENDA)),
                    cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_DATAVENDA)),
                    cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_QUANTIDADEVENDA)),
                    cursor.getString(cursor.getColumnIndex(VendaDb.COLUMN_PRECOVENDA))));
        }
        return lista;
    }

    public Cursor consultarCursorVenda(Cliente cliente){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + VendaDb.TABLE_NAME + " where " +
                VendaDb.COLUMN_CLIENTEVENDA + " = '" + cliente.toString() + "'" + " Order by _id desc", null);
        return cursor;
    }

    public double somarVendas(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total=0.0;
        Cursor cursorsoma = db.rawQuery("SELECT SUM(" +VendaDb.COLUMN_PRECOVENDA + ") total FROM "
                + VendaRegister.VendaDb.TABLE_NAME + " where " + VendaDb.COLUMN_CLIENTEVENDA + " = '" + cliente.toString() + "'", null);
        if (cursorsoma.moveToFirst()) {
            total = cursorsoma.getDouble(0);
        }
        cursorsoma.close();
        db.close();
        return total;
    }

    public double somarTodasVendas() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalVendas= 0.0;
        Cursor cursorsomatotal = db.rawQuery("SELECT SUM(" + VendaDb.COLUMN_PRECOVENDA + ") total FROM "
                + VendaRegister.VendaDb.TABLE_NAME, null);
        if (cursorsomatotal.moveToFirst()) {
            totalVendas = cursorsomatotal.getDouble(0);
        }
        cursorsomatotal.close();
        db.close();
        return totalVendas;
    }

    public double aPagar(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total=0.0;
        Cursor cursorsoma = db.rawQuery("SELECT SUM(" +VendaDb.COLUMN_PRECOVENDA+ ") total FROM "
                + VendaRegister.VendaDb.TABLE_NAME + " where " + VendaDb.COLUMN_CLIENTEVENDA + " = '" + cliente.toString() + "'", null);
        if (cursorsoma.moveToFirst()) {
            total = cursorsoma.getDouble(0);
        }
        cursorsoma.close();
        db.close();
        return total;
    }

}
