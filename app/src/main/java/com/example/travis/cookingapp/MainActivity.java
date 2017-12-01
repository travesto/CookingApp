package com.example.travis.cookingapp;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mResultsView;
    private FoodAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecipeService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize REST client
        mService = APIUtils.getRecipeService();

        // Set-up results list
        mResultsView = (RecyclerView) findViewById(R.id.resultsList);
        mResultsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mResultsView.setLayoutManager(mLayoutManager);

        // Set-up results list adapter
        mAdapter = new FoodAdapter(MainActivity.this, new ArrayList<FoodResult>(0), new FoodAdapter.PostItemListener() {
            @Override
            public void onPostClick(String title) {
                Log.d("onClickDebug", "Title is " + title);
            }
        });
        mResultsView.setAdapter(mAdapter);
        getResults();
    }

    public void getResults() {
        mService.searchIngredients("tomato").enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful()) {
                    mAdapter.updateResults(response.body().getResults());
                    Log.d("Main Activity", "Got recipes");
                } else {
                    Log.e("Main Activity", "Call error");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e("MainActivity", "Call failed.");
            }
        });
    }
}
