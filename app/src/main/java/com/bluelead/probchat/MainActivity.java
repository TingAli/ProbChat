package com.bluelead.probchat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bluelead.probchat.Adapters.TypesSpinnerAdapter;
import com.bluelead.probchat.Models.Type;
import com.bluelead.probchat.NetworkUtils.Callback;
import com.bluelead.probchat.NetworkUtils.NetworkUtils;
import com.bluelead.probchat.UI.ChannelActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mErrorMessageTextView;
    private LinearLayout mMainContentLinearLayout, mLoadingLinearLayout;
    private ArrayList<Type> mTypesArrayList;
    private final Context CONTEXT = MainActivity.this;
    private Spinner mTypesSpinner;
    private TypesSpinnerAdapter mTypesSpinnerAdapter;
    private Type mTypeSelected;
    private Bundle mBundle;
    private RecyclerView mDocumentedRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //activity_main

        mErrorMessageTextView = (TextView) findViewById(R.id.main_errorMessage_tv);
        mMainContentLinearLayout = (LinearLayout) findViewById(R.id.main_content);
        mLoadingLinearLayout = (LinearLayout) findViewById(R.id.main_loading_ly);
        mTypesSpinner = (Spinner) findViewById(R.id.types_spinner);

        mTypesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                // Here you get the current item (a User object) that is selected by its position
                Type type = mTypesSpinnerAdapter.getItem(position);
                mTypeSelected = type;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
                mTypesSpinner.setSelection(0);
            }
        });

        new TypesQueryTask().execute();
    }

    public class TypesQueryTask extends AsyncTask<Void, Void, ArrayList<Type>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingLinearLayout.setVisibility(View.VISIBLE);
            mMainContentLinearLayout.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<Type> doInBackground(Void... params) {
            NetworkUtils.getTypes(CONTEXT,
                    new Callback<ArrayList<Type>>() {
                        @Override
                        public void next(ArrayList<Type> result) {
                            mTypesArrayList = result;
                            if(mTypesArrayList != null) {
                                showContent();
                            }
                            else {
                                showErrorMessage();
                            }
                        }
                    });
            return mTypesArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Type> types) {
            mLoadingLinearLayout.setVisibility(View.INVISIBLE);
            mMainContentLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    private void showContent() {
        mTypesSpinnerAdapter = new TypesSpinnerAdapter(CONTEXT, android.R.layout.simple_spinner_item, mTypesArrayList);
        mTypesSpinner.setAdapter(mTypesSpinnerAdapter);
        // First, make sure the error is invisible
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mMainContentLinearLayout.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        // First, hide the currently visible data
        mMainContentLinearLayout.setVisibility(View.INVISIBLE);
        // Then, show the error
        mErrorMessageTextView.setVisibility(View.VISIBLE);
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
            if(mTypeSelected != null) {
                Class channelActivity = ChannelActivity.class;
                Intent channelIntent = new Intent(CONTEXT, channelActivity);

                mBundle = new Bundle();
                mTypeSelected.setAction(Constants.MATCHING_ACTION);
                mBundle.putParcelable("PAR_KEY", mTypeSelected);
                channelIntent.putExtras(mBundle);

                startActivity(channelIntent);
            }
            else {
                Toast.makeText(CONTEXT, "ERROR", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
