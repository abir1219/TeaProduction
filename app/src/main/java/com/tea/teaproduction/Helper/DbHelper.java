package com.tea.teaproduction.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TeaProduction.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

// TODO Auto-generated method stub
        db.execSQL(
                "create table category " +
                        "(GeadeCategoryId text PRIMARY KEY, GeadeCategoryName text)"
        );

        db.execSQL(
                "create table shift " +
                        "(ShiftId text PRIMARY KEY, ShiftName text)"
        );

        db.execSQL(
                "create table sector " +
                        "(SectorId text PRIMARY KEY, SectorName text)"
        );

        db.execSQL(
                "create table employee " +
                        "(EmpId text PRIMARY KEY,EmpCode text, EmpFullName text)"
        );

        db.execSQL(
                "create table consignment " +
                        "(EmpId text,EmpCode text,Category text,Shift text, Sector text,Weight text,Date text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS shift");
        db.execSQL("DROP TABLE IF EXISTS sector");
        db.execSQL("DROP TABLE IF EXISTS employee");
        db.execSQL("DROP TABLE IF EXISTS consignment");
        onCreate(db);
    }


    public boolean insertEmployee(String empId, String empCode, String empName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EmpId", empId);
        cv.put("EmpCode", empCode);
        cv.put("EmpFullName", empName);

        long result = db.insert("employee", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean insertCategory(String GeadeCategoryId, String GeadeCategoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("GeadeCategoryId", GeadeCategoryId);
        cv.put("GeadeCategoryName", GeadeCategoryName);

        long result = db.insert("category", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public boolean insertShift(String ShiftId, String ShiftName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ShiftId", ShiftId);
        cv.put("ShiftName", ShiftName);

        long result = db.insert("shift", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertSector(String SectorId, String SectorName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("SectorId", SectorId);
        cv.put("SectorName", SectorName);

        long result = db.insert("sector", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertConsignment(String emp_id, String emp_code, String category, String shift, String sector,
                                     String weight, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("EmpId", emp_id);
        cv.put("EmpCode", emp_code);
        cv.put("Category", category);
        cv.put("Shift", shift);
        cv.put("Sector", sector);
        cv.put("Weight", weight);
        cv.put("Date", date);

        long result = db.insert("consignment", null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getConsignmentData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from consignment", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getCategoryData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from category", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getShiftData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from shift", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getSectorData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from sector", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getAllEmployee() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from employee", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getSingleEmployee(String empCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from employee where EmpCode = ?", new String[]{empCode});
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }


}
