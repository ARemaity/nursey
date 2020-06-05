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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class nurse_client_requst_profile extends AppCompatActivity {
    TextView fname,address,phone,email,pname,pcase,time;
    private String URLstring = "https://nursey.000webhostapp.com/api/getclient.php?tid=";
    private static ProgressDialog mProgressDialog;
    Button delete,accept;
    private int TID,hour;
    private  String interval;
    String deleteURL = "https://nursey.000webhostapp.com/api/delete-client.php";
    String acceptURL = "https://nursey.000webhostapp.com/api/accept-client.php";
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_client_requst_profile);
        fname=findViewById(R.id.nurseClientReqfirstName);
        address=findViewById(R.id.nurseClientReqaddress);
        phone=findViewById(R.id.nurseClientReqnum);
        email=findViewById(R.id.nurseClientReqemail);
        pname=findViewById(R.id.nurseClientReqpname);
        pcase=findViewById(R.id.nurseClientReqpdetails);
        time=findViewById(R.id.nurseClientReqhour);
        delete=findViewById(R.id.deletebtn);
        accept=findViewById(R.id.acceptbtn);
        Intent mIntent = getIntent();
        TID = mIntent.getIntExtra("tid",0);
        hour = mIntent.getIntExtra("hour",0);
        setURLstring(TID);
        interval = mIntent.getStringExtra("time");
        time.setText(interval);
        retrieveJSON();


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSimpleProgressDialog(nurse_client_requst_profile.this, "Loading...","Please wait",false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, acceptURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                Toast.makeText(getApplicationContext(), ServerResponse , Toast.LENGTH_SHORT).show();
                                removeSimpleProgressDialog();
                                finish();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                removeSimpleProgressDialog();

                                // Showing error message if something goes wrong.
                                Toast.makeText(nurse_client_requst_profile.this, "the error"+volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tid",Integer.toString(TID));
                        params.put("hour",Integer.toString(hour));
                        return params;
                    }

                };

                requestQueue = Volley.newRequestQueue(nurse_client_requst_profile.this);


                requestQueue.add(stringRequest);

            }




        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSimpleProgressDialog(nurse_client_requst_profile.this, "Loading...","Please wait",false);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                Toast.makeText(getApplicationContext(), ServerResponse , Toast.LENGTH_SHORT).show();
                                removeSimpleProgressDialog();
                                finish();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {

                                // Hiding the progress dialog after all task complete.
                                removeSimpleProgressDialog();

                                // Showing error message if something goes wrong.
                                Toast.makeText(nurse_client_requst_profile.this, "the error"+volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tid",Integer.toString(TID));
                        return params;
                    }

                };

                requestQueue = Volley.newRequestQueue(nurse_client_requst_profile.this);


                requestQueue.add(stringRequest);

            }




        });

    }
    public void setURLstring(int id) {
        this.URLstring+=id;
    }
    private void retrieveJSON() {

        showSimpleProgressDialog(nurse_client_requst_profile.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(nurse_client_requst_profile.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);



                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {


                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    fname.setText("name : "+dataobj.getString("fname"));
                                    address.setText("Address: "+dataobj.getString("address"));
                                    phone.setText("Phone number : "+dataobj.getString("phone_number"));
                                    email.setText(dataobj.getString("email"));
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

}
