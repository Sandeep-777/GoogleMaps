package com.sandeep.sthapit.maps;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;

public class RouteInformationActivity extends AppCompatActivity {

    TextView tvView;
    ListView listView;
    DatabaseHelper myDbHelper;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_information);

        tvView = (TextView) findViewById(R.id.tv_route_title);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        tvView.setText(name);
        init(name);
    }

    public void init(String name) {
        myDbHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.lv_route_item);
        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
        listView = (ListView) findViewById(R.id.lv_route_item);
        ArrayList<String> route = myDbHelper.showPlaceInRoute(name);
        listAdapter = new ArrayAdapter<String>(this, R.layout.route_item, route);
        listView.setAdapter(listAdapter);
    }


}
