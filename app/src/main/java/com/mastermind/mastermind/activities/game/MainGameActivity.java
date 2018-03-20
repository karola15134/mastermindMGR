package com.mastermind.mastermind.activities.game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.game.ColorList;
import com.mastermind.mastermind.bean.game.ColorMap;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;
import java.util.Map;

public class MainGameActivity extends AppCompatActivity {

    private PopupWindow mPopupWindow;

    private Context mContext;

    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;

    private Button mButton;


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
        GameVariantEnum variantGame = (GameVariantEnum) extras.get("gameVariant");


        ColorList color  = new ColorList(variantGame);  //random colors
        ColorMap colorMap = new ColorMap(); //add real color to ColorEnum

        fillButtons(colorMap);



        if(variantGame.equals(GameVariantEnum.CLASSIC))  {
            LinearLayout layout = (LinearLayout) findViewById(R.id.superLayout);
            layout.setVisibility(LinearLayout.GONE);

            Button button1 = (Button) findViewById(R.id.attemptColorBut5);
            button1.setVisibility(Button.GONE);


        }

        List<ColorEnum> colorsRand = color.getColorsRand();








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



    }


