package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import com.isd.nursey.utils.PreferenceUtils;

public class login_act extends AppCompatActivity {
    EditText emailEdittext, passwordEdittext;
    Button loginbtn, newaccbtn;
    String semail,spassword;
    String HttpUrlpost = "https://nursey.000webhostapp.com/api/login.php";
    ProgressDialog progress;
    RequestQueue requestQueue;
    PreferenceUtils utils = new PreferenceUtils();
    Dialog myDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_act);
        myDialog = new Dialog(this);


        emailEdittext = findViewById(R.id.emailLogin);
        passwordEdittext = findViewById(R.id.passwordLogin);
        loginbtn = findViewById(R.id.loginbtn);





        if (utils.getEmail(this) != null ) {

            if (utils.getType(this).equals("nurse")) {

                Intent intent = new Intent(login_act.this, nurse_main.class);
                startActivity(intent);

            } else if (utils.getType(this).equals("client")) {

                Intent intent = new Intent(login_act.this, client_main.class);
                startActivity(intent);


            }
        }


        progress = new ProgressDialog(login_act.this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if(checkif(emailEdittext)||checkif(passwordEdittext)){

    Toast.makeText(login_act.this,"Please enter All details", Toast.LENGTH_LONG).show();


        }else {
        if(validateEmail()){
    semail = emailEdittext.getText().toString();
    spassword = passwordEdittext.getText().toString();
    progress.setMessage("Please Wait, Server is Working :)");
    progress.show();


    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlpost,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String ServerResponse) {

                    // Hiding the progress dialog after all task complete.
                    progress.dismiss();

                    String[] parts = ServerResponse.split("/");
                    String part1 = parts[0];
                    String part2 = parts[1];
if(part1.equals("2")){
    int idnurse =Integer.parseInt(part2.trim());
    PreferenceUtils.saveEmail(semail, login_act.this);
    PreferenceUtils.saveType("client", login_act.this);
    PreferenceUtils.saveID(idnurse, login_act.this);
    Intent intent = new Intent(login_act.this, client_main.class);
    startActivity(intent);

}else if(part1.equals("1")){
    int idclient =Integer.parseInt(part2.trim());
    PreferenceUtils.saveEmail(semail, login_act.this);
    PreferenceUtils.saveType("nurse", login_act.this);
    PreferenceUtils.saveID(idclient, login_act.this);
    Intent intent = new Intent(login_act.this, nurse_main.class);
    startActivity(intent);
} else if(part1.equals("3")){
    //Toast.makeText(login_act.this, "email or password is incorrect ", Toast.LENGTH_LONG).show();
    Toast.makeText(login_act.this, "Your account is Deactivated", Toast.LENGTH_LONG).show();

    emailEdittext.setText("");
    passwordEdittext.setText("");
                    }


else if(part1.equals("4")){
    //Toast.makeText(login_act.this, "email or password is incorrect ", Toast.LENGTH_LONG).show();
    Toast.makeText(login_act.this, "Wrong email or password", Toast.LENGTH_LONG).show();

    emailEdittext.setText("");
    passwordEdittext.setText("");
}else{

    //Toast.makeText(login_act.this, "email or password is incorrect ", Toast.LENGTH_LONG).show();
    Toast.makeText(login_act.this, ServerResponse, Toast.LENGTH_LONG).show();

    emailEdittext.setText("");
    passwordEdittext.setText("");

}

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                    // Hiding the progress dialog after all task complete.
                    progress.dismiss();

                    // Showing error message if something goes wrong.
                    Toast.makeText(login_act.this, "the error"+volleyError.toString(), Toast.LENGTH_LONG).show();

                }
            }) {
        @Override
        protected Map<String, String> getParams() {

            // Creating Map String Params.
            Map<String, String> params = new HashMap<String, String>();

            // Adding All values to Params.
            params.put("email", semail);
            params.put("password", spassword);


            return params;
        }

    };

    // Creating RequestQueue.
    RequestQueue requestQueue = Volley.newRequestQueue(login_act.this);

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



    private boolean validateEmail() {
        String emailInput = emailEdittext.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailEdittext.setError("Field can't be empty");
            emailEdittext.setText("");
            emailEdittext.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailEdittext.setError("Please enter a valid email address");
            emailEdittext.setText("");
            emailEdittext.requestFocus();
            return false;
        } else {
            emailEdittext.setError(null);
            return true;
        }
    }

    public void showpopup(View view) {
        TextView txtclose;
        Button clientregsbtn;
        Button nurseregsbtn;
        myDialog.setContentView(R.layout.popup_regs);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);

        clientregsbtn = (Button) myDialog.findViewById(R.id.clientreg);
        nurseregsbtn = (Button) myDialog.findViewById(R.id.nurseregs);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        clientregsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_act.this, client_regs.class);
                startActivity(intent);
            }
        });
        nurseregsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_act.this, nurse_re.class);
                startActivity(intent);
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

}
