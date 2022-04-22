package com.tea.teaproduction.ui.StockManagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.R;
import com.tea.teaproduction.databinding.FragmentStockOutBinding;

public class StockOutFragment extends Fragment implements View.OnClickListener {
    FragmentStockOutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStockOutBinding.inflate(inflater, container, false);

        btnClick();
        return binding.getRoot();
    }

    private void btnClick() {
        binding.llMenu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llMenu:
                ((MainActivity) getActivity()).openDrawer();
                break;
        }
    }
}