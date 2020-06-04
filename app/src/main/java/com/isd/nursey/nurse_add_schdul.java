package com.isd.nursey;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.isd.nursey.utils.PreferenceUtils;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class nurse_add_schdul extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    String HttpUrlpost = "https://nursey.000webhostapp.com/api/addsch.php";
    private static ProgressDialog mProgressDialog;
    ProgressDialog progress;
    RequestQueue requestQueue;
    PreferenceUtils utils = new PreferenceUtils();
    EditText suned,sated,monde,weded,tuesde,thured,fried;
    Button sunbt,satbt,monbt,wedbtn,tuesbt,thubt,fribt;
    String sunst="1",satst="1",monst="1",wedst="1",tuesst="1",thust="1",frist="1";
    String sunx,satx,monx,wedx,tuesx,thux,frix;
    int i;
    Button submitsc;

    Calendar calendar ;
    TimePickerDialog timePickerDialog ;
    int CalendarHour, CalendarMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_add_schdul);
        submitsc=findViewById(R.id.submitsch);

        final int ids=utils.getID(nurse_add_schdul.this);
        sunbt=findViewById(R.id.sunpick);
        monbt=findViewById(R.id.monpick);
        wedbtn=findViewById(R.id.wedpick);
        tuesbt=findViewById(R.id.tuepick);
        thubt=findViewById(R.id.thupick);
        fribt=findViewById(R.id.fripick);
        satbt=findViewById(R.id.satpick);
        suned=findViewById(R.id.sunedit);
        monde=findViewById(R.id.monedit);
        sated=findViewById(R.id.satedit);
        weded=findViewById(R.id.wededit);
        tuesde=findViewById(R.id.tueedit);
        fried=findViewById(R.id.friedit);
        thured=findViewById(R.id.thuedit);



        calendar = Calendar.getInstance();
        progress = new ProgressDialog(nurse_add_schdul.this);
        submitsc.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            sunx = suned.getText().toString();
                                            monx = monde.getText().toString();
                                            tuesx = tuesde.getText().toString();
                                            wedx = weded.getText().toString();
                                            thux = thured.getText().toString();
                                            frix = fried.getText().toString();
                                            satx = sated.getText().toString();

                                            progress.setMessage("Please Wait, Server is Working :)");
                                            progress.show();


                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrlpost,
                                                    new Response.Listener<String>() {
                                                        @Override
                                                        public void onResponse(String ServerResponse) {

                                                            // Hiding the progress dialog after all task complete.
                                                            progress.dismiss();

                                                            // Showing response message coming from server.
                                                            Toast.makeText(nurse_add_schdul.this, ServerResponse, Toast.LENGTH_LONG).show();
                                                            new Handler().postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    nurse_add_schdul.this.finish();
                                                                }
                                                            }, 3000);
                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError volleyError) {

                                                            // Hiding the progress dialog after all task complete.
                                                            progress.dismiss();

                                                            // Showing error message if something goes wrong.
                                                            Toast.makeText(nurse_add_schdul.this, volleyError.toString(), Toast.LENGTH_LONG).show();

                                                        }
                                                    }) {
                                                @Override
                                                protected Map<String, String> getParams() {

                                                    // Creating Map String Params.
                                                    Map<String, String> params = new HashMap<String, String>();

                                                    // Adding All values to Params.
                                                    params.put("id", Integer.toString(ids));
                                                    params.put("sun", sunst);
                                                    params.put("mon", monst);
                                                    params.put("tue",tuesst);
                                                    params.put("wed", wedst);
                                                    params.put("thur", thust);
                                                    params.put("fri", frist);
                                                    params.put("sat", satst);
                                                    params.put("sunz", sunx);
                                                    params.put("monz", monx);
                                                    params.put("tuez",tuesx);
                                                    params.put("wedz", wedx);
                                                    params.put("thurz", thux);
                                                    params.put("friz", frix);
                                                    params.put("satz", satx);
                                                    params.put("submit", "submit");
                                                    return params;
                                                }

                                            };

                                            // Creating RequestQueue.
                                            RequestQueue requestQueue = Volley.newRequestQueue(nurse_add_schdul.this);

                                            // Adding the StringRequest object into requestQueue.
                                            requestQueue.add(stringRequest);

                                        }

                                    });
        sunbt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 0;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        monbt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 1;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        tuesbt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 2;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        wedbtn.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 3;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        thubt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 4;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        fribt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i =5;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });


        satbt.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){

                i = 6;
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(nurse_add_schdul.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(nurse_add_schdul.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

    });




//
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String SelectedTime;
        switch(i)
        {

            case 0 :
                sunst=Integer.toString(hourOfDay);
                sunbt.setText(hourOfDay+":00");
                suned.setEnabled(true);


                break; // break is optional

            case 1 :
                monst=Integer.toString(hourOfDay);
                monbt.setText(monst+":00");
                monde.setEnabled(true);
                break; // break is optional

            // values must be of same type of expression
            case 2 :
                tuesst=Integer.toString(hourOfDay);
                tuesbt.setText(tuesst+":00");
                tuesde.setEnabled(true);

                break; // break is optional

            case 3:
                wedst=Integer.toString(hourOfDay);
                wedbtn.setText(wedst+":00");
                weded.setEnabled(true);
                break; // break is optional

            case 4 :
                thust=Integer.toString(hourOfDay);
                thubt.setText(thust+":00");
                thured.setEnabled(true);
                break; // break is optional

            case 5 :
                frist=Integer.toString(hourOfDay);
                fribt.setText(frist);
                fried.setEnabled(true);

                break;
            case 6 :
                satst=Integer.toString(hourOfDay);
                satbt.setText(satst+":00");
                sated.setEnabled(true);

                break;
            default :
                monst=Integer.toString(hourOfDay);

        }


    }





}