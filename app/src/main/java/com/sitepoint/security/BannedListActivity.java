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
import com.firebase.client.Firebase;
import com.security.R;

import java.util.ArrayList;
import java.util.List;

public class BannedListActivity extends AppCompatActivity {

    private Firebase mRef;
    private String mUserId;
    private String itemsUrl;

    private List<BannedObject> banneds;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banned_list);
        listview = (ListView)findViewById(R.id.listview);

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

        banneds = new ArrayList<>();

        //*******EDIT BANNED LIST ITEMS HERE********//

        banneds.add(new BannedObject("Amy Collins", R.mipmap.mug1, "Cordero Lounge"));
        banneds.add(new BannedObject("Jack Jones", R.mipmap.mug2, "Blue Boar"));
        banneds.add(new BannedObject("Emma Brown", R.mipmap.mug3, "The George"));
        banneds.add(new BannedObject("Rich Jackman", R.mipmap.mug4, "Lamb and Fountain"));
        banneds.add(new BannedObject("Connor Smith", R.mipmap.mug5, "Archangel"));
        banneds.add(new BannedObject("Jackie Foster", R.mipmap.mug6, "The Ship"));
        banneds.add(new BannedObject("Will Roberts", R.mipmap.mug7, "Vine Tree"));

        BannedAdapter adapter = new BannedAdapter(banneds);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BannedObject banned = banneds.get(position);
            }
        });

    }

    private class BannedAdapter extends ArrayAdapter<BannedObject>{

        public BannedAdapter(List<BannedObject> items) {
            super(BannedListActivity.this, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){

            if (convertView ==null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.row_banned, null);
            }

            TextView lblTitle = (TextView)convertView.findViewById(R.id.title);
            ImageView image = (ImageView)convertView.findViewById(R.id.image);

            BannedObject banned = getItem(position);

            lblTitle.setText(banned.getName());
            image.setBackgroundResource(banned.getPicture());

            return convertView;
        }
    }

    // Creates Menu Bar and Log-Out Function
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu
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
