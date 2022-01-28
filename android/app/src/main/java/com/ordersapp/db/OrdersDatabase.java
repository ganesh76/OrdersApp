package com.ordersapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Orders.class}, version = 1, exportSchema = false)
public abstract class OrdersDatabase extends RoomDatabase {

    public abstract OrdersDao wordDao();

    private static volatile OrdersDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static OrdersDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OrdersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OrdersDatabase.class, "orders_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
