package com.mastermind.mastermind.activities.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.enums.GameVariantEnum;

public class GameVariantActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_variant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void onButtonClick(View view) {

        GameVariantEnum gameVariant=GameVariantEnum.CLASSIC;
        if(view.getId() == R.id.classicButton) gameVariant=GameVariantEnum.CLASSIC;
        if(view.getId() == R.id.superButton) gameVariant=GameVariantEnum.SUPER;

        Intent intent = new Intent(this,MainGameActivity.class);
        intent.putExtra("gameVariant",gameVariant);

        startActivity(intent);

    }
}
