package com.example.travis.cookingapp.database;

import android.graphics.Bitmap;

public class ItemsTable {
    public static final String TABLE_ITEMS = "favorites";
    public static final String COLUMN_ID = "title";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_HREF = "href";
    public static final String COLUMN_PHOTO = "photo";

    public static final String[] ALL_COLUMNS =
            {COLUMN_ID, COLUMN_INGREDIENTS, COLUMN_HREF, COLUMN_PHOTO};


    public static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_ITEMS + "(" +
                    COLUMN_ID + " TEXT PRIMARY KEY," +
                    COLUMN_INGREDIENTS + " TEXT," +
                    COLUMN_HREF + " TEXT," +
                    COLUMN_PHOTO + " TEXT" + ");";
    public static final String SQL_DELETE =
            "DROP TABLE " + TABLE_ITEMS;
}
