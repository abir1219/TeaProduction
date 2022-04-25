package com.tea.teaproduction.ui.Home;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.Model.SectorModel;
import com.tea.teaproduction.Model.ShiftModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentHomeBinding;
import com.tea.teaproduction.Model.CategoryModel;
import com.tea.teaproduction.utils.Urls;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeFragment extends Fragment implements View.OnClickListener {
    FragmentHomeBinding binding;
    DbHelper dbHelper;
    List<String> categoryList;
    List<String> shiftList;
    List<String> sectorList;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> shiftAdapter;
    ArrayAdapter<String> sectorAdapter;
    String empId = "";

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav);
        try{
            navigationView.setVisibility(View.VISIBLE);
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav);
        try{
            navigationView.setVisibility(View.VISIBLE);
        }catch (Exception e){}
        BtnClick();
        dbHelper = new DbHelper(getActivity());
        loadCategorySpinner();
        loadShiftSpinner();
        loadSectorSpinner();
        binding.tilEmpCode.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (binding.tilEmpCode.getEditText().length() == 10) {
                    getEmployeeDetailsByEmpCode();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return binding.getRoot();
    }

    private void loadCategorySpinner() {
        categoryList = new ArrayList<>();
        Cursor cursor = dbHelper.getCategoryData();
        if (cursor!=null && cursor.getCount() > 0) {
            List<CategoryModel> categoryModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);
                categoryModel.add(new CategoryModel(GeadeCategoryId, GeadeCategoryName));
            }
            categoryList.add("Select Category");
            for (int i = 0; i < categoryModel.size(); i++) {
                categoryList.add(categoryModel.get(i).getGeadeCategoryName().toString());
            }
            categoryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoryList);
            categoryAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spCategory.setAdapter(categoryAdapter);
            binding.spCategory.setSelected(false);  // must
            binding.spCategory.setSelection(0, true);

            binding.spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    if (!item.equals("Select Category")) {
                        binding.tieCategory.setText(item);
                        binding.spCategory.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this, stateId, Toast.LENGTH_SHORT).show();
                        //loadDistrictSpinner(stateId);
                    } else {
                        binding.spCategory.setVisibility(View.GONE);
                        //binding.etState.setText("Select State");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void loadShiftSpinner() {
        shiftList = new ArrayList<>();
        Cursor cursor = dbHelper.getShiftData();
        if (cursor!=null && cursor.getCount() > 0) {
            List<ShiftModel> shiftModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String ShiftId = cursor.getString(0);
                String ShiftName = cursor.getString(1);
                shiftModel.add(new ShiftModel(ShiftId, ShiftName));
            }
            shiftList.add("Select Shift");
            for (int i = 0; i < shiftModel.size(); i++) {
                shiftList.add(shiftModel.get(i).getShiftName());
            }
            shiftAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, shiftList);
            shiftAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spShift.setAdapter(shiftAdapter);
            binding.spShift.setSelected(false);  // must
            binding.spShift.setSelection(0, true);

            binding.spShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    if (!item.equals("Select Shift")) {
                        binding.tieShift.setText(item);
                        binding.spShift.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this, stateId, Toast.LENGTH_SHORT).show();
                        //loadDistrictSpinner(stateId);
                    } else {
                        binding.spShift.setVisibility(View.GONE);
                        //binding.etState.setText("Select State");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }


    private void loadSectorSpinner() {
        sectorList = new ArrayList<>();
        Cursor cursor = dbHelper.getSectorData();
        if (cursor!=null && cursor.getCount() > 0) {
            List<SectorModel> sectorModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String SectorId = cursor.getString(0);
                String SectorName = cursor.getString(1);
                sectorModel.add(new SectorModel(SectorId, SectorName));
            }
            sectorList.add("Select Sector");
            for (int i = 0; i < sectorModel.size(); i++) {
                sectorList.add(sectorModel.get(i).getSectorName().toString());
            }
            sectorAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, sectorList);
            sectorAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spSector.setAdapter(sectorAdapter);
            binding.spSector.setSelected(false);  // must
            binding.spSector.setSelection(0, true);

            binding.spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    if (!item.equals("Select Sector")) {
                        binding.tieSector.setText(item);
                        binding.spSector.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this, stateId, Toast.LENGTH_SHORT).show();
                        //loadDistrictSpinner(stateId);
                    } else {
                        binding.spSector.setVisibility(View.GONE);
                        //binding.etState.setText("Select State");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }


    private void getEmployeeDetailsByEmpCode() {
        Cursor cursor = dbHelper.getSingleEmployee(binding.tilEmpCode.getEditText().getText().toString());
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                binding.tilEmpName.getEditText().setText(cursor.getString(2));
                empId = cursor.getString(0);
            }
        }
        /*StringRequest sr = new StringRequest(Request.Method.POST, Urls.GET_EMPLOYEE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = new
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
                body.put("emp_code",binding.tieEmpCode.getText().toString());
                return body;
            }
        };
        Volley.newRequestQueue(getActivity()).add(sr);*/
    }

    private void BtnClick() {
        binding.llMenu.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.tieCategory.setOnClickListener(this);
        binding.tieShift.setOnClickListener(this);
        binding.tieSector.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSave:
                checkDetails();
                break;
            case R.id.tieCategory:
                //Toast.makeText(getActivity(), "Category Clicked", Toast.LENGTH_SHORT).show();
                binding.spCategory.performClick();
                binding.tieCategory.setText("");
                binding.spCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.tieShift:
                //Toast.makeText(getActivity(), "Category Clicked", Toast.LENGTH_SHORT).show();
                binding.spShift.performClick();
                binding.tieShift.setText("");
                binding.spShift.setVisibility(View.VISIBLE);
                break;
            case R.id.tieSector:
                //Toast.makeText(getActivity(), "Category Clicked", Toast.LENGTH_SHORT).show();
                binding.spSector.performClick();
                binding.tieSector.setText("");
                binding.spSector.setVisibility(View.VISIBLE);
                break;
            case R.id.llMenu:
                ((MainActivity) getActivity()).openDrawer();
                break;
        }
    }

    private void checkDetails() {
        boolean isSaved = dbHelper.insertConsignment(empId,binding.tieEmpCode.getText().toString(),binding.tieCategory.getText().toString(),
                binding.tieShift.getText().toString(),binding.tieSector.getText().toString(),binding.tieWeight.getText().toString(),
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));

        if(isSaved){
            Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            empId = "";
            binding.tieEmpCode.setText("");
            binding.tieCategory.setText("");
            binding.tieShift.setText("");
            binding.tieSector.setText("");
            binding.tieWeight.setText("");

        }else{
            Toast.makeText(getActivity(), "Something gone wrong", Toast.LENGTH_SHORT).show();
        }
    }
}