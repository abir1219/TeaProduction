package com.tea.teaproduction.ui.StockManagement;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.Model.CategoryListModel;
import com.tea.teaproduction.Model.CategoryModel;
import com.tea.teaproduction.Model.CompanyModel;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockInBinding;

import java.util.ArrayList;
import java.util.List;

public class StockInFragment extends Fragment implements View.OnClickListener{
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockInBinding.inflate(inflater,container,false);
        btnClick();
        dbHelper = new DbHelper(getActivity());
        loadCompanySpinner();
        loadCategorySpinner();
        loadItemSpinner();

        return binding.getRoot();
    }

    private void loadItemSpinner() {
        itemList = new ArrayList<>();
        Cursor cursor = dbHelper.getItemCategory();
        if (cursor!=null && cursor.getCount() > 0) {
            List<ItemListModel> itemListModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);
                itemListModel.add(new ItemListModel(GeadeCategoryId, GeadeCategoryName));
            }
            itemList.add("Select Item");
            for (int i = 0; i < itemListModel.size(); i++) {
                itemList.add(itemListModel.get(i).getItemName().toString());
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
                    itemId = itemListModel.get(i).getItemId();
                    if (!item.equals("Select Item")) {
                        binding.tieItem.setText(item);
                        binding.spItem.setVisibility(View.GONE);
                        //Toast.makeText(RegisterActivity.this, stateId, Toast.LENGTH_SHORT).show();
                        //loadDistrictSpinner(stateId);
                    } else {
                        binding.spItem.setVisibility(View.GONE);
                        //binding.etState.setText("Select State");
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
        if (cursor!=null && cursor.getCount() > 0) {
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
                    categoryId = categoryListModel.get(i).getItemCategoryId();
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
        Cursor cursor = dbHelper.getItemCategory();
        if (cursor!=null && cursor.getCount() > 0) {
            List<CompanyModel> companyModel = new ArrayList<>();
            while (cursor.moveToNext()) {
                String GeadeCategoryId = cursor.getString(0);
                String GeadeCategoryName = cursor.getString(1);
                companyModel.add(new CompanyModel(GeadeCategoryId, GeadeCategoryName));
            }
            companyList.add("Select Company");
            for (int i = 0; i < companyModel.size(); i++) {
                companyList.add(companyModel.get(i).getItemName().toString());
            }
            companyAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, companyList);
            companyAdapter.setDropDownViewResource(R.layout.spinner_list);

            binding.spCompany.setAdapter(companyAdapter);
            binding.spCompany.setSelected(false);  // must
            binding.spCompany.setSelection(0, true);

            binding.spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    String item = parent.getSelectedItem().toString();
                    companyId = companyModel.get(i).getItemId();
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tieCompany:
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

        }
    }
}