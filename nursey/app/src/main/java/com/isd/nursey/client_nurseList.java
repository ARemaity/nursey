package com.isd.nursey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import java.util.ArrayList;

public class client_nurseList extends AppCompatActivity {
    private String dayx,cityx,domainx,genderx,hourx;
    private String URLstring = "https://nursey.000webhostapp.com/api/searchnurse.php?";
    PreferenceUtils utils = new PreferenceUtils();
    private static ProgressDialog mProgressDialog;
    private ListView listView;
    ArrayList<nurseModel> dataModelArrayList;
    private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_nurse_list);
        Bundle bundle = getIntent().getExtras();
        cityx = bundle.getString("city");
        genderx = bundle.getString("gender");
        hourx = bundle.getString("hour");
        domainx = bundle.getString("domain");
        dayx = bundle.getString("day");
        listView=findViewById(R.id.listView4);
setURL(cityx,genderx,hourx,dayx,domainx);
retrieveJSON();
    }
public  void  setURL(String c,String g,String h,String d,String dmn){
        this.URLstring+="h="+h+"&d="+d+"&g="+g+"&c="+c+"&dmn="+dmn;
}
    private void retrieveJSON() {

        showSimpleProgressDialog(client_nurseList.this, "Loading...","Please wait",false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLstring,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d("strrrrr", ">>" + response);



                        try {


                            JSONObject ex =new JSONObject(response);
                            int theredata=Integer.parseInt(ex.getString("exsit"));
                            if(theredata==1) {
                                showSimpleProgressDialog(client_nurseList.this, "Loading...","Please wait",false);
                                JSONObject obj = new JSONObject(response);
                                dataModelArrayList = new ArrayList<>();
                                JSONArray dataArray = obj.getJSONArray("data");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    nurseModel x = new nurseModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);
                                    x.setNID(Integer.parseInt(dataobj.getString("NID")));
                                    x.setSID(Integer.parseInt(dataobj.getString("SID")));
                                    x.setName(dataobj.getString("fname"),dataobj.getString("lname"));
                                    x.setDob(dataobj.getString("dob"));
                                    x.setGender(dataobj.getString("gender"));
                                    x.setDay(dataobj.getString("day"));
                                    x.setNumberofHour(Integer.parseInt(dataobj.getString("available_time")));
                                    if (dataModelArrayList.isEmpty()) {
                                        Log.d("befoooooooooore", ">>>>>>>>>>>>>>>>>>0000000000000000");

                                    } else {


                                        Log.d("befoooooooooore", ">>>>>>>>>11111111111111111111111");
                                    }


                                    dataModelArrayList.add(x);
                                    if (dataModelArrayList.isEmpty()) {
                                        Log.d("Aftrrrrrrrrr", ">>>>>>>>>>>>>>>>>>0000000000000000");


                                    } else {


                                        Log.d("Aftrrrrrrrr", ">>>>>>>>>11111111111111111111111");
                                        setupListview();
                                    }

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
    private void setupListview(){

        removeSimpleProgressDialog();  //will remove progress dialog
        listAdapter =new nurseListAdapter(this,dataModelArrayList);
        listView.setAdapter(listAdapter);

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
