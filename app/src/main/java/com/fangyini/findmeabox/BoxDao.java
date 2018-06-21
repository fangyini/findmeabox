package com.fangyini.findmeabox;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BoxDao {

    @Query("SELECT * FROM box")
    List<Box> loadAllBoxes();

    @Insert
    void insertBox(Box box);

    @Delete
    void deleteBox(Box box);

    @Query("SELECT * FROM box WHERE id=:id")
    Box loadBoxById(int id);

}
