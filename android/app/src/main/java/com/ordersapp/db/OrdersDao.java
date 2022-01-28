package com.ordersapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Observable;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface OrdersDao {
    @Query("SELECT * FROM Orders")
    Single<List<Orders>> getAllOrders();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertOrders(List<Orders> orders);

    @Query("UPDATE orders SET completed = :status WHERE id = :id")
    Single<Integer> updateOder(int id, boolean status);
}
