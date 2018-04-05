package com.iiitd.finance.portfolimanager2;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

public class AddActivity extends Activity implements AdapterView.OnItemSelectedListener {
    float returns;
    int horizon;
    int risk;
    int final_amount, present_amount;
    String purpose;
    Boolean custom_enabled = false;
    String[] items_risk = new String[]{"Low", "Medium", "High"};
    String[] items_purpose = new String[]{"Education", "Marriage", "Custom"};
    TreeMap<String, Integer> risk_map = new TreeMap<>();
    static Float inflation_rate = 1.1f;
    static String TAG = "AddActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        risk_map.put("Education", 0); risk_map.put("Marriage", 1); risk_map.put("World Tour", 2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getActionBar().setDisplayHomeAsUpEnabled(false);
        Spinner dropdown = findViewById(R.id.add_activity_risk);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items_risk);
        dropdown.setAdapter(adapter);
//        dropdown.setOnItemSelectedListener(this);

        dropdown = findViewById(R.id.add_activity_purpose);
        adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_spinner_dropdown_item, items_purpose);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);
        disableCustom();

        final EditText present_amount_edit_text = findViewById(R.id.add_activity_present_amount);
        final TextView final_amount_text_view = findViewById(R.id.add_activity_final_amount);

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
                        return;
                    }

                    EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
                    int temp_horizon = Integer.parseInt(horizon_edit_text.getText().toString());
                    Integer present_amount = Integer.parseInt(present_amount_edit_text.getText()
                            .toString());
                    Integer final_amount = Math.round((int)(Math.pow(inflation_rate, temp_horizon)
                            * present_amount));
                    final_amount_text_view.setText(""+final_amount);
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
    private void disableCustom(){
        System.out.println("Disabling custom");
        custom_enabled = false;
        View view = findViewById(R.id.add_activity_relative_risk);
        view.setVisibility(View.GONE);
    }
    private void enableCustom(){
        System.out.println("Enabling custom");
        custom_enabled = true;
        View view = findViewById(R.id.add_activity_relative_risk);
        view.setVisibility(View.VISIBLE);
    }
    public void Submit(View view){
        Spinner spinner = findViewById(R.id.add_activity_purpose);
        purpose = spinner.getSelectedItem().toString();
        if(custom_enabled) {
            Spinner risk_spinner = findViewById(R.id.add_activity_risk);
            risk = risk_spinner.getSelectedItemPosition();
        }
        else {
            risk = risk_map.get(purpose);
        }
        System.out.println(custom_enabled + " "+ risk);
        try {
            EditText horizon_edit_text = findViewById(R.id.add_activity_horizon);
            horizon = Integer.parseInt(horizon_edit_text.getText().toString());
            EditText present_amount_edit_text = findViewById(R.id.add_activity_present_amount);
            present_amount = Integer.parseInt(present_amount_edit_text.getText().toString());
            TextView final_amount_view_text = findViewById(R.id.add_activity_final_amount);
            final_amount = Integer.parseInt(final_amount_view_text.getText().toString());

        } catch (Exception e) {
            Toast.makeText(this, "Some error occurred in input", Toast.LENGTH_SHORT).show();
            return;
        }
        Requirement new_requirement = new Requirement(returns, risk, horizon, purpose,
                present_amount, final_amount);
        Bundle b = new Bundle();
        b.putSerializable("requirement", new_requirement);
        Intent intent = new Intent();
        intent.putExtra("bundle_requirement", b);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        if(parent.getItemAtPosition(position).toString().equals("Custom"))
            //enable different fields
            enableCustom();
        else
            disableCustom();

    }

    public void onNothingSelected(AdapterView<?> parent) {
        disableCustom();
    }


}
