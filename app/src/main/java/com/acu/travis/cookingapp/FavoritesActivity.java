package com.acu.travis.cookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acu.travis.cookingapp.database.DataSource;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    DataSource mDataSource;
    public  List<FoodResult> Favorites;
    private RecyclerView mResultsView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        mDataSource = new DataSource(this);
        mDataSource.open();

        Favorites = mDataSource.getAllItems();
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set-up results list
        mResultsView = (RecyclerView) findViewById(R.id.resultsList);
        mResultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mResultsView.setLayoutManager(mLayoutManager);

        // Set-up results list adapter
        mAdapter = new FoodAdapter(this, new ArrayList<FoodResult>(0), new FoodAdapter.LinkButtonListener() {
            @Override
            public void onLinkClick(String href) {
                Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
                webIntent.putExtra("href", href);
                startActivity(webIntent);
            }
        }, new FoodAdapter.FavoriteButtonListener() {
            @Override
            public void onFavoriteClick(int adapterPosition, FoodResult item) {
                item.setFavorite(false);
                mDataSource.deleteItem(item);
                mAdapter.removeItem(adapterPosition);
            }
        });
        mResultsView.setAdapter(mAdapter);
        mAdapter.updateResults(Favorites);
    }
}
