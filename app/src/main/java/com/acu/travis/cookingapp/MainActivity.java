package com.acu.travis.cookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.acu.travis.cookingapp.database.DataSource;

public class MainActivity extends AppCompatActivity {

    DataSource mDataSource;

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDataSource = new DataSource(this);
        mDataSource.open();


    }



    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        EditText editText = (EditText) findViewById(R.id.searchText);
        String message = "Results for " + editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra("query", editText.getText().toString());
        startActivity(intent);
    }

    public void favorites(View view) {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }
}
