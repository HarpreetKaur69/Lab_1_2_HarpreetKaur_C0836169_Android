package com.example.lab_1_2_harpreetkaur_c0836169_android.roomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE name LIKE :query")
    List<Product> search(String query);

    @Insert
    void insert(Product task);

    @Delete
    void delete(Product task);

    @Update
    void update(Product task);

}
