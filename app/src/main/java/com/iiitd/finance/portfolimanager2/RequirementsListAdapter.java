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
        TextView label;
        label = view.findViewById(R.id.requirement_list_purpose);
        label.setText(requirement.purpose);
        label = view.findViewById(R.id.requirement_list_horizon);
        label.setText(""+requirement.horizon + " years");
        label = view.findViewById(R.id.requirement_list_final_amount);
        label.setText("Rs. "+requirement.final_amount);
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
