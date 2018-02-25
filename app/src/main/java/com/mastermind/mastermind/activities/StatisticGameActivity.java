package com.mastermind.mastermind.activities;


import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.adapter.StatisticListViewAdapter;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;
import com.mastermind.mastermind.service.DBhandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StatisticGameActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    ListView menu;
    List<StatisticGame> items;
    SimpleDateFormat formatDate = new SimpleDateFormat("dd MM yyyy");


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticlist);


            DBhandler db = new DBhandler(this);
            Date date = new Date();
            String dateS = formatDate.format(date);

            List<StatisticGame> items = db.getAllStat();





            menu = (ListView) findViewById(R.id.statisticMenuListView);


            StatisticListViewAdapter adapter = new StatisticListViewAdapter(this,
                    R.layout.statisticlistitem, items);
            menu.setAdapter(adapter);
            menu.setOnItemClickListener(this);



    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
