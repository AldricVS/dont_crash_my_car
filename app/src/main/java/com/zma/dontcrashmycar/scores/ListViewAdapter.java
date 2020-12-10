package com.zma.dontcrashmycar.scores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter {
    private int color1 = 0xaa002A7C;
    private int color2 = 0xF8A10310;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ListViewAdapter(Context context, int resource, List<PlayerData> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if(position % 2 == 0)
            view.setBackgroundColor(color1);
        else
            view.setBackgroundColor(color2);

        return view;
    }
}