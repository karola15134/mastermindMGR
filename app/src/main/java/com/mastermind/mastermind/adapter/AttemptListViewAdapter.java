package com.mastermind.mastermind.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
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
import com.mastermind.mastermind.bean.game.ColorMap;
import com.mastermind.mastermind.bean.layout.Attempt;
import com.mastermind.mastermind.bean.layout.Item;
import com.mastermind.mastermind.enums.ColorEnum;
import com.mastermind.mastermind.enums.GameVariantEnum;

import java.util.List;

public class AttemptListViewAdapter extends ArrayAdapter<Attempt> {

    private Context context;

    private GameVariantEnum variant;

    private ColorMap colorMap ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public AttemptListViewAdapter(Context context, int resource, List<Attempt> colors, GameVariantEnum variant) {
        super(context, resource,colors);
        this.context = context;
        this.variant = variant;
        this.colorMap = new ColorMap();
    }

    private class ViewHolder{

        ImageView color1;
        ImageView color2;
        ImageView color3;
        ImageView color4;
        ImageView color5;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        AttemptListViewAdapter.ViewHolder holder = null;
        Attempt rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.attempts_item, null);
            holder = new AttemptListViewAdapter.ViewHolder();
            holder.color1 = (ImageView) convertView.findViewById(R.id.colorImage);
            holder.color2 = (ImageView) convertView.findViewById(R.id.colorImage2);
            holder.color3 = (ImageView) convertView.findViewById(R.id.colorImage3);
            holder.color4 = (ImageView) convertView.findViewById(R.id.colorImage4);


            if(variant.equals(GameVariantEnum.SUPER)) {

                Log.i("adapter variant", variant.toString());
                holder.color5 = (ImageView) convertView.findViewById(R.id.colorImage5);
                holder.color5.setVisibility(View.VISIBLE);
            }
            convertView.setTag(holder);
        } else
            holder = (AttemptListViewAdapter.ViewHolder) convertView.getTag();


        holder.color1.setBackgroundColor(rowItem.getColor1());

        holder.color2.setBackgroundColor(rowItem.getColor2());

        holder.color3.setBackgroundColor(rowItem.getColor3());

        holder.color4.setBackgroundColor(rowItem.getColor4());


        if(variant.equals(GameVariantEnum.SUPER)) {
            holder.color5.setBackgroundColor(rowItem.getColor5());
        }

        return convertView;
    }

}
