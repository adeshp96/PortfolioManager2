package com.iiitd.finance.portfolimanager2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultActivity extends Activity {
    ArrayList<Requirement> requirements = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        ResultActivityListAdapter adapter = new ResultActivityListAdapter(getApplicationContext());
        requirements = (ArrayList<Requirement>) getIntent().getBundleExtra
                ("bundle_requirements").get("requirements");
//        TreeMap<String, Integer> freq_map = new  TreeMap<>();
//        TreeMap<String, MutualFund> map = new  TreeMap<>();
        for(int i = 0; i < requirements.size(); i++){
            ArrayList<MutualFund> results = Manager.getMutualFunds(requirements.get(i));
//            adapter.requirements.add(requirements.get(i));
//            adapter.mutual_funds.add(results);
            Solution solution = Solution.getSolution(requirements.get(i), results);
            adapter.solutions.add(solution);
//            for(int j = 0; j < results.size(); j++){
//                MutualFund mf = results.get(j);
//                map.put(mf.name, mf);
//                if(freq_map.containsKey(mf.name))
//                    freq_map.put(mf.name, freq_map.get(mf.name) + 1);
//                else
//                    freq_map.put(mf.name, 1);
//            }
        }
        ListView listView = findViewById(R.id.result_actiivty_list);
        listView.setAdapter(adapter);
//        ArrayList <Pair<Integer, String>> pq_simulation = new ArrayList <>();
//        for(Map.Entry<String, Integer> i: freq_map.entrySet())
//            pq_simulation.add(new Pair<>(i.getValue(), i.getKey()));
//        pq_simulation.sort(new Comparator<Pair<Integer, String>>() {
//            @Override
//            public int compare(Pair<Integer, String> t1, Pair<Integer, String> t2) {
//               return t2.first - t1.first;
//            }
//        });
//        TextView mf1 = findViewById(R.id.global_mf1);
//        TextView mf2 = findViewById(R.id.global_mf2);
//        TextView mf3 = findViewById(R.id.global_mf3);
//        if(pq_simulation.size() >= 1) {
//            String text = map.get(pq_simulation.get(0).second).toString();
//            mf1.setText(text + pq_simulation.get(0).first);
//            System.out.println("First MF: "+text);
//        }
//        if(pq_simulation.size() >= 2) {
//            String text = map.get(pq_simulation.get(1).second).toString();
//            mf2.setText(text + pq_simulation.get(1).first);
//            System.out.println("Second MF: "+text);
//        }
//        if(pq_simulation.size() >= 3) {
//            String text = map.get(pq_simulation.get(2).second).toString();
//            mf3.setText(text + pq_simulation.get(2).first);
//            System.out.println("Third MF: "+text);
//        }
    }


}
