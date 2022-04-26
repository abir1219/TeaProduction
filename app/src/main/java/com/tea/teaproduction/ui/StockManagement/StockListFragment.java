package com.tea.teaproduction.ui.StockManagement;

import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockListBinding;
import com.tea.teaproduction.ui.StockManagement.Adapter.StockListAdapter;
import com.tea.teaproduction.ui.StockManagement.Model.StockModel;

import java.util.ArrayList;
import java.util.List;

public class StockListFragment extends Fragment implements View.OnClickListener{
    FragmentStockListBinding binding;
    DbHelper dbHelper;
    List<StockModel> modelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockListBinding.inflate(inflater, container, false);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dbHelper = new DbHelper(getActivity());
        btnClick();
        loadStockList();
        return binding.getRoot();
    }

    private void btnClick() {
        binding.llMenu.setOnClickListener(this);
    }

    private void loadStockList() {
        Cursor cursor = dbHelper.getStockDetails();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String itemName = "";
                Cursor cursor1 = dbHelper.getSingleItemName(cursor.getString(1));
                if (cursor1 != null && cursor1.getCount() > 0) {
                    while (cursor1.moveToNext()) {
                        itemName = cursor1.getString(1);
                    }
                }

                modelList = new ArrayList<>();
                Log.d("ITEM_NAME_RES",itemName);

                modelList.add(new StockModel(cursor.getInt(0), cursor.getString(1), itemName, cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10)
                        , cursor.getString(11).isEmpty() ? "0" : cursor.getString(11), cursor.getString(12), cursor.getString(13), cursor.getString(14).isEmpty() ? "0" : cursor.getString(14),
                        cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(17),
                        cursor.getString(19), cursor.getString(20), cursor.getString(21), cursor.getString(22)
                        , cursor.getString(23), cursor.getString(24)));
            }

            StockListAdapter adapter = new StockListAdapter(modelList, getActivity());
            binding.rvStock.setAdapter(adapter);
            binding.rvStock.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llMenu:
                ((MainActivity)getActivity()).openDrawer();
                break;
        }
    }
}