package com.iiitd.finance.portfolimanager2;

import android.content.Context;
import android.util.Log;
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
    static String TAG = "ResultActivityListAdapter";
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
        TextView label;
        label = view.findViewById(R.id.local_purpose);
        label.setText(solution.requirement.purpose.toString());
        label = view.findViewById(R.id.local_horizon);
        label.setText("Investment Period : " + String.valueOf(solution.requirement.horizon)+" years");

        label = view.findViewById(R.id.local_sip);
        label.setText("Investment per Month :  " + solution.sip_investment_amount.toString());
        label = view.findViewById(R.id.sip_local_total_deposits);
        float sip_total_deposit = solution.sip_investment_amount * 12 *solution.requirement.horizon;
        label.setText("Total Deposits :            " + String.valueOf(sip_total_deposit));
        label = view.findViewById(R.id.sip_local_total_return);
        float sip_return = solution.requirement.final_amount - sip_total_deposit;
        label.setText("Total Returns :              " + String.valueOf(sip_return));
        label = view.findViewById(R.id.sip_local_maturity_amount);
        label.setText("Maturity Amount :          " + String.valueOf(solution.requirement.final_amount));


        label = view.findViewById(R.id.local_lumpsum);
        label.setText("Investment (Total Deposit) : " + solution.lumpsum_investment_amount.toString());
        label = view.findViewById(R.id.ota_local_total_return);
        float ota_return = solution.requirement.final_amount - solution.lumpsum_investment_amount;
        label.setText("Total Returns :                  " + String.valueOf(ota_return));
        label = view.findViewById(R.id.ota_local_maturity_amount);
        label.setText("Maturity Amount :                " + String.valueOf(solution.requirement.final_amount));
        if(mutual_fund_list.size() >= 1)
        {
            label = view.findViewById(R.id.local_mf1);
            label.setText(mutual_fund_list.get(0).toString() + " " + proportions_list.get(0));
        }
        if(mutual_fund_list.size() >= 2)
        {
            label = view.findViewById(R.id.local_mf2);
            label.setText(mutual_fund_list.get(1).toString() + " " + proportions_list.get(1));
        }
        if(mutual_fund_list.size() >= 3)
        {
            label = view.findViewById(R.id.local_mf3);
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
