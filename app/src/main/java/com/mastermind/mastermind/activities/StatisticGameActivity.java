package com.mastermind.mastermind.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.adapter.StatisticListViewAdapter;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;
import com.mastermind.mastermind.service.DBhandler;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StatisticGameActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    ListView menu;
    List<StatisticGame> items = new ArrayList<StatisticGame>();
    SimpleDateFormat formatDate = new SimpleDateFormat("dd MM yyyy");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statisticlist);

        final DBhandler db = new DBhandler(this);
        final Spinner spinner = (Spinner) findViewById(R.id.sortSpinner);


        for (int i = 0; i < 10; i++) {
            db.addStat(new StatisticGame(new Date().toString(), GameVariantEnum.CLASSIC, 10, SolutionEnum.GENETIC_ALG));
            db.addStat(new StatisticGame(new Date().toString(), GameVariantEnum.CLASSIC, 10, SolutionEnum.USER));
        }


        List<StatisticGame> itemsAll = db.getAllStat();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                List<StatisticGame> itemsAll = db.getAllStat();

                switch ((int) position) {
                    case 0:
                        StatisticListViewAdapter adapter = new StatisticListViewAdapter(StatisticGameActivity.this,
                                R.layout.statisticlistitem, itemsAll);
                        menu.setAdapter(adapter);
                        break;

                    case 1:
                        List<StatisticGame> itemsUser = new ArrayList<StatisticGame>();
                        for (StatisticGame game : itemsAll) {
                            if (game.getTypeOfSolution().equals(SolutionEnum.USER)) {
                                itemsUser.add(game);
                            }
                        }

                        StatisticListViewAdapter adapterU = new StatisticListViewAdapter(StatisticGameActivity.this,
                                R.layout.statisticlistitem, itemsUser);
                        menu.setAdapter(adapterU);
                        break;

                    case 2:
                        List<StatisticGame> itemsGenetic = new ArrayList<StatisticGame>();
                        for (StatisticGame game : itemsAll) {
                            if (game.getTypeOfSolution().equals(SolutionEnum.GENETIC_ALG)) {
                                itemsGenetic.add(game);
                            }
                        }

                        StatisticListViewAdapter adapterG = new StatisticListViewAdapter(StatisticGameActivity.this,
                                R.layout.statisticlistitem, itemsGenetic);
                        menu.setAdapter(adapterG);
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {


            }
        });


        menu = (ListView) findViewById(R.id.statisticMenuListView);


        StatisticListViewAdapter adapter = new StatisticListViewAdapter(this,
                R.layout.statisticlistitem, items);
        menu.setAdapter(adapter);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    public void onButtonClick(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Jesteś pewien? \nPotwierdzenie oznacza usunięcie wszystkich statystyk");

        alertDialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                DBhandler db = new DBhandler(StatisticGameActivity.this);
                List<StatisticGame> list = db.getAllStat();

                for (StatisticGame game : list) {
                    db.deleteStat(game);
                }

                list=db.getAllStat();
                Toast.makeText(StatisticGameActivity.this, "Usunieto", Toast.LENGTH_LONG).show();

                menu = (ListView) findViewById(R.id.statisticMenuListView);
                StatisticListViewAdapter adapterE = new StatisticListViewAdapter(StatisticGameActivity.this,
                        R.layout.statisticlistitem, list);
                menu.setAdapter(adapterE);
            }
        });

        alertDialogBuilder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    }
