package com.tea.teaproduction.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.tea.teaproduction.ui.StockManagement.Model.StockModel;

import java.util.ArrayList;
import java.util.List;

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

        db.execSQL("create table user "+
                "(userId text PRIMARY KEY,role text,username text, password text)");

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

        db.execSQL("CREATE TABLE stock (" +
                        " StockId INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                        " ItemId text DEFAULT NULL," +
                        " ItemCategoryId text DEFAULT NULL," +
                        " CompanyId text DEFAULT NULL," +
                        " ItemRate text DEFAULT NULL," +
                        " SGST text DEFAULT NULL," +
                        " CGST text DEFAULT NULL," +
                        " IGST text DEFAULT NULL," +
                        " ItemTotal text DEFAULT NULL," +
                        " PurchaseDate text DEFAULT NULL," +
                        " PurchaseRemark text," +
                        " StockIn text DEFAULT 0," +
                        " DispatchDate text DEFAULT NULL," +
                        " DispatchRemark text," +
                        " StockOut text DEFAULT 0," +
                        " InvoiceNumber text DEFAULT NULL," +
                        " InvoiceDate text DEFAULT NULL," +
                        " UnitPrice text DEFAULT NULL," +
                        " CustomPrice1 text DEFAULT NULL," +
                        " CustompriceValue1 text DEFAULT NULL," +
                        " CustomPrice2 text DEFAULT NULL," +
                        " CustompriceValue2 text DEFAULT NULL," +
                        " CustomPrice3 text DEFAULT NULL," +
                        " CustompriceValue3 text DEFAULT NULL," +
                        " Available CHECK( Available IN ('Yes','No')) DEFAULT 'Yes'," +
                        " CreatedOn timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        " ModifiedOn TIMESTAMP DEFAULT CURRENT_TIMESTAMP) "




                /*"create table stock " +
                        "(StockId INT PRIMARY KEY AUTOINCREMENT,ItemId TEXT ,ItemCatID TEXT,CompanyID TEXT,SGST TEXT,CGST TEXT,IGST TEXT,PurchaseDate TEXT," +
                        "REMARK TEXT,TotalItem TEXT,InvoiceNo TEXT,InvoiceDate TEXT,UnitPrice TEXT,CustomPrice1 TEXT," +
                        "CustomValue1 TEXT,CustomPrice2 TEXT,CustomValue2 TEXT,CustomPrice3 TEXT,CustomValue3 TEXT)"*/
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
        db.execSQL("DROP TABLE IF EXISTS user");
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
        Log.d("GeadeCategoryId", GeadeCategoryId);
        Log.d("GeadeCategoryName", GeadeCategoryName);
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

    public boolean insertUser(String userId,String role,String username,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userId",userId);
        contentValues.put("role",role);
        contentValues.put("username",username);
        contentValues.put("password",password);

        long result = db.insert("user", null, contentValues);

        db.close();
        System.out.println("RESULT: "+result);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    //userId text PRIMARY KEY,role text,username text, password text

    public Cursor login(String username,String password){
        System.out.println("Username: "+username+", password: "+password);
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from user where username = "+"'"+username+"'"+" and password = "+"'"+password+"'";
        System.out.println("LOGIN_SQL: "+sql);
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount() > 0)
            return cursor;
        else
            return null;

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
        Log.d("DB_COMPANY_ID", CompanyId);
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

    public void deleteConsignmentData() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE consignment");

        db.execSQL(
                "create table consignment " +
                        "(EmpId TEXT,EmpCode TEXT,Category TEXT,Shift TEXT, Sector TEXT,Weight TEXT,Date TEXT)"
        );
    }

    public boolean addStock(String ItemId, String ItemCatID, String CompanyID, String SGST, String CGST, String IGST,
                            String PurchaseDate, String REMARK, String InvoiceNo, String InvoiceDate,
                            String UnitPrice, String CustomPrice1, String CustomValue1, String CustomPrice2,
                            String CustomValue2, String CustomPrice3, String CustomValue3, String StockIn) {


        Double rate = Double.parseDouble(UnitPrice) * Double.parseDouble(StockIn);
        Double ItemTotal = 0.0;
        int totalGST = 0;
        if (IGST.equals("0")) {
            totalGST = Integer.parseInt(IGST);
            ItemTotal = (rate + (rate * totalGST) / 100);
        } else if (!CGST.equals("0") && !SGST.equals("0")) {
            totalGST = Integer.parseInt(CGST) + Integer.parseInt(SGST);
            Log.d("totalGST: ", "" + totalGST);
            ItemTotal = (rate + (rate * totalGST) / 100);
            Log.d("ItemTotal: ", "" + ItemTotal);
        }

        //Double ItemTotal = (rate + (rate * totalGST) / 100);


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ItemId", ItemId);
        contentValues.put("ItemCategoryId", ItemCatID);
        contentValues.put("CompanyId", CompanyID);
        contentValues.put("SGST", SGST);
        contentValues.put("CGST", CGST);
        contentValues.put("IGST", IGST);
        contentValues.put("PurchaseDate", PurchaseDate);
        contentValues.put("StockIn", StockIn);
        contentValues.put("PurchaseRemark", REMARK);
        contentValues.put("InvoiceNumber", InvoiceNo);
        contentValues.put("InvoiceDate", InvoiceDate);
        contentValues.put("UnitPrice", UnitPrice);
        contentValues.put("CustomPrice1", CustomPrice1);
        contentValues.put("CustompriceValue1", CustomValue1);
        contentValues.put("CustomPrice2", CustomPrice2);
        contentValues.put("CustompriceValue2", CustomValue2);
        contentValues.put("CustomPrice3", CustomPrice3);
        contentValues.put("CustompriceValue3", CustomValue3);
        contentValues.put("ItemRate", String.format("%.2f", rate));
        contentValues.put("ItemTotal", String.format("%.2f", ItemTotal));


        long result = db.insert("stock", null, contentValues);
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getStockDetails() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from stock", null);
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }

    public void deleteStockData() {
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

    private int availableStock(String itemId) {
        System.out.println("Item_id: " + itemId);
        int availableStock = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from stock where ItemId = ? AND Available = ? ORDER BY StockId ASC",
                new String[]{itemId, "Yes"});
        System.out.println("COUNT_RES: " + cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Log.d("StockIn", cursor.getString(11));
                availableStock += Integer.parseInt(cursor.getString(11));
            }
            db.close();
        }
        return availableStock;
    }

    public boolean stockDispatch(String Item_id, String total_out_stock, String despatch_date, String DispatchRemark) {
        int demandDispatchCount = Integer.parseInt(total_out_stock);
        System.out.println("demandDispatchCount: " + demandDispatchCount);


        int availableStock = availableStock(Item_id);
        boolean status = false;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from stock where ItemId = ? AND Available = ? ORDER BY StockId ASC",
                new String[]{Item_id, "Yes"});

        if (cursor != null && cursor.getCount() > 0) {
            Log.d("CURSOR_OK", "YES");
            System.out.println("availableStock: " + availableStock);
            if (demandDispatchCount <= availableStock) {
                System.out.println("Cursor_count: " + cursor.getCount());
                System.out.println("cursor.moveToNext: " + cursor.moveToNext());
                int stockCount = 0;

                do {
                    System.out.println("stockId: " + cursor.getString(0));
                    String stockId = cursor.getString(0);
                    int result = 0;
                    ContentValues contentValues = new ContentValues();
                    if (demandDispatchCount > Integer.parseInt(cursor.getString(11))) {
                        stockCount += Integer.parseInt(cursor.getString(11));        //8
                        result = demandDispatchCount - Integer.parseInt(cursor.getString(11));  //2
                        System.out.println("RESULT_RES: " + result);
                        if (result != 0) {
                            if (stockCount <= demandDispatchCount) {
                                SQLiteDatabase database = this.getWritableDatabase();
                                String newStockIn = "0";
                                String newStockOut = cursor.getString(11);
                                String sql = "UPDATE stock SET StockIn = " + "'" + newStockIn + "'" + ",StockOut = " + "'" + newStockOut + "'" +
                                        ",Available = 'No',DispatchDate=" + "'" + despatch_date + "'" + "," +
                                        "DispatchRemark=" + "'" + DispatchRemark + "'" + " WHERE StockId = " + "'" + stockId + "'";
                                Log.d("SQL_RES", sql);
                                database.execSQL(sql);
                            } else {
                                SQLiteDatabase database = this.getWritableDatabase();
                                String newStockIn = String.valueOf(stockCount - demandDispatchCount);
                                String newStockOut = String.valueOf(result - 1);
                                String sql = "UPDATE stock SET StockIn = " + "'" + newStockIn + "'" + ",StockOut = " + "'" + newStockOut + "'" +
                                        ",Available = 'Yes',DispatchDate=" + "'" + despatch_date + "'" + "," +
                                        "DispatchRemark=" + "'" + DispatchRemark + "'" + " WHERE StockId = " + "'" + stockId + "'";
                                Log.d("SQL_RES", sql);
                                database.execSQL(sql);
                            }
                        } else {
                            /*SQLiteDatabase database = this.getWritableDatabase();
                            String newStockIn = "0";
                            String newStockOut = cursor.getString(11);
                            String sql = "UPDATE stock SET StockIn = " + "'" + newStockIn + "'" + ",StockOut = " + "'" + newStockOut + "'" +
                                    ",Available = 'No',DispatchDate=" + "'" + despatch_date + "'" + "," +
                                    "DispatchRemark=" + "'" + DispatchRemark + "'" + " WHERE StockId = " + "'" + stockId + "'";
                            Log.d("SQL_RES", sql);
                            database.execSQL(sql);*/
                            break;
                        }
                    } else {
                        /*
                        *//*contentValues.put("StockIn", newStockIn);
                        contentValues.put("Available", "No");
                        contentValues.put("StockOut", newStockOut);
                        contentValues.put("DispatchDate", despatch_date);
                        contentValues.put("DispatchRemark", DispatchRemark);*//*
                        //database.update("stock", contentValues, "StockId = ?", new String[]{stockId});*/
                        String newStockIn = String.valueOf(Integer.parseInt(cursor.getString(11)) - demandDispatchCount);
                        String newStockOut = String.valueOf(demandDispatchCount);
                        SQLiteDatabase database = this.getWritableDatabase();
                        String sql = "UPDATE stock SET StockIn = " + "'" + newStockIn + "'" + ",StockOut = " + "'" + newStockOut + "'" +
                                ",Available = 'Yes',DispatchDate=" + "'" + despatch_date + "'" + "," +
                                "DispatchRemark=" + "'" + DispatchRemark + "'" + " WHERE StockId = " + "'" + stockId + "'";
                        Log.d("SQL_RES", sql);
                        database.execSQL(sql);
                    }
                    db.close();
                } while (cursor.moveToNext());
                status = true;
            } else {
                status = false;
            }
        }
        return status;
    }


    public Cursor getSingleItemName(String itemId) {
        System.out.println("ItemID: "+itemId);
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from itemList where ItemId = ?",new String[]{itemId});
        if (cursor.getCount() > 0) {
            return cursor;
        } else {
            return null;
        }
    }
}
