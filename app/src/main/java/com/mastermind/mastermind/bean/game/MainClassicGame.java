package com.mastermind.mastermind.bean.game;


import com.mastermind.mastermind.enums.ColorClassicEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainClassicGame {

private final static int COUNT_COLOR_RAND = 4;

private List<ColorClassicEnum> randomColors;

private ClassicList colors ;

    public MainClassicGame() {
        this.randomColors =new ArrayList<ColorClassicEnum>();
        this.colors = new ClassicList();
        randomColors();
    }

    public List<ColorClassicEnum> getRandomColors() {
        return randomColors;
    }

    private void randomColors() {

        List<ColorClassicEnum> list = colors.getColors();
        Random gen = new Random();

        for(int i=0;i<COUNT_COLOR_RAND;i++)
        {
            int index = gen.nextInt(COUNT_COLOR_RAND);
            randomColors.add(list.get(index));

        }
    }



}
