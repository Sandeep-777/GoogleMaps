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

import java.io.IOException;
import java.util.ArrayList;


public class RouteDisplayActivity extends AppCompatActivity {

    TextView tv_list;
    EditText et_from;
    EditText et_to;
    TextView tv_direct;
    TextView tv_indirect;
    ListView listView;
    ListView indirectlistView;
    DatabaseHelper myDbHelper;
    ListView directListView;
    ListView indirectListView;
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_display);
        init();
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
                Intent intent = new Intent(RouteDisplayActivity.this, MainActivity.class);
                Toast.makeText(RouteDisplayActivity.this, "Displaying Maps", Toast.LENGTH_SHORT).show();
                RouteDisplayActivity.this.startActivity(intent);
                return true;

            case R.id.menu_credits:
                Toast.makeText(RouteDisplayActivity.this, "Save is Selected", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void init() {
        myDbHelper = new DatabaseHelper(this);
        listView = (ListView) findViewById(R.id.listView_routeListDirect);
        indirectlistView = (ListView) findViewById(R.id.listView_routeListIndirect);
        tv_direct = (TextView) findViewById(R.id.tv_direct_routes);
        tv_indirect = (TextView) findViewById(R.id.tv_indirect_routes);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String route = ((TextView)view).getText().toString();
                Intent intent = new Intent(RouteDisplayActivity.this, RouteInformationActivity.class);
                intent.putExtra("name", route);
                startActivity(intent);
                Toast.makeText(getBaseContext(), route, Toast.LENGTH_LONG).show();

            }
        });

    }

    public void show_routes(View view) {
        tv_list = (TextView) findViewById(R.id.tv_query_route_list);
        et_from = (EditText) findViewById(R.id.et_query_from);
        et_to = (EditText) findViewById(R.id.et_query_to);
        directListView = (ListView) findViewById( R.id.listView_routeListDirect );
        indirectListView = (ListView) findViewById( R.id.listView_routeListIndirect );
        tv_direct.setVisibility(View.VISIBLE);
        tv_indirect.setVisibility(View.VISIBLE);

        ArrayList<String> direct = myDbHelper.showIntersectList(et_from.getText().toString(), et_to.getText().toString());
        if (direct.size() < 1) {
            direct.add("No Direct Routes");
        }
        ArrayList<String> indirect = myDbHelper.showUnionList(et_from.getText().toString(), et_to.getText().toString());
        if (indirect.size() < 1) {
            indirect.add("No Indirect Routes");
        }
        listAdapter = new ArrayAdapter<String>(this, R.layout.route_item, direct);
        directListView.setAdapter( listAdapter );
        listAdapter = new ArrayAdapter<String>(this, R.layout.route_item, indirect);
        indirectListView.setAdapter(listAdapter);

    }

    public void go_to_map(View view) {
        Intent intent = new Intent(RouteDisplayActivity.this, MainActivity.class);

        Toast.makeText(view.getContext(), "Map Location", Toast.LENGTH_SHORT).show();
        RouteDisplayActivity.this.startActivity(intent);
    }

}
