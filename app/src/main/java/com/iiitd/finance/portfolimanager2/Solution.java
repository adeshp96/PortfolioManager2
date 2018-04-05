package com.iiitd.finance.portfolimanager2;

import java.util.ArrayList;

public class Solution {
    ArrayList<MutualFund> mutual_funds = new ArrayList<>();
    ArrayList <Float> proportions = new ArrayList<>();
    Requirement requirement;
    Float lumpsum_investment_amount = null;
    Float sip_investment_amount = null;
    Float actual_returns = null; //Of form 1.10
    private Solution(){
        //Private constructor to prevent instantiation outside self
    }
    public static Solution getSolution(Requirement requirement, ArrayList<MutualFund>
            mutual_funds){
        Solution solution = new Solution();
        solution.requirement = requirement;
        for (int i = 0; i < Math.min(mutual_funds.size(), 2); i++){
            solution.mutual_funds.add(mutual_funds.get(i));
            solution.proportions.add(0.33f);
            solution.actual_returns = solution.proportions.get(i) * solution.mutual_funds.get(i)
                    .returns;
        }
        solution.lumpsum_investment_amount = requirement.final_amount/solution.actual_returns;
        /*A = P * [{(1+i)^n – 1 }/i]
        A = P * P2
        A = final amount
        P = installment each time
        n = total number of installments
        i = interest rate for that tenure (example if yearly return is 24% , but payments are made
        monthly then i = 24/12 = 2%)
        */
        Float i = solution.actual_returns/12;
        int n = (solution.requirement.horizon * 12);
        Float p2 = (float)(Math.pow(i + 1, n) - 1)/i;
        solution.sip_investment_amount = solution.requirement.final_amount/p2;
        solution.actual_returns += 1;
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
