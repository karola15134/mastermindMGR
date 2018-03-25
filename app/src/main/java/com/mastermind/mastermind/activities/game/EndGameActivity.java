package com.mastermind.mastermind.activities.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.statistic.StatisticGameActivity;
import com.mastermind.mastermind.adapter.StatisticListViewAdapter;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.service.db.DBhandler;

import org.w3c.dom.Text;

import java.util.List;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        String variantGame = extras.getString("variant");
        String solution = extras.getString("solution");
        String attempt = extras.getString("attemptCount");


        TextView variant = (TextView) findViewById(R.id.variantTxtView);
        String variantGameS ="";
        if(GameVariantEnum.CLASSIC.equals(GameVariantEnum.valueOf(variantGame))){
            variantGameS = "Klasyczna - 6 kolorów ";
        }else{
            variantGameS = "Super - 8 kolorów";
        }
        variant.setText(variantGameS);

        TextView solutionTxt = (TextView) findViewById(R.id.solutionTxtView);
        solutionTxt.setText(solution);

        TextView attemptTxt = (TextView) findViewById(R.id.attemptTxtView);
        attemptTxt.setText(attempt);

    }

    public void newGameButClick(View view) {


        startActivity(new Intent(this,GameVariantActivity.class));


    }

    public void endbutClick(View view) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Wyjdź z gry");

        alertDialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                finishAffinity();
            }
        });

        alertDialogBuilder.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
