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
import android.widget.ImageView;
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

public class nurse_own_profile extends AppCompatActivity {
    PreferenceUtils utils = new PreferenceUtils();
    nurseModel nm;
TextView name,email,address,type,phone,gender,age;
private String URLstring = "http://nursey.000webhostapp.com/api/getnurse.php?nid=";
    private String imgURL = "http://nursey.000webhostapp.com/uploads/image-";
    private static ProgressDialog mProgressDialog;
Button cvbtn,schbtn,updatebtn;
ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_own_profile);
        final int ids=utils.getID(nurse_own_profile.this);
        imgURL=imgURL+ids+".jpg";
        name=findViewById(R.id.nurseownname);
        email=findViewById(R.id.nurseownemail);
        address=findViewById(R.id.nurseownaddress);
        type=findViewById(R.id.nurseowntype);
        age=findViewById(R.id.nurseownage);
        phone=findViewById(R.id.nurseownphonenum);
        gender=findViewById(R.id.nurseowngender);
        cvbtn=findViewById(R.id.nurseowncv);
        schbtn=findViewById(R.id.nurseownschd);
        updatebtn=findViewById(R.id.updatenursebtn);
        profile=findViewById(R.id.nurseownimg);
        if(URLUtil.isValidUrl(imgURL)){
            Glide.with(this).load(imgURL).into(profile);
        }


        setURLstring(ids);
        retrieveJSON();
        cvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nursey.000webhostapp.com/uploads/"+ids+".pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        schbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nurse_own_profile.this, nurse_add_schdul.class);
                startActivity(intent);
            }
        });
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nurse_own_profile.this, nurse_update_profile.class);
                startActivity(intent);
            }
        });

    }
    public void setURLstring(int id) {
        this.URLstring+=id;
    }
    private void retrieveJSON() {

        showSimpleProgressDialog(nurse_own_profile.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(nurse_own_profile.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {
                                    nm =new nurseModel();

                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    name.setText(dataobj.getString("fname")+" "+dataobj.getString("lname"));
                                    address.setText(dataobj.getString("address"));
                                    nm.setDob(dataobj.getString("dob"));
                                    age.setText(nm.getAge());
                                    phone.setText(dataobj.getString("phone_number"));
                                    email.setText(dataobj.getString("email"));
                                    gender.setText(dataobj.getString("gender"));
                                    type.setText(dataobj.getString("domain"));

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
        retrieveJSON();
    }
}
