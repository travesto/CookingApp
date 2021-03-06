package com.acu.travis.cookingapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;

import com.acu.travis.cookingapp.database.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultsActivity extends AppCompatActivity {

    private DataSource mDataSource;
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
        final String query = intent.getStringExtra("query");
        Log.d("Results Activity", "Searched for: " + query);

        // Initialize REST client
        mService = APIUtils.getRecipeService();

        // Initialize SQL data
        mDataSource = new DataSource(this);
        mDataSource.open();

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
                webIntent.putExtra("query", query);
                startActivityForResult(webIntent, 1);
            }
        }, new FoodAdapter.FavoriteButtonListener() {
            @Override
            public void onFavoriteClick(int adapterPosition, FoodResult item) {
                ImageButton favoriteButton = (ImageButton) mResultsView.findViewHolderForAdapterPosition(adapterPosition).itemView.findViewById(R.id.favoritesButton);

                if(item.getFavorite()) {
                    item.setFavorite(false);
                    mDataSource.deleteItem(item);
                    // Use the adapter position to find the specific view instance and unhighlight button
                    favoriteButton.setColorFilter(Color.rgb(170, 170, 170));
                } else {
                    item.setFavorite(true);
                    mDataSource.createItem(item);
                    // Inverse of above
                    favoriteButton.setColorFilter(Color.rgb(251,186,66));
                }
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
                    List<FoodResult> queryResults = response.body().getResults();
                    HashMap<String, Boolean> favorites = new HashMap<>();
                    for(FoodResult result : mDataSource.getAllItems())
                        favorites.put(result.getTitle(), result.getFavorite());

                    for(FoodResult result: queryResults)
                    {
                        if (favorites.get(result.getTitle()) != null)
                            result.setFavorite(true);
                    }
                    mAdapter.updateResults(response.body().getResults());
                } else {
                    Log.e("Results Activity", "Call error");
                }
            }

            @Override
            public void onFailure(Call<RecipeResult> call, Throwable t) {
                Log.e("Results Activity", "Call failed.");
                Log.e("Results Activity", t.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String query = data.getStringExtra("query");
            Log.d("Results Feedback", "Got query: " + query);
            getResults(query);
        }
    }
}
