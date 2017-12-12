package com.example.travis.cookingapp;

/**
 * Created by aggie on 12/1/2017.
 */

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.travis.cookingapp.database.ItemsTable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;

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
    private Bitmap thumbnail;

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

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = getImage(thumbnail);
    }

    // convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    // convert from byte array to bitmap
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(8);

        values.put(ItemsTable.COLUMN_ID, title);
        values.put(ItemsTable.COLUMN_INGREDIENTS, ingredients);
        values.put(ItemsTable.COLUMN_HREF, href);
        values.put(ItemsTable.COLUMN_PHOTO, getBytes(thumbnail));
        return values;
    }

}
