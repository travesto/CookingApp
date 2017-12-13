package com.acu.travis.cookingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.acu.travis.cookingapp.FoodResult;

import java.util.ArrayList;
import java.util.List;


public class DataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public FoodResult createItem(FoodResult item) {
        ContentValues values = item.toValues();
        mDatabase.insert(ItemsTable.TABLE_ITEMS, null, values);
        return item;
    }


    public long getFoodResultsCount() {
        return DatabaseUtils .queryNumEntries(mDatabase, ItemsTable.TABLE_ITEMS);
    }

    public void seedDatabase(List<FoodResult> dataItemList) {
        long numItems = getFoodResultsCount();
        if (numItems == 0) {
            for (FoodResult item : dataItemList) {
                try {
                    createItem(item);
                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<FoodResult> getAllItems() {
        List<FoodResult> foodResults = new ArrayList<>();
        Cursor cursor = mDatabase.query(ItemsTable.TABLE_ITEMS, ItemsTable.ALL_COLUMNS, null, null, null, null, null, null);
        while(cursor.moveToNext()) {
            FoodResult item = new FoodResult();
            item.setTitle(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ID)));
            item.setIngredients(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_INGREDIENTS)));
            item.setHref(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_HREF)));
            item.setThumbnail(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_PHOTO)));
            item.setFavorite(true);
            foodResults.add(item);
        }

        return foodResults;
    }

    public void deleteItem(FoodResult item) {
        mDatabase.delete(ItemsTable.TABLE_ITEMS, ItemsTable.COLUMN_ID + "=?", new String[]{item.getTitle()});
    }



}
