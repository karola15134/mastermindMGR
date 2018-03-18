package com.mastermind.mastermind.bean.game;


import com.mastermind.mastermind.enums.ColorClassicEnum;
import com.mastermind.mastermind.enums.ColorSuperEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainSuperGame {

    private final static int COUNT_COLOR_RAND = 5;

    private List<ColorSuperEnum> randomColors;

    private SuperList colors;

    public MainSuperGame() {
        this.randomColors = new ArrayList<ColorSuperEnum>();
        this.colors = new SuperList();
        randomColors();

    }


    private void randomColors() {

        List<ColorSuperEnum> list = colors.getColors();
        Random gen = new Random();

        for(int i=0;i<COUNT_COLOR_RAND;i++)
        {
            int index = gen.nextInt(COUNT_COLOR_RAND);
            randomColors.add(list.get(index));

        }
    }


    public List<ColorSuperEnum> getRandomColors() {
        return randomColors;
    }
}
