package com.tea.teaproduction.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.ManageSharedPreferenceData.YoDB;
import com.tea.teaproduction.Model.CategoryListModel;
import com.tea.teaproduction.Model.CompanyModel;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.Model.SectorModel;
import com.tea.teaproduction.Model.ShiftModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.ActivitySplashScreenBinding;
import com.tea.teaproduction.Model.CategoryModel;
import com.tea.teaproduction.ui.Home.EmployeeModel;
import com.tea.teaproduction.utils.ConnectionReceiver;
import com.tea.teaproduction.utils.Constants;
import com.tea.teaproduction.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SplashScreenActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {
    private static final String INIT_VECTOR = "encryptionIntVec";
    private String SECRET_KEY = "aesEncryptionKey";
    ActivitySplashScreenBinding binding;
    Animation animation;
    Handler mHandler;
    Intent intent;

    DbHelper dbHelper;
    List<EmployeeModel> employeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DbHelper(SplashScreenActivity.this);
        loadAnimateLogo();
        loadData();

        redirect();


        /*mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //redirect();
                *//*if (YoDB.getPref().read(Constants.ID, "").isEmpty()) {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }*//*
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                finish();
            }
        }, 4000);*/
    }


    private void redirect() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (YoDB.getPref().read(Constants.ID, "").isEmpty()) {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                    startActivity(intent);
                }
            }
        }, 5000);
    }

    public boolean checkInternet() {
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        return isConnected;
    }

    private void loadData() {
        if (checkInternet()) {
            //Toast.makeText(getApplicationContext(), "Have Internet", Toast.LENGTH_SHORT).show();
            loadCategoryFromServer();
            loadShiftFromServer();
            loadSectorFromServer();
            loadEmployeeDetailsFromServer();
            loadConsignmentFromLocalToServer(); //
            loadStockFromLocalToServer(); //
            loadCompanyListFromServer();
            loadCategoryListFromServer();
            loadItemListFromServer();
            insertUser();
        }
    }


    private void loadStockFromServer() {
        //Toast.makeText(this, "loadStock", Toast.LENGTH_SHORT).show();
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.STOCK_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("STOCK_LIST_RES", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String ItemId = object.getString("ItemId");
                        String ItemCategoryId = object.getString("ItemCategoryId");
                        String CompanyId = object.getString("CompanyId");
                        String SGST = object.getString("SGST");
                        String CGST = object.getString("CGST");
                        String IGST = object.getString("IGST");
                        String PurchaseDate = object.getString("PurchaseDate");
                        String PurchaseRemark = object.getString("PurchaseRemark");
                        String InvoiceNumber = object.getString("InvoiceNumber");
                        String InvoiceDate = object.getString("InvoiceDate");
                        String UnitPrice = object.getString("UnitPrice");
                        String CustomPrice1 = object.getString("CustomPrice1");
                        String CustompriceValue1 = object.getString("CustompriceValue1");
                        String CustomPrice2 = object.getString("CustomPrice2");
                        String CustompriceValue2 = object.getString("CustompriceValue2");
                        String CustomPrice3 = object.getString("CustomPrice3");
                        String CustompriceValue3 = object.getString("CustompriceValue3");
                        String StockIn = object.getString("StockIn");
                        String StockOut = object.getString("StockOut");
                        String DispatchDate = object.getString("DispatchDate");
                        String DispatchRemark = object.getString("DispatchRemark");
                        String Available = object.getString("Available");

                        dbHelper.addStock(ItemId,ItemCategoryId,CompanyId,SGST,CGST,IGST,PurchaseDate,PurchaseRemark,InvoiceNumber,InvoiceDate,
                                UnitPrice,CustomPrice1,CustompriceValue1,CustomPrice2,CustompriceValue2,CustomPrice3,CustompriceValue3,StockIn,
                                StockOut,DispatchDate,DispatchRemark,Available);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(SplashScreenActivity.this).add(sr);
    }

    private void insertUser() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.USER_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteUserData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("LOGIN_RES", jsonObject.toString());
                    JSONArray array = jsonObject.getJSONArray("result");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String userId = object.getString("UserId");
                        String UserName = object.getString("UserName");

                        String Password = (object.getString("ORGPassword"));
                        Log.d("DECRYPT_PW", Password);
                        String role = object.getString("RoleDisplayName");

                        dbHelper.insertUser(userId, role, UserName, Password);


                        //String strResult=decryptMsg(object.getString("Password"),secretKey);
                        //String Password;
                        /*try {
                            String Password = decrypt(object.getString("Password"));
                            Log.d("DECRYPT_PW", Password);
                            String role = object.getString("RoleDisplayName");

                            dbHelper.insertUser(userId, role, UserName, Password);
                        } catch (Exception e) {
                            Log.d("DECRYPT_MSG", e.getMessage());
                        }*/

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(SplashScreenActivity.this).add(sr);
    }

    private String decrypt(String password) throws Exception {
        SecretKey key = generateKey(password);
        String decrypt = "";
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decodeValue = Base64.decode("", Base64.DEFAULT);
            byte[] decValue = c.doFinal(decodeValue);
            decrypt = new String(decValue);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return decrypt;
    }

    private SecretKey generateKey(String password) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


    private void loadStockFromLocalToServer() {
        Cursor cursor = dbHelper.getStockDetails();
        //Log.d("STOCK_COUNT","Count: "+cursor.getCount());
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String ItemId = cursor.getString(1);
                String ItemCatID = cursor.getString(2);
                String CompanyID = cursor.getString(3);
                String SGST = cursor.getString(5);
                String CGST = cursor.getString(6);
                String IGST = cursor.getString(7);
                String PurchaseDate = cursor.getString(9);
                String REMARK = cursor.getString(10);
                String StockIn = cursor.getString(11);
                String InvoiceNo = cursor.getString(15);
                String InvoiceDate = cursor.getString(16);
                String UnitPrice = cursor.getString(17);
                String CustomPrice1 = cursor.getString(18);
                String CustomValue1 = cursor.getString(19);
                String CustomPrice2 = cursor.getString(20);
                String CustomValue2 = cursor.getString(21);
                String CustomPrice3 = cursor.getString(22);
                String CustomValue3 = cursor.getString(23);
                String StockOut = cursor.getString(14);
                String DispatchDate = cursor.getString(12);
                String DispatchRemark = cursor.getString(13);
                String Available = cursor.getString(24);

                //login(ItemId,ItemCatID,CompanyID);

                StringRequest sr = new StringRequest(Request.Method.POST, Urls.PURCHASE_STOCK, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dbHelper.deleteStockData();
                        loadStockFromServer();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> body = new HashMap<>();
                        body.put("Item_id", ItemId);
                        body.put("Item_category_id", ItemCatID);
                        body.put("Company_id", CompanyID);
                        body.put("sgst", SGST);
                        body.put("igst", IGST);
                        body.put("cgst", CGST);
                        body.put("Puchase_date", PurchaseDate);
                        body.put("puchase_remark", REMARK);
                        body.put("stock_in", StockIn);
                        body.put("Invoice_number", InvoiceNo);
                        body.put("Invoice_date", InvoiceDate);
                        body.put("unit_price", UnitPrice);
                        body.put("custom_price1", CustomPrice1);
                        body.put("custom_value1", CustomValue1);
                        body.put("custom_price2", CustomPrice2);
                        body.put("custom_value2", CustomValue2);
                        body.put("custom_price3", CustomPrice3);
                        body.put("custom_value3", CustomValue3);
                        body.put("stock_out", StockOut);
                        body.put("despatch_date", DispatchDate);
                        body.put("despatch_remark", DispatchRemark);
                        body.put("Available", Available);
                        return body;
                    }
                };
                Volley.newRequestQueue(SplashScreenActivity.this).add(sr);
            }
        }
        else{
            loadStockFromServer();
        }
    }

    private void login() {
    }

    private void loadCompanyListFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.COMPANY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteCompanyData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("COMPANY_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<CompanyModel> companyModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String CompanyId = object.getString("CompanyId");
                        String CompanyName = object.getString("CompanyName");

                        Cursor cursor = dbHelper.getCompanyList();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                System.out.println("Cursor: " + cursor.getString(0));
                                if (!CompanyId.equals(cursor.getString(0))) {
                                    Log.d("CompanyId & cursor: ", CompanyId + " & " + cursor.getString(0));
                                    dbHelper.insertCompany(CompanyId, CompanyName);
                                    continue;
                                }
                            }
                        } else {
                            dbHelper.insertCompany(CompanyId, CompanyName);
                        }
                        //dbHelper.insertShift(ShiftId,ShiftName);
                        companyModel.add(new CompanyModel(CompanyId, CompanyName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadCategoryListFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.ITEM_CATEGORY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteItemCategoryData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Company_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<CategoryListModel> categoryListModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String ItemCategoryId = object.getString("ItemCategoryId");
                        String CategoryName = object.getString("CategoryName");

                        Cursor cursor = dbHelper.getItemCategory();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!ItemCategoryId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertItemCategory(ItemCategoryId, CategoryName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertItemCategory(ItemCategoryId, CategoryName);
                        }

                        //dbHelper.insertSector(SectorId,SectorName);

                        categoryListModel.add(new CategoryListModel(ItemCategoryId, CategoryName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadItemListFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.ITEM_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteItemListData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("ITEM_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<ItemListModel> itemListModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String ItemId = object.getString("ItemId");
                        String ItemName = object.getString("ItemName");

                        Cursor cursor = dbHelper.getItemList();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!ItemId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertItemList(ItemId, ItemName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertItemList(ItemId, ItemName);
                        }

                        //dbHelper.insertSector(SectorId,SectorName);

                        itemListModel.add(new ItemListModel(ItemId, ItemName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadConsignmentFromLocalToServer() {
        Cursor cursor = dbHelper.getConsignmentData();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String EmpId = cursor.getString(0);
                String EmpCode = cursor.getString(1);
                String Category = cursor.getString(2);
                String Shift = cursor.getString(3);
                String Sector = cursor.getString(4);
                String Weight = cursor.getString(5);
                String Date = cursor.getString(6);

                StringRequest sr = new StringRequest(Request.Method.POST, Urls.CONSIGNMENT_ADD, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dbHelper.deleteConsignmentData();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> body = new HashMap<>();
                        body.put("emp_id", EmpId);
                        body.put("emp_code", EmpCode);
                        body.put("category", Category);
                        body.put("shift", Shift);
                        body.put("sector", Sector);
                        body.put("weight", Weight);
                        body.put("date", Date);
                        return body;
                    }
                };
                Volley.newRequestQueue(SplashScreenActivity.this).add(sr);
            }
        }
    }

    private void loadEmployeeDetailsFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.GET_ALL_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteEmployeeData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("EMP_RES", response);
                    JSONArray array = jsonObject.getJSONArray("result");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String EmpId = object.getString("EmpId");
                        String EmpFullName = object.getString("EmpFullName");
                        String EmpCode = object.getString("EmpCode");

                        Cursor cursor = dbHelper.getAllEmployee();
                        employeeModel = new ArrayList<>();
                        if (cursor != null && cursor.getCount() > 0) {
                            //Toast.makeText(SplashScreenActivity.this, "have", Toast.LENGTH_SHORT).show();
                            while (cursor.moveToNext()) {
                                if (!EmpId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertEmployee(EmpId, EmpCode, EmpFullName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertEmployee(EmpId, EmpCode, EmpFullName);
                        }
                        //dbHelper.insertEmployee(EmpId,EmpCode,EmpFullName);
                        //Log.d("EMPLOYEE_RES",""+dbHelper.insertEmployee(EmpId,EmpCode,EmpFullName));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(SplashScreenActivity.this).add(sr);
    }

    private void loadSectorFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.SECTOR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteSectorData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Sector_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<SectorModel> sectorModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String SectorId = object.getString("SectorId");
                        String SectorName = object.getString("SectorName");

                        Cursor cursor = dbHelper.getSectorData();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!SectorId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertSector(SectorId, SectorName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertSector(SectorId, SectorName);
                        }

                        //dbHelper.insertSector(SectorId,SectorName);

                        sectorModel.add(new SectorModel(SectorId, SectorName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadShiftFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.SHIFT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbHelper.deleteShiftData();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Shift_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<ShiftModel> shiftModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String ShiftId = object.getString("ShiftId");
                        String ShiftName = object.getString("ShiftName");

                        Cursor cursor = dbHelper.getShiftData();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!ShiftId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertShift(ShiftId, ShiftName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertShift(ShiftId, ShiftName);
                        }
                        //dbHelper.insertShift(ShiftId,ShiftName);

                        shiftModel.add(new ShiftModel(ShiftId, ShiftName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadCategoryFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.CATEGORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    dbHelper.deleteCategoryData();
                    Log.d("Shift_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<CategoryModel> categoryModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);
                        String GeadeCategoryId = object.getString("GeadeCategoryId");
                        String GeadeCategoryName = object.getString("GeadeCategoryName");

                        Cursor cursor = dbHelper.getCategoryData();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!GeadeCategoryId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertCategory(GeadeCategoryId, GeadeCategoryName);
                                    break;
                                }
                            }
                        } else {
                            dbHelper.insertCategory(GeadeCategoryId, GeadeCategoryName);
                        }
                        //dbHelper.insertShift(ShiftId,ShiftName);

                        categoryModel.add(new CategoryModel(GeadeCategoryId, GeadeCategoryName));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                Log.d("ERROR_RES", error.getMessage());
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }

    private void loadAnimateLogo() {
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        binding.ivSplash.setAnimation(animation);
    }

    @Override
    public void onNetworkChange(boolean isConnected) {

    }
}