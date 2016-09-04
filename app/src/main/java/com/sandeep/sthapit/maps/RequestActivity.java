package com.sandeep.sthapit.maps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;

public class RequestActivity extends AppCompatActivity {

    // json object response url
    private String urlJsonObj1 = "https://app123go.herokuapp.com/go/2";
    String urlJsonObj;
    // json array response url
    private String urlJsonArry = "http://app123go.herokuapp.com/test-all";
    private static String TAG = MainActivity.class.getSimpleName();

    TextView v_no;
    TextView v_type;
    TextView v_capacity;
    TextView v_status;
    TextView v_location;
    TextView v_time;

    ListView list;
    static RequestDataAdapter mAdapter;
    ArrayList<RequestData> items;

    // Progress dialog
    private ProgressDialog pDialog;
    private TextView txtResponse;
    // temporary string to show the parsed response
    private String jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        urlJsonObj = urlJsonObj1 + "/" + name;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        list = (ListView) findViewById(R.id.listView_request_data);
        v_no = (TextView) findViewById(R.id.textView_v_no);
        v_type = (TextView) findViewById(R.id.textView_v_type);
        v_capacity = (TextView) findViewById(R.id.textView_v_capacity);
        v_status = (TextView) findViewById(R.id.textView_v_status);
        v_location = (TextView) findViewById(R.id.textView_v_c_location);
        v_time = (TextView) findViewById(R.id.textView_v_time);

        items = new ArrayList<>();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonArrayRequest();

    }

    /**
     * Method to make json object request where json response starts wtih {
     */
//    private void makeJsonObjectRequest() {
//
//        showpDialog();
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET,
//                urlJsonObj, null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//
//                try {
//                    // Parsing json object response
//                    // response will be a json object
//                    String name = response.getString("id");
//                    String email = response.getString("v_no");
//                    String phone = response.getString("p_location");
//                    String home = response.getString("type");
//                    String mobile = response.getString("status");
//
//                    jsonResponse = "";
//                    jsonResponse += "id: " + name + "\n\n";
//                    jsonResponse += "v_no: " + email + "\n\n";
//                    jsonResponse += "p_location: " + home + "\n\n";
//                    jsonResponse += "type: " + mobile + "\n\n";
//
//                    txtResponse.setText(jsonResponse);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "Error: " + e.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//                hidepDialog();
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
//                // hide the progress dialog
//                hidepDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);
//    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeJsonArrayRequest() {

        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject item = (JSONObject) response
                                        .get(i);

                                String number = item.getString("v_no");
                                String type = item.getString("type");
                                String location = item.getString("c_location");
                                String status = item.getString("status");
                                String capacity = item.getString("capacity");
                                String time = item.getString("c_time");

                                RequestData rdata = new RequestData(number, type, Integer.decode(capacity), status, time, location);
                                items.add(rdata);

                            }
                            mAdapter = new RequestDataAdapter(items, getApplicationContext());
                            list.setAdapter(mAdapter);
                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    RequestData dataModel = items.get(position);
//
//                Snackbar.make(view, dataModel.getName()+"\n"+dataModel.getType()+" API: "+dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
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
                Intent intent = new Intent(RequestActivity.this, MainActivity.class);
                Toast.makeText(RequestActivity.this, "Displaying Maps", Toast.LENGTH_SHORT).show();
                RequestActivity.this.startActivity(intent);
                return true;

            case R.id.menu_credits:
                Toast.makeText(RequestActivity.this, "Prepared by Sandeep Sthapit, Ramesh Neupane, Ujjwa Jung Thapa, Bidur Parajuli and Nirajan Mahato", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}