package com.itproger.calcjava;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBase  extends SQLiteOpenHelper {

    private static final String db_name = "task_manager";
    private static final int db_version = 1;
    private static final String db_table = "task";
    private static final String db_column = "taskName";
    private static final String db_title = "taskTitle";
    private static final String db_id = "taskId";




    //создаем БД
    public DataBase(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }


    //создаем таблицы
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT);",db_table,db_id,db_column,db_title);
        db.execSQL(query);
    }

    //удаляет таблицу и повторно вызывает onCreate
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String query = String.format("DELETE TABLE IF EXISTS %s",db_table);
        db.execSQL(query);
        onCreate(db);

    }
//добавление в БД
    public void insertData(String task){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(db_column,task);

        db.insertWithOnConflict(db_table, null, values, SQLiteDatabase.CONFLICT_REPLACE );

    }


//удаление записи
    public void deleteData(String taskName){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(db_table, db_column + "= ?", new String[] {taskName} );
        db.close();
    }



    //считывание всех данных с БД
    public ArrayList<String> getAllTask(){

        ArrayList<String> allTask = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.query(db_table, new String[] {db_column}, null,null,null,null,null);
        while(cursor.moveToNext()){
            //вытаскиваем значение по индексу
            int index = cursor.getColumnIndex(db_column);
            allTask.add(cursor.getString(index));
        }
            cursor.close();
            db.close();
            return  allTask;
    }



}
