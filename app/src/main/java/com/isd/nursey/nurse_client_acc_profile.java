package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import static android.Manifest.permission.CALL_PHONE;

public class nurse_client_acc_profile extends AppCompatActivity {
TextView fname,address,phone,email,pname,pcase,time;
    private String URLstring = "https://nursey.000webhostapp.com/api/getclient.php?tid=";
    private static ProgressDialog mProgressDialog;
    Button delete,contact;
    private int TID;
    private  String interval;
    String deleteURL = "https://nursey.000webhostapp.com/api/delete-client.php";
    ProgressDialog progress;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_client_acc_profile);
        fname=findViewById(R.id.nurseClientfirstName);
        address=findViewById(R.id.nurseClientProfileaddress);
        phone=findViewById(R.id.nurseClientProfilenum);
        email=findViewById(R.id.nurseClientemail);
        pname=findViewById(R.id.nurseClientProfilepname);
        pcase=findViewById(R.id.nurseClientProfilepdetails);
        time=findViewById(R.id.nurseClientProfilehour);
        delete=findViewById(R.id.nurseClientdltBtn);
        contact=findViewById(R.id.contactclient);
        Intent mIntent = getIntent();
        TID = mIntent.getIntExtra("tid",0);
       setURLstring(TID);
        interval = mIntent.getStringExtra("time");

        retrieveJSON();
        delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                showSimpleProgressDialog(nurse_client_acc_profile.this, "Loading...","Please wait",false);

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
                                Toast.makeText(nurse_client_acc_profile.this, "the error"+volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tid",Integer.toString(TID));
                        return params;
                    }

                };

                requestQueue = Volley.newRequestQueue(nurse_client_acc_profile.this);


                requestQueue.add(stringRequest);

            }




        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);

                i.setData(Uri.parse("tel:"+phone.getText().toString()));

                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(i);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
            }

        });




    }
    public void setURLstring(int id) {
        this.URLstring+=id;
    }
    private void retrieveJSON() {

        showSimpleProgressDialog(nurse_client_acc_profile.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(nurse_client_acc_profile.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);



                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {


                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    fname.setText(dataobj.getString("fname"));
                                     address.setText(dataobj.getString("address"));
                                     phone.setText(dataobj.getString("phone_number"));
                                         email.setText(dataobj.getString("email"));
                                      pname.setText(dataobj.getString("patient_name"));
                                       pcase.setText("Case: "+dataobj.getString("case_details"));
                                    time.setText(interval);
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
