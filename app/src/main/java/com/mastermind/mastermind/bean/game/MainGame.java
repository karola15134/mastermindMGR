package com.mastermind.mastermind.bean.game;

import com.mastermind.mastermind.enums.ColorEnum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainGame {

    private Integer attemptCount = 0;

    private ReverseColorMap reverseMap = new ReverseColorMap();

    public boolean checkAttempt(List<Integer> colorsId,List<ColorEnum> randColors){

        boolean result = true;
        Map<Integer,ColorEnum> colorsMap =reverseMap.getColorsMap();


        for(int i=0;i<colorsId.size();i++) {

            ColorEnum currentColor = colorsMap.get(colorsId.get(i));
            if (!currentColor.equals(randColors.get(i))) { result=false; break; }
        }

        attemptCount++;

        return result;


    }


}

