package com.mastermind.mastermind.activities.game;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.activities.statistic.StatisticGameActivity;
import com.mastermind.mastermind.adapter.StatisticListViewAdapter;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.bean.game.ColorList;
import com.mastermind.mastermind.bean.game.ColorMap;
import com.mastermind.mastermind.bean.game.MainGame;
import com.mastermind.mastermind.enums.BlackBoxEnum;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;
import com.mastermind.mastermind.service.db.DBhandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainGameActivity extends AppCompatActivity {

    private PopupWindow mPopupWindow;

    private Context mContext;

    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;

    private Button mButton;

    private Integer attemptsButtonCount = 0;

    private GameVariantEnum variantGame;

    private List<ColorEnum> colorsRand;

    private MainGame mainGame = new MainGame(this);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();
        mActivity = MainGameActivity.this;
        mConstraintLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        mButton = (Button) findViewById(R.id.attemptButton);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);


                View customView = inflater.inflate(R.layout.pop_window,null);

                mPopupWindow = new PopupWindow(
                        customView,
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );

                mPopupWindow.setAnimationStyle(android.R.anim.fade_in);
                mPopupWindow.setHeight(750);

                if(Build.VERSION.SDK_INT>=21){
                    mPopupWindow.setElevation(5.0f);
                }


                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);


                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mPopupWindow.dismiss();
                    }
                });


                mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER,0,0);
            }
        });


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


}


