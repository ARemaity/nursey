package com.isd.nursey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class clientListAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<clientTimeModel> dataModelArrayList;

    public clientListAdapter(Context context, ArrayList<clientTimeModel> dataModelArrayList) {

        this.context = context;
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public int getViewTypeCount(){


        if(getCount() > 0){
            return getCount();
        }else{
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
        ViewHolder holder;

        // the getTag returns the viewHolder object set as a tag to the view
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.clientlist, null, true);



            holder.name = (TextView) convertView.findViewById(R.id.text1);
            holder.day = (TextView) convertView.findViewById(R.id.text2);
            holder.time = (TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();


        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.day.setText(dataModelArrayList.get(position).getDay());
        holder.time.setText(dataModelArrayList.get(position).gettimeInterval());



        return convertView;
    }

    private class ViewHolder {

        protected TextView  name,day,time;

    }

}
