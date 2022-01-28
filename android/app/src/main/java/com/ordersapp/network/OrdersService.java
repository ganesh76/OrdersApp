package com.ordersapp.network;

import com.ordersapp.db.Orders;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface OrdersService {
    @GET("/todos")
    Observable<List<Orders>> getOrders();
}
