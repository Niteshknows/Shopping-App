package com.example.shoppingapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapp.R;
import com.example.shoppingapp.data.ClickListener;
import com.example.shoppingapp.model.Item;

import java.lang.ref.WeakReference;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<Item> data;
    public ClickListener onClickListener;

    public CartAdapter(List<Item> data, ClickListener listener){
        this.data=data;
         onClickListener= listener;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(view,onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
       String title=data.get(position).getName();
       holder.name_tv.setText(title);
        String quantity= String.valueOf(data.get(position).getQuantity());
        holder.quantity_tv.setText(quantity);
        String price= String.valueOf(data.get(position).getCost());
        holder.price_tv.setText(price);
//        String title=data[position];
//        holder.name_tv.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{
        TextView name_tv;
        TextView price_tv;
        TextView quantity_tv;
        public ImageView editBtn;
        public ImageView deleteBtn;

        public CartViewHolder(@NonNull View itemView,ClickListener listener) {
            super(itemView);

            name_tv= itemView.findViewById(R.id.name_tv);
            price_tv= itemView.findViewById(R.id.price_tv);
            quantity_tv= itemView.findViewById(R.id.quantity_tv);
            editBtn=itemView.findViewById(R.id.editBtn);
            deleteBtn=itemView.findViewById(R.id.deleteBtn);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     onClickListener.onEditClicked(view,getBindingAdapterPosition());
                }
            });

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onDeleteClicked(view,getBindingAdapterPosition());
                }
            });
        }

    }
}
