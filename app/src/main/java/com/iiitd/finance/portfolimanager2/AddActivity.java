package com.iiitd.finance.portfolimanager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {
    float returns;
    int horizon;
    int risk;
    int final_amount, present_amount;
    boolean is_tax_saving = false;
    boolean is_retirement_saving = false;
    String purpose;
    Boolean risk_enabled = false;
    Boolean name_enabled = false;
    String[] items_risk = new String[]{"Low", "Medium", "High"};
    String[] items_purpose = new String[]{"Education", "Marriage", "Travel", "Tax-Saving",
            "Retirement-Saving","Custom"};
    TreeMap<String, Integer> risk_map = new TreeMap<>();
    static Float inflation_rate = 1.1f;
    static String TAG = "AddActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        risk_map.put("Education", 0); risk_map.put("Marriage", 1); risk_map.put("Travel", 2);
        risk_map.put("Retirement-Saving", 0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        Spinner dropdown = findViewById(R.id.add_activity_risk);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items_risk);
        dropdown.setAdapter(adapter);

        dropdown = findViewById(R.id.add_activity_purpose);
        adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items_purpose);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        disableRisk();

        final EditText present_amount_edit_text = findViewById(R.id.add_activity_present_amount);
        final TextView final_amount_text_view = findViewById(R.id.add_activity_final_amount);
        final Button submit_button = findViewById(R.id.add_activity_submit_button);
        submit_button.setVisibility(View.GONE);
        present_amount_edit_text.addTextChangedListener(new TextWatcher() {
            //Reference: https://stackoverflow.com/questions/20824634/android-on-text-change-listener
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try{
                    if(present_amount_edit_text.getText().toString().length() == 0) {
                        final_amount_text_view.setText("");
                        submit_button.setVisibility(View.GONE);
                        return;
                    }

                    EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
                    int temp_horizon = Integer.parseInt(horizon_edit_text.getText().toString());
                    Integer present_amount = Integer.parseInt(present_amount_edit_text.getText()
                            .toString());
                    Integer final_amount = Math.round((int)(Math.pow(inflation_rate, temp_horizon)
                            * present_amount));
                    final_amount_text_view.setText(""+final_amount);
                    submit_button.setVisibility(View.VISIBLE);
                }
                catch(NumberFormatException e){
                    Log.d(TAG, "Caught exception while changing final amount");
                    Toast.makeText(getApplicationContext(), "Error while processing (Only " +
                            "numbers allowed)!", Toast
                            .LENGTH_SHORT).show();
                    present_amount_edit_text.setText("");
                }
            }
        });

        final EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
        final LinearLayout linear_layout_present_amount = findViewById(R.id
                .add_activity_relative_present_amount);
        final LinearLayout linear_layout_final_amount = findViewById(R.id
                .add_activity_relative_final_amount);
        linear_layout_final_amount.setVisibility(View.GONE);
        linear_layout_present_amount.setVisibility(View.GONE);
        horizon_edit_text.addTextChangedListener(new TextWatcher() {
            //Reference: https://stackoverflow.com/questions/20824634/android-on-text-change-listener
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                try{
                    if(horizon_edit_text.getText().toString().length() == 0) {
                        final_amount_text_view.setText("");
                        linear_layout_final_amount.setVisibility(View.GONE);
                        linear_layout_present_amount.setVisibility(View.GONE);
                        return;
                    }
                    linear_layout_final_amount.setVisibility(View.VISIBLE);
                    linear_layout_present_amount.setVisibility(View.VISIBLE);
                    EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
                    if(present_amount_edit_text.getText().toString().length() == 0)
                    {
                        return;
                    }

                    int temp_horizon = Integer.parseInt(horizon_edit_text.getText().toString());
                    Integer present_amount = Integer.parseInt(present_amount_edit_text.getText()
                            .toString());
                    Log.d(TAG, "Here");
                    Integer final_amount = Math.round((int)(Math.pow(inflation_rate, temp_horizon)
                            * present_amount));
                    final_amount_text_view.setText(""+final_amount);
                }
                catch(NumberFormatException e){
                    Log.d(TAG, "Caught exception while changing final amount");
                    Toast.makeText(getApplicationContext(), "Error while processing (Only " +
                            "numbers allowed)!", Toast
                            .LENGTH_SHORT).show();
                    horizon_edit_text.setText("");
                }
            }
        });
    }
    private void enableName(){
        name_enabled = true;
        View view = findViewById(R.id.add_activity_relative_name);
        view.setVisibility(View.VISIBLE);
    }
    private void disableName(){
        name_enabled = false;
        View view = findViewById(R.id.add_activity_relative_name);
        view.setVisibility(View.GONE);
    }
    private void disableRisk(){
        System.out.println("Disabling custom");
        risk_enabled = false;
        View view = findViewById(R.id.add_activity_relative_risk);
        view.setVisibility(View.GONE);
    }
    private void enableRisk(){
        System.out.println("Enabling custom");
        risk_enabled = true;
        View view = findViewById(R.id.add_activity_relative_risk);
        view.setVisibility(View.VISIBLE);
    }
    public void Submit(View view){
        if(name_enabled){
            EditText name_edit_text = findViewById(R.id.add_activity_name);
            purpose = name_edit_text.getText().toString();
        }
        else {
            Spinner spinner = findViewById(R.id.add_activity_purpose);
            purpose = spinner.getSelectedItem().toString();
        }

        if(risk_enabled) {
            Spinner risk_spinner = findViewById(R.id.add_activity_risk);
            risk = risk_spinner.getSelectedItemPosition();
        }
        else {
            risk = risk_map.get(purpose);
        }
        System.out.println(risk_enabled + " "+ risk);
        try {
            EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
            horizon = Integer.parseInt(horizon_edit_text.getText().toString());
            EditText present_amount_edit_text = findViewById(R.id.add_activity_present_amount);
            present_amount = Integer.parseInt(present_amount_edit_text.getText().toString());
            TextView final_amount_view_text = findViewById(R.id.add_activity_final_amount);
            final_amount = Integer.parseInt(final_amount_view_text.getText().toString());
            if(purpose.equals("Tax-Saving"))
                is_tax_saving = true;
            if(purpose.equals("Retirement-Saving"))
                is_retirement_saving = true;
        } catch (Exception e) {
            Toast.makeText(this, "Some error occurred in input", Toast.LENGTH_SHORT).show();
            return;
        }
        Requirement new_requirement = new Requirement(returns, risk, horizon, purpose,
                present_amount, final_amount, is_tax_saving, is_retirement_saving);
        Bundle b = new Bundle();
        b.putSerializable("requirement", new_requirement);
        Intent intent = new Intent();
        intent.putExtra("bundle_requirement", b);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(parent.getItemAtPosition(position).toString().equals("Custom")) {
            //enable different fields
            enableRisk();
            enableName();
        }
        else if (parent
                .getItemAtPosition(position).toString().equals("Tax-Saving"))
        {
            enableRisk();
            disableName();
        }
        else{
            disableRisk();
            disableName();
        }


    }

    public void onNothingSelected(AdapterView<?> parent) {
        disableRisk();
    }


}
