package com.example.fatec.databaseactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.fatec.databaseactivity.CategoriaRegister.CategoriaDb;
import java.util.ArrayList;

public class CategoriaDbHelper extends SQLiteOpenHelper{
  public static final int DATABASE_VERSION = 10;
  public static final String DATABASE_NAMECAT = "Categoria.db";


  private static final String CREATECAT = "create table " + CategoriaDb.TABLE_NAME +" ( "
          + CategoriaDb._ID +" integer primary key autoincrement, "
          + CategoriaDb.COLUMN_NOMECAT + " text) ";

    private static final String DELETECAT = "drop table if exists " + CategoriaDb.TABLE_NAME;

    public CategoriaDbHelper(Context context){
        super(context, DATABASE_NAMECAT, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATECAT);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETECAT);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean cadastrar_Categoria(Categoria Categoria) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CategoriaDb.COLUMN_NOMECAT, Categoria.getNome());
        long id = db.insert(CategoriaDb.TABLE_NAME, null, contentValues);
        Categoria.setId(id);
        return true;
    }

    public ArrayList consultarCategoria(){
        ArrayList lista = new ArrayList();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Categoria Order by nomeCat", null);
        while(cursor.moveToNext()){
            lista.add(new Categoria(cursor.getLong(cursor.getColumnIndex(CategoriaDb._ID)),
                        cursor.getString(cursor.getColumnIndex(CategoriaDb.COLUMN_NOMECAT))));
        }

        return lista;

    }

}
