package com.isd.nursey;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import com.google.android.material.navigation.NavigationView;
import com.isd.nursey.utils.PreferenceUtils;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class client_main extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,TimePickerDialog.OnTimeSetListener  {

Spinner days,citys,domains,genders;
String dayx="all",cityx="all",domainx="all",genderx="all",hourx="all";
Button searchbtn,hourbtn;
    String[] dayArray = new String[]{

            "All",
            "Sunday",
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"
    };
    String[] cityArray = new String[]{
"All",
            "Beirut",
            "Tripoli",
            "Sidon",
            "Nabatiye",
            "Habbouch",
            "Jounieh",
            "Zahle",
            "Ghazieh"
    };
    String[] domainArray = new String[]{
            "All",
            "Elderly Care",
            "Child Care",
            "Post Labor Care",
            "New Born Care",
            "Post Surgery Care",
            "Post Surgery Care"
    };
    String[] genderArray = new String[]{
            "All",
            "Male",
            "Female"
    };
    PreferenceUtils utils = new PreferenceUtils();
    boolean doubleBackToExitPressedOnce = false;
    Calendar calendar ;
    TimePickerDialog timePickerDialog ;
    int CalendarHour, CalendarMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);
        days = findViewById(R.id.dayspinner);
        citys = findViewById(R.id.cityspinner);
        domains = findViewById(R.id.domainspinner);
        genders = findViewById(R.id.genderspinner);
        searchbtn = findViewById(R.id.searchbtn);
        hourbtn = findViewById(R.id.searchhourpicker);
//        adapter Setting
        ArrayAdapter<String> domainAdapter;
        ArrayAdapter<String> cityAdapter;
        ArrayAdapter<String> dayAdapter;
        ArrayAdapter<String> genderAdapter;
        domainAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,domainArray);
        cityAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,cityArray);
        dayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dayArray);
        genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,genderArray);
        days.setAdapter(dayAdapter);
        domains.setAdapter(domainAdapter);
        citys.setAdapter(cityAdapter);
        genders.setAdapter(genderAdapter);


        days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



                switch (position)

                {

                    case 0:

                        dayx = "all";

                        break;

                    case 1:

                        dayx = "sun";

                        break;

                    case 2:

                        dayx = "mon";

                        break;
                    case 3:

                        dayx = "tue";

                        break;

                    case 4:

                        dayx = "wed";

                        break;

                    case 5:

                        dayx = "thu";

                        break;
                    case 6:

                        dayx = "fri";

                        break;
                    case 7:

                        dayx = "sat";

                        break;
                    default :

                        dayx = "all";
                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        genders.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position)

                {

                    case 0:

                        genderx = "all";

                        break;

                    case 1:

                        genderx = "male";

                        break;

                    case 2:

                        genderx = "female";

                        break;
                    case 3:


                    default :

                        genderx = "all";
                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        domains.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (position)

                {

                    case 0:

                        domainx = "all";

                        break;

                    case 1:

                        domainx = "Elderly Care";

                        break;

                    case 2:

                        domainx = "Child Care";

                        break;
                    case 3:

                        domainx = "Post Labor Care";

                        break;

                    case 4:

                        domainx = "New Born Care";

                        break;

                    case 5:

                        domainx = "Post Surgery Care";

                        break;

                    default :

                        domainx = "all";
                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        citys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //use postion value

                switch (position)

                {



                    case 0:

                        cityx = "all";

                        break;

                    case 1:

                        cityx = "Beirut";

                        break;

                    case 2:

                        cityx = "Sidon";

                        break;
                    case 3:

                        cityx = "Nabatiye";

                        break;

                    case 4:

                        cityx = "Jounieh";

                        break;

                    case 5:

                        cityx = "Zahle";

                        break;
                    case 6:

                        cityx = "Ghazieh";

                        break;

                    default :

                        cityx = "all";
                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });




//  drawer Setting
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final int ids = utils.getID(client_main.this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        calendar = Calendar.getInstance();
        hourbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);

                CalendarMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = TimePickerDialog.newInstance(client_main.this, CalendarHour, CalendarMinute, true);

                timePickerDialog.setThemeDark(false);

                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        Toast.makeText(client_main.this, "Time Not Selected", Toast.LENGTH_SHORT).show();
                    }
                });

                timePickerDialog.show(getFragmentManager(), "select Time");
            }

        });

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), client_nurseList.class);
                intent.putExtra("city",cityx);
                intent.putExtra("gender", genderx);
                intent.putExtra("hour",hourx);
                intent.putExtra("domain", domainx);
                intent.putExtra("day", dayx);

                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.nurse_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            finishAffinity();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.client_profile) {
            Intent my_intent = new Intent(client_main.this, client_own_profile.class);
            startActivity(my_intent);

        } else if (id == R.id.client_nurses) {
        Intent my_intent= new Intent(client_main.this,nurse_view_requestList.class);
        startActivity(my_intent);

        } else if (id == R.id.client_logout) {
            Toast.makeText(client_main.this, "logging Out", Toast.LENGTH_LONG).show();
            PreferenceUtils.saveEmail("", client_main.this);
            PreferenceUtils.saveType("", client_main.this);
            PreferenceUtils.saveID(0, client_main.this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    client_main.this.finish();
                }
            }, 1000);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {


        hourx=Integer.toString(hourOfDay);
                hourbtn.setText(hourOfDay+":00");




    }

}
