package com.example.kandarp.mygurukul;

/**
 * Created by Kandarp on 6/28/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private final String[] menuValues;

    public ImageAdapter(Context context, String[] menuValues) {
        this.context = context;
        this.menuValues = menuValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;

        if (convertView == null) {
            gridView = new View(context);
            // get layout from menu_grid_view.xmld_view.xml
            gridView = inflater.inflate(R.layout.menu_grid_view, null);
            // set value into textview
            TextView textView = (TextView) gridView.findViewById(R.id.textView);
            textView.setText(menuValues[position]);
            // set image based on selected text
            ImageView imageView = (ImageView) gridView.findViewById(R.id.imageView);
            String items = menuValues[position];

            if (items.equals("My Profile")) {
                imageView.setImageResource(R.drawable.myprofile);
            } else if (items.equals("Performance Details")) {
                imageView.setImageResource(R.drawable.perfodetails);
            } else if (items.equals("Institutional Holidays")) {
                imageView.setImageResource(R.drawable.timetable);
            } else {
                imageView.setImageResource(R.drawable.instituionalholidays);
            }
        } else {
            gridView = (View) convertView;
        }
        return gridView;
    }

    @Override
    public int getCount() {
        return menuValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
