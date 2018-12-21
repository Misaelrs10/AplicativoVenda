package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.RecebimentoRegister.RecebimentoDb;
import java.util.ArrayList;

public class RecebimentoDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 1;
  public static final String DATABASE_NAMEREC = "Recebimento.db";

  private static final String CREATEREC = "create table " + RecebimentoDb.TABLE_NAME +" ( "
          + RecebimentoDb._ID +" integer primary key autoincrement, "
          + RecebimentoDb.COLUMN_CLIENTE + " text, "
          + RecebimentoDb.COLUMN_VALOR + " text, "
          + RecebimentoDb.COLUMN_DATA + " text)";

    private static final String DELETEREC = "drop table if exists " + RecebimentoDb.TABLE_NAME;


    public RecebimentoDbHelper(Context context){
        super(context, DATABASE_NAMEREC, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATEREC);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEREC);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean cadastrarRecebimento(Recebimento Recebimento) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecebimentoDb.COLUMN_CLIENTE, Recebimento.getCliente());
        contentValues.put(RecebimentoDb.COLUMN_VALOR, Recebimento.getValor());
        contentValues.put(RecebimentoDb.COLUMN_DATA, Recebimento.getData());
        long id = db.insert(RecebimentoDb.TABLE_NAME, null, contentValues);
        Recebimento.setId(id);
        return true;
    }

    public ArrayList consultarRecebimento(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RecebimentoDb.TABLE_NAME, null);
        while(cursor.moveToNext()){
            lista.add(new Recebimento(cursor.getLong(cursor.getColumnIndex(RecebimentoDb._ID)),
                    cursor.getString(cursor.getColumnIndex(RecebimentoDb.COLUMN_CLIENTE)),
                    cursor.getString(cursor.getColumnIndex(RecebimentoDb.COLUMN_VALOR)),
                    cursor.getString(cursor.getColumnIndex(RecebimentoDb.COLUMN_DATA))));
        }
        return lista;
    }

    public Cursor consultarCursorRecebimento(Cliente cliente){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + RecebimentoRegister.RecebimentoDb.TABLE_NAME + " where " +
                RecebimentoRegister.RecebimentoDb.COLUMN_CLIENTE + " = '" + cliente.toString() + "'" + "Order by _id desc", null);
        return cursor;
    }

    public double somarRecebimento(Cliente cliente) {
        SQLiteDatabase db = this.getReadableDatabase();
        double total=0.0;
        Cursor cursorsoma = db.rawQuery("SELECT SUM(" + RecebimentoDb.COLUMN_VALOR+ ") total FROM "
                + RecebimentoRegister.RecebimentoDb.TABLE_NAME + " where " +
                RecebimentoDb.COLUMN_CLIENTE + " = '" + cliente.toString() + "'", null);
        if (cursorsoma.moveToFirst()) {
            total = cursorsoma.getDouble(0);
        }
        cursorsoma.close();
        db.close();
        return total;
    }
}
