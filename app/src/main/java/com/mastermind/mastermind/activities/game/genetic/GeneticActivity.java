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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.game.GameVariantActivity;
import com.mastermind.mastermind.activities.game.user.EndGameActivity;
import com.mastermind.mastermind.activities.game.user.MainGameActivity;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.genetic.Population;
import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;
import com.mastermind.mastermind.service.db.DBhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class GeneticActivity extends AppCompatActivity {

    private List<ColorEnum> randColors ;

    private ColorList colorList ;

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

    }

    private void addToStatistic(int attemptsSize) {

        String date = new Date().toString();
        StatisticGame stat = new StatisticGame(date,variantGame,attemptsSize, SolutionEnum.GENETIC_ALG);
        db.addStat(stat);

    }

    private void copyBestMember(List<ColorEnum> attempt, Genotype bestMember) {

        List<ColorEnum> chromosome = bestMember.getChromosome();


       for(int i=0;i<chromosome.size();i++)
        {
            attempt.add(chromosome.get(i));
        }

    }

    private List<BlackBoxEnum>  checkAttempt(List<ColorEnum> colorsId, List<ColorEnum> randColors)
    {

        List<BlackBoxEnum> blackBox = new ArrayList<BlackBoxEnum>();

        Boolean[] array = new Boolean[randColors.size()];
        Arrays.fill(array, Boolean.FALSE);


        // BLACK
        for(int i=0;i<colorsId.size();i++)
        {
            ColorEnum currentColor = colorsId.get(i);

            if(currentColor.equals(randColors.get(i)))
            {
                blackBox.add(BlackBoxEnum.BLACK);
                array[i]=true;
            }
        }


        //WHITE

        for(int i=0;i<colorsId.size();i++){

            ColorEnum currentColor = colorsId.get(i);

            for(int j=0;j<randColors.size();j++)
            {
                if(i!=j && currentColor.equals(randColors.get(j)))
                {
                    if(array[j]==false) {
                        blackBox.add(BlackBoxEnum.WHITE);
                        array[j] = true;
                    }
                }
            }

        }

        return blackBox;
    }

    public int getColorValue(List<BlackBoxEnum> blackBox , BlackBoxEnum color){

        int colorCount = 0;

        for(BlackBoxEnum colorBox: blackBox){

            if(colorBox.equals(color)) colorCount++;
        }

        return colorCount;

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
             geneticAlgorithm();
                return null;
            }
        }.execute();




    }



    private void geneticAlgorithm() {



        colorList= new ColorList(variantGame);

        randColors = colorList.getColorsRand();

        List<Integer> currentAttepmt = new ArrayList<Integer>();

        Population population = new Population(variantGame,POPULATION_SIZE,MUTATE_PROB,CROSS_PROB);
        population.initPopulation(prevAttempt,prevBlack,prevWhite);


        if(variantGame.equals(GameVariantEnum.CLASSIC)){
            chromomoseSize = 4;
        }else
            chromomoseSize = 5;

        List<ColorEnum> attempt;

        Integer generationCount = 0;


        do{


            Log.i("GEN COUNT", generationCount.toString());
            population.newPopulation(prevAttempt,prevBlack,prevWhite);

            population.statistic();
            Genotype bestMember = population.getBestMember();
            attempt = new ArrayList<ColorEnum>();
            copyBestMember(attempt,bestMember);

            List<BlackBoxEnum> blackBox = checkAttempt(attempt,randColors);
            black = getColorValue(blackBox,BlackBoxEnum.BLACK);
            white = getColorValue(blackBox,BlackBoxEnum.WHITE);

            prevBlack.add(black);
            prevWhite.add(white);
            prevAttempt.add(new Genotype(attempt));

            generationCount++;

        }while(black!= chromomoseSize && generationCount<GENERATION_SIZE);




        Log.i("RANDOM COLORS",randColors.toString());

        for(Genotype member: prevAttempt)
        {
            Log.i("ATTEMPTS",member.getChromosome().toString());
        }

        if(generationCount < GENERATION_SIZE - 1)
            addToStatistic(prevAttempt.size());


        endGame(prevAttempt.size());


    }

    private void endGame(int size) {

        Intent intent = new Intent(GeneticActivity.this,EndGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("solution","Algorytm");
        bundle.putString("variant",variantGame.toString());
        bundle.putString("attemptCount",Integer.toString(size));

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
