package com.mastermind.mastermind.algorithm;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.mastermind.mastermind.activities.game.EndGameActivity;
import com.mastermind.mastermind.activities.game.TestActivity;
import com.mastermind.mastermind.activities.game.genetic.GeneticActivity;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.genetic.Population;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;
import com.mastermind.mastermind.service.db.DBhandler;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GeneticAlgorithm {

    private List<ColorEnum> colorsRandom ;

    private Double crossProb;

    private  Double mutateProb;

    private Integer popSize;

    private GameVariantEnum variantGame;

    private List<Genotype> prevAttempt = new ArrayList<Genotype>();

    private List<Integer> prevBlack = new ArrayList<Integer>();

    private List<Integer> prevWhite = new ArrayList<Integer>();

    private Integer chromomoseSize ;

    private Integer black;

    private Integer white;

    private DBhandler db ;

    private Context context;





    public GeneticAlgorithm(List<ColorEnum> colorsRandom, Double crossProb, Double mutateProb, Integer popSize, GameVariantEnum variantGame,Context context) {
        this.colorsRandom = colorsRandom;
        this.crossProb = crossProb;
        this.mutateProb = mutateProb;
        this.popSize = popSize;
        this.variantGame = variantGame;
        this.chromomoseSize = colorsRandom.size();
        this.context = context;
        this.prevAttempt = new ArrayList<>();
        this.db = new DBhandler(context);
    }


    public void mainGame(){

        List<Integer> currentAttepmt = new ArrayList<Integer>();
        Population population = new Population(variantGame,popSize,mutateProb,crossProb);
        population.initPopulation(prevAttempt,prevBlack,prevWhite);
        List<ColorEnum> attempt;

        do{

            population.newPopulation(prevAttempt,prevBlack,prevWhite);

            population.statistic();
            Genotype bestMember = population.getBestMember();
            attempt = new ArrayList<ColorEnum>();
            copyBestMember(attempt,bestMember);

            List<BlackBoxEnum> blackBox = checkAttempt(attempt,colorsRandom);
            black = getColorValue(blackBox,BlackBoxEnum.BLACK);
            white = getColorValue(blackBox,BlackBoxEnum.WHITE);

            prevBlack.add(black);
            prevWhite.add(white);
            prevAttempt.add(new Genotype(attempt));

        }while(black!= chromomoseSize);


        Log.i("RANDOM COLORS",colorsRandom.toString());

        for(Genotype member: prevAttempt)
        {
            Log.i("ATTEMPTS",member.getChromosome().toString());
        }


        addToStatistic(prevAttempt.size());

        //endGame(prevAttempt);

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

   public void addToStatistic(int attemptsSize) {

        String date = new Date().toString();
        StatisticGame stat = new StatisticGame(date,variantGame,attemptsSize, SolutionEnum.GENETIC_ALG);
        db.addStat(stat);

    }

    public void endGame(List<Genotype> attemptsList) {

        Intent intent = new Intent(context,EndGameActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("solution","Algorytm");
        bundle.putString("variant",variantGame.toString());
        bundle.putString("attemptCount",Integer.toString(attemptsList.size()));
        bundle.putSerializable("list",(Serializable) attemptsList);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public List<Genotype> getPrevAttempt() {
        return prevAttempt;
    }
}
