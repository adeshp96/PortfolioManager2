package com.iiitd.finance.portfolimanager2;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {
    float returns;
    int horizon;
    int risk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        Spinner dropdown = findViewById(R.id.add_activity_risk);
        String[] items = new String[]{"Low", "Medium", "High"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
    }
    public void Submit(View view){
        if(risk == -1){
            Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT);
            return;
        }
        try {
            EditText returns_edit_text = findViewById(R.id.add_activity_returns);
            returns = Float.parseFloat(returns_edit_text.getText().toString());
            EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
            horizon = Integer.parseInt(horizon_edit_text.getText().toString());
        }
        catch(Exception e){
            Toast.makeText(this, "Please enter number only in the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        Requirement new_requirement = new Requirement(returns, risk, horizon);

        Bundle b = new Bundle();
        b.putSerializable("requirement", new_requirement);
        Intent intent = new Intent();
        intent.putExtra("bundle_requirement", b);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        risk = position;
    }

    public void onNothingSelected(AdapterView<?> parent) {

        risk = -1;
    }


}
