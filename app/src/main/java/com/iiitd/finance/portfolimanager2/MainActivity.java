package com.iiitd.finance.portfolimanager2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private static String TAG = "MainActivity";
    private static int AddActivityRequestCode = 0;
    RequirementsListAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AssetManager assetManager = getAssets();
        Manager.LoadDetails(assetManager);
        adapter = new RequirementsListAdapter(getApplicationContext(), null);
        adapter.requirements.add(new Requirement(1.2f, 0, 2, "Education",
                100000,100000, true));
        ListView listView = findViewById(R.id.requirements_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Requirement requirement = adapter.requirements.get(position);
                ArrayList<Requirement> requirementsToConsider = new ArrayList<>();
                requirementsToConsider.add(requirement);
                goToResult(requirementsToConsider);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
    private void goToResult(ArrayList<Requirement> requirements){
        if(requirements != null){
            Intent intent2 = new Intent(this, ResultActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("requirements", requirements);
            intent2.putExtra("bundle_requirements", b);
            startActivity(intent2);
            return;
        }
        if(adapter.requirements.size() == 0){
            Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
        }
        Intent intent2 = new Intent(this, ResultActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("requirements", adapter.requirements);
        intent2.putExtra("bundle_requirements", b);
        startActivity(intent2);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, AddActivityRequestCode);
                return true;
            case R.id.submit:
                goToResult(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddActivityRequestCode) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getBundleExtra("bundle_requirement");
                Requirement new_requirement = (Requirement)b.getSerializable("requirement");
                adapter.requirements.add(new_requirement);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
