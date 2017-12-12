package com.example.travis.cookingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.travis.cookingapp.database.DataSource;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    DataSource mDataSource;
    public  List<FoodResult> Favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mDataSource = new DataSource(this);
        mDataSource.open();

        Favorites = mDataSource.getAllItems();
    }
}
