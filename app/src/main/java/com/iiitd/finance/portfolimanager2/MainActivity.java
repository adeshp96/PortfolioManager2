package com.iiitd.finance.portfolimanager2;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

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
        ListView listView = (ListView) findViewById(R.id.requirements_list);
        listView.setAdapter(adapter);
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.add:
                Intent intent = new Intent(this, AddActivity.class);
                startActivityForResult(intent, AddActivityRequestCode);
                return true;
            case R.id.submit:
                if(adapter.requirements.size() == 0){
                    Toast.makeText(this, "Empty list", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent intent2 = new Intent(this, ResultActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("requirements", adapter.requirements);
                intent2.putExtra("bundle_requirements", b);
                startActivity(intent2);
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
