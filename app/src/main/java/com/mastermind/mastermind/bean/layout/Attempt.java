package com.mastermind.mastermind.bean.layout;


import android.os.Build;
import android.support.annotation.RequiresApi;

import com.mastermind.mastermind.bean.game.ColorMap;
import com.mastermind.mastermind.enums.ColorEnum;

import java.io.Serializable;
import java.util.List;

public class Attempt implements Serializable {

    private Integer color1;
    private Integer color2;
    private Integer  color3;
    private Integer color4;
    private Integer  color5;



    @RequiresApi(api = Build.VERSION_CODES.O)
    public Attempt(Integer  color1, Integer color2, Integer  color3, Integer  color4, Integer  color5) {
        this.color1 = color1;
        this.color2 = color2;
        this.color3 = color3;
        this.color4 = color4;
        this.color5 = color5;


    }

    public Attempt() {

    }

    public Integer  getColor1() {
        return color1;
    }


    public void setColor1(Integer color1) {
        this.color1 = color1;
    }

    public Integer  getColor2() {
        return color2;
    }

    public void setColor2(Integer color2) {
        this.color2 = color2;
    }

    public Integer  getColor3() {
        return color3;
    }

    public void setColor3(Integer  color3) {
        this.color3 = color3;
    }

    public Integer  getColor4() {
        return color4;
    }

    public void setColor4(Integer color4) {
        this.color4 = color4;
    }

    public Integer  getColor5() {
        return color5;
    }

    public void setColor5(Integer  color5) {
        this.color5 = color5;
    }



}
