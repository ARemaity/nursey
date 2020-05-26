package com.isd.nursey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class feedbackAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<feedbackModel> dataModelArrayList;

    public feedbackAdapter(Context context, ArrayList<feedbackModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount() {


        if (getCount() > 0) {
            return getCount();
        } else {
            return super.getViewTypeCount();
        }
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return dataModelArrayList.size();
    }

    public Object getItem(int position) {
        return dataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        feedbackAdapter.ViewHolder holder;

        // the getTag returns the viewHolder object set as a tag to the view
        if (convertView == null) {
            holder = new feedbackAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.feedbacklsit, null, true);


            holder.name = (TextView) convertView.findViewById(R.id.feedbackname);
            holder.content = (TextView) convertView.findViewById(R.id.feedbackmessage);


            convertView.setTag(holder);
        } else holder = (feedbackAdapter.ViewHolder) convertView.getTag();


        holder.name.setText(dataModelArrayList.get(position).getClientName());
        holder.content.setText(dataModelArrayList.get(position).getContent());




        return convertView;
    }

    private class ViewHolder {

        protected TextView name,content;

    }

}
