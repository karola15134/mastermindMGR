package com.mastermind.mastermind.bean.game;

import android.util.Log;

import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainGame {

    private Integer attemptCount = 0;

    private ReverseColorMap reverseMap = new ReverseColorMap();

    private boolean checkAttempt(List<Integer> colorsId,List<ColorEnum> randColors){

        boolean result = true;
        Map<Integer,ColorEnum> colorsMap =reverseMap.getColorsMap();


        for(int i=0;i<colorsId.size();i++) {

            ColorEnum currentColor = colorsMap.get(colorsId.get(i));
            if (!currentColor.equals(randColors.get(i))) { result=false; break; }
        }

        attemptCount++;

        return result;


    }

    public List<BlackBoxEnum> checkAnswer(List<Integer> colorsId,List<ColorEnum> randColors){

        List<BlackBoxEnum> blackBox = new ArrayList<BlackBoxEnum>() ;

          if(!checkAttempt(colorsId,randColors))
        {
            blackBox = buildBlackBox(colorsId,randColors);
        }
        else
          {
              Log.i(attemptCount.toString(), "checkAnswer: attempts count");
          }

        return blackBox;
    }

    private List<BlackBoxEnum> buildBlackBox(List<Integer> colorsId, List<ColorEnum> randColors) {

        List<BlackBoxEnum> blackBox = new ArrayList<BlackBoxEnum>();
        Map<Integer,ColorEnum> colorsMap =reverseMap.getColorsMap();
        Boolean[] array = new Boolean[randColors.size()];
        Arrays.fill(array, Boolean.FALSE);


        // BLACK
        for(int i=0;i<colorsId.size();i++)
        {
            Integer currentColor = colorsId.get(i);

            if(colorsMap.get(currentColor).equals(randColors.get(i)))
            {
                blackBox.add(BlackBoxEnum.BLACK);
                array[i]=true;
            }
        }


        //WHITE

        for(int i=0;i<colorsId.size();i++){

            Integer currentColor = colorsId.get(i);

            for(int j=0;j<randColors.size();j++)
            {
               if(i!=j && colorsMap.get(currentColor).equals(randColors.get(j)))
               {
                   if(array[j]==false) {
                       blackBox.add(BlackBoxEnum.WHITE);
                       array[j] = true;
                   }
               }
            }

        }





        return blackBox;

    }

    public Integer getAttemptCount() {
        return attemptCount;
    }
}

