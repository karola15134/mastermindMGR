package com.mastermind.mastermind.bean.game.genetic;


import android.util.Log;

import com.mastermind.mastermind.bean.game.user.ReverseColorMap;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Genotype {

    private List<ColorEnum> chromosome; //4 lub 5 genów ( rozwiazanie ) do koloru przypisana liczba


    private double fittness;   //wartosc funkcji przystosowania


    public Genotype(List<ColorEnum> chromosome) {
        this.chromosome = chromosome;
    }

    public List<ColorEnum> getChromosome() {
        return chromosome;
    }

    public void setChromosome(List<ColorEnum> chromosome) {
        this.chromosome = chromosome;
    }

    public double getFittness() {
        return fittness;
    }

    public void setFittness(double fittness) {
        this.fittness = fittness;
    }






    private List<BlackBoxEnum>  returnBlackBox(List<ColorEnum> colorsId, List<ColorEnum> randColors)
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

    public void calcFitness(List<Genotype> prevAttempts , List<Integer> prevBlack, List<Integer> prevWhite ){

        int distance=0;
        int blackCount ;
        int whiteCount ;

        List<BlackBoxEnum> blackBox ;

        Iterator it = prevBlack.iterator();
        Iterator it2 = prevWhite.iterator();

        for(Genotype member : prevAttempts)
        {
            List<ColorEnum> chromosome = member.getChromosome();
            blackBox = returnBlackBox(chromosome,this.chromosome);

            blackCount=getColorValue(blackBox,BlackBoxEnum.BLACK);
            whiteCount=getColorValue(blackBox,BlackBoxEnum.WHITE);

            distance -= Math.abs((Integer) it.next() - blackCount) + Math.abs((Integer) it2.next() - whiteCount);


        }

        Log.i("DISTANCE", Integer.toString(distance));
        this.fittness = distance;


    }





}


