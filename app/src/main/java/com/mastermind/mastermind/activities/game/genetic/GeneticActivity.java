package com.mastermind.mastermind.activities.game.genetic;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.game.GameVariantActivity;
import com.mastermind.mastermind.activities.game.EndGameActivity;
import com.mastermind.mastermind.algorithm.GeneticAlgorithm;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.genetic.Population;
import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;
import com.mastermind.mastermind.service.db.DBhandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GeneticActivity extends AppCompatActivity {

    private List<ColorEnum> randColors ;

    private ColorList colorList  ;

    private GameVariantEnum variantGame;

    private List<Genotype> prevAttempt = new ArrayList<Genotype>();

    private List<Integer> prevBlack = new ArrayList<Integer>();

    private List<Integer> prevWhite = new ArrayList<Integer>();

    private final static Integer POPULATION_SIZE = 100;

    private final static Integer GENERATION_SIZE=300;

    private final static double MUTATE_PROB = 4;

    private final static  double CROSS_PROB = 0.4;

    final DBhandler db = new DBhandler(this);

    private Integer chromomoseSize ;

    private Integer black;

    private Integer white;

    private ProgressBar spinner;

    private TextView text;

    GeneticAlgorithm geneticAlgorithm;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genetic);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        text = (TextView)findViewById(R.id.textView3) ;
        text.setVisibility(View.GONE);


        Bundle extras = getIntent().getExtras();
        variantGame = (GameVariantEnum) extras.get("gameVariant");

        colorList = new ColorList(variantGame);
        randColors = colorList.getColorsRand();
        geneticAlgorithm = new GeneticAlgorithm(randColors,CROSS_PROB,MUTATE_PROB,POPULATION_SIZE,variantGame,this);


    }



    public void newGameButClick(View view) {


        startActivity(new Intent(this,GameVariantActivity.class));


    }

    public void endbutClick(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Wyjdź z gry");

        alertDialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finishAffinity();
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


    public void geneticGameButClick(View view) {


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                spinner.setVisibility(View.VISIBLE);
                text.setVisibility(View.VISIBLE);

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                spinner.setVisibility(View.GONE);
                text.setVisibility(View.GONE);

            }

            @Override
            protected Void doInBackground(Void... params) {
             geneticAlgorithm.mainGame();

                return null;
            }
        }.execute();




    }

}
