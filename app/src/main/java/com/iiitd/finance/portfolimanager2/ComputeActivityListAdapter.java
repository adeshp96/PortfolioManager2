package com.iiitd.finance.portfolimanager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adesh on 1/4/18.
 */

public class ComputeActivityListAdapter extends ArrayAdapter{
    ArrayList<Requirement> requirements = new ArrayList<Requirement> ();
    ArrayList<ArrayList<MutualFund>> mutual_funds = new ArrayList<ArrayList<MutualFund>>();
    public ComputeActivityListAdapter(Context context) {
        super(context, 0);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Requirement requirement = requirements.get(position);
        ArrayList <MutualFund> mutual_fund_list = mutual_funds.get(position);
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.compute_activity_list_item, parent, false);
        TextView label = (TextView) view.findViewById(R.id.requirement);
        label.setText(requirement.toString());
        if(mutual_fund_list.size() >= 1)
        {
            label = (TextView) view.findViewById(R.id.local_mf1);
            label.setText(mutual_fund_list.get(0).toString());
        }
        if(mutual_fund_list.size() >= 2)
        {
            label = (TextView) view.findViewById(R.id.local_mf2);
            label.setText(mutual_fund_list.get(1).toString());
        }
        return view;
    }

    public int getCount() {
        return requirements.size();
    }


    public long getItemId(int position) {
        return position;
    }
}
