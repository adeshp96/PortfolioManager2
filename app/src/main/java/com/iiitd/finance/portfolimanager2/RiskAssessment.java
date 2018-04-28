package com.iiitd.finance.portfolimanager2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class RiskAssessment extends Activity {
    boolean proceedToNextActivity;
   String calculatedRisk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        proceedToNextActivity=true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_assessment);
    }
    public void CalculateRisk(View v){
        proceedToNextActivity=true;
        try {
            RadioGroup q1_rg = (RadioGroup) findViewById(R.id.q1_rg);
            RadioButton q1_option = (RadioButton) findViewById(q1_rg.getCheckedRadioButtonId());
            String q1_text = q1_option.getText().toString();

            RadioGroup q2_rg = (RadioGroup) findViewById(R.id.q3_rg);
            RadioButton q2_option = (RadioButton) findViewById(q2_rg.getCheckedRadioButtonId());
            String q2_text = q2_option.getText().toString();

            RadioGroup q3_rg = (RadioGroup) findViewById(R.id.q4_rg);
            RadioButton q3_option = (RadioButton) findViewById(q3_rg.getCheckedRadioButtonId());
            String q3_text = q3_option.getText().toString();

            RadioGroup q4_rg = (RadioGroup) findViewById(R.id.q5_rg);
            RadioButton q4_option = (RadioButton) findViewById(q4_rg.getCheckedRadioButtonId());
            String q4_text = q4_option.getText().toString();

            RadioGroup q5_rg = (RadioGroup) findViewById(R.id.q6_rg);
            RadioButton q5_option = (RadioButton) findViewById(q5_rg.getCheckedRadioButtonId());
            String q5_text = q5_option.getText().toString();

            RadioGroup q6_rg = (RadioGroup) findViewById(R.id.q7_rg);
            RadioButton q6_option = (RadioButton) findViewById(q6_rg.getCheckedRadioButtonId());
            String q6_text = q6_option.getText().toString();

            RadioGroup q7_rg = (RadioGroup) findViewById(R.id.q8_rg);
            RadioButton q7_option = (RadioButton) findViewById(q7_rg.getCheckedRadioButtonId());
            String q7_text = q7_option.getText().toString();

            RadioGroup q8_rg = (RadioGroup) findViewById(R.id.q9_rg);
            RadioButton q8_option = (RadioButton) findViewById(q8_rg.getCheckedRadioButtonId());
            String q8_text = q8_option.getText().toString();

            RadioGroup q9_rg = (RadioGroup) findViewById(R.id.q10_rg);
            RadioButton q9_option = (RadioButton) findViewById(q9_rg.getCheckedRadioButtonId());
            String q9_text = q9_option.getText().toString();

            RadioGroup q10_rg = (RadioGroup) findViewById(R.id.q11_rg);
            RadioButton q10_option = (RadioButton) findViewById(q10_rg.getCheckedRadioButtonId());
            String q10_text = q10_option.getText().toString();

            RadioGroup q11_rg = (RadioGroup) findViewById(R.id.q12_rg);
            RadioButton q11_option = (RadioButton) findViewById(q11_rg.getCheckedRadioButtonId());
            String q11_text = q11_option.getText().toString();
        }
        catch(Exception e){
            proceedToNextActivity=false;
            Toast.makeText(RiskAssessment.this, "Attempt All Questions" , Toast.LENGTH_LONG).show();

        }
        if(proceedToNextActivity) {

            calculatedRisk = "Medium";
            Intent i = new Intent(this, UserRiskDisplayActivity.class);
//            Bundle b = new Bundle();
//            b.putSerializable("calculatedRisk", calculatedRisk);
            i.putExtra("calculatedRisk",calculatedRisk );
            startActivity(i);
        }
    }

}
