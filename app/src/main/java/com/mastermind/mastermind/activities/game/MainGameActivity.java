package com.mastermind.mastermind.activities.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.game.ColorList;
import com.mastermind.mastermind.enums.ColorEnum;
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


        ColorList color  = new ColorList(variantGame);

        List<ColorEnum> colorsRand = color.getColorsRand();
        Log.i(colorsRand.toString(), "random colors: ");






    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    }


