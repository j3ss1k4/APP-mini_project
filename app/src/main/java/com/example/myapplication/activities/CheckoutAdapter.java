package com.example.myapplication.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.dal.AppDatabase;
import com.example.myapplication.entities.OrderDetail;
import com.example.myapplication.entities.Product;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<OrderDetail> details;
    private AppDatabase db;

    public CheckoutAdapter(List<OrderDetail> details, AppDatabase db) {
        this.details = details;
        this.db = db;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        OrderDetail detail = details.get(position);
        Product product = db.productDao().getById(detail.productId);
        
        if (product != null) {
            holder.tvName.setText(product.name);
        }
        holder.tvQuantity.setText("x" + detail.quantity);
        holder.tvPrice.setText("$" + (detail.unitPrice * detail.quantity));
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvQuantity, tvPrice;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCartProductName);
            tvQuantity = itemView.findViewById(R.id.tvCartQuantity);
            tvPrice = itemView.findViewById(R.id.tvCartPrice);
        }
    }
}
