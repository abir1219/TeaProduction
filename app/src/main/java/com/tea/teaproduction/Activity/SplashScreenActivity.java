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
import com.tea.teaproduction.Model.CategoryListModel;
import com.tea.teaproduction.Model.CompanyModel;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.Model.SectorModel;
import com.tea.teaproduction.Model.ShiftModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.ActivitySplashScreenBinding;
import com.tea.teaproduction.Model.CategoryModel;
import com.tea.teaproduction.utils.ConnectionReceiver;
import com.tea.teaproduction.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {
    ActivitySplashScreenBinding binding;
    Animation animation;
    Handler mHandler;
    Intent intent;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dbHelper = new DbHelper(SplashScreenActivity.this);
        loadAnimateLogo();
        loadData();


        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //redirect();
                /*if (YoDB.getPref().read(Constants.ID, "").isEmpty()) {
                    intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }*/
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in_animation, R.anim.fade_out_animation);
                finish();
            }
        }, 4000);
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
            loadConsignmentFromLocalToServer();
            loadCompanyListFromServer();
            loadCategoryListFromServer();
            loadItemListFromServer();
        }
    }

    private void loadCompanyListFromServer() {
        StringRequest sr = new StringRequest(Request.Method.POST, Urls.COMPANY_LIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Company_RES", response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    List<CompanyModel> companyModel = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String ItemId = object.getString("ItemId");
                        String ItemName = object.getString("ItemName");

                        Cursor cursor = dbHelper.getCompanyList();
                        if (cursor != null && cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                if (!ItemId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertCompany(ItemId, ItemName);
                                }
                            }
                        } else {
                            dbHelper.insertCompany(ItemId, ItemName);
                        }

                        //dbHelper.insertSector(SectorId,SectorName);

                        companyModel.add(new CompanyModel(ItemId, ItemName));
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
                                    dbHelper.insertCompany(ItemCategoryId, CategoryName);
                                }
                            }
                        } else {
                            dbHelper.insertCompany(ItemCategoryId, CategoryName);
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
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Company_RES", response);
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

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> body = new HashMap<>();
                        body.put("emp_id",EmpId);
                        body.put("emp_code",EmpCode);
                        body.put("category",Category);
                        body.put("shift",Shift);
                        body.put("sector",Sector);
                        body.put("weight",Weight);
                        body.put("date",Date);
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
                        if (cursor != null && cursor.getCount() > 0) {
                            Toast.makeText(SplashScreenActivity.this, "have", Toast.LENGTH_SHORT).show();
                            while (cursor.moveToNext()) {
                                if (!EmpId.equalsIgnoreCase(cursor.getString(0))) {
                                    dbHelper.insertEmployee(EmpId, EmpCode, EmpFullName);
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
                    Log.d("Category_RES", response);
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
                                }
                            }
                        } else {
                            dbHelper.insertCategory(GeadeCategoryId, GeadeCategoryName);
                        }

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