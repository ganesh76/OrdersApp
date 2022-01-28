package com.ordersapp.di;

import com.ordersapp.view.OrdersListActivity;

import dagger.Component;

@Component(modules = NetworkModule.class)
public interface AppComponent {

    void inject(OrdersListActivity activity);
}
