package com.sandeep.sthapit.maps;

import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

public class RouteInformationActivity extends AppCompatActivity {

    TextView tvView;
    ListView listView;
    String route;
    DatabaseHelper myDbHelper;
    ArrayAdapter<String> listAdapter;
    static ArrayList<LatLng> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_information);

        tvView = (TextView) findViewById(R.id.tv_route_title);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        route = name;
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
        locations = myDbHelper.showLatLongPlace(name);
        listAdapter = new ArrayAdapter<String>(this, R.layout.route_item, route);
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_map:
                Intent intent = new Intent(RouteInformationActivity.this, MainActivity.class);
                Toast.makeText(RouteInformationActivity.this, "Displaying Maps", Toast.LENGTH_SHORT).show();
                RouteInformationActivity.this.startActivity(intent);
                return true;

            case R.id.menu_credits:
                Toast.makeText(RouteInformationActivity.this, "Prepared by Sandeep Sthapit, Ramesh Neupane, Ujjwa Jung Thapa, Bidur Parajuli and Nirajan Mahato", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void show_route_on_map(View view) {
        Intent intent = new Intent(RouteInformationActivity.this, MapsWithRoute.class);
        intent.putParcelableArrayListExtra("locations", locations);
        Toast.makeText(view.getContext(), "Map Route Location", Toast.LENGTH_SHORT).show();
        RouteInformationActivity.this.startActivity(intent);
    }

    public void show_more_info(View view) {
        Intent intent = new Intent(RouteInformationActivity.this, RequestActivity.class);
        intent.putExtra("name", route);
    }

}
