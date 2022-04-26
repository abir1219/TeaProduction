package com.tea.teaproduction.ui.StockManagement;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.Model.ItemListModel;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockOutBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StockOutFragment extends Fragment implements View.OnClickListener {
    FragmentStockOutBinding binding;
    ArrayAdapter<String> itemAdapter;
    List<String> itemList;
    String itemId = "";
    DbHelper dbHelper;
    Dialog dialog;

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
        binding.tvSave.setOnClickListener(this);
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
                /*binding.spItem.performClick();
                binding.tieItem.setText("");
                binding.spItem.setVisibility(View.VISIBLE);*/
                setDialogLayout("Item");
                break;
            case R.id.tvSave:
                checkDetails();
                break;
        }
    }

    private void setDialogLayout(String type) {
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_searchable_spinner);
        dialog.getWindow().setLayout(800,1200);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        EditText etSearch = dialog.findViewById(R.id.etSearch);
        ListView listView = dialog.findViewById(R.id.listView);

        if(type.equalsIgnoreCase("Item")){
            tvTitle.setText("Select Item");
            itemAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itemList);
            //categoryAdapter.setDropDownViewResource(R.layout.spinner_list);
            listView.setAdapter(itemAdapter);
        }

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(type.equalsIgnoreCase("Item")){
                    itemAdapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(type.equalsIgnoreCase("Item")){
                    if(!itemAdapter.getItem(position).equalsIgnoreCase("Select Item")){
                        binding.tilItem.getEditText().setText(itemAdapter.getItem(position));
                    }
                }
                dialog.dismiss();
            }
        });
    }

    private void checkDetails() {
        boolean result = dbHelper.stockDispatch(itemId,binding.tieTotalItem.getText().toString(),new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault()).format(new Date()),binding.tieRemark.getText().toString());

        if(result){
            Toast.makeText(getActivity(), "Successfully Dispatched", Toast.LENGTH_SHORT).show();
            //itemId = "";
            binding.tieItem.setText("");
            binding.tieTotalItem.setText("");
            binding.tieRemark.setText("");
        }else{
            Toast.makeText(getActivity(), "Stock is not available", Toast.LENGTH_SHORT).show();
        }
    }
}