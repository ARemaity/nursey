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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isd.nursey.utils.PreferenceUtils;

import java.util.HashMap;
import java.util.Map;

public class client_nurse_feedback extends AppCompatActivity {
    String HttpUrlpost = "https://nursey.000webhostapp.com/api/client-feedback.php";
    private static ProgressDialog mProgressDialog;
    ProgressDialog progress;
    RequestQueue requestQueue;
    PreferenceUtils utils = new PreferenceUtils();
    EditText message;
    String rmessage;
    Button addfeedback;
    int nid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_nurse_feedback);
        addfeedback = findViewById(R.id.addfeedback);
        message = findViewById(R.id.feedbackcontent);
        Intent mIntent = getIntent();
        nid = mIntent.getIntExtra("nid",0);
        progress = new ProgressDialog(client_nurse_feedback.this);
        final int cid = utils.getID(client_nurse_feedback.this);

        addfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkif(message)) {

                    Toast.makeText(client_nurse_feedback.this, "Please enter All details", Toast.LENGTH_LONG).show();


                } else {


                    rmessage = message.getText().toString();


                    progress.setMessage("Please Wait, Server is Working :)");
                    progress.show();


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlpost,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Hiding the progress dialog after all task complete.
                                    progress.dismiss();

                                    // Showing response message coming from server.
                                    Toast.makeText(client_nurse_feedback.this, ServerResponse, Toast.LENGTH_LONG).show();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            client_nurse_feedback.this.finish();
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
                                    Toast.makeText(client_nurse_feedback.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("cid", Integer.toString(cid));
                            params.put("nid",  Integer.toString(nid));
                            params.put("content", rmessage);

                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(client_nurse_feedback.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);

                }
            }


        });

    }

    public boolean checkif(EditText inputs) {

        String sUsername = inputs.getText().toString();
        if (sUsername.matches("")) {
            return true;
        }
        return false;
    }

}
