package com.fangyini.findmeabox;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity(tableName = "box")
public class Box {

    @PrimaryKey(autoGenerate = true)
    private int id;
    //private String username;
    //private float lat;
    //private float lon;
    private String content;
    private Date date;

    Box(int id, /*String username, float lat, float lon,*/ String content, Date date) {
        this.id = id;
        //this.username = username;
        //this.lat = lat;
        //this.lon = lon;
        this.content = content;
        this.date = date;
    }

    @Ignore
    Box(/*String username, float lat, float lon,*/ String content, Date date) {
        //this.username = username;
        //this.lat = lat;
        //this.lon = lon;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public String getUsername() {
        return username;
    }*/

    /*public void setUserName(String username) {
        this.username = username;
    }*/

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
