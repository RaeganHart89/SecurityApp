package com.sitepoint.security;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.security.R;

public class ClickCounter extends ActionBarActivity implements View.OnClickListener {

    private Firebase mRef;
    private String mUserId;
    private String itemsUrl;

    Button addButton;
    Button subtractButton;
    Button resetButton;
    Button optionsButton;
    TextView textTitle;
    EditText scoreText;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_counter);

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


        addButton = (Button) findViewById(R.id.addButton);
        subtractButton = (Button) findViewById(R.id.subtractButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        optionsButton = (Button) findViewById(R.id.optionsButton);
        scoreText = (EditText) findViewById(R.id.editText);
        textTitle = (TextView) findViewById(R.id.myTextTitle);

        //---set on click listeners on the buttons-----
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ClickCounter.this, OptionsActivity.class);
                startActivity(i);
            }
        });

        // change font size of the text
        textTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
    }

    @Override
    public void onClick(View v) {
        if (v == addButton) {
            counter++;
            scoreText.setText(Integer.toString(counter));
            scoreText.setBackgroundColor(Color.WHITE);
        }
        if (v == subtractButton) {
            counter--;
            scoreText.setText(Integer.toString(counter));
            scoreText.setBackgroundColor(Color.WHITE);
        }

        if (v == resetButton) {
            counter = 0;
            scoreText.setText(Integer.toString(counter));
            scoreText.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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






