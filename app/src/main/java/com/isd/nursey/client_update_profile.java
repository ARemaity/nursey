package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isd.nursey.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class client_update_profile extends AppCompatActivity {
    String HttpUrlpost = "https://nursey.000webhostapp.com/api/client-update.php";
    private static ProgressDialog mProgressDialog;
    ProgressDialog progress;
    RequestQueue requestQueue;
    PreferenceUtils utils = new PreferenceUtils();
    EditText pname,pcase;
    String rpname,rpcase;
    Button updatep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_update_profile);
        pname=findViewById(R.id.updatepatientname);
        updatep=findViewById(R.id.updatepdetails);
        pcase=findViewById(R.id.updatecasedetail);
        progress = new ProgressDialog(client_update_profile.this);
        final int cid=utils.getID(client_update_profile.this);

        updatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkif(pname)||checkif(pcase)){

                    Toast.makeText(client_update_profile.this,"Please enter All details", Toast.LENGTH_LONG).show();


                }else{




                            rpname = pname.getText().toString();
                            rpcase = pcase.getText().toString();

                            progress.setMessage("Please Wait, Server is Working :)");
                            progress.show();


                            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlpost,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String ServerResponse) {

                                            // Hiding the progress dialog after all task complete.
                                            progress.dismiss();

                                            // Showing response message coming from server.
                                            Toast.makeText(client_update_profile.this, ServerResponse, Toast.LENGTH_LONG).show();
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    client_update_profile.this.finish();
                                                }
                                            }, 2000);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {

                                            // Hiding the progress dialog after all task complete.
                                            progress.dismiss();

                                            // Showing error message if something goes wrong.
                                            Toast.makeText(client_update_profile.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() {

                                    // Creating Map String Params.
                                    Map<String, String> params = new HashMap<String, String>();

                                    params.put("cid", Integer.toString(cid));
                                    params.put("patient_name", rpname);
                                    params.put("case_details", rpcase);

                                    return params;
                                }

                            };

                            // Creating RequestQueue.
                            RequestQueue requestQueue = Volley.newRequestQueue(client_update_profile.this);

                            // Adding the StringRequest object into requestQueue.
                            requestQueue.add(stringRequest);

                        }
                    }


        });

    }

    public boolean checkif(EditText inputs){

        String sUsername = inputs.getText().toString();
        if (sUsername.matches("")) {
            return  true;
        }
        return  false;
    }



}
