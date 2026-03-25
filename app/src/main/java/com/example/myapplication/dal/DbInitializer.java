package com.example.myapplication.dal;

import android.content.Context;

import com.example.myapplication.entities.Category;
import com.example.myapplication.entities.Product;
import com.example.myapplication.entities.User;

public class DbInitializer {
    public static void initialize(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        if (db.userDao().countUsers() == 0) {
            db.userDao().insert(new User("admin", "123", "Administrator"));
            db.userDao().insert(new User("user", "123", "Normal User"));
        }

        if (db.categoryDao().countCategories() == 0) {
            db.categoryDao().insert(new Category("Electronics"));
            db.categoryDao().insert(new Category("Clothing"));
            db.categoryDao().insert(new Category("Home & Kitchen"));
        }

        if (db.productDao().countProducts() == 0) {
            db.productDao().insert(new Product("Laptop", 1200.0, "High performance laptop", 1));
            db.productDao().insert(new Product("Smartphone", 800.0, "Latest smartphone", 1));
            db.productDao().insert(new Product("T-Shirt", 20.0, "Cotton t-shirt", 2));
            db.productDao().insert(new Product("Jeans", 40.0, "Blue denim jeans", 2));
            db.productDao().insert(new Product("Blender", 50.0, "Kitchen blender", 3));
        }
    }
}
