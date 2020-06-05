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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CALL_PHONE;

public class client_nurse_accepted_profile extends AppCompatActivity {

    PreferenceUtils utils = new PreferenceUtils();
    TextView name,address,type,gender,age,time,email;
    EditText nbofhour;
    ImageView profile;
    String finalnbhour;
    ProgressDialog progress;
    RequestQueue requestQueue;
    String deleteURL = "https://nursey.000webhostapp.com/api/delete-client.php";
    private String imgURL = "http://nursey.000webhostapp.com/uploads/image-";
    private static ProgressDialog mProgressDialog;
    public int nurseid;
    Button cvbtn,callbtn,addfeedback,deletenurse;
    private ListView listView;
    int nids,tids,phones;
    String names,addresss,domains,genders,emails,rangetimes,ages;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_nurse_accepted_profile);
        final int ids=utils.getID(client_nurse_accepted_profile.this);

        name=findViewById(R.id.mysnursenursename);
        address=findViewById(R.id.mysnurseaddress);
        type=findViewById(R.id.mysnursetype);
        age=findViewById(R.id.mysnurseage);
        email=findViewById(R.id.mysnurseemail);
        gender=findViewById(R.id.mysnursegender);
        cvbtn=findViewById(R.id.mysnursecv);
        time=findViewById(R.id.mysnursetime);
        deletenurse=findViewById(R.id.mysnursedltBtn);
        addfeedback=findViewById(R.id.mysnurseaddfeedback);
        nbofhour=findViewById(R.id.clientnbofhour);
        callbtn=findViewById(R.id.mysnursecontact);
        profile=findViewById(R.id.clientaccimage);
        if(URLUtil.isValidUrl(imgURL)){
            Glide.with(this).load(imgURL).into(profile);
        }
        Intent mIntent = getIntent();
        nids = mIntent.getIntExtra("nid",0);
        imgURL=imgURL+nids+".jpg";
        tids = mIntent.getIntExtra("tid",0);
        phones=mIntent.getIntExtra("phone",0);
        names=mIntent.getStringExtra("name");
        addresss= mIntent.getStringExtra("address");
        domains= mIntent.getStringExtra("domain");
        genders= mIntent.getStringExtra("gender");
        emails= mIntent.getStringExtra("email");
        rangetimes= mIntent.getStringExtra("rangetime");
        ages= mIntent.getStringExtra("age");
        name.setText(names);
        address.setText(addresss);
        type.setText(domains);
        age.setText(ages);
        email.setText(emails);
        gender.setText(genders);
        time.setText(rangetimes);


        cvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://nursey.000webhostapp.com/uploads/"+nids+".pdf";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        addfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(client_nurse_accepted_profile.this, client_nurse_feedback.class);
                myIntent.putExtra("nid", nids);
                startActivity(myIntent);

            }
        });
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    Intent i = new Intent(Intent.ACTION_CALL);

                    i.setData(Uri.parse("tel:"+phones));

                    if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        startActivity(i);
                    } else {
                        requestPermissions(new String[]{CALL_PHONE}, 1);
                    }
                }

            }
        });

        deletenurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showSimpleProgressDialog(client_nurse_accepted_profile.this, "Loading...","Please wait",false);

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
                                Toast.makeText(client_nurse_accepted_profile.this, "the error"+volleyError.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {


                        Map<String, String> params = new HashMap<String, String>();
                        params.put("tid",Integer.toString(tids));
                        return params;
                    }

                };

                requestQueue = Volley.newRequestQueue(client_nurse_accepted_profile.this);


                requestQueue.add(stringRequest);

            }




        });

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
