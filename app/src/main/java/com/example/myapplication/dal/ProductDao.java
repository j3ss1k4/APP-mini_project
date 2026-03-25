package com.example.myapplication.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.entities.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Insert
    void insert(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE categoryId = :categoryId")
    List<Product> getByCategoryId(int categoryId);

    @Query("SELECT * FROM products WHERE id = :id")
    Product getById(int id);

    @Query("SELECT COUNT(*) FROM products")
    int countProducts();
}
