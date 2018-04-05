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
import java.util.TreeMap;

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
        MutualFund mf;
        TreeMap<Integer, String> risk_inverse_map = new TreeMap<>();
        risk_inverse_map.put(0, "low"); risk_inverse_map.put(1, "medium"); risk_inverse_map.put(2,
                "high");
        TreeMap<Integer, String> rating_map = new TreeMap<>();
        rating_map.put(1, "*"); rating_map.put(2, "**");
        rating_map.put(3, "***"); rating_map.put(4, "****");
        rating_map.put(4, "****");
        if(mutual_fund_list.size() >= 1)
        {
            mf = mutual_fund_list.get(0);
            label = view.findViewById(R.id.local_mf1_name);
            label.setText(mf.name);
            label = view.findViewById(R.id.local_mf1_risk);
            label.setText("Risk: "+risk_inverse_map.get(mf.risk));
            label = view.findViewById(R.id.local_mf1_rating);
            label.setText(rating_map.get(mf.rating));
            label = view.findViewById(R.id.local_mf1_proportion);
            label.setText(Math.round(proportions_list.get(0))* 100+"%");
        }
        if(mutual_fund_list.size() >= 2)
        {
            mf = mutual_fund_list.get(1);
            label = view.findViewById(R.id.local_mf2_name);
            label.setText(mf.name);
            label = view.findViewById(R.id.local_mf2_risk);
            label.setText("Risk: "+risk_inverse_map.get(mf.risk));
            label = view.findViewById(R.id.local_mf2_rating);
            label.setText(rating_map.get(mf.rating));
            label = view.findViewById(R.id.local_mf2_proportion);
            label.setText(Math.round(proportions_list.get(1))* 100+"%");
        }
        if(mutual_fund_list.size() >= 3)
        {
            mf = mutual_fund_list.get(2);
            label = view.findViewById(R.id.local_mf3_name);
            label.setText(mf.name);
            label = view.findViewById(R.id.local_mf3_risk);
            label.setText("Risk: "+risk_inverse_map.get(mf.risk));
            label = view.findViewById(R.id.local_mf3_rating);
            label.setText(rating_map.get(mf.rating));
            label = view.findViewById(R.id.local_mf3_proportion);
            label.setText(Math.round(proportions_list.get(2))* 100+"%");
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
