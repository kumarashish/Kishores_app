package ashish.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    @BindView(R.id.heading1)
    TextView entryForm;
    @BindView(R.id.heading11)
    TextView employee;
    @BindView(R.id.heading111)
    TextView addemployee;
    @BindView(R.id.heading12)
    TextView lorry;
    @BindView(R.id.heading121)
    TextView addPickupDetails;
    @BindView(R.id.heading122)
    TextView adddeliverydetails;
    @BindView(R.id.heading2)
    TextView reports;
    @BindView(R.id.heading21)
    TextView employeereports;
    @BindView(R.id.heading211)
    TextView employeeInformation;
    @BindView(R.id.entry_llout)
    LinearLayout entryLayout;
    @BindView(R.id.report_llout)
    LinearLayout reportLayout;
    boolean isentryFormClicked=false;
    boolean isemployeeClicked=false;
    boolean islorryClicked=false;
    boolean isreportsClicked=false;
    boolean isemployeereports=false;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        entryForm.setOnClickListener(this);
        employee.setOnClickListener(this);
        lorry.setOnClickListener(this);
        addemployee.setOnClickListener(this);
        addPickupDetails.setOnClickListener(this);
        adddeliverydetails.setOnClickListener(this);
        reports.setOnClickListener(this);
        employeereports.setOnClickListener(this);
        employeeInformation.setOnClickListener(this);




    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.heading1:
                if(isentryFormClicked)
                {
                    isentryFormClicked=false;
                    entryLayout.setVisibility(View.GONE);
                }else{
                    isentryFormClicked=true;
                   entryLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.heading11:
                if(isemployeeClicked)
                {
                    isemployeeClicked=false;
                    addemployee.setVisibility(View.GONE);
                }else{
                    isemployeeClicked=true;
                    addemployee.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.heading111:
                startActivity(new Intent(MainActivity.this,AddEmployee.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.heading12:
                if(islorryClicked)
                {
                    islorryClicked=false;
                    adddeliverydetails.setVisibility(View.GONE);
                    addPickupDetails.setVisibility(View.GONE);
                }else{
                    islorryClicked=true;
                    adddeliverydetails.setVisibility(View.VISIBLE);
                    addPickupDetails.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.heading121:
                Delivery_Details.headingValue="Add Pickup Details";
                startActivity(new Intent(MainActivity.this,Delivery_Details.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.heading122:
                Delivery_Details.headingValue="Add Delivery Details";
                startActivity(new Intent(MainActivity.this,Delivery_Details.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.heading2:
                if(isreportsClicked)
                {
                    isreportsClicked=false;
                    reportLayout.setVisibility(View.GONE);
                }else{
                    isreportsClicked=true;
                    reportLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.heading21:
                if(isemployeereports)
                {
                    isemployeereports=false;
                    employeeInformation.setVisibility(View.GONE);
                }else{
                    isemployeereports=true;
                    employeeInformation.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.heading211:
                startActivity(new Intent(MainActivity.this,EmployeeDetails.class));

                break;
        }

    }
}
