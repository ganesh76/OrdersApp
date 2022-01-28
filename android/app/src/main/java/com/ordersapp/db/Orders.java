package com.ordersapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.moshi.Json;

@Entity
public class Orders {

    @Json(name = "id")
    @PrimaryKey
    private int id;

    @Json(name = "userId")
    @ColumnInfo(name = "userId")
    private int userId;

    @Json(name = "title")
    @ColumnInfo(name = "title")
    private String title;

    @Json(name = "completed")
    @ColumnInfo(name = "completed")
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
