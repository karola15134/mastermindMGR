package com.mastermind.mastermind.bean.game;


import com.mastermind.mastermind.enums.ColorClassicEnum;

import java.util.ArrayList;
import java.util.List;

public class ClassicList {

    List<ColorClassicEnum> colors ;

    public ClassicList() {
        this.colors = new ArrayList<ColorClassicEnum>();

            colors.add(ColorClassicEnum.GREEN);
            colors.add(ColorClassicEnum.RED);
            colors.add(ColorClassicEnum.YELLOW);
            colors.add(ColorClassicEnum.LIGTH_GREY);
            colors.add(ColorClassicEnum.DARK_GREY);
            colors.add(ColorClassicEnum.BLUE);

    }

    public List<ColorClassicEnum> getColors() {
        return colors;
    }
}
