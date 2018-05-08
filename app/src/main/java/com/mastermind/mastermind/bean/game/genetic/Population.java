package com.mastermind.mastermind.bean.game.genetic;


import android.util.Log;

import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.bean.game.user.ReverseColorMap;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Population {

    private List<Genotype> populationMembers ;

    private List<Genotype> newPopulationMembers;

    private Integer populationSize;

    private GameVariantEnum variant;

    private  double mutateProb  ;

    private  double crossProb ;

    private double [] choiceProb;

    private double summaryFitness;

    private Double minFitness=999999.0;

    private Double maxFitness=-999999.0;

    private Genotype bestMember;

    private Genotype worstMember;

    private Integer chromomoseSize ;

    public Genotype getBestMember() {
        return bestMember;
    }

    public Population(GameVariantEnum variant, Integer populationSize, double mutateProb , double crossProb) {

        this.populationMembers = new ArrayList<Genotype>();
        this.newPopulationMembers = new ArrayList<Genotype>();
        this.populationSize=populationSize;
        this.variant = variant;
        this.mutateProb = mutateProb;
        this.crossProb = crossProb ;
        this.summaryFitness = 0;

        if(variant.equals(GameVariantEnum.CLASSIC)){
            chromomoseSize = 4;
        }else
            chromomoseSize = 5;


    }

    public List<Genotype> getPopulationMembers() {
        return populationMembers;
    }

    public double getSummaryFitness() {
        return summaryFitness;
    }


    public void initPopulation(List<Genotype> prevAttempts , List<Integer> prevBlack, List<Integer> prevWhite){

        generateMemberGenotype(prevAttempts,prevBlack,prevWhite);

    }


    private void generateMemberGenotype(List<Genotype> prevAttempts, List<Integer> prevBlack, List<Integer> prevWhite) {

        Random rand = new Random();

        for(int i=0;i<populationSize;i++){

            ColorList colors = new ColorList(variant);
            List<ColorEnum> colorsrand = colors.getColorsRand();
            Genotype g = new Genotype(colorsrand);
            populationMembers.add(g);
            g.calcFitness(prevAttempts,prevBlack,prevWhite);
        }


    }


    public void calcFitnessAll(List<Genotype> genotypes,List<Genotype> prevAttempts , List<Integer> prevBlack, List<Integer> prevWhite)
    {
        for(Genotype g: genotypes){

            g.calcFitness(prevAttempts,prevBlack,prevWhite);
        }
    }

    public void newPopulation(List<Genotype> prevAttempts , List<Integer> prevBlack, List<Integer> prevWhite) {

        calcFitnessAll(populationMembers,prevAttempts,prevBlack,prevWhite);
        nonNegativeFitness();
        statistic(); //najlepszy i najgorszy osobnik
        eliteSelection(); //obliczenie prawd. wybrania dla najlepszych osobnikow
        initNewPopulation(prevAttempts,prevBlack,prevWhite); //wylosowanie nowych osobnikow
        randNewPopulation(); //wylosowanie najlepszych osobnnikow
        crossPopulation(); //krzyzowanie
        mutatePopulation(); //mutacja
        this.populationMembers = newPopulationMembers; //sukcesja
        calcFitnessAll(populationMembers,prevAttempts,prevBlack,prevWhite);

    }

    private void nonNegativeFitness() {

        for(Genotype member : populationMembers)
            member.setFittness(member.getFittness()-minFitness);


    }

    public void statistic() {

        minFitness = 999999.0;
        maxFitness = -999999.0;

        for(int i=0;i<populationSize;i++) {
            if (populationMembers.get(i).getFittness() > maxFitness)
            {
                maxFitness = populationMembers.get(i).getFittness();
                bestMember = populationMembers.get(i);
            }

            if(populationMembers.get(i).getFittness() < minFitness)
            {
                minFitness = populationMembers.get(i).getFittness();
                worstMember = populationMembers.get(i);
            }
        }

    }

    private void mutatePopulation() {

        Random rand = new Random();

        for(int i=0;i<populationSize;i++){

            for(int j=0;j<chromomoseSize;j++)
            {
                Double random = ((double) rand.nextInt(100000)/1000);

                if(random < mutateProb)
                {
                    populationMembers.get(i).getChromosome().set(j,randChromosomeColor(variant));
                }
            }
        }

    }

    private ColorEnum randChromosomeColor(GameVariantEnum variant) {

        ColorList list = new ColorList(variant);
        return list.getColor();

    }

    private void crossPopulation() {

        Random rand = new Random();

       for(int i=0;i<populationSize;i+=2){

                Double random = ((double) rand.nextInt(1000)/1000);
                if(random < crossProb) {
                    swapColors(populationMembers.get(i), populationMembers.get(i + 1), rand.nextInt(chromomoseSize - 1));

                }
        }
    }

    private void swapColors(Genotype genotype, Genotype genotype1, int colorCount) {

    List<ColorEnum> chromosome = genotype.getChromosome();
    List<ColorEnum> chromosome2 = genotype1.getChromosome();

    for(int i=0;i<colorCount+1;i++)
    {
        ColorEnum color = chromosome.get(i);
        ColorEnum color2 = chromosome2.get(i);

        ColorEnum tmp = color;

        chromosome.set(i, color2 );
        chromosome2.set(i,tmp);

    }

    }


    private void randNewPopulation() {

        double randMember;

        Random rand = new Random();

        for(int i=0 ; i<populationSize ; i++)
        {
             randMember = (double)(rand.nextInt(10000))/100;

             for(int j=0; j<(populationSize/2)-1;j++)
             {
                 if(randMember < choiceProb[j])
                 {
                     //Log.i("rand", Double.toString(choiceProb[j]));
                     newPopulationMembers.set(i,populationMembers.get(j));
                     break;
                 }
             }

        }


    }

    private void initNewPopulation(List<Genotype> prevAttempts, List<Integer> prevBlack, List<Integer> prevWhite) {

        for (int i = 0; i < populationSize; i++) {
            ColorList colors = new ColorList(variant);
            List<ColorEnum> colorsrand = colors.getColorsRand();
            Genotype genotype = new Genotype(colorsrand);
            this.newPopulationMembers.add(genotype);

            genotype.calcFitness(prevAttempts, prevBlack, prevWhite);
        }
    }

    private void eliteSelection() {

        choiceProb = new double[populationSize/2];

        Collections.sort(populationMembers, new Comparator<Genotype>() {
            @Override
            public int compare(Genotype g, Genotype g2)
            {

                return  Double.compare(g2.getFittness(),g.getFittness());
            }
        });


        summaryFitness = 0;

        for(int i= (populationSize/2) + 1; i<populationSize; i++)
        {
            summaryFitness+=populationMembers.get(i).getFittness();
        }

      //  Log.i("sumFitnesSelection", Double.toString((populationMembers.get(populationSize/2).getFittness() * 100) /summaryFitness));

        int j=1;
        choiceProb[0] = ( populationMembers.get(populationSize/2).getFittness() * 100 )/ summaryFitness;
      //  Log.i("choiceProb [0]", Double.toString(choiceProb[0]));
        for(int i=populationSize/2 + 1 ;i < populationSize; i++){
              choiceProb[j] = (populationMembers.get(i).getFittness() * 100 )/ (summaryFitness + choiceProb[j - 1]);
            Log.i("choiceProb " + j, Double.toString(choiceProb[j]));
              j++;

        }





    }
}
