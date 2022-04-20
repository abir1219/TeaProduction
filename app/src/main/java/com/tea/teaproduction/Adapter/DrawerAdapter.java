package com.tea.teaproduction.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.teaproduction.MainActivity;
import com.tea.teaproduction.Model.DrawerModel;
import com.tea.teaproduction.R;

import java.util.List;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {
    private MainActivity.OnDrawerMenuListener mListener;
    List<DrawerModel> modelList;
    Context context;
    public static int selected_postion = -1;

    public DrawerAdapter(List<DrawerModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public DrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DrawerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DrawerViewHolder holder, int position) {
        holder.tvMenu.setText(modelList.get(position).getName());
        //Glide.with(context).load(modelList.get(position).getImage()).into(holder.ivMenu);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mListener.onDrawerMenuClick(position);
                } catch (NullPointerException e) {
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class DrawerViewHolder extends RecyclerView.ViewHolder {
        public static ImageView ivMenu;
        public static TextView tvMenu;

        public DrawerViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.ivMenu);
            tvMenu = itemView.findViewById(R.id.tvMenu);
        }
    }

    public void setListenerDrawerMenu(MainActivity.OnDrawerMenuListener listener) {
        mListener = listener;
    }

    public void colorChange(int pos){

    }

}
