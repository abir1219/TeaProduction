package com.tea.teaproduction.ui;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentConsignmentListBinding;
import com.tea.teaproduction.ui.Home.Adapter.ConsignmentAdapter;
import com.tea.teaproduction.ui.Home.Model.ConsignmentModel;

import java.util.ArrayList;
import java.util.List;

public class ConsignmentListFragment extends Fragment {
    FragmentConsignmentListBinding binding;
    DbHelper dbHelper;
    List<ConsignmentModel> modelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentConsignmentListBinding.inflate(inflater,container,false);

        dbHelper = new DbHelper(getActivity());
        setLayout();
        loadConsignmentList();
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private void setLayout() {
        binding.rvConsignment.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
    }

    private void loadConsignmentList() {
        modelList = new ArrayList<>();
        Cursor cursor = dbHelper.getConsignmentData();
        if(cursor != null && cursor.getCount() > 0){
            while (cursor.moveToNext()){
                modelList.add(new ConsignmentModel(cursor.getString(1),cursor.getString(6),cursor.getString(5)));
            }
        }
        ConsignmentAdapter adapter = new ConsignmentAdapter(modelList,getActivity());
        binding.rvConsignment.setAdapter(adapter);
    }
}