package com.example.travis.cookingapp;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aggie on 11/28/2017.
 */

public class FoodJsonParser {

    public static List<FoodResult> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readResultsArray(reader);
        } finally {
            reader.close();
        }
    }

    private static List<FoodResult> readResultsArray(JsonReader reader) throws IOException {
        List<FoodResult> results = new ArrayList<FoodResult>();
        String title, version, href;
        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            } else if(name.equals("version")) {
                version = reader.nextString();
            } else if(name.equals("href")) {
                href = reader.nextString();
            } else if (name.equals("results")) {
                reader.beginArray();
                while (reader.hasNext()) {
                    results.add(readResult(reader));
                }
                reader.endArray();
            }
        }
        reader.endObject();
        return results;
    }

    private static FoodResult readResult(JsonReader reader) throws IOException {
        String title = "";
        String href = "";
        List<String> ingredients = new ArrayList<String>();
        String thumbnail = "";

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("title")) {
                title = reader.nextString();
            } else if (name.equals("href")) {
                href = reader.nextString();
            } else if (name.equals("ingredients")) {
                ingredients = readIngredients(reader);
            } else if (name.equals("thumbnail")) {
                thumbnail = reader.nextString();
            }
        }
        reader.endObject();

        return new FoodResult(title, href, ingredients, thumbnail);
    }

    private static List<String> readIngredients(JsonReader reader) throws IOException {
        List<String> ingredients = new ArrayList<String>();
        String ingredientString = reader.nextString();
        ingredients = Arrays.asList(ingredientString.split(","));
        return ingredients;
    }
}


