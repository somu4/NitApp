package com.example.nitapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    public String myYear, myBranch;

    ViewPager viewPager = null;
    MenuItem prevMenuItem = null;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating();




        myYear = getIntent().getStringExtra("roll").substring(0, 4);

        if(getIntent().getStringExtra("roll").length()<16)
            myYear="2019";

        myBranch = getIntent().getStringExtra("roll").substring(6, 8);
        if(getIntent().getStringExtra("roll").length()<16)
            myBranch="CS";


        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
        prevMenuItem = bottomNavigationView.getMenu().getItem(2).setChecked(false);
        viewPager.setCurrentItem(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                bottomNavigationView.getMenu().getItem(0).setChecked(false);
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void creating() {


        DatabaseReference scheduleRef = FirebaseDatabase.getInstance().getReference("schedule");

        String[] branches={"CS","MM","ME","CE","EE","EC","PI"};




        for(int p=0;p<7;p++) {

            long temp = 2016;

            DatabaseReference branchRef=scheduleRef.child(branches[p]);

            DatabaseReference hodRef=branchRef.child("hod");

            hodRef.setValue("*");

            for (long j = 0; j < 4; j++, temp++) {

                DatabaseReference branchyearRef = branchRef.child(temp + "");

                branchyearRef.child("setter").setValue("BK SINGH");


                char x = 'b', y = '1';

                for (int i = 1; i <= 40; i++) {
                    String sId = Character.toString(x) + "" + Character.toString(y);

                    DatabaseReference branchyeartableBRef =branchyearRef.child("table").child(sId);

                    DatabaseReference myref3 = branchyeartableBRef.child("subcode");
                    myref3.setValue("1");
                    myref3 = branchyeartableBRef.child("lecture");
                    myref3.setValue(true);
                    y++;
                    if (y > '5') {
                        y = '1';
                        x++;
                    }
                }


                for (long k = 1; k <= 10; k++) {
                    DatabaseReference branchyeartablesubjectRef  = branchyearRef.child("table").child("subjects").child("sub" + k + "");
                    DatabaseReference myRef4 = branchyeartablesubjectRef.child("subcode");
                    myRef4.setValue(branches[p]+"501");
                    myRef4 = branchyeartablesubjectRef.child("teachername");
                    myRef4.setValue("Sanjay Kumar");
                    myRef4 = branchyeartablesubjectRef.child("subjectname");
                    myRef4.setValue("Optimisation");
                    myRef4=branchyeartablesubjectRef.child("teacherid");
                    myRef4.setValue("2016027@p.com");
                }

            }
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            Toast.makeText(this, "clicked on logout", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.nav_schedule:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav_hostel:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_home:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.nav_mess:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.nav_medical:
                    viewPager.setCurrentItem(4);
                    break;
            }
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
            switch (i) {
                case 0:
                    fragment = new ScheduleFragment();
                    break;
                case 1:
                    fragment = new HostelFragment();
                    break;
                case 2:
                    fragment = new HomeFragment();
                    break;
                case 3:
                    fragment = new MessFragment();
                    break;
                case 4:
                    fragment = new MedicalFragment();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

}
