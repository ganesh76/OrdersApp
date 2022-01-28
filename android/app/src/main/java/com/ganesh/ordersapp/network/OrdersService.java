package com.ganesh.ordersapp.network;

import com.ganesh.ordersapp.db.Orders;

import java.util.List;


import io.reactivex.Observable;
import retrofit2.http.GET;

public interface OrdersService {
    @GET("/todos")
    Observable<List<Orders>> getOrders();
}
