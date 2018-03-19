package com.mastermind.mastermind.activities.game;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.game.ColorList;
import com.mastermind.mastermind.bean.game.ColorMap;
import com.mastermind.mastermind.bean.game.MainGame;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;

public class MainGameActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle extras = getIntent().getExtras();
        GameVariantEnum variantGame = (GameVariantEnum) extras.get("gameVariant");


        ColorList color  = new ColorList(variantGame);  //random colors
        ColorMap colorMap = new ColorMap(); //add real color to ColorEnum


        List<ColorEnum> colorsRand = color.getColorsRand();

        MainGame game = new MainGame();
        game.runGame(colorsRand);





    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    }


