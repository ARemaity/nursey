package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class client_own_profile extends AppCompatActivity {
    TextView fname,address,phone,email,pname,pcase;
    private String URLstring = "http://nursey.000webhostapp.com/api/getsingleclient.php?cid=";
    private static ProgressDialog mProgressDialog;
    Button update;
    PreferenceUtils utils = new PreferenceUtils();

    ProgressDialog progress;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_own_profile);
        final int ids=utils.getID(client_own_profile.this);
        fname=findViewById(R.id.clientownfirstName);
        address=findViewById(R.id.clientownaddress);
        phone=findViewById(R.id.clientownnum);
        email=findViewById(R.id.clientownemail);
        pname=findViewById(R.id.clientownpname);
        pcase=findViewById(R.id.clientownpdetails);
        update=findViewById(R.id.updateclientbtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(client_own_profile.this, client_update_profile.class);
                startActivity(intent);
            }
        });
        setURLstring(ids);
        retrieveJSON();





    }
    public void setURLstring(int id) {
        this.URLstring+=id;
    }
    private void retrieveJSON() {

        showSimpleProgressDialog(client_own_profile.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(client_own_profile.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);



                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {


                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    fname.setText("name : "+dataobj.getString("fname"));
                                    address.setText("Address: "+dataobj.getString("address"));
                                    phone.setText("Phone number : "+dataobj.getString("phone_number"));
                                    email.setText("Email :"+dataobj.getString("email"));
                                    pname.setText("Patient name : "+dataobj.getString("patient_name"));
                                    pcase.setText("Patient Case : "+dataobj.getString("case_details"));

                                    removeSimpleProgressDialog();
                                }



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
    @Override
    protected void onRestart() {
        super.onRestart();
        if(utils.getID(client_own_profile.this)==0){
            client_own_profile.this.finish();
        }
        retrieveJSON();
    }
}
