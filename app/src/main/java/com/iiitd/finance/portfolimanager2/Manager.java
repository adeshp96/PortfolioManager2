package com.iiitd.finance.portfolimanager2;

import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;


class Manager{
    private static String TAG = "Manager";
    private static ArrayList<MutualFund> equity_mutual_funds = new ArrayList<MutualFund> ();
    private static ArrayList<MutualFund> debt_mutual_funds = new ArrayList<MutualFund> ();
    private static ArrayList<MutualFund> retirement_saving_mutual_funds = new ArrayList<MutualFund> ();
    private static AssetManager assetManager = null;
    private static void LoadMFFromFiles(String mf_filename, String risks_filename,
                                        ArrayList<MutualFund> output){
        InputStream file, risks_file;
        try {
            file = assetManager.open(mf_filename);
            risks_file = assetManager.open(risks_filename);
        }
        catch(IOException e){
            Log.d(TAG, e.getMessage());
            throw new RuntimeException(mf_filename + "File cannot be loaded");
        }
        BufferedReader bufferedReader = null;
        String line;
        TreeMap<String, MutualFund> map = new TreeMap<String, MutualFund>();
        TreeMap<String, Integer> ratings_map = new TreeMap<String, Integer>();
        ratings_map.put("* ", 1);
        ratings_map.put("* * ", 2);
        ratings_map.put("* * * ", 3);
        ratings_map.put("* * * * ", 4);
        ratings_map.put("* * * * * ", 5);
        try {
            bufferedReader =  new BufferedReader(new InputStreamReader(file));
            for(int i =0; i < 3; i++) bufferedReader.readLine(); //Read header data
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("\"# Rating is provisional\""))
                    break;
                String name = line.split(",")[0];
                name = name.substring(1, name.length() - 1);
                if(!name.contains("Direct Plan"))
                    continue;
                String rating_string = line.split(",")[1];
                rating_string = rating_string.substring(1, rating_string.length() - 1);
                if(rating_string.equals("Unrated"))
                    continue;
                Integer rating = ratings_map.get(rating_string);
                String category= line.split(",")[2];
                category = category.substring(1, category.length() - 1).trim();
                String returns_string = line.split(",")[6];
                returns_string = returns_string.substring(1, returns_string.length() - 1);
                if(returns_string.equals("-"))
                    continue;
                Float returns = (float)Math.cbrt(1 + (Float.parseFloat(returns_string)/100));
//                returns = Math.cbrt((returns/100) + 1) - 1;
//                returns = Math.round(returns * 100.0) / 100.0;
                MutualFund mf = new MutualFund(name, category, returns, rating);
                map.put(name, mf);
            }
            bufferedReader =  new BufferedReader(new InputStreamReader(risks_file));
            for(int i =0; i < 3; i++) bufferedReader.readLine(); //Read header data
            while ((line = bufferedReader.readLine()) != null) {
                if(line.equals("\"# Rating is provisional\""))
                    break;
                String name = line.split(",")[0];
                name = name.substring(1, name.length() - 1);
                if(!map.containsKey(name))
                    continue;
                MutualFund mf = map.get(name);
                String risk = line.split(",")[2];
                if(risk.contains("Average"))
                    mf.risk = 1;
                else if(risk.contains("Low"))
                    mf.risk = 0;
                else if(risk.contains("High"))
                    mf.risk = 2;
            }
            for(String name: map.keySet()){
                MutualFund mf = map.get(name);
                if(mf.category.equals("DT-LIQ"))
                    mf.risk = 0;
                if(mf.risk != -1) {
                    output.add(mf);
                }
            }
        }
        catch(IOException e){
            throw new RuntimeException("File read error");
        }
    }
    static void LoadDetails(AssetManager assetManager2){
        assetManager = assetManager2;
        LoadMFFromFiles("debt.csv", "debt-risks.csv",
                debt_mutual_funds);
        LoadMFFromFiles("equity.csv", "equity-risks.csv", equity_mutual_funds);
        LoadMFFromFiles("equity-tax-saving.csv", "equity-tax-saving-risks.csv",
                equity_mutual_funds);
        LoadMFFromFiles("retirement-combined.csv", "retirement-combined-risks.csv",
                retirement_saving_mutual_funds);
        Log.d(TAG, "Equity funds matched (Including Tax saving) " + equity_mutual_funds.size());
        Log.d(TAG, "Debt funds matched "+debt_mutual_funds.size());
        Log.d(TAG, "Retirement-Saving mutual funds matched "+ retirement_saving_mutual_funds.size
                ());

    }


    static ArrayList<MutualFund> getMutualFunds(Requirement requirement){
        float returns = requirement.returns;
        int risk = requirement.risk;
        float horizon = requirement.horizon;
        ArrayList <MutualFund> output = new ArrayList<>();
        output.add(new MutualFund("papapa","MC", 0.12f, 0));
        output.add(new MutualFund("lalala", "LC", 0.11f, 1));
        ArrayList<String> categories = new ArrayList<>();
        String type;
        if(requirement.is_tax_saving){
            type = "equity";
            categories.add("EQ-TS");
        }
        if(requirement.is_retirement_saving){
            type = "retirement-saving";
            categories.add("*");
        }
        else if(horizon <= 3){
            if(risk == 0){
                type = "debt";
                categories.add("DT-LIQ");
            }
            else if(risk == 1){
                type = "debt";
                categories.add("DT-LIQ");
                categories.add("GL-ST");
                categories.add("GL-MLT");
            }
            else if (risk == 2) {
                type = "equity";
                categories.add("EQ-SC");
                categories.add("EQ-MC");
            }
            else
                throw new RuntimeException("Requirement cannot be understood. Risk is " + risk +
                        " for requirement " + requirement);
        }
        else if(horizon <= 6){
            if(risk == 0){
                type = "debt";
                categories.add("GL-ST");
                categories.add("GL-MLT");
            }
            else if(risk == 1){
                type = "equity";
                categories.add("EQ-MC");
            }
            else if (risk == 2) {
                type = "equity";
                categories.add("EQ-MC");
                categories.add("EQ-SC");
            }
            else
                throw new RuntimeException("Requirement cannot be understood");
        }
        else {
            if(risk == 0){
                type = "equity";
                categories.add("EQ-LC");
            }
            else if(risk == 1){
                type = "equity";
                categories.add("EQ-MC");
                categories.add("EQ-LC");
            }
            else if (risk == 2) {
                type = "equity";
                categories.add("EQ-MC");
                categories.add("EQ-SC");
            }
            else
                throw new RuntimeException("Requirement cannot be understood");
        }
        System.out.println(type  + " " + categories.get(0));
        if(type.equals("equity")){
            for(int i = 0; i < equity_mutual_funds.size(); i++){
                MutualFund mf = equity_mutual_funds.get(i);
                if(categories.contains(mf.category)) {
                    if(mf.risk <= risk)
                        output.add(mf);
                }
            }
        }
        if(type.equals("debt")){
            for(int i = 0; i < debt_mutual_funds.size(); i++){
                MutualFund mf = debt_mutual_funds.get(i);
                if(categories.contains(mf.category)) {
                    if(mf.risk <= risk)
                        output.add(mf);
                }
            }
        }
        if(type.equals("retirement-saving")){
            for(int i = 0; i < retirement_saving_mutual_funds.size(); i++){
                MutualFund mf = retirement_saving_mutual_funds.get(i);
                    output.add(mf);
            }
        }
        System.out.println("output size " + output.size());
        Collections.sort(output);
        return output;
    }
}
