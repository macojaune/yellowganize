package com.marvinl.yellowganize;

import android.graphics.Bitmap;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Marvin on 16/12/2017.
 */

@Entity
public class Post {
  @PrimaryKey
    private int id;

    private Date date;
    private Time time;
    private String picture;
    private String caption;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
