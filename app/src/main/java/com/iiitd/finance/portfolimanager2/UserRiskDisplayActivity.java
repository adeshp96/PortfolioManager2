package com.iiitd.finance.portfolimanager2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class UserRiskDisplayActivity extends Activity {
    String calculatedRisk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_risk);
        Intent intent = getIntent();
        calculatedRisk = intent.getStringExtra("calculatedRisk");

        TextView textView = (TextView)findViewById(R.id.user_risk_to_display);
        textView.setText(calculatedRisk);

    }

    public void SaveAndProceed(View v){
        Intent i1 = new Intent(this,MainActivity.class);
        i1.putExtra("risk",calculatedRisk);
        startActivity(i1);
    }

}
