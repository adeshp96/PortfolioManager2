package com.iiitd.finance.portfolimanager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RequirementsListAdapter extends ArrayAdapter<Requirement> {
    ArrayList<Requirement> requirements = new ArrayList<Requirement> ();
    public RequirementsListAdapter(Context context, ArrayList<Requirement> objects) {
        super(context, 0, objects);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Requirement requirement = getItem(position);
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.requirements_list_item, parent, false);
        TextView label = (TextView) view.findViewById(R.id.label);
        label.setText(requirement.toString());
        return view;
    }

    public int getCount() {
        return requirements.size();
    }

    public Requirement getItem(int position) {
        return requirements.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


}
