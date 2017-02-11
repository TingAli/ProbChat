package com.bluelead.probchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.bluelead.probchat.UI.ChannelActivity;

public class MainActivity extends AppCompatActivity {

    private TextView mResponseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response); //activity_main


        mResponseTextView = (TextView) findViewById(R.id.response_tv);

        //new WebSocketAsync().execute();


        //new AsyncMethod().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main_menu, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_start) {
            //start activity channel
            startActivity(new Intent(this, ChannelActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
