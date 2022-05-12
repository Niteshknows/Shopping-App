package com.example.shoppingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.model.Item;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {

    private List<Item> data;

    public CheckoutAdapter(List<Item> data){
        this.data=data;
    }

    @NonNull
    @Override
    public CheckoutAdapter.CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.checkout_item,parent,false);
        return new CheckoutAdapter.CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutAdapter.CheckoutViewHolder holder, int position) {

        String title=data.get(position).getName();
        holder.name_tv.setText(title);
        String quantity= String.valueOf(data.get(position).getQuantity());
        holder.quantity_tv.setText(quantity);
        int amount = data.get(position).getCost()*data.get(position).getQuantity();
        String amt= String.valueOf(amount);
        holder.price_tv.setText(amt);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CheckoutViewHolder extends RecyclerView.ViewHolder{
        TextView name_tv;
        TextView price_tv;
        TextView quantity_tv;
        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv= itemView.findViewById(R.id.checkout_name_tv);
            price_tv= itemView.findViewById(R.id.checkout_price_tv);
            quantity_tv= itemView.findViewById(R.id.checkout_quantity_tv);
        }
    }
}
