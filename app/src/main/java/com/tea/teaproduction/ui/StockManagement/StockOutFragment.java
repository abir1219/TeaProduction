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
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockOutBinding;

import java.util.ArrayList;
import java.util.List;

public class StockOutFragment extends Fragment implements View.OnClickListener {
    FragmentStockOutBinding binding;
    ArrayAdapter<String> itemAdapter;
    List<String> itemList;
    String itemId = "";
    DbHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockOutBinding.inflate(inflater, container, false);

        btnClick();
        dbHelper = new DbHelper(getActivity());
        loadItemSpinner();
        return binding.getRoot();
    }

    private void btnClick() {
        binding.llMenu.setOnClickListener(this);
        binding.tieItem.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMenu:
                ((MainActivity) getActivity()).openDrawer();
                break;
            case R.id.tieItem:
                binding.spItem.performClick();
                binding.tieItem.setText("");
                binding.spItem.setVisibility(View.VISIBLE);
                break;
        }
    }
}