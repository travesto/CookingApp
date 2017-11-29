package com.example.travis.cookingapp;

import java.util.List;

/**
 * Created by aggie on 11/28/2017.
 */

public class FoodResult {
    public String Title;
    public String Href;
    public List<String> Ingredients;
    public String Thumbnail;

    public FoodResult(String title, String href, List<String> ingredients, String thumbnail)
    {
        Title = title;
        Href = href;
        Ingredients = ingredients;
        Thumbnail = thumbnail;
    }
}
