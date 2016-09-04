package com.sandeep.sthapit.maps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sandeep on 8/28/16.
 */
public class RequestDataAdapter extends ArrayAdapter<RequestData> implements View.OnClickListener {

    private ArrayList<RequestData> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView v_no;
        TextView type;
        TextView status;
        TextView capacity;
        TextView time;
        TextView location;
    }

    public RequestDataAdapter(ArrayList<RequestData> data, Context context) {
        super(context, R.layout.request_item, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RequestData data = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.request_item, parent, false);
            viewHolder.v_no = (TextView) convertView.findViewById(R.id.textView_v_no);
            viewHolder.type = (TextView) convertView.findViewById(R.id.textView_v_type);
            viewHolder.capacity = (TextView) convertView.findViewById(R.id.textView_v_capacity);
            viewHolder.status = (TextView) convertView.findViewById(R.id.textView_v_status);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textView_v_time);
            viewHolder.location = (TextView) convertView.findViewById(R.id.textView_v_c_location);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        String temp;
        viewHolder.v_no.setText(data.getVno());
        temp = "Type: " + data.getType();
        viewHolder.type.setText(temp);
        temp = "Capacity: " + data.getCapacity();
        viewHolder.capacity.setText(temp);
        temp = "Time: " + data.getTime();
        viewHolder.time.setText(temp);
        temp = "Status: " + data.getStatus();
        viewHolder.status.setText(temp);
        temp = "Location: " + data.getLocation();
        viewHolder.location.setText(temp);
        // Return the completed view to render on screen
        return convertView;
    }
}