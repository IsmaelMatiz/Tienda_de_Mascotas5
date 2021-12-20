package com.example.reto2.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;

    public DBHelper(Context context) {
        super(context, "Reto3.db", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }
    //creacion de la tabla o base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE PRODUCTOS(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR," +
                "price VARCHAR," +
                "image BLOB," +
                "fav VARCHAR" +
                ")");
        db.execSQL("CREATE TABLE SERVICIOS(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR," +
                "price VARCHAR," +
                "image BLOB," +
                "fav VARCHAR" +
                ")");
        db.execSQL("CREATE TABLE SUCURSALES(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR," +
                "description VARCHAR," +
                "price VARCHAR," +
                "image BLOB," +
                "fav VARCHAR" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PRODUCTOS");
        db.execSQL("DROP TABLE IF EXISTS SUCURSALES");
        db.execSQL("DROP TABLE IF EXISTS SERVICIOS");
    }

    // Metodos Propios....
    public void insertData(String campo1, String campo2, String campo3, byte[] image, String table, String campo5){
        String sql = "INSERT INTO "+ table +" VALUES(null, ?, ?, ?, ?,?)";
        SQLiteStatement statement = sqLiteDatabase.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, campo1);
        statement.bindString(2, campo2);
        statement.bindString(3, campo3);
        statement.bindBlob(4, image);
        statement.bindString(5, campo5);
        statement.executeInsert();
    }

    public Cursor getData(String table){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+table, null);
        return cursor;
    }

    public Cursor getDataById(String table, String id){
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+table+" WHERE id = "+id, null);
        return cursor;
    }

    public void deleteDataById(String table, String id){
        sqLiteDatabase.execSQL("DELETE FROM "+table+" WHERE id = "+id);
    }

    public void updateProductoById(String table,String id,String campo1, String campo2, String campo3, byte[] image, String campo4){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", campo1);
        contentValues.put("description", campo2);
        contentValues.put("price", campo3);
        contentValues.put("image", image);
        contentValues.put("fav",campo4);
        sqLiteDatabase.update(table,contentValues,"id = ?",new String[]{id});
    }

}
