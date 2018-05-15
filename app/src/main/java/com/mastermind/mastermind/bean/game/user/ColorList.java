package com.mastermind.mastermind.bean.game.user;


import android.util.Log;

import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorList {

    private final static int COUNT_COLOR_CLASSIC = 4;

    private final static int COUNT_COLOR_SUPER = 5;

    private List<ColorEnum> colors = new ArrayList<ColorEnum>();

    private  List<ColorEnum> currentColors = new ArrayList<>();

    private List<ColorEnum> colorsRand = new ArrayList<ColorEnum>();

    private GameVariantEnum gameVariant;




    public ColorList(GameVariantEnum gameVariant) {

        colors.add(ColorEnum.GREEN);
        colors.add(ColorEnum.RED);
        colors.add(ColorEnum.YELLOW);
        colors.add(ColorEnum.LIGTH_GREY);
        colors.add(ColorEnum.DARK_GREY);
        colors.add(ColorEnum.BLUE);
        colors.add(ColorEnum.ORANGE);
        colors.add(ColorEnum.DARK_BLUE);
        this.gameVariant = gameVariant;

        fillCurrentColors();

        Random gen = new Random();

        switch(gameVariant.toString()) {

            case "CLASSIC":

                for(int i=0;i<COUNT_COLOR_CLASSIC;i++)
                {
                    int index = gen.nextInt(5);
                    colorsRand.add(colors.get(index));

                }

                break;

            case "SUPER":

                for(int i=0;i<COUNT_COLOR_SUPER;i++)
                {
                    int index = gen.nextInt(7);
                    colorsRand.add(colors.get(index));

                }
                break;
        }


    }

    private void fillCurrentColors() {

        switch(gameVariant.toString()) {

            case "CLASSIC":

                for(int i=0;i<6;i++)
                {
                    currentColors.add(colors.get(i));

                }

                break;

            case "SUPER":

                for(int i=0;i<8;i++)
                {
                    currentColors.add(colors.get(i));

                }
                break;
        }
    }

    public ColorEnum getColor (){

        Random gen = new Random();


        ColorEnum color = ColorEnum.BLUE ;

        switch(gameVariant.toString()) {

            case "CLASSIC":

               color = colors.get(gen.nextInt(5));

            case "SUPER":

                color = colors.get(gen.nextInt(7));
        }

        return color;

    }

    public List<ColorEnum> getColorsRand() {
        return colorsRand;
    }

    public List<ColorEnum> getColors() {
        return colors;
    }

    public List<ColorEnum> getCurrentColors() {
        return currentColors;
    }

    public void setCurrentColors(List<ColorEnum> currentColors) {
        this.currentColors = currentColors;
    }

    public ColorEnum[] copyLists(ColorEnum[] attempt) {

        ColorEnum [] copy = new ColorEnum[attempt.length];

        for(int i= 0 ;i< attempt.length ; i++)
        {
            copy[i] = attempt[i];
        }

        return copy;

    }
}