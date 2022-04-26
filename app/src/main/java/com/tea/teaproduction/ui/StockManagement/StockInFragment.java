package com.tea.teaproduction.ui.StockManagement;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.Model.CategoryListModel;
import com.tea.teaproduction.Model.CategoryModel;
import com.tea.teaproduction.Model.CompanyModel;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockInBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StockInFragment extends Fragment implements View.OnClickListener {
    FragmentStockInBinding binding;
    List<String> categoryList;
    DbHelper dbHelper;
    List<String> companyList;
    List<String> itemList;
    ArrayAdapter<String> categoryAdapter;
    ArrayAdapter<String> companyAdapter;
    ArrayAdapter<String> itemAdapter;
    String itemId = "";
    String categoryId = "";
    String companyId = "";
    Calendar calendar;

    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav);
        try {
            navigationView.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockInBinding.inflate(inflater, container, false);
        BottomNavigationView navigationView = getActivity().findViewById(R.id.bottom_nav);
        try {
            navigationView.setVisibility(View.GONE);
        } catch (Exception e) {
        }
        btnClick();
        dbHelper = new DbHelper(getActivity());
        loadCompanySpinner();
        loadCategorySpinner();
        loadItemSpinner();

        return binding.getRoot();
    }

    private void loadItemSpinner() {
        itemList = new ArrayList<>();
        Cursor cursor = dbHelper.getItemList();
        if (cursor != null && cursor.getCount() > 0) {
            List<ItemListModel> itemListModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);
                itemListModel.add(new ItemListModel(GeadeCategoryId, GeadeCategoryName));
            }
            itemList.add("Select Item");
            for (int i = 0; i < itemListModel.size(); i++) {
                itemList.add(itemListModel.get(i).getItemName());
            }
            itemAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, itemList);
            itemAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spItem.setAdapter(itemAdapter);
            binding.spItem.setSelected(false);  // must
            binding.spItem.setSelection(0, true);

            binding.spItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    itemId = itemListModel.get(i - 1).getItemId();
                    if (!item.equals("Select Item")) {
                        binding.tieItem.setText(item);
                        binding.spItem.setVisibility(View.GONE);
                    } else {
                        binding.spItem.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void loadCategorySpinner() {
        categoryList = new ArrayList<>();
        Cursor cursor = dbHelper.getItemCategory();
        if (cursor != null && cursor.getCount() > 0) {
            List<CategoryListModel> categoryListModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);
                categoryListModel.add(new CategoryListModel(GeadeCategoryId, GeadeCategoryName));
            }
            categoryList.add("Select Category");
            for (int i = 0; i < categoryListModel.size(); i++) {
                categoryList.add(categoryListModel.get(i).getCategoryName().toString());
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
                    categoryId = categoryListModel.get(i - 1).getItemCategoryId();
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

    private void loadCompanySpinner() {
        companyList = new ArrayList<>();
        Cursor cursor = dbHelper.getCompanyList();
        if (cursor != null && cursor.getCount() > 0) {
            List<CompanyModel> companyModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);

                Log.d("Company Id & Name: ", GeadeCategoryId + " & " + GeadeCategoryName);
                companyModel.add(new CompanyModel(GeadeCategoryId, GeadeCategoryName));
            }
            companyList.add("Select Company");
            for (int i = 0; i < companyModel.size(); i++) {
                companyList.add(companyModel.get(i).getItemName());
            }

            Log.d("COMPANY_NAME", companyList.toString());
            companyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, companyList);
            companyAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spCompany.setAdapter(companyAdapter);
            binding.spCompany.setSelected(false);  // must
            binding.spCompany.setSelection(0, true);

            binding.spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    companyId = companyModel.get(i - 1).getItemId();
                    if (!item.equals("Select Company")) {
                        binding.tieCompany.setText(item);
                        binding.spCompany.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this, stateId, Toast.LENGTH_SHORT).show();
                        //loadDistrictSpinner(stateId);
                    } else {
                        binding.spCompany.setVisibility(View.GONE);
                        //binding.etState.setText("Select State");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void btnClick() {

        binding.tieCompany.setOnClickListener(this);
        binding.tieCategory.setOnClickListener(this);
        binding.tieItem.setOnClickListener(this);
        binding.tieInvoiceDate.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
        binding.llMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMenu:
                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.tvSave:
                checkDetails();
                break;
            case R.id.tieCompany:
                //Toast.makeText(getActivity(), "Company", Toast.LENGTH_SHORT).show();
                binding.spCompany.performClick();
                binding.tieCompany.setText("");
                binding.spCompany.setVisibility(View.VISIBLE);
                break;
            case R.id.tieCategory:
                binding.spCategory.performClick();
                binding.tieCategory.setText("");
                binding.spCategory.setVisibility(View.VISIBLE);
                break;
            case R.id.tieItem:
                binding.spItem.performClick();
                binding.tieItem.setText("");
                binding.spItem.setVisibility(View.VISIBLE);
                break;
            case R.id.tieInvoiceDate:
                calendar = Calendar.getInstance();
                DatePickerDialog date1 = new DatePickerDialog(getActivity(), datePickerDialog, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //deliveryDate.setText(SimpleDateFormat.getDateInstance().format(format.format(calendar.getTime())));
                //String dateText = DateFormat.format("yyyy/MM/dd", calendar).toString();
                //date.getDatePicker().setMinDate();
                date1.getDatePicker();
                date1.show();
                break;

        }
    }

    private void checkDetails() {
        boolean isSaved = dbHelper.addStock(
                itemId, categoryId, companyId,
                !binding.tieSgst.getText().toString().isEmpty() ? binding.tieSgst.getText().toString() : "0",
                !binding.tieCgst.getText().toString().isEmpty() ? binding.tieCgst.getText().toString() : "0",
                !binding.tieIgst.getText().toString().isEmpty() ? binding.tieIgst.getText().toString() : "0",
                new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()),
                binding.tieRemark.getText().toString(),
                binding.tieInvoiceNo.getText().toString(), binding.tieInvoiceDate.getText().toString(),
                binding.tieRate.getText().toString(), binding.tieCustomPrice1.getText().toString(),
                binding.tieCustomValue1.getText().toString(),
                binding.tieCustomPrice2.getText().toString(), binding.tieCustomValue2.getText().toString(),
                binding.tieCustomPrice3.getText().toString(),
                binding.tieCustomValue3.getText().toString(), binding.tieTotalItem.getText().toString(),"0","",
                "","Yes"
        );

        if (isSaved) {
            Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            /*itemId = "";
            categoryId = "";
            companyId = "";*/
            binding.tieCategory.setText("");
            binding.tieCompany.setText("");
            binding.tieItem.setText("");
            binding.tieSgst.setText("");
            binding.tieCgst.setText("");
            binding.tieIgst.setText("");
            binding.tieRemark.setText("");
            binding.tieTotalItem.setText("");
            binding.tieInvoiceNo.setText("");
            binding.tieInvoiceDate.setText("");
            binding.tieRate.setText("");
            binding.tieCustomPrice1.setText("");
            binding.tieCustomValue1.setText("");
            binding.tieCustomPrice2.setText("");
            binding.tieCustomValue2.setText("");
            binding.tieCustomPrice3.setText("");
            binding.tieCustomValue3.setText("");

        } else {
            Toast.makeText(getActivity(), "Something gone wrong", Toast.LENGTH_SHORT).show();
        }
    }


    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//DOB = DateFormat.format("yyyy-MM-dd", calendar).toString();
            //updateLabel();
            //binding.dob.getEditText().setText(DateFormat.format("yyyy-MM-dd", calendar).toString());
            binding.tieInvoiceDate.setText(DateFormat.format("yyyy-MM-dd", calendar).toString());
        }

    };
}