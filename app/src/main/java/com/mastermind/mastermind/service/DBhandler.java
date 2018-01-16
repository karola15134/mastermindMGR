package com.mastermind.mastermind.service;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mastermind.mastermind.bean.db.StatisticGame;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db";
    private static final String TABLE_STATISTIC_GAME = "statistics";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_COLORS = "colors_count";
    private static final String KEY_ATTEMPT = "attempts_count";

    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_STATISTIC_TABLE = " CREATE TABLE " + TABLE_STATISTIC_GAME + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " DATE,"
        + KEY_COLORS + " INTEGER," + KEY_ATTEMPT  + " INTEGER" + ")" ;

        sqLiteDatabase.execSQL(CREATE_STATISTIC_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_STATISTIC_GAME);
        onCreate(sqLiteDatabase);

    }

    public void addStat(StatisticGame stat)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_COLORS,stat.getColorsCount());
        values.put(KEY_ATTEMPT,stat.getAttemptsCount());
        values.put(KEY_DATE, String.valueOf(stat.getDate()));

        db.insert(TABLE_STATISTIC_GAME,null,values);
        db.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<StatisticGame> getAllStat() throws ParseException {
        List<StatisticGame> listOfStat = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_GAME;

        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
    //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        Date date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StatisticGame stat = new StatisticGame();
                stat.setId(Integer.parseInt(cursor.getString(0)));
                date = format.parse(cursor.getString(1));
                stat.setDate(date);
                stat.setColorsCount(Integer.parseInt(cursor.getString(2)));
                stat.setAttemptsCount(Integer.parseInt(cursor.getString( 3)));

                listOfStat.add(stat);
            } while (cursor.moveToNext());


        }
        return listOfStat;
    }
}
