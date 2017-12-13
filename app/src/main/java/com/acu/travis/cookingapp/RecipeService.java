package com.acu.travis.cookingapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by admin on 12/4/17.
 */

public interface RecipeService {

    @GET("/api/?")
    Call<RecipeResult> searchIngredients(@Query("i") String ingredients);


}
