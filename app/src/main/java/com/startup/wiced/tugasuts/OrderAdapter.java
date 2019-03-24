package com.startup.wiced.tugasuts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private ArrayList<Order> order;
    private static RVClickListener mylistener;

    public OrderAdapter(ArrayList<Order> order,RVClickListener rvcl) {
        this.order = order;
        mylistener = rvcl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.row_item_order, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvQty.setText(order.get(i).getQty() + " " + order.get(i).getType());
        viewHolder.tvTopping.setText(order.get(i).getToppings().toString());
        viewHolder.tvSubtotal.setText(""+order.get(i).getSubtotal());
    }

    @Override
    public int getItemCount() {
        return (order!=null)?order.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvQty,tvTopping,tvSubtotal;
        private ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //findViewById
            tvQty = itemView.findViewById(R.id.textView_qty_type);
            tvTopping = itemView.findViewById(R.id.textView_toppings);
            tvSubtotal = itemView.findViewById(R.id.textView_subtotal);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                mylistener.recyclerViewListClicked(v, ViewHolder.this.getLayoutPosition());
                //getLayoutPosition() mendapatkan posisi ViewHolder yg di click
                }
            });
        }
    }
}
