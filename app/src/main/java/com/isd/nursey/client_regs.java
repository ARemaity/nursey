package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Patterns;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class client_regs extends AppCompatActivity {
    String HttpUrlpost = "https://nursey.000webhostapp.com/api/client-register.php";
    private static ProgressDialog mProgressDialog;
    ProgressDialog progress;
    RequestQueue requestQueue;

EditText cname,caddress,cnum,cemail,cpass,cspass,pname,pcase;
    String rname,raddress,rnum,remail,rpass,rpname,rpcase;
Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_regs);
        cname=findViewById(R.id.clientname);
        caddress=findViewById(R.id.clientaddress);
        cnum=findViewById(R.id.clientnum);
        cemail=findViewById(R.id.clientemail);
        cpass=findViewById(R.id.clientfpass);
        cspass=findViewById(R.id.clientspass);
        pname=findViewById(R.id.patientname);
        register=findViewById(R.id.submit);
        pcase=findViewById(R.id.casedetail);
       progress = new ProgressDialog(client_regs.this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if(checkif(cname)||checkif(caddress)||checkif(caddress)||checkif(cnum)||checkif(cemail)||checkif(cpass)||checkif(cspass)||checkif(pname)||checkif(pcase)){

    Toast.makeText(client_regs.this,"Please enter All details", Toast.LENGTH_LONG).show();


}else{

    if(validateEmail()){
if(validatePassword()) {

    rname = cname.getText().toString();
    raddress = caddress.getText().toString();
    remail = cemail.getText().toString();
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
                    Toast.makeText(client_regs.this, ServerResponse, Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            client_regs.this.finish();
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
                    Toast.makeText(client_regs.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
        @Override
        protected Map<String, String> getParams() {

            // Creating Map String Params.
            Map<String, String> params = new HashMap<String, String>();

            // Adding All values to Params.
            params.put("fname", rname);
            params.put("address", raddress);
            params.put("pass",rpass);
            params.put("email", remail);
            params.put("phone_number", rnum);
            params.put("patient_name", rpname);
            params.put("case_details", rpcase);

            return params;
        }

    };

    // Creating RequestQueue.
    RequestQueue requestQueue = Volley.newRequestQueue(client_regs.this);

    // Adding the StringRequest object into requestQueue.
    requestQueue.add(stringRequest);

}
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

    private boolean validateEmail() {
        String emailInput = cemail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            cemail.setError("Field can't be empty");
            cemail.setText("");
            cemail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            cemail.setError("Please enter a valid email address");
            cemail.setText("");
            cemail.requestFocus();
            return false;
        } else {
            cemail.setError(null);
            return true;
        }
    }


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
