package com.ganesh.ordersapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ganesh.ordersapp.MainApplication;
import com.ganesh.ordersapp.R;
import com.ganesh.ordersapp.db.Orders;
import com.ganesh.ordersapp.db.OrdersDatabase;
import com.ganesh.ordersapp.network.OrdersService;
import com.ganesh.ordersapp.view.adapter.OrdersListAdapter;

import org.json.JSONObject;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrdersListActivity extends AppCompatActivity implements OrdersListAdapter.OrderClickListener {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    OrdersListAdapter ordersListAdapter;
    @Inject
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((MainApplication) getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_list);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerView);
        OrdersService api = retrofit.create(OrdersService.class);
        MainApplication mainApplication = (MainApplication) getApplication();
        mainApplication.getIsOrderUpdated().observe(this, aBoolean -> {
            if (aBoolean) {
                refreshUi();
            }

        });
        progressBar.setVisibility(View.VISIBLE);
        OrdersDatabase.getDatabase(this).wordDao().getAllOrders().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(orders -> {
            if (orders.size() > 0) {
                ordersListAdapter = new OrdersListAdapter(OrdersListActivity.this, orders);
                recyclerView.setAdapter(ordersListAdapter);
                progressBar.setVisibility(View.GONE);
            } else {
                api.getOrders().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(ordersModels -> {
                            // Log.e("Data:", "" + ordersModels.size());
                            OrdersDatabase.getDatabase(this).wordDao().insertOrders(ordersModels).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                                    () -> {
                                        try {
                                            ordersListAdapter = new OrdersListAdapter(OrdersListActivity.this, ordersModels);
                                            recyclerView.setAdapter(ordersListAdapter);
                                            progressBar.setVisibility(View.GONE);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        // }
                                        //  );
                                    });
                        });


            }
        });
    }

    @Override
    public void onOrderItemClick(Orders orderModel) {
        Log.e("orderModel", "" + orderModel.getTitle());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", orderModel.getUserId());
            jsonObject.put("id", orderModel.getId());
            jsonObject.put("title", orderModel.getTitle());
            jsonObject.put("completed", orderModel.getCompleted());
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainApplication.getAppInstance().setOrderData(jsonObject.toString());
        //  MainApplication.getAppInstance().changeOrderStatus(orderModel.getId());
        startActivity(new Intent(OrdersListActivity.this, MainActivity.class));
    }

    public void refreshUi() {
        OrdersDatabase.getDatabase(this).wordDao().getAllOrders().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                (ordersModels) -> {
                    try {
                        ordersListAdapter = new OrdersListAdapter(OrdersListActivity.this, ordersModels);
                        recyclerView.setAdapter(ordersListAdapter);
                        ordersListAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // }
                    //  );
                });
    }
}
