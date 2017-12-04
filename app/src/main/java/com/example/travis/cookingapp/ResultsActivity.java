package com.example.travis.cookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private RecyclerView mResultsView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String query = intent.getStringExtra("query");
        Log.d("Results Activity", "Searched for: " + query);

        // Initialize REST client
        mService = APIUtils.getRecipeService();

        // Set-up results list
        mResultsView = (RecyclerView) findViewById(R.id.resultsList);
        mResultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mResultsView.setLayoutManager(mLayoutManager);

        // Set-up results list adapter
        mAdapter = new FoodAdapter(ResultsActivity.this, new ArrayList<FoodResult>(0), new FoodAdapter.PostItemListener() {
            @Override
            public void onPostClick(String title, String href) {
                Log.d("Results onClick", "Title is " + title);
                Log.d("Results onClick", "Link: " + href);
            }
        });
        mResultsView.setAdapter(mAdapter);
        getResults(query);
    }

    public void getResults(String query) {
        mService.searchIngredients(query).enqueue(new Callback<RecipeResult>() {
            @Override
            public void onResponse(Call<RecipeResult> call, Response<RecipeResult> response) {
                if (response.isSuccessful()) {
                    mAdapter.updateResults(response.body().getResults());
                } else {
                    Log.e("Results Activity", "Call error");
                }
            }

            @Override
            public void onFailure(Call<RecipeResult> call, Throwable t) {
                Log.e("MainActivity", "Call failed.");
            }
        });
    }
}
