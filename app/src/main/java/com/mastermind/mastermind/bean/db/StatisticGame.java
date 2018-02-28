package com.mastermind.mastermind.bean.db;


import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.enums.SolutionEnum;

import java.time.LocalDate;
import java.util.Date;

public class StatisticGame {
    private int id;
    private String date;
    private GameVariantEnum typeOfGame;
    private int attemptsCount;
    private SolutionEnum typeOfSolution;


    public StatisticGame() {
    }

    public StatisticGame(String date, GameVariantEnum typeOfGame, int attemptsCount,SolutionEnum typeOfSolution) {
        this.date = date;
        this.typeOfGame = typeOfGame;
        this.attemptsCount = attemptsCount;
        this.typeOfSolution = typeOfSolution;
    }

    public int getId() { return id; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public GameVariantEnum getTypeOfGame() { return typeOfGame; }

    public void setTypeOfGame(GameVariantEnum typeOfGame) { this.typeOfGame = typeOfGame; }

    public int getAttemptsCount() {
        return attemptsCount;
    }

    public void setAttemptsCount(int attemptsCount) {
        this.attemptsCount = attemptsCount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SolutionEnum getTypeOfSolution() { return typeOfSolution; }

    public void setTypeOfSolution(SolutionEnum typeOfSolution) { this.typeOfSolution = typeOfSolution; }

    @Override
    public String toString() {
        return "StatisticGame{" +
                "id=" + id +
                ", date=" + date +
                ", colorsCount=" + getTypeOfGame() +
                ", attemptsCount=" + attemptsCount +
                '}';
    }
}
