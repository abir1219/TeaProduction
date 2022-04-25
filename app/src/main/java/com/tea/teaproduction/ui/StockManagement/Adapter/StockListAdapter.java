package com.tea.teaproduction.ui.StockManagement.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tea.teaproduction.Helper.DbHelper;
import com.tea.teaproduction.R;
import com.tea.teaproduction.ui.StockManagement.Model.StockModel;

import java.util.List;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {
    List<StockModel> modelList;
    Context context;

    public StockListAdapter(List<StockModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*dbHelper = new DbHelper(context);
        Cursor cursor = dbHelper.getSingleItemName(modelList.get(position).getItemId());
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                holder.tvItemName.setText(cursor.getString(1));
                Log.d("ItemName : ",cursor.getString(1));
            }
        }*/
        Log.d("ADAPTER_ITEM_NAME",modelList.get(position).getItemName());
        Log.d("ADAPTER_AVAILABLE",modelList.get(position).getAvailable());
        Log.d("ADAPTER_ITEM_STOCKIN",modelList.get(position).getStockIn());
        Log.d("ADAPTER_ITEM_STOCKOUT",modelList.get(position).getStockOut());

        holder.tvItemName.setText(modelList.get(position).getItemName());

        holder.tvAvailable.setText(modelList.get(position).getAvailable());
        holder.tvStockIn.setText(modelList.get(position).getStockIn());
        holder.tvStockOut.setText(modelList.get(position).getStockOut());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvStockIn, tvStockOut, tvAvailable;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvStockIn = itemView.findViewById(R.id.tvStockIn);
            tvStockOut = itemView.findViewById(R.id.tvStockOut);
            tvAvailable = itemView.findViewById(R.id.tvAvailable);
        }
    }
}
