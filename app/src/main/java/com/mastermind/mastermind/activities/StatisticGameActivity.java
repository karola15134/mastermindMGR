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
import com.mastermind.mastermind.service.DBhandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StatisticGameActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    ListView menu;
    List<StatisticGame> items;
    DBhandler db = new DBhandler(this);
    SimpleDateFormat formatDate = new SimpleDateFormat("dd MM yyyy");


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticlist);


        items = new ArrayList<StatisticGame>();


        for(int i=0;i<5;i++){
            StatisticGame stat = new StatisticGame(new Date(),5,10);
            StatisticGame stat2 = new StatisticGame(new Date(),7,20);
          //  db.addStat(stat);
         //   db.addStat(stat2);
            items.add(stat);
            items.add(stat2);
        }








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
