package com.mastermind.mastermind.adapter;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mastermind.mastermind.R;
import com.mastermind.mastermind.bean.db.StatisticGame;
import com.mastermind.mastermind.bean.layout.Item;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class StatisticListViewAdapter extends ArrayAdapter<StatisticGame> {
    Context context;

    public StatisticListViewAdapter(Context context, int resource, List<StatisticGame> items) {
        super(context, resource , items);
        this.context=context;
    }

    private class ViewHolder {

        TextView date;
        TextView colorsCount;
        TextView attemptsCount;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent) {
        StatisticListViewAdapter.ViewHolder holder = null;
        StatisticGame rowItem = getItem(position);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.statisticlistitem, null);
            holder = new StatisticListViewAdapter.ViewHolder();

            holder.date = (TextView) convertView.findViewById(R.id.dateTxt);
            holder.colorsCount = (TextView) convertView.findViewById(R.id.colorsCountTxt);
            holder.attemptsCount = (TextView) convertView.findViewById(R.id.attemptsCountTxt);
            convertView.setTag(holder);
        } else
            holder = (StatisticListViewAdapter.ViewHolder) convertView.getTag();

        Date date = rowItem.getDate();
        String dateStr=format.format(date);
        holder.date.setText(dateStr);

        Integer attemptsCountInt = rowItem.getAttemptsCount();
        holder.attemptsCount.setText(attemptsCountInt.toString());

        Integer colorsCountInt = rowItem.getColorsCount();
        holder.colorsCount.setText(colorsCountInt.toString());

        return convertView;
    }

}
