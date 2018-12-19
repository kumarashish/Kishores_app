package ashish.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    @BindView(R.id.heading1)
    TextView entryForm;
    @BindView(R.id.heading12)
    TextView lorry;
    @BindView(R.id.heading121)
    TextView booking_request;
    @BindView(R.id.heading122)
    TextView booking_delete;
    @BindView(R.id.heading123)
    TextView booking_approval;
    @BindView(R.id.heading124)
    TextView scan_document;
    @BindView(R.id.heading221)
    TextView booking_report;
    @BindView(R.id.heading222)
    TextView rate_pending_report;
    @BindView(R.id.heading2)
    TextView reports;
    @BindView(R.id.heading22)
    TextView reportsLorry;
    @BindView(R.id.heading3)
    TextView logout;
    @BindView(R.id.entry_llout)
    LinearLayout entryLayout;
    @BindView(R.id.report_llout)
    LinearLayout reportLayout;
    boolean isentryFormClicked=false;
    boolean islorryClicked=false;
    boolean isreportsClicked=false;
    boolean isemployeereports=false;
    boolean isLorryreports=false;
    DrawerLayout drawer;
    @BindView(R.id.contentdata)
      View content;
    View main;
View view1,view2,view3,view4,view5,view6;
AppController controller;
@BindView(R.id.userName)
TextView userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        controller=(AppController)getApplicationContext();
        main=(View) content.findViewById(R.id.contentmain);
        view1=(View)main.findViewById(R.id.view1);
        view2=(View)main.findViewById(R.id.view2);
        view3=(View)main.findViewById(R.id.view3);
        view4=(View)main.findViewById(R.id.view4);
        view5=(View)main.findViewById(R.id.view5);
        view6=(View)main.findViewById(R.id.view6);
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
        lorry.setOnClickListener(this);
        booking_request.setOnClickListener(this);
        booking_report.setOnClickListener(this);
        reports.setOnClickListener(this);
        booking_approval.setOnClickListener(this);
        reportsLorry.setOnClickListener(this);
        booking_approval.setOnClickListener(this);
        logout.setOnClickListener(this);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
        view3.setOnClickListener(this);
        view4.setOnClickListener(this);
        view5.setOnClickListener(this);
        view6.setOnClickListener(this);

        userName.setText(controller.getManager().getName()+"( "+controller.getManager().getUserId()+" )");


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

            case R.id.heading12:
                if(islorryClicked)
                {
                    islorryClicked=false;

                   booking_approval.setVisibility(View.GONE);
                    booking_delete.setVisibility(View.GONE);
                   booking_request.setVisibility(View.GONE);
                   scan_document.setVisibility(View.GONE);
                }else{
                    islorryClicked=true;
                    booking_approval.setVisibility(View.VISIBLE);
                    booking_delete.setVisibility(View.VISIBLE);
                    booking_request.setVisibility(View.VISIBLE);
                    scan_document.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.heading121:
            case R.id.view1:
                Lorry_Booking.headingValue="Booking Request";
                startActivity(new Intent(MainActivity.this,Lorry_Booking.class));
                drawer.closeDrawer(GravityCompat.START);
                break;

            case R.id.heading123:
            case R.id.view3:
                startActivity(new Intent(MainActivity.this,LorryPass.class));
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

            case R.id.heading22:
                if (isLorryreports) {
                    isLorryreports= false;
                    booking_report.setVisibility(View.GONE);
                    rate_pending_report.setVisibility(View.GONE);
                } else {
                    isLorryreports = true;
                    booking_report.setVisibility(View.VISIBLE);
                    rate_pending_report.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.heading221:
            case R.id.view5:
                LorryReport.headingValue="Booking Report";
                startActivity(new Intent(MainActivity.this,LorryReport.class));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.heading222:
            case R.id.heading3:
                controller.getManager().setUserLoggedIn(false);
                Toast.makeText(this,"Logged out sucessfully",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,Login.class));
                finish();
                break;




        }

    }
}
