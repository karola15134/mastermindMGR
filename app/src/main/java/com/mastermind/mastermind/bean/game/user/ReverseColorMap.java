package com.mastermind.mastermind.bean.game.user;


import android.graphics.Color;

import com.mastermind.mastermind.enums.ColorEnum;

import java.util.HashMap;
import java.util.Map;

public class ReverseColorMap {

    private Map<Integer,ColorEnum> colorsMap = new HashMap<Integer,ColorEnum>();

    public ReverseColorMap() {

        Integer blue = Color.parseColor("#00BCD4");
        colorsMap.put(blue,ColorEnum.BLUE);

        Integer green = Color.parseColor("#9CCC65");
        colorsMap.put(green,ColorEnum.GREEN);

        Integer yellow =Color.parseColor("#F4FF81");
        colorsMap.put(yellow,ColorEnum.YELLOW);

        Integer light_grey = Color.parseColor("#E0E0E0");
        colorsMap.put(light_grey,ColorEnum.LIGTH_GREY);

        Integer dark_grey = Color.parseColor("#757575");
        colorsMap.put(dark_grey,ColorEnum.DARK_GREY);

        Integer orange = Color.parseColor("#FFCC80");
        colorsMap.put(orange,ColorEnum.ORANGE);

        Integer dark_blue = Color.parseColor("#5C6BC0");
        colorsMap.put(dark_blue,ColorEnum.DARK_BLUE);

        Integer red = Color.parseColor("#F06292");
        colorsMap.put(red,ColorEnum.RED);
    }

    public Map<Integer, ColorEnum> getColorsMap() {
        return colorsMap;
    }
}
