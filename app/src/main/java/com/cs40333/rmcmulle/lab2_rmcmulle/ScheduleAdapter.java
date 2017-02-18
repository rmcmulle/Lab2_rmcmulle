package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs40333.rmcmulle.lab2_rmcmulle.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 2/8/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<String[]> {
    ScheduleAdapter (Context context, ArrayList<String[]> schedule) {
        super(context, R.layout.schedule_item, schedule);
    }

    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        String[] matchItem = getItem(position);
//        System.out.println("MATCH ITEM: " + matchItem);
        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.team_img);
        String mDrawableName = matchItem[0];
        int resID = getContext().getResources().getIdentifier(mDrawableName , "drawable", getContext().getPackageName());
        teamLogo.setImageResource(resID);

//        System.out.println("POSITION: " + position);
//        matchItem = getItem(position)[0];
//        System.out.println("MATCH ITEM 2: " + matchItem[0]);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.team_name);
        teamName.setText(matchItem[1]);

//        matchItem = getItem(position)[0];
//        System.out.println("MATCH ITEM 3: " + matchItem[0]);
        TextView teamDate = (TextView) scheduleView.findViewById(R.id.team_date);
        teamDate.setText(matchItem[2]);
        

        return scheduleView;
    }


}