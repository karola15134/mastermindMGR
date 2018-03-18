package com.mastermind.mastermind.activities.game;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.game.MainClassicGame;
import com.mastermind.mastermind.bean.game.MainSuperGame;
import com.mastermind.mastermind.enums.ColorClassicEnum;
import com.mastermind.mastermind.enums.ColorSuperEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;

public class MainGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        GameVariantEnum variantGame = (GameVariantEnum) extras.get("gameVariant");

        if(variantGame.equals(GameVariantEnum.CLASSIC)) {
            MainClassicGame game = new MainClassicGame();

            List<ColorClassicEnum> colors = game.getRandomColors();
            Log.i(colors.toString(), "random colors: ");
        }

         else if(variantGame.equals(GameVariantEnum.SUPER)) {
            MainSuperGame game = new MainSuperGame();

            List<ColorSuperEnum> colors = game.getRandomColors();
            Log.i(colors.toString(), "random colors: ");
        }



    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    }


