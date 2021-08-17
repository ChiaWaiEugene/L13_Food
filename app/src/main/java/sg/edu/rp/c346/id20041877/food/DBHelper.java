package sg.edu.rp.c346.id20041877.food;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FOOD = "Food";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LOCATION = "location";
    private static final String COLUMN_COMMENT = "comment";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createFoodTableSql = "CREATE TABLE " + TABLE_FOOD + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_LOCATION + " TEXT, "
                + COLUMN_COMMENT + " TEXT, "
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createFoodTableSql);
        Log.i("info", createFoodTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        onCreate(db);
    }

    public long insertFood(String name, String location, String comment, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_LOCATION, location);
        values.put(COLUMN_COMMENT, comment);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_FOOD, null, values);
        db.close();
        Log.d("SQL INSERT", "" + result);
        return result;
    }

    public ArrayList<Food> getAllFood() {
        ArrayList<Food> FoodList = new ArrayList();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_LOCATION + ","
                + COLUMN_COMMENT + ","
                + COLUMN_STARS + " FROM " + TABLE_FOOD;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String comment = cursor.getString(3);
                int stars = cursor.getInt(4);

                Food newFood = new Food(id, name, location, comment, stars);
                FoodList.add(newFood);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return FoodList;
    }

    public ArrayList<Food> getAllFoodByStars(int starsFilter) {
        ArrayList<Food> FoodList = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_LOCATION, COLUMN_COMMENT, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_FOOD, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String location = cursor.getString(2);
                String comment = cursor.getString(3);
                int stars = cursor.getInt(4);

                Food newFood = new Food(id, name, location, comment, stars);
                FoodList.add(newFood);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return FoodList;
    }

    public int updateFood(Food data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_LOCATION, data.getLocation());
        values.put(COLUMN_COMMENT, data.getComment());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_FOOD, values, condition, args);
        db.close();
        return result;
    }

    public int deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_FOOD, condition, args);
        db.close();
        return result;
    }

}