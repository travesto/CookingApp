package com.acu.travis.cookingapp;

/**
 * Created by aggie on 12/1/2017.
 */

public class APIUtils {
    public static final String baseUrl = "http://www.recipepuppy.com";

    public static RecipeService getRecipeService() {
        return FoodREST.getClient(baseUrl).create(RecipeService.class);
    }
}
