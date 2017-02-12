package com.cs40333.rmcmulle.lab2_rmcmulle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cs40333.rmcmulle.lab2_rmcmulle.R;

/**
 * Created by Ryan on 2/8/2017.
 */

public class ScheduleAdapter extends ArrayAdapter<String> {
    ScheduleAdapter (Context context, String[] schedule) {
        super(context, R.layout.schedule_item, schedule);
    }
    public View getView (int position, View convertView, ViewGroup parent) {
        LayoutInflater scheduleInflater = LayoutInflater.from(getContext());
        View scheduleView = scheduleInflater.inflate(R.layout.schedule_item, parent, false);

        String matchItem = getItem(position);
        TextView teamName = (TextView) scheduleView.findViewById(R.id.team_name);
        teamName.setText(matchItem);

        ImageView teamLogo = (ImageView) scheduleView.findViewById(R.id.team_img);
        

        return scheduleView;
    }
}