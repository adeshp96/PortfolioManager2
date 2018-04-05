package com.iiitd.finance.portfolimanager2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by adesh on 1/4/18.
 */

public class ResultActivityListAdapter extends ArrayAdapter{
    ArrayList<Solution> solutions = new ArrayList<Solution>();
    public ResultActivityListAdapter(Context context) {
        super(context, 0);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Solution solution = solutions.get(position);
        ArrayList <MutualFund> mutual_fund_list = solution.mutual_funds;
        ArrayList <Float> proportions_list = solution.proportions;
        if (view == null)
            view = LayoutInflater.from(getContext()).inflate(R.layout.result_activity_list_item, parent, false);
        TextView label = (TextView) view.findViewById(R.id.requirement);
        label.setText(solution.requirement.toString());
        if(mutual_fund_list.size() >= 1)
        {
            label = (TextView) view.findViewById(R.id.local_mf1);
            label.setText(mutual_fund_list.get(0).toString() + " " + proportions_list.get(0));
        }
        if(mutual_fund_list.size() >= 2)
        {
            label = (TextView) view.findViewById(R.id.local_mf2);
            label.setText(mutual_fund_list.get(1).toString() + " " + proportions_list.get(1));
        }
        if(mutual_fund_list.size() >= 3)
        {
            label = (TextView) view.findViewById(R.id.local_mf2);
            label.setText(mutual_fund_list.get(2).toString() + " " + proportions_list.get(2));
        }
        if(mutual_fund_list.size() >= 4)
            Toast.makeText(getContext(), "Only 3 funds displayed in a portfolio", Toast.LENGTH_SHORT)
                .show();
        return view;
    }

    public int getCount() {
        return solutions.size();
    }


    public long getItemId(int position) {
        return position;
    }
}
