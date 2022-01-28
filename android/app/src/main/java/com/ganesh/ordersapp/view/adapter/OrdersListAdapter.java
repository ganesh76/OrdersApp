package com.ganesh.ordersapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ganesh.ordersapp.R;
import com.ganesh.ordersapp.db.Orders;


import java.util.ArrayList;
import java.util.List;

public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.OrdersViewHolder> {

    private List<Orders> orderModels;
    private Context context;
    private OrderClickListener orderClickListener;
    private String orderPlacedText;
    private String orderPendingText;

    public OrdersListAdapter(Context context, List<Orders> orderModels) {
        this.context = context;
        this.orderModels = orderModels;
        this.orderClickListener = (OrderClickListener) context;
        orderPlacedText = context.getString(R.string.order_status) + " : " + context.getString(R.string.placed);
        orderPendingText = context.getString(R.string.order_status) + " : " + context.getString(R.string.pending);
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row_item, parent, false);

        return new OrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {
        final Orders orderModel = orderModels.get(position);
        holder.title.setText(orderModel.getId() + " : " + orderModel.getTitle());
        if (orderModel.getCompleted())
            holder.status.setText(orderPlacedText);
        else
            holder.status.setText(orderPendingText);

        holder.parentRow.setOnClickListener(view -> orderClickListener.onOrderItemClick(orderModel));
    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout parentRow;
        public TextView title, status;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            parentRow = itemView.findViewById(R.id.parentRow);
            title = itemView.findViewById(R.id.order_title);
            status = itemView.findViewById(R.id.order_status);
        }
    }

    public void setOrderModels(ArrayList<Orders> orderModels) {
        this.orderModels = orderModels;
        notifyDataSetChanged();
    }

    public interface OrderClickListener {
        public void onOrderItemClick(Orders orderModel);
    }
}