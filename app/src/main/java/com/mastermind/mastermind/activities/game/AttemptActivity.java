package com.mastermind.mastermind.activities.game;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.adapter.AttemptListViewAdapter;
import com.mastermind.mastermind.adapter.StatisticListViewAdapter;
import com.mastermind.mastermind.bean.layout.Attempt;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;

public class AttemptActivity extends AppCompatActivity {
    private ListView menu;
    private List<Attempt> attemptList;
    private AttemptListViewAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt);

        menu = findViewById(R.id.listViewAttempt);
        attemptList = (List<Attempt>) getIntent().getSerializableExtra("list");

       if(getIntent().getStringExtra("variant").equals(GameVariantEnum.CLASSIC.toString())){
           adapter = new AttemptListViewAdapter(this,
                   R.layout.attempts_item,attemptList,GameVariantEnum.CLASSIC);

       }else
       {
           adapter = new AttemptListViewAdapter(this,
                   R.layout.attempts_item,attemptList,GameVariantEnum.SUPER);
       }


      menu.setAdapter(adapter);

    }
}
