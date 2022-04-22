package com.tea.teaproduction.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
                        "(GeadeCategoryId TEXT PRIMARY KEY, GeadeCategoryName TEXT)"
        );

        db.execSQL(
                "create table shift " +
                        "(ShiftId TEXT PRIMARY KEY, ShiftName TEXT)"
        );

        db.execSQL(
                "create table sector " +
                        "(SectorId TEXT PRIMARY KEY, SectorName TEXT)"
        );

        db.execSQL(
                "create table employee " +
                        "(EmpId TEXT PRIMARY KEY,EmpCode TEXT, EmpFullName TEXT)"
        );

        db.execSQL(
                "create table consignment " +
                        "(EmpId TEXT,EmpCode TEXT,Category TEXT,Shift TEXT, Sector TEXT,Weight TEXT,Date TEXT)"
        );

        db.execSQL(
                "create table company " +
                        "(CompanyId TEXT PRIMARY KEY,CompanyName TEXT)"
        );

        db.execSQL(
                "create table itemCategory " +
                        "(ItemCategoryId TEXT PRIMARY KEY,CategoryName TEXT)"
        );

        db.execSQL(
                "create table itemList " +
                        "(ItemId TEXT PRIMARY KEY,ItemName TEXT)"
        );

        db.execSQL(
                "create table stock " +
                        "(ItemId TEXT ,ItemCatID TEXT,CompanyID TEXT,SGST TEXT,CGST TEXT,IGST TEXT,PurchaseDate TEXT," +
                        "REMARK TEXT,TotalItem TEXT,InvoiceNo TEXT,InvoiceDate TEXT,UnitPrice TEXT,CustomPrice1 TEXT," +
                        "CustomValue1 TEXT,CustomPrice2 TEXT,CustomValue2 TEXT,CustomPrice3 TEXT,CustomValue3 TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS category");
        db.execSQL("DROP TABLE IF EXISTS shift");
        db.execSQL("DROP TABLE IF EXISTS sector");
        db.execSQL("DROP TABLE IF EXISTS employee");
        db.execSQL("DROP TABLE IF EXISTS consignment");
        db.execSQL("DROP TABLE IF EXISTS company");
        db.execSQL("DROP TABLE IF EXISTS itemCategory");
        db.execSQL("DROP TABLE IF EXISTS itemList");
        db.execSQL("DROP TABLE IF EXISTS stock");
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
        Log.d("GeadeCategoryId",GeadeCategoryId);
        Log.d("GeadeCategoryName",GeadeCategoryName);
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

        db.close();
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
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertCompany(String CompanyId, String CompanyName) {
        Log.d("DB_COMPANY_ID",CompanyId);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CompanyId", CompanyId);
        cv.put("CompanyName", CompanyName);

        long result = db.insert("company", null, cv);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertItemCategory(String ItemCategoryId, String CategoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ItemCategoryId", ItemCategoryId);
        cv.put("CategoryName", CategoryName);

        long result = db.insert("itemCategory", null, cv);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean insertItemList(String ItemId, String ItemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ItemId", ItemId);
        cv.put("ItemName", ItemName);

        long result = db.insert("itemList", null, cv);
        db.close();
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
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteConsignmentData(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE consignment");

        db.execSQL(
                "create table consignment " +
                        "(EmpId TEXT,EmpCode TEXT,Category TEXT,Shift TEXT, Sector TEXT,Weight TEXT,Date TEXT)"
        );
    }

    public boolean addStock(String ItemId,String ItemCatID ,String CompanyID ,String SGST ,String CGST ,String IGST ,
                            String PurchaseDate ,String REMARK ,String TotalItem ,String InvoiceNo ,String InvoiceDate ,
                            String UnitPrice ,String CustomPrice1,String CustomValue1,String CustomPrice2 ,
                            String CustomValue2 ,String CustomPrice3 ,String CustomValue3 ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ItemId",ItemId);
        contentValues.put("ItemCatID",ItemCatID);
        contentValues.put("CompanyID",CompanyID);
        contentValues.put("SGST",SGST);
        contentValues.put("CGST",CGST);
        contentValues.put("IGST",IGST);
        contentValues.put("PurchaseDate",PurchaseDate);
        contentValues.put("REMARK",REMARK);
        contentValues.put("TotalItem",TotalItem);
        contentValues.put("InvoiceNo",InvoiceNo);
        contentValues.put("InvoiceDate",InvoiceDate);
        contentValues.put("UnitPrice",UnitPrice);
        contentValues.put("CustomPrice1",CustomPrice1);
        contentValues.put("CustomValue1",CustomValue1);
        contentValues.put("CustomPrice2",CustomPrice2);
        contentValues.put("CustomValue2",CustomValue2);
        contentValues.put("CustomPrice3",CustomPrice3);
        contentValues.put("CustomValue3",CustomValue3);


        long result = db.insert("stock",null,contentValues);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getStockDetails(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from stock", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public void deleteStockData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE stock");

        db.execSQL(
                "create table stock " +
                        "(ItemId TEXT ,ItemCatID TEXT,CompanyID TEXT,SGST TEXT,CGST TEXT,IGST TEXT,PurchaseDate TEXT," +
                        "REMARK TEXT,TotalItem TEXT,InvoiceNo TEXT,InvoiceDate TEXT,UnitPrice TEXT,CustomPrice1 TEXT," +
                        "CustomValue1 TEXT,CustomPrice2 TEXT,CustomValue2 TEXT,CustomPrice3 TEXT,CustomValue3 TEXT)"
        );
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

    public Cursor getCompanyList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from company", null);
        
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getItemCategory() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from itemCategory", null);
        
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public Cursor getItemList() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from itemList", null);
        
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
