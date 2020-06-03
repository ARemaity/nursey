package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.isd.nursey.utils.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class client_nurse_req_profile extends AppCompatActivity {

    PreferenceUtils utils = new PreferenceUtils();
    nurseModel nm;
    TextView name,address,type,gender,age,time;
    EditText nbofhour;
    ImageView profile;
    String finalnbhour;
    private String URLstring = "http://nursey.000webhostapp.com/api/getsinglenurse.php?nid=";
    private String URLRequest = "http://nursey.000webhostapp.com/api/requestnurse.php";
    private String imgURL = "http://nursey.000webhostapp.com/uploads/image-";
    private static ProgressDialog mProgressDialog;
    public int nurseid;
    Button cvbtn,requestbtn;
    private ListView listView;
    LinearLayout ll;
    LinearLayout fl;
    int sid,nursenbhour,clienthour;
    String timerange;
    ArrayList<feedbackModel> dataModelArrayList;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_nurse_req_profile);
        name=findViewById(R.id.clientreqnursename);
        address=findViewById(R.id.clientreqnurseaddress);
        type=findViewById(R.id.clientreqnursetype);
        final int ids=utils.getID(client_nurse_req_profile.this);
        imgURL=imgURL+ids+".jpg";
        age=findViewById(R.id.clientreqnurseage);
        gender=findViewById(R.id.clientreqnursegender);
        cvbtn=findViewById(R.id.clientreqnursecv);
        time=findViewById(R.id.clientreqnursetime);
        requestbtn=findViewById(R.id.requstnusebtn);
        ll = findViewById(R.id.maincontainer);
        fl =  findViewById(R.id.feedbackcontainer);
        nbofhour=findViewById(R.id.clientnbofhour);
        listView=findViewById(R.id.clientreqnursfeedbacklist);
        profile=findViewById(R.id.clientreqimage);
        if(URLUtil.isValidUrl(imgURL)){
            Glide.with(this).load(imgURL).into(profile);
        }
        Intent mIntent = getIntent();
        nurseid = mIntent.getIntExtra("nid",0);
        sid = mIntent.getIntExtra("sid",0);
        nursenbhour=mIntent.getIntExtra("nursenbhour",0);
        clienthour=mIntent.getIntExtra("clienthour", clienthour);
        timerange= mIntent.getStringExtra("timerange");
        time.setText(timerange);

        setURLstring(nurseid);
        retrieveJSON();
        cvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nursey.000webhostapp.com/uploads/"+nurseid+".pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        requestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalnbhour = nbofhour.getText().toString().trim();

                if (finalnbhour.isEmpty()) {
                    nbofhour.setError("can't be empty");
                    nbofhour.setText("");
                    nbofhour.requestFocus();

                } else {
                    int value = Integer.parseInt(finalnbhour);
                    if (value > nursenbhour) {
                        nbofhour.setError("can't be more than " + nursenbhour);
                        nbofhour.setText("");
                        nbofhour.requestFocus();
                    } else {
                        showSimpleProgressDialog(client_nurse_req_profile.this, "Loading...", "Please wait", false);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLRequest,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String ServerResponse) {

                                        Toast.makeText(getApplicationContext(), ServerResponse, Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(client_nurse_req_profile.this, "the error" + volleyError.toString(), Toast.LENGTH_LONG).show();

                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {


                                Map<String, String> params = new HashMap<String, String>();
                                params.put("sid", Integer.toString(sid));
                                params.put("cid", Integer.toString(ids));
                                params.put("time", Integer.toString(clienthour));
                                params.put("nbofhour", finalnbhour);
                                return params;
                            }

                        };

                        RequestQueue requestQueues = Volley.newRequestQueue(client_nurse_req_profile.this);


                        requestQueues.add(stringRequest);
                    }
                }
            }
        });

    }
    public void setURLstring(int id) {
        this.URLstring+=id;
    }
    private void retrieveJSON() {

        showSimpleProgressDialog(client_nurse_req_profile.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(client_nurse_req_profile.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {
                                    nm =new nurseModel();

                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    name.setText(dataobj.getString("fname")+" "+dataobj.getString("lname"));
                                    address.setText(dataobj.getString("address"));
                                    nm.setDob(dataobj.getString("dob"));
                                    age.setText(nm.getAge());
                                    gender.setText(dataobj.getString("gender"));
                                    type.setText(dataobj.getString("domain"));

                                }
                                int therefeedback=Integer.parseInt(ex.getString("fexsit"));
                                if(therefeedback==1) {
                                    dataModelArrayList = new ArrayList<>();
                                    JSONArray feedbackArray = obj.getJSONArray("feedback");

                                    for (int i = 0; i < feedbackArray.length(); i++) {

                                        feedbackModel x = new feedbackModel();
                                        JSONObject dataobjs = feedbackArray.getJSONObject(i);

                                        x.setClientName(dataobjs.getString("cname"));
                                        x.setContent(dataobjs.getString("content"));


                                        dataModelArrayList.add(x);


                                    }
                                if(dataModelArrayList.isEmpty()){

                              }else{
                           setupListview();
                  }

                                }else{

                                    ll.removeView(fl);
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
    private void setupListview(){

        removeSimpleProgressDialog();  //will remove progress dialog
        listAdapter =new feedbackAdapter(this,dataModelArrayList);
        listView.setAdapter(listAdapter);

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
        retrieveJSON();
    }
}
