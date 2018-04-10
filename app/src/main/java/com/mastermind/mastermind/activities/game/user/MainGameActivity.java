package com.mastermind.mastermind.activities.game.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.game.user.ColorList;
import com.mastermind.mastermind.bean.game.user.ColorMap;
import com.mastermind.mastermind.bean.game.user.MainGame;
import com.mastermind.mastermind.bean.layout.Attempt;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainGameActivity extends AppCompatActivity   {

    private PopupWindow mPopupWindow;

    private Context mContext;

    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;

    private Button mButton;

    private Integer attemptsButtonCount = 0;

    private GameVariantEnum variantGame;

    private List<ColorEnum> colorsRand;

    private MainGame mainGame = new MainGame(this);

    private List<Attempt> attemptList = new ArrayList<Attempt>();

    private ListView menu ;

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
        variantGame = (GameVariantEnum) extras.get("gameVariant");


        ColorList color  = new ColorList(variantGame);  //random colors
        ColorMap colorMap = new ColorMap(); //add real color to ColorEnum

        fillButtons(colorMap);



        if(variantGame.equals(GameVariantEnum.CLASSIC))  {
            LinearLayout layout = (LinearLayout) findViewById(R.id.superLayout);
            layout.setVisibility(LinearLayout.GONE);

            Button button1 = (Button) findViewById(R.id.attemptColorBut5);
            button1.setVisibility(Button.GONE);


        }

        colorsRand = color.getColorsRand();






    }

    private void fillButtons(ColorMap colorMap) {

        Map<ColorEnum,Integer> colorsMap = colorMap.getColorsMap();

        Button button1 = (Button) findViewById(R.id.color1);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.GREEN));

        button1=(Button) findViewById(R.id.color2);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.RED));

        button1=(Button) findViewById(R.id.color3);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.YELLOW));

        button1=(Button) findViewById(R.id.color4);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.LIGTH_GREY));

        button1=(Button) findViewById(R.id.color5);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.DARK_GREY));

        button1=(Button) findViewById(R.id.color6);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.BLUE));

        button1=(Button) findViewById(R.id.color7);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.ORANGE));

        button1=(Button) findViewById(R.id.color8);
        button1.setBackgroundColor(colorsMap.get(ColorEnum.DARK_BLUE));

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void colorButtonsClick(View view) {

      Button currentButton =  findViewById(view.getId());
      ColorDrawable buttonColor = (ColorDrawable) currentButton.getBackground();

      Integer colorId = buttonColor.getColor();
      setAttemptColors(colorId);


    }

    private void setAttemptColors(Integer colorId) {

        Button button = null;


        if(attemptsButtonCount<=4) {


            switch (attemptsButtonCount) {
                case 0:
                    button = (Button) findViewById(R.id.attemptColorBut);
                    break;

                case 1:
                    button = (Button) findViewById(R.id.attemptColorBut2);
                    break;

                case 2:
                    button = (Button) findViewById(R.id.attemptColorBut3);
                    break;

                case 3:
                    button = (Button) findViewById(R.id.attemptColorBut4);
                    break;

                case 4:
                    button = (Button) findViewById(R.id.attemptColorBut5);
                    break;
            }

            button.setBackgroundColor(colorId);
            button.setTag("true");
            attemptsButtonCount++;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendButtonClick(View view) {

        int count;
        if(variantGame.equals(GameVariantEnum.CLASSIC)) { count=4; }
        else {count =5;}


        if(attemptsButtonCount.equals(count)) {
            List<Integer> colorsId = new ArrayList<Integer>();

            Integer colorId;
            Button currentButton;
            ColorDrawable buttonColor;

            currentButton = findViewById(R.id.attemptColorBut);
            buttonColor = (ColorDrawable) currentButton.getBackground();
            colorId = buttonColor.getColor();
            colorsId.add(colorId);

            currentButton = findViewById(R.id.attemptColorBut2);
            buttonColor = (ColorDrawable) currentButton.getBackground();
            colorId = buttonColor.getColor();
            colorsId.add(colorId);

            currentButton = findViewById(R.id.attemptColorBut3);
            buttonColor = (ColorDrawable) currentButton.getBackground();
            colorId = buttonColor.getColor();
            colorsId.add(colorId);

            currentButton = findViewById(R.id.attemptColorBut4);
            buttonColor = (ColorDrawable) currentButton.getBackground();
            colorId = buttonColor.getColor();
            colorsId.add(colorId);

            if (variantGame.equals(GameVariantEnum.SUPER)) {

                currentButton = findViewById(R.id.attemptColorBut5);
                buttonColor = (ColorDrawable) currentButton.getBackground();
                colorId = buttonColor.getColor();
                colorsId.add(colorId);

            }



            List<BlackBoxEnum> blackBox = mainGame.checkAnswer(colorsId,colorsRand);
            if(blackBox==null)
            {

                showSuccessMessage();



            }else{
                resetAttemptColors();
                showBlackBox(blackBox);

                Attempt attempt = converseToAttempt(colorsId);
                attemptList.add(attempt);


            }


        } else {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Podaj poprawną ilość kolorów");

            alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }




        Log.i("colors",colorsRand.toString());


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Attempt converseToAttempt(List<Integer> colorsId) {

        Attempt attempt = new Attempt();
        attempt.setColor1(colorsId.get(0));
        attempt.setColor2(colorsId.get(1));
        attempt.setColor3(colorsId.get(2));
        attempt.setColor4(colorsId.get(3));

        Log.i(attempt.getColor1().toString(), "converseToAttempt: ");
        if(variantGame.equals(GameVariantEnum.SUPER)){

            attempt.setColor5(colorsId.get(4));
        }

        return attempt;
    }

    private void showSuccessMessage() {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Gratulacje!\n Kod został odgadnięty poprawnie ");

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(MainGameActivity.this,EndGameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("solution","Gracz");
                bundle.putString("variant",variantGame.toString());
                bundle.putString("attemptCount",mainGame.getAttemptCount().toString());

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }


    private void showBlackBox(List<BlackBoxEnum> blackBox) {

        resetBlackBox();
        int size = blackBox.size();

        List<Button> buttonList = new ArrayList<Button>();

        Button but;

        but = findViewById(R.id.blackBoxBut1);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut2);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut3);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut4);
        buttonList.add(but);


        for(int i=0;i<size;i++)
        {
            if(blackBox.get(i).equals(BlackBoxEnum.BLACK)) {
                buttonList.get(i).setBackgroundColor(Color.BLACK);
            }else
            {
                buttonList.get(i).setBackgroundColor(Color.WHITE);
            }
        }

    }





    public void resetButtonClick(View view) {

        resetAttemptColors();
    }

    private void resetBlackBox() {

        List<Button> buttonList = new ArrayList<Button>();

        Button but;

        but = findViewById(R.id.blackBoxBut1);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut2);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut3);
        buttonList.add(but);

        but = findViewById(R.id.blackBoxBut4);
        buttonList.add(but);

        for(Button button : buttonList)
        {
            button.setBackgroundColor(Color.parseColor("#FFF3EAEA"));
        }



    }

    private void resetAttemptColors(){

        Button button;
        Integer color = Color.parseColor("#EEEEEE");

        button = (Button) findViewById(R.id.attemptColorBut);
        button.setBackgroundColor(color);
        button.setTag("false");

        button = (Button) findViewById(R.id.attemptColorBut2);
        button.setBackgroundColor(color);
        button.setTag("false");

        button = (Button) findViewById(R.id.attemptColorBut3);
        button.setBackgroundColor(color);
        button.setTag("false");

        button = (Button) findViewById(R.id.attemptColorBut4);
        button.setBackgroundColor(color);
        button.setTag("false");


        if(variantGame.equals(GameVariantEnum.SUPER)) {

            button = (Button) findViewById(R.id.attemptColorBut5);
            button.setBackgroundColor(color);
            button.setTag("false");
        }


        attemptsButtonCount=0;

        resetBlackBox();
    }


    public void attemptClick(View view) {

        Intent intent = new Intent(this,AttemptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",(Serializable) attemptList);
        bundle.putString("variant",variantGame.toString());
        intent.putExtras(bundle);
        startActivity(intent);

    }
}


