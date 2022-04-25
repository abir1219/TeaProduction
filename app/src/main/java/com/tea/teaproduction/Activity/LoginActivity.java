package com.tea.teaproduction.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tea.teaproduction.CustomDialog.CustomProgressDialog;
import com.tea.teaproduction.Helper.ManageLoginData;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.ActivityLoginBinding;
import com.tea.teaproduction.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BtnClick();

    }

    private void BtnClick() {
        binding.tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLogin:
                checkLoginData();
                break;
        }
    }

    private void checkLoginData() {
        if (binding.tilPhone.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            binding.tilPhone.getEditText().requestFocus();
        } else if (binding.tilPhone.getEditText().getText().toString().length() != 10) {
            Toast.makeText(LoginActivity.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
            binding.tilPhone.getEditText().requestFocus();
        } else if (binding.tilPassword.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter password", Toast.LENGTH_SHORT).show();
            binding.tilPassword.getEditText().requestFocus();
        } else {
            //checkPermission();
            login();
        }
    }

    private void login() {
        String username = binding.tilPhone.getEditText().getText().toString();
        String password = binding.tilPassword.getEditText().getText().toString();


        StringRequest sr = new StringRequest(Request.Method.POST, Urls.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CustomProgressDialog.showDialog(LoginActivity.this, false);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject result = object.getJSONObject("result");
                    String id = result.getString("user_id");
                    String name = result.getString("name");
                    String role_name = result.getString("role_name");
                    ManageLoginData.addLoginData(id, name, role_name);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.getMessage());
                Toast.makeText(getApplicationContext(), "Getting some troubles", Toast.LENGTH_SHORT).show();
                CustomProgressDialog.showDialog(LoginActivity.this, false);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("username ", username);
                map.put("password", password);
                return map;
            }
        };
        Volley.newRequestQueue(getApplicationContext()).add(sr);
    }
}