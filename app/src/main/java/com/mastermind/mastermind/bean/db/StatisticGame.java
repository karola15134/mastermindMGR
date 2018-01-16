package com.mastermind.mastermind.bean.db;


import java.time.LocalDate;
import java.util.Date;

public class StatisticGame {
    private int id;
    private Date date;
    private int colorsCount;
    private int attemptsCount;


    public StatisticGame() {
    }

    public StatisticGame(Date date, int colorsCount, int attemptsCount) {
        this.date = date;
        this.colorsCount = colorsCount;
        this.attemptsCount = attemptsCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getColorsCount() {
        return colorsCount;
    }

    public void setColorsCount(int colorsCount) {
        this.colorsCount = colorsCount;
    }

    public int getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(int attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    public void setId(int id) {
        this.id = id;
    }


}
