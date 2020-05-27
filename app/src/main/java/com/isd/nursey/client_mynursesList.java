package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isd.nursey.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class client_mynursesList extends AppCompatActivity {

    private String URLstring = "https://nursey.000webhostapp.com/api/getaccnurses.php?cid=";
    PreferenceUtils utils = new PreferenceUtils();

    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<nurseModel> dataModelArrayList;
    private int nid;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_mynurses_list);
        final int ids=utils.getID(client_mynursesList.this);
        URLstring+=ids;
        listView=findViewById(R.id.mynurselistview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> a, View v, int position,
                                    long id) {

                nid= dataModelArrayList.get(position).getNID();
                Intent myIntent = new Intent(client_mynursesList.this, client_nurse_feedback.class);
                myIntent.putExtra("nid", nid);
                startActivity(myIntent);
            }
        });
        retrieveJSON();
    }

    private void retrieveJSON() {

        showSimpleProgressDialog(client_mynursesList.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(client_mynursesList.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);


                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    nurseModel x = new nurseModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    x.setName(dataobj.getString("fname"),dataobj.getString("lname"));
                                    x.setPhone_number(Integer.parseInt(dataobj.getString("phone_number")));
                                    x.setEmail(dataobj.getString("email"));
                                    x.setDob(dataobj.getString("dob"));
                                    x.setDob(dataobj.getString("domain"));
                                    x.setGender(dataobj.getString("gender"));
                                    x.setTime(Integer.parseInt(dataobj.getString(("time"))));
                                    x.setDay(dataobj.getString("day"));
                                    x.setAddress(dataobj.getString("address"));
                                    x.setNumberofHour(Integer.parseInt(dataobj.getString("nbhour")));



                                    if (dataModelArrayList.isEmpty()) {
                                        Log.d("befoooooooooore", ">>>>>>>>>>>>>>>>>>0000000000000000");

                                    } else {


                                        Log.d("befoooooooooore", ">>>>>>>>>11111111111111111111111");
                                    }


                                    dataModelArrayList.add(x);
                                    if (dataModelArrayList.isEmpty()) {
                                        Log.d("Aftrrrrrrrrr", ">>>>>>>>>>>>>>>>>>0000000000000000");


                                    } else {


                                        Log.d("Aftrrrrrrrr", ">>>>>>>>>11111111111111111111111");
                                    }

                                }

                                setupListview();

                            }else{


                                Toast.makeText(getApplicationContext(), "no data to view ", Toast.LENGTH_SHORT).show();
                                removeSimpleProgressDialog();
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);


    }
    private void setupListview(){

        removeSimpleProgressDialog();  //will remove progress dialog
        listAdapter =new mynurseAdapter(this,dataModelArrayList);
        listView.setAdapter(listAdapter);

    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
