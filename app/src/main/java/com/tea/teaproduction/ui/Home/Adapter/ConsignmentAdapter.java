package com.tea.teaproduction.ui.Home.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.teaproduction.R;
import com.tea.teaproduction.ui.Home.Model.ConsignmentModel;

import java.util.List;

public class ConsignmentAdapter extends RecyclerView.Adapter<ConsignmentAdapter.ViewHolder>{
    List<ConsignmentModel> modelList;
    Context context;

    public ConsignmentAdapter(List<ConsignmentModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.consignment_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.date.setText(modelList.get(position).getDate());
        holder.empCode.setText(modelList.get(position).getEmpCode());
        holder.weight.setText(modelList.get(position).getWeight());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView date,empCode,weight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            empCode = itemView.findViewById(R.id.empCode);
            weight = itemView.findViewById(R.id.weight);
        }
    }
}
