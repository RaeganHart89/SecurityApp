package com.sitepoint.security;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.security.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OptionsActivity extends AppCompatActivity {

    private Firebase mRef;
    private String mUserId;
    private String itemsUrl;
    private Button CounterButton;
    private Button NotesButton;
    private Button BannedButton;
    private Button RulesButton;
    private Button MapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

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

        CounterButton = (Button) findViewById(R.id.CounterButton);
        NotesButton = (Button) findViewById(R.id.NotesButton);
        BannedButton = (Button) findViewById(R.id.BannedButton);
        RulesButton = (Button) findViewById(R.id.RulesButton);
        MapButton = (Button) findViewById(R.id.MapButton);

        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, MapActivity.class);
                startActivity(i);
            }
        });

        RulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, RulesListActivity.class);
                startActivity(i);
            }
        });

        BannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, BannedListActivity.class);
                startActivity(i);
            }
        });

        CounterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, ClickCounter.class);
                startActivity(i);

            }
        });

        NotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OptionsActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

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

    public void getCurrentDate(View view) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        SimpleDateFormat mdformat = new SimpleDateFormat("dd / MM / yyyy ");
        String strDate = "Current Date : " + mdformat.format(calendar.getTime());
        display(strDate);
    }

    private void display(String num) {
        TextView textView = (TextView) findViewById(R.id.current_date_view);
        textView.setText(num);
    }
}

