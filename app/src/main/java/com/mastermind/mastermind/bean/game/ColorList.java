package com.mastermind.mastermind.bean.game;


import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ColorList {

    private final static int COUNT_COLOR_CLASSIC = 4;

    private final static int COUNT_COLOR_SUPER = 5;

    private List<ColorEnum> colors = new ArrayList<ColorEnum>();

    private List<ColorEnum> colorsRand = new ArrayList<ColorEnum>();


    public ColorList(GameVariantEnum gameVariant) {

        colors.add(ColorEnum.GREEN);
        colors.add(ColorEnum.RED);
        colors.add(ColorEnum.YELLOW);
        colors.add(ColorEnum.LIGTH_GREY);
        colors.add(ColorEnum.DARK_GREY);
        colors.add(ColorEnum.BLUE);
        colors.add(ColorEnum.ORANGE);
        colors.add(ColorEnum.DARK_BLUE);

        final String variant = gameVariant.toString();

        Random gen = new Random();

        switch(variant) {

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


    public List<ColorEnum> getColorsRand() {
        return colorsRand;
    }
}
