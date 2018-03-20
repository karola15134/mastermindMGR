package com.mastermind.mastermind.bean.game;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import com.mastermind.mastermind.enums.ColorEnum;
import java.util.HashMap;
import java.util.Map;


public class ColorMap {

    private Map<ColorEnum,Integer> colorsMap = new HashMap<ColorEnum,Integer>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ColorMap(){

     Integer blue = Color.parseColor("#00BCD4");
     colorsMap.put(ColorEnum.BLUE,blue);

     Integer green = Color.parseColor("#9CCC65");
     colorsMap.put(ColorEnum.GREEN,green);

     Integer yellow =Color.parseColor("#F4FF81");
     colorsMap.put(ColorEnum.YELLOW,yellow);

     Integer light_grey = Color.parseColor("#E0E0E0");
     colorsMap.put(ColorEnum.LIGTH_GREY,light_grey);

     Integer dark_grey = Color.parseColor("#757575");
     colorsMap.put(ColorEnum.DARK_GREY,dark_grey);

     Integer orange = Color.parseColor("#FFCC80");
     colorsMap.put(ColorEnum.ORANGE,orange);

     Integer dark_blue = Color.parseColor("#5C6BC0");
     colorsMap.put(ColorEnum.DARK_BLUE,dark_blue);

     Integer red = Color.parseColor("#F06292");
     colorsMap.put(ColorEnum.RED,red);


    }

    public Map<ColorEnum, Integer> getColorsMap() {
        return colorsMap;
    }
}
