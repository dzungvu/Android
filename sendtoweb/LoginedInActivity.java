package vn.edu.topica.sendtoweb;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

public class LoginedInActivity extends AppCompatActivity {

    private DrawerLayout mDrawwer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    public String s;

    private ActionBarDrawerToggle drawerToggle;

    FragmentManager fragmentManager= getSupportFragmentManager();
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logined_in);
        addControls();
        addEvents();

    }

    private void addControls() {
        Intent intent = getIntent();
        s = intent.getStringExtra("SOURCE");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawwer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setDrawerToggle();
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(nvDrawer);
        drawerToggle.syncState();

        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();


//        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.aboutFragment);
//        aboutFragment.setMemeText(s);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawwer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawer(item);
                return true;
            }
        });
    }

    private void selectDrawer(MenuItem item) {
        Fragment fragment = null;
        switch(item.getItemId()) {
            case R.id.nav_first_fragment:
                displayAboutFragment();
                mDrawwer.closeDrawer(Gravity.LEFT);
                break;
            case R.id.nav_second_fragment:
                displayCoursesFragment();
                mDrawwer.closeDrawer(Gravity.LEFT);
                break;

            case R.id.nav_third_fragment:
                displayCalendarFragment();
                mDrawwer.closeDrawer(Gravity.LEFT);
                break;

            case R.id.mnuOnline:
                displayOnlineFragment();
                mDrawwer.closeDrawer(Gravity.LEFT);
                break;

        }
//
//
//        // Insert the fragment by replacing any existing fragment
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
//
//        // Highlight the selected item has been done by NavigationView
//        item.setChecked(true);
//        // Set action bar title
//        setTitle(item.getTitle());
//        // Close the navigation drawer
//        mDrawwer.closeDrawers();
    }

    private void displayAboutFragment() {


        Bundle bundle = new Bundle();
        bundle.putString("DATA", s);
        fragmentTransaction=fragmentManager.beginTransaction();
        AboutFragment aboutFragment=new AboutFragment();
        aboutFragment.setArguments(bundle);
        if (getSupportFragmentManager().findFragmentById(R.id.areaFragment)==null){
            fragmentTransaction.add(R.id.areaFragment, aboutFragment).commit();
        }
        else fragmentTransaction.replace(R.id.areaFragment, aboutFragment).commit();

    }

    private void displayCoursesFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("DATA", s);
        fragmentTransaction=fragmentManager.beginTransaction();
        CoursesFragment coursesFragment=new CoursesFragment();
        coursesFragment.setArguments(bundle);
        if (getSupportFragmentManager().findFragmentById(R.id.areaFragment)==null){
            fragmentTransaction.add(R.id.areaFragment, coursesFragment).commit();
        }
        else fragmentTransaction.replace(R.id.areaFragment, coursesFragment).commit();
    }

    private void displayCalendarFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("DATA", s);
        fragmentTransaction=fragmentManager.beginTransaction();
        CalendarFragment calendarFragment=new CalendarFragment();
        calendarFragment.setArguments(bundle);
        if (getSupportFragmentManager().findFragmentById(R.id.areaFragment)==null){
            fragmentTransaction.add(R.id.areaFragment, calendarFragment).commit();
        }
        else fragmentTransaction.replace(R.id.areaFragment, calendarFragment).commit();
    }

    private void displayOnlineFragment(){
        Bundle bundle = new Bundle();
        bundle.putString("DATA", s);
        fragmentTransaction=fragmentManager.beginTransaction();
        OnlineFragment onlineFragment=new OnlineFragment();
        onlineFragment.setArguments(bundle);
        if (getSupportFragmentManager().findFragmentById(R.id.areaFragment)==null){
            fragmentTransaction.add(R.id.areaFragment, onlineFragment).commit();
        }
        else fragmentTransaction.replace(R.id.areaFragment, onlineFragment).commit();
    }



    private void addEvents() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getDataString(){
     return s;
    }

}
