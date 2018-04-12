package com.mastermind.mastermind.activities.game;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.game.GameVariantActivity;
import com.mastermind.mastermind.activities.game.genetic.GeneticActivity;
import com.mastermind.mastermind.bean.game.genetic.Genotype;
import com.mastermind.mastermind.bean.game.user.ColorMap;
import com.mastermind.mastermind.bean.layout.Attempt;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EndGameActivity extends AppCompatActivity {

    private List<Genotype> genotypeList;

    private String variantGameS;

    private List<Attempt> attemptsList;

    private String variantGame;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        variantGame = extras.getString("variant");
        String solution = extras.getString("solution");
        String attempt = extras.getString("attemptCount");

        if(solution.equals("Algorytm")) {
            genotypeList = (List<Genotype>) getIntent().getSerializableExtra("list");
            attemptsList = new ArrayList<Attempt>();
            converseToAttempts(genotypeList, attemptsList);
        }else{
            attemptsList = (List<Attempt>) getIntent().getSerializableExtra(("list"));
        }

        TextView variant = (TextView) findViewById(R.id.variantTxtView);
         variantGameS ="";
        if(GameVariantEnum.CLASSIC.equals(GameVariantEnum.valueOf(variantGame))){
            variantGameS = "Klasyczny - 4 kolory ";
        }else{
            variantGameS = "Super - 5 kolorów";
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void detailsButOnClick(View view) {




        Intent intent = new Intent(this,AttemptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",(Serializable) attemptsList);
        bundle.putString("variant",variantGame);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void converseToAttempts(List<Genotype> genotypeList, List<Attempt> attemptsList) {

        for(Genotype gen : genotypeList){

            Attempt attempt = converseToAttempt(gen.getChromosome());
            attemptsList.add(attempt);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Attempt converseToAttempt(List<ColorEnum> chromosome) {

        Attempt attempt = new Attempt();
        ColorMap colorMap = new ColorMap();

        attempt.setColor1(colorMap.getColorsMap().get(chromosome.get(0)));
        attempt.setColor2(colorMap.getColorsMap().get(chromosome.get(1)));
        attempt.setColor3(colorMap.getColorsMap().get(chromosome.get(2)));
        attempt.setColor4(colorMap.getColorsMap().get(chromosome.get(3)));

        if(variantGame.equals(GameVariantEnum.SUPER.toString())){

            attempt.setColor5(colorMap.getColorsMap().get(chromosome.get(4)));
        }

    return attempt;





    }
}
