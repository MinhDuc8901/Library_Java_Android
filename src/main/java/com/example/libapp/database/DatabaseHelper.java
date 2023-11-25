package com.example.libapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.libapp.model.SchoolSupply;
import com.example.libapp.model.TypeSchoolSupply;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "SchoolSupply", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query1 = "Create table schoolSupply (id integer primary key autoincrement,name text, type_id integer, ages text,image text,price double)";
        String query2 = "Create table typeSchoolSupply (id integer primary key autoincrement,name text,description text)";
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query1 = "drop table if exists schoolSupply";
        String query2 = "drop table if exists typeSchoolSupply";
        sqLiteDatabase.execSQL(query1);
        sqLiteDatabase.execSQL(query2);
        onCreate(sqLiteDatabase);
    }

    public boolean addSchoolSupply(SchoolSupply item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",item.getName());
        values.put("type_id",item.getType_id());
        values.put("ages",item.getAges());
        values.put("image",item.getImage());
        values.put("price",item.getPrice());
        db.insert("schoolSupply",null,values);
        db.close();
        return true;
    }

    public boolean addTypeSchoolSupply(TypeSchoolSupply item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",item.getName());
        values.put("description",item.getDesc());
        db.insert("typeSchoolSupply",null,values);
        db.close();
        return true;
    }

    public List<SchoolSupply> getListSchoolSupply(){
        List<SchoolSupply> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =db.rawQuery("Select * from schoolSupply",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            SchoolSupply item = new SchoolSupply(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4),cursor.getDouble(5));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }

    public List<TypeSchoolSupply> getListTypeSchoolSupply(){
        List<TypeSchoolSupply> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from typeSchoolSupply", null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Log.w("hello1",cursor.getString(0)+"");
            TypeSchoolSupply item = new TypeSchoolSupply(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }

    public boolean deleteSchoolSupplyById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("schoolSupply","id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public boolean deleteTypeSchoolSupplyById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("typeSchoolSupply","id = ?",new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public boolean updateSchoolSupplyById(SchoolSupply schoolSupply){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",schoolSupply.getName());
        contentValues.put("type_id",schoolSupply.getType_id());
        contentValues.put("ages",schoolSupply.getAges());
        contentValues.put("image",schoolSupply.getImage());
        contentValues.put("price",schoolSupply.getPrice());
        db.update("schoolSupply",contentValues,"id = ? ", new String[]{String.valueOf(schoolSupply.getId())});
        db.close();
        return true;
    }

    public boolean updateTypeSupply(TypeSchoolSupply typeSchoolSupply){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",typeSchoolSupply.getName());
        values.put("description",typeSchoolSupply.getDesc());
        db.update("typeSchoolSupply",values,"id = ? ",new String[]{String.valueOf(typeSchoolSupply.getId())});
        db.close();
        return true;
    }

    public List<SchoolSupply> getSchoolSupplyByTypeSchool(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        List<SchoolSupply> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from schoolSupply where type_id = "+ String.valueOf(id), null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            SchoolSupply item = new SchoolSupply(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),
                    cursor.getString(3),cursor.getString(4),cursor.getDouble(5));
            list.add(item);
            cursor.moveToNext();
        }
        return list;
    }

}
