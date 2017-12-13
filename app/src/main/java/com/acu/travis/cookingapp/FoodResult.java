package com.acu.travis.cookingapp;

/**
 * Created by aggie on 12/1/2017.
 */

import android.content.ContentValues;

import com.acu.travis.cookingapp.database.ItemsTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodResult {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("ingredients")
    @Expose
    private String ingredients;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    // Tracks favorites
    private boolean isFavorite;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean getFavorite() { return isFavorite; }

    public void setFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(8);

        values.put(ItemsTable.COLUMN_ID, title);
        values.put(ItemsTable.COLUMN_INGREDIENTS, ingredients);
        values.put(ItemsTable.COLUMN_HREF, href);
        values.put(ItemsTable.COLUMN_PHOTO, thumbnail);
        return values;
    }

}
