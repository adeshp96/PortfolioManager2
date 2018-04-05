package com.iiitd.finance.portfolimanager2;

import android.util.Log;

import java.util.ArrayList;

public class Solution {
    ArrayList<MutualFund> mutual_funds = new ArrayList<>();
    ArrayList <Float> proportions = new ArrayList<>();
    Requirement requirement;
    Float lumpsum_investment_amount = null;
    Float sip_investment_amount = null;
    Float actual_returns = 0f; //Of form 1.10
    private Solution(){
        //Private constructor to prevent instantiation outside self
    }
    static String TAG = "Solution";
    public static Solution getSolution(Requirement requirement, ArrayList<MutualFund>
            mutual_funds){
        Solution solution = new Solution();
        solution.requirement = requirement;
        Log.d(TAG, "Solution received " + requirement +  " " + mutual_funds);
        for (int i = 0; i < Math.min(mutual_funds.size(), 3); i++){
            solution.mutual_funds.add(mutual_funds.get(i));
            solution.proportions.add(0.33333f);
            solution.actual_returns += solution.proportions.get(i) * solution.mutual_funds.get(i)
                    .returns;
            Log.d(TAG, mutual_funds.get(i) + " proportion: " + solution.proportions.get(i)
                    + " contribution to actual return "+solution.proportions.get(i) * solution.mutual_funds.get(i)
                    .returns);
        }
        Float compounded_interest_yearly = (float)Math.pow(solution.actual_returns, requirement
                .horizon);
        solution.lumpsum_investment_amount = requirement.final_amount/compounded_interest_yearly;
        /*A = P * [{(1+i)^n – 1 }/i]
        A = P * P2
        A = final amount
        P = installment each time
        n = total number of installments
        i = interest rate for that tenure (example if yearly return is 24% , but payments are made
        monthly then i = 24/12 = 2%)
        */
        Float i = (solution.actual_returns - 1)/12;
        int n = (solution.requirement.horizon * 12);
        Float p2 = (float)(Math.pow(i + 1, n) - 1 )/i;
        solution.sip_investment_amount = solution.requirement.final_amount/p2;

        Log.d(TAG, requirement + ", i " +i+ " n "+n+"p2 " +p2+ " actual_returns: " + solution
                .actual_returns);
        return solution;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "mutual_funds=" + mutual_funds +
                ", proportions=" + proportions +
                ", requirement=" + requirement +
                '}';
    }
}
