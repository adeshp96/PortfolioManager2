package com.iiitd.finance.portfolimanager2;

import java.util.ArrayList;

public class Solution {
    ArrayList<MutualFund> mutual_funds = new ArrayList<>();
    ArrayList <Float> proportions = new ArrayList<>();
    Requirement requirement;
    Float lumpsum_investment_amount = null;
    Float sip_investment_amount = null;
    Float final_amount = null;
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
            solution.final_amount = 0.33f * solution.mutual_funds.get(i).returns;
        }
        solution.final_amount += 1;
        
        solution.lumpsum_investment_amount = 2f ;
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
