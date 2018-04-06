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
        for(int i = 0; i < requirements.size(); i++){

            Solution solution = Solution.getSolution(requirements.get(i));
            adapter.solutions.add(solution);
        }
        ListView listView = findViewById(R.id.result_actiivty_list);
        listView.setAdapter(adapter);
    }


}
