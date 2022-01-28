package com.ganesh.ordersapp.network;

import com.squareup.moshi.Json;

public class Orders {
    @Json(name = "userId")
    int userId;
    @Json(name = "id")
    int id;
    @Json(name = "title")
    String title;
    @Json(name = "completed")
    boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


}
