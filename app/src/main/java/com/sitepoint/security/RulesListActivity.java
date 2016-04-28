package com.sitepoint.security;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.security.R;

import java.util.ArrayList;
import java.util.List;

public class RulesListActivity extends AppCompatActivity {

    private Firebase mRef;
    private String mUserId;
    private String itemsUrl;
    private List<RuleObject> rules;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_list);
        listview = (ListView) findViewById(R.id.listview);

        // Check Authentication
        mRef = new Firebase(Constants.FIREBASE_URL);
        if (mRef.getAuth() == null) {
            loadLoginView();
        }

        try {
            mUserId = mRef.getAuth().getUid();
        } catch (Exception e) {
            loadLoginView();
        }

        itemsUrl = Constants.FIREBASE_URL + "/users/" + mUserId + "/items";

        rules = new ArrayList<>();

        // ****** EDIT RULES AND REGULATIONS HERE ******//
        rules.add(new RuleObject("-----------------------------------------------------------------"));
        rules.add(new RuleObject("What Are Bouncers Legally Allowed to Do?"));
        rules.add(new RuleObject("-----------------------------------------------------------------"));
        rules.add(new RuleObject("- Issue verbal warnings"));
        rules.add(new RuleObject("- Ask you to leave"));
        rules.add(new RuleObject("- Check for ID"));
        rules.add(new RuleObject("- Refuse entry if the patron is too intoxicated, fails to comply with establishment policies, or engages in aggressive behavior"));
        rules.add(new RuleObject("- Call the police"));
        rules.add(new RuleObject("- Protect innocent bystanders from violence"));
        rules.add(new RuleObject("- Break up fights that are not involved in"));
        rules.add(new RuleObject("- Respond with equal force if necessary"));
        rules.add(new RuleObject("-----------------------------------------------------------------"));
        rules.add(new RuleObject("-----------------------------------------------------------------"));
        rules.add(new RuleObject("What Are Bouncers Not Entitled to Do?"));
        rules.add(new RuleObject("-----------------------------------------------------------------"));
        rules.add(new RuleObject("- Strike a patron with a punch or kick"));
        rules.add(new RuleObject("- Push or physically throw a person out of the establishment"));
        rules.add(new RuleObject("- Restrain them in a chokeholds, “joint locks”, or other techniques"));
        rules.add(new RuleObject("- Use weapons or pepper spray"));
        rules.add(new RuleObject("- Call the police"));
        rules.add(new RuleObject("-----------------------------------------------------------------"));

        RuleAdapter adapter = new RuleAdapter(rules);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RuleObject rule = rules.get(position);
            }
        });

    }

    private class RuleAdapter extends ArrayAdapter<RuleObject> {

        public RuleAdapter(List<RuleObject> items) {
            super(RulesListActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.row_rules, null);
            }
            TextView lblTitle2 = (TextView) convertView.findViewById(R.id.lblTitle2);

            RuleObject rule = getItem(position);

            lblTitle2.setText(rule.getInfo());

            return convertView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_logout) {
            mRef.unauth();
            loadLoginView();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadLoginView() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}


