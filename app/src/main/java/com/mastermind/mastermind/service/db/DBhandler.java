package com.mastermind.mastermind.service.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "statistic_db";
    private static final String TABLE_STATISTIC_GAME = "statistics";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_COLORS = "colors_count";
    private static final String KEY_ATTEMPT = "attempts_count";
    private static final String KEY_TYPE_OF_SOLUTION = "type_of_solution";

    public DBhandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_STATISTIC_TABLE2 = " CREATE TABLE " + TABLE_STATISTIC_GAME + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " DATE,"
        + KEY_COLORS + " TEXT," + KEY_ATTEMPT  + " INTEGER ," +  KEY_TYPE_OF_SOLUTION + " TEXT " + ")" ;

        sqLiteDatabase.execSQL(CREATE_STATISTIC_TABLE2);
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

        values.put(KEY_DATE, String.valueOf(stat.getDate()));
        values.put(KEY_ATTEMPT,stat.getAttemptsCount());
        values.put(KEY_COLORS,stat.getTypeOfGame().toString());
        values.put(KEY_TYPE_OF_SOLUTION, stat.getTypeOfSolution().toString() );

        Log.i(KEY_TYPE_OF_SOLUTION, "addStat: ");
        db.insert(TABLE_STATISTIC_GAME,null,values);
        db.close();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<StatisticGame> getAllStat() {
        List<StatisticGame> listOfStat = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_STATISTIC_GAME;

        SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy");
        String date;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StatisticGame stat = new StatisticGame();
                stat.setId(Integer.parseInt(cursor.getString(0)));
                date = (cursor.getString(1));
                stat.setDate(date);
                stat.setTypeOfGame(GameVariantEnum.valueOf(cursor.getString(2)));
                stat.setAttemptsCount(Integer.parseInt(cursor.getString( 3)));
                stat.setTypeOfSolution(SolutionEnum.valueOf(cursor.getString(4)));

                listOfStat.add(stat);
            } while (cursor.moveToNext());


        }
        return listOfStat;
    }

    public void deleteStat(StatisticGame stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STATISTIC_GAME, KEY_ID + " = ?",
                new String[] { String.valueOf(stat.getId()) });
        db.close();
    }
}
