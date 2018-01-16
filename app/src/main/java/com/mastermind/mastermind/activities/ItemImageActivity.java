package com.mastermind.mastermind.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.adapter.CustomListViewAdapter;
import com.mastermind.mastermind.bean.layout.Item;

import java.util.ArrayList;
import java.util.List;


public class ItemImageActivity extends Activity implements
        OnItemClickListener {

    public static final String[] titles = new String[] { "Graj",
            "Ustawienia", "Statystyki", "Wyjście" };

    public static final Integer[] images = { R.drawable.grajxxx,
            R.drawable.ustawieniaxxx, R.drawable.statystykixxx, R.drawable.wyjsciexxx };

    ListView menu;
    List<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        items = new ArrayList<Item>();
        for (int i = 0; i < titles.length; i++) {
            Item item = new Item(images[i], titles[i]);
            items.add(item);
        }

        menu = (ListView) findViewById(R.id.menu);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.rowlistview, items);
        menu.setAdapter(adapter);
        menu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        String title= items.get(position).getTitle();

        switch(title)
        {
            case "Wyjście":

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Wyjdź z gry");

                alertDialogBuilder.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finishAndRemoveTask ();
                    }
                });

                alertDialogBuilder.setNegativeButton("Nie",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;

            case "Statystyki":

                Intent intent = new Intent(this, StatisticGameActivity.class);
                startActivity(intent);
                break;

        }

    }
}
