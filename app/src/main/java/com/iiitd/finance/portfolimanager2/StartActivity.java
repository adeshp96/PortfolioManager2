package com.iiitd.finance.portfolimanager2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void start(View v){
        Intent i = new Intent(this,RiskAssessment.class);
        startActivity(i);
    }

}
