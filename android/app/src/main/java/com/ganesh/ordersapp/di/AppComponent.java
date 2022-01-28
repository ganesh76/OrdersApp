package com.ganesh.ordersapp.di;

import com.ganesh.ordersapp.view.OrdersListActivity;

import dagger.Component;

@Component(modules = NetworkModule.class)
public interface AppComponent {

    void inject(OrdersListActivity activity);
}
