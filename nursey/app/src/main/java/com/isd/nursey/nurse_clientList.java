package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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

public class nurse_clientList extends AppCompatActivity {

    private String URLstring = "https://nursey.000webhostapp.com/api/getallclient.php?status=1&id=";
    PreferenceUtils utils = new PreferenceUtils();

    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<clientTimeModel> dataModelArrayList;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_client_list);
        final int ids=utils.getID(nurse_clientList.this);
        URLstring+=ids;
        listView=findViewById(R.id.listView1);
        retrieveJSON();
    }

    private void retrieveJSON() {

        showSimpleProgressDialog(nurse_clientList.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(nurse_clientList.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);


                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    clientTimeModel x = new clientTimeModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    Log.d("strrrrr", ">>" + dataobj.getString("TID"));
                                    x.setNumberofHour(Integer.parseInt(dataobj.getString("nbHour")));
                                    x.setName(dataobj.getString("fname"));
                                    x.setDay(dataobj.getString("day"));
                                    x.setTID(Integer.parseInt(dataobj.getString("TID")));

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
        listAdapter =new clientListAdapter(this,dataModelArrayList);
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
