package com.mastermind.mastermind.activities.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.game.genetic.GeneticActivity;
import com.mastermind.mastermind.activities.game.user.MainGameActivity;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.genetic.Population;
import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;

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

        RadioButton userCh = (RadioButton) findViewById(R.id.userRadio);
        RadioButton algCh = (RadioButton) findViewById(R.id.algRadio);

        GameVariantEnum gameVariant = GameVariantEnum.CLASSIC;
        if (view.getId() == R.id.classicButton) gameVariant = GameVariantEnum.CLASSIC;
        if (view.getId() == R.id.superButton) gameVariant = GameVariantEnum.SUPER;

        Intent intent = null;

        if(userCh.isChecked()) {

            intent = new Intent(this, MainGameActivity.class);
            intent.putExtra("gameVariant", gameVariant);
            startActivity(intent);

        } else if (algCh.isChecked())
        {
             intent = new Intent(this, GeneticActivity.class);
            intent.putExtra("gameVariant", gameVariant);
            startActivity(intent);
        }
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Wybierz rodzaj rozwiązania ");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }




    }

    public void onCheckboxClicked(View view){

        RadioButton userCh = (RadioButton) findViewById(R.id.userRadio);
        RadioButton algCh = (RadioButton) findViewById(R.id.algRadio);

        switch(view.getId()){

            case R.id.userRadio:

               algCh.setChecked(false);
               break;

            case R.id.algRadio:
                userCh.setChecked(false);
                break;

        }


    }
}
