package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
    EditText cname,caddress,cnum,cpass,cspass,pname,pcase;
    String rname,raddress,rnum,rpass,rpname,rpcase;
    String iname,iaddress,ipname,ipcase;
    int iphone;
    Button updatep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_update_profile);
        pname=findViewById(R.id.updatepatientname);
        updatep=findViewById(R.id.updatepdetails);
        pcase=findViewById(R.id.updatecasedetail);
        cname=findViewById(R.id.updateclientname);
        caddress=findViewById(R.id.updateclientaddress);
        cnum=findViewById(R.id.updateclientnum);
        cpass=findViewById(R.id.updateclientfpass);
        cspass=findViewById(R.id.updateclientspass);
        Intent mIntent = getIntent();
        iphone=mIntent.getIntExtra("phone",0);
        iname= mIntent.getStringExtra("fname");
        iaddress= mIntent.getStringExtra("address");
        ipname= mIntent.getStringExtra("pname");
        ipcase= mIntent.getStringExtra("pcase");
        progress = new ProgressDialog(client_update_profile.this);
        final int cid=utils.getID(client_update_profile.this);
        final String cemail=utils.getEmail(client_update_profile.this);
        final String cpassword=utils.getPassword(client_update_profile.this);
        pname.setText(ipname);
        pcase.setText(ipcase);
        pname.setText(iname);
        caddress.setText(iaddress);
        cnum.setText(Integer.toString(iphone));
        cpass.setText(cpassword);
        cspass.setText(cpassword);
        cname.setText(iname);
        updatep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkif(cname)||checkif(caddress)||checkif(caddress)||checkif(cnum)||checkif(cpass)||checkif(cspass)||checkif(pname)||checkif(pcase)){

                    Toast.makeText(client_update_profile.this,"Please enter All details", Toast.LENGTH_LONG).show();


                }else {
                    if(validatePassword()) {

                        rname = cname.getText().toString();
                        raddress = caddress.getText().toString();
                        rnum = cnum.getText().toString();
                        rpass = cpass.getText().toString();
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
                                            PreferenceUtils.saveEmail("", client_update_profile.this);
                                            PreferenceUtils.saveType("", client_update_profile.this);
                                            PreferenceUtils.saveID(0, client_update_profile.this);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    client_update_profile.this.finish();
                                                }
                                            }, 1000);

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
                            params.put("fname", rname);
                            params.put("address", raddress);
                            params.put("pass", rpass);
                            params.put("email", cemail);
                            params.put("phone_number", rnum);
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

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    private boolean validatePassword() {
        String passwordInput = cpass.getText().toString().trim();
        String secondpassword = cspass.getText().toString().trim();
        if(!passwordInput.equals(secondpassword)){
            cspass.setError("Passwords not same");
            cspass.setText("");
            cpass.setText("");
            cspass.requestFocus();
            return false;
        }
        else {
            cpass.setError(null);
            return true;
        }
    }


}
