package com.mastermind.mastermind.bean.game;


import com.mastermind.mastermind.enums.ColorClassicEnum;
import com.mastermind.mastermind.enums.ColorSuperEnum;

import java.util.ArrayList;
import java.util.List;

public class SuperList {

    List<ColorSuperEnum> colors ;

    public SuperList() {
        this.colors = new ArrayList<ColorSuperEnum>();

        colors.add(ColorSuperEnum.GREEN);
        colors.add(ColorSuperEnum.RED);
        colors.add(ColorSuperEnum.YELLOW);
        colors.add(ColorSuperEnum.LIGTH_GREY);
        colors.add(ColorSuperEnum.DARK_GREY);
        colors.add(ColorSuperEnum.BLUE);
        colors.add(ColorSuperEnum.ORANGE);
        colors.add(ColorSuperEnum.DARK_BLUE);


    }

    public List<ColorSuperEnum> getColors() {
        return colors;
    }
}
