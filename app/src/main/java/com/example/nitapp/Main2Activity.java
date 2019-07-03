package com.example.nitapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Main2Activity extends AppCompatActivity {
    private ViewPager viewPager = null;
    private MenuItem prevMenuItem = null;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolBar;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        toolBar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolBar);

        drawerLayout = findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolBar,R.string.open_navigation_drawer,R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        viewPager = findViewById(R.id.viewPager2);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        bottomNavigationView = findViewById(R.id.bottom_navigation2);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HostelFragment()).commit();
        prevMenuItem = bottomNavigationView.getMenu().getItem(0).setChecked(false);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                //Log.d("page", "onPageSelected: "+position);
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });



    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //Fragment fragment = null;
            switch(menuItem.getItemId()){
                case R.id.nav_schedule2: viewPager.setCurrentItem(0);
                    break;

                case R.id.nav_mess2: viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_medical2: viewPager.setCurrentItem(2);
                    break;
            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
    };
    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch(i){

                case 0: fragment = new Schedule2Fragment();
                    break;
                case 1: fragment = new Mess2Fragment();
                    break;
                case 2: fragment = new Medical2Fragment();
                    break;

            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
