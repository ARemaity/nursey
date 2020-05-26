package com.isd.nursey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class nurseListAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<nurseModel> dataModelArrayList;

    public nurseListAdapter(Context context, ArrayList<nurseModel> dataModelArrayList) {

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
        nurseListAdapter.ViewHolder holder;

        // the getTag returns the viewHolder object set as a tag to the view
        if (convertView == null) {
            holder = new nurseListAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nurselist, null, true);



            holder.name = (TextView) convertView.findViewById(R.id.nursename);
            holder.age = (TextView) convertView.findViewById(R.id.nurseage);
            holder.gender = (TextView) convertView.findViewById(R.id.nursegender);
            holder.interval = (TextView) convertView.findViewById(R.id.nurseinterval);
            holder.day = (TextView) convertView.findViewById(R.id.nurseday);
            convertView.setTag(holder);
        }else holder = (nurseListAdapter.ViewHolder) convertView.getTag();


        holder.name.setText(dataModelArrayList.get(position).getName());
        holder.age.setText(dataModelArrayList.get(position).getAge());
        holder.gender.setText(dataModelArrayList.get(position).getGender());
        holder.interval.setText(dataModelArrayList.get(position).gettimeInterval());
        holder.day.setText(dataModelArrayList.get(position).getDay());


        return convertView;
    }

    private class ViewHolder {

        protected TextView  name,gender,age,interval,day;

    }

}
