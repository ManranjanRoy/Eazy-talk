package com.ventrux.eazetalk;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ventrux.eazetalk.Activity.DummyLoginpage;
import com.ventrux.eazetalk.Adaptor.ViewPagerAdapter;
import com.ventrux.eazetalk.Api.Configss;
import com.ventrux.eazetalk.Api.StaticData;
import com.ventrux.eazetalk.Fragment.ChatFragment;
import com.ventrux.eazetalk.Fragment.HomeFragment;
import com.ventrux.eazetalk.Fragment.LiveFragment;
import com.ventrux.eazetalk.Fragment.VideoCallFragment;
import com.ventrux.eazetalk.Fragment.VoiceCallFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView navigationView;
    TextView navname,navemail;
    BottomNavigationView bottomNavigationView;

    //Fragments
    HomeFragment homeFragment;
    LiveFragment liveFragment;
    ChatFragment chatFragment;
    VoiceCallFragment voiceCallFragment;
    VideoCallFragment videoCallFragment;
    MenuItem prevMenuItem;
    //This is our viewPager
    private ViewPager viewPager;
    Toolbar toolbar,toolbar1;
    boolean loggedIn;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
   @Override
   protected void onResume() {
       super.onResume();
       SharedPreferences sharedPreferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
       //Fetching the boolean value form sharedpreferences
       loggedIn = sharedPreferences.getBoolean(Configss.LOGGEDIN_SHARED_PREF, false);

       //If we will get true
       if(!loggedIn){

           //We will start the Profile Activity
           Intent intent = new Intent(this, DummyLoginpage.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(intent);
           finish();
       }else{
           //requestMultiplePermissions();
       }

   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        toolbar1=findViewById(R.id.toolbar1);
        //toolbar.setTitle("our24mart");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        /*SharedPreferences sharedPreferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //Fetching the boolean value form sharedpreferences
        tokencode = sharedPreferences.getString(Configss.tokencode, "default");*/

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_home);
        View headerView = navigationView.getHeaderView(0);
        navname = (TextView) headerView.findViewById(R.id.navname);
        navemail=(TextView)headerView.findViewById(R.id.navemail);

        navigationView.setNavigationItemSelectedListener(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewPager.setCurrentItem(0);
                                toolbar.setVisibility(View.VISIBLE);
                                toolbar1.setVisibility(View.GONE);
                                setSupportActionBar(toolbar);

                                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                                NavigationView navigationView = findViewById(R.id.nav_view);
                                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                                        MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                                drawer.addDrawerListener(toggle);
                                toggle.syncState();
                                navigationView.setNavigationItemSelectedListener(MainActivity.this);
                                break;
                            case R.id.action_search:
                                viewPager.setCurrentItem(1);
                                toolbar.setVisibility(View.GONE);
                                toolbar1.setVisibility(View.VISIBLE);
                                setSupportActionBar(toolbar);
                                break;
                            case R.id.action_wishlist:
                                viewPager.setCurrentItem(2);
                                toolbar.setVisibility(View.GONE);
                                toolbar1.setVisibility(View.GONE);
                                setSupportActionBar(toolbar);
                                break;
                            case R.id.action_cart:
                                viewPager.setCurrentItem(3);
                                toolbar.setVisibility(View.GONE);
                                toolbar1.setVisibility(View.GONE);
                                setSupportActionBar(toolbar);
                                break;
                            case R.id.action_profile:
                                viewPager.setCurrentItem(4);
                                toolbar.setVisibility(View.GONE);
                                toolbar1.setVisibility(View.GONE);
                                setSupportActionBar(toolbar);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position ==0) {
                    toolbar.setVisibility(View.VISIBLE);
                    toolbar1.setVisibility(View.GONE);
                    setSupportActionBar(toolbar);

                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    NavigationView navigationView = findViewById(R.id.nav_view);
                    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                            MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                    drawer.addDrawerListener(toggle);
                    toggle.syncState();
                    navigationView.setNavigationItemSelectedListener(MainActivity.this);
                }else{
                    toolbar1.setVisibility(View.GONE);
                    toolbar.setVisibility(View.GONE);
                    setSupportActionBar(toolbar1);
                }

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                Log.d("page", "onPageSelected: "+position);
                StaticData.postion=position;
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //Disable ViewPager Swipe

       /* viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });*/



        setupViewPager(viewPager);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.setCheckedItem(R.id.nav_home);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        homeFragment =new HomeFragment();
        liveFragment =new LiveFragment();
        chatFragment =new ChatFragment();
        voiceCallFragment=new VoiceCallFragment();
        videoCallFragment=new VideoCallFragment();
        adapter.addFragment(homeFragment);
        adapter.addFragment(liveFragment);
        adapter.addFragment(chatFragment);
        adapter.addFragment(voiceCallFragment);
        adapter.addFragment(videoCallFragment);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(StaticData.postion);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (StaticData.postion !=0){
                viewPager.setCurrentItem(0);
            }else {
                super.onBackPressed();
            }
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();


        if (id == R.id.nav_home) {
            viewPager.setCurrentItem(0);
            toolbar.setVisibility(View.VISIBLE);
            toolbar1.setVisibility(View.GONE);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(MainActivity.this);
            // Handle the camera action
        }
        else if (id == R.id.nav_profile) {
            viewPager.setCurrentItem(2);
            toolbar.setVisibility(View.GONE);
            toolbar1.setVisibility(View.GONE);
            setSupportActionBar(toolbar);
        }


        else  if (id==R.id.nav_share){
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Our24 Mart");
                String shareMessage= "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + "com.ventrux.eazetalk" +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch(Exception e) {
                //e.toString();
            }
        }
        else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

  //logout ffunction
    private void logout(){

        SharedPreferences sharedPreferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String roleid = sharedPreferences.getString(Configss.login_role,"Not Available");

        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton(Html.fromHtml("<font color='#FF7F27'>Yes</font>"),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        if(roleid.equals("0")) {
                            SharedPreferences preferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Getting editor
                            SharedPreferences.Editor editor = preferences.edit();

                            //Puting the value false for loggedin
                            editor.putBoolean(Configss.LOGGEDIN_SHARED_PREF, false);

                            //Putting blank value to email
                            editor.putString(Configss.EMAIL_SHARED_PREF, "");
                            editor.putString(Configss.tokencode, "");

                            //Saving the sharedpreferences
                            editor.commit();
                            Intent intent = new Intent(MainActivity.this, DummyLoginpage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        /*  else if(roleid.equals("1")) {
                            SharedPreferences preferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Getting editor
                            SharedPreferences.Editor editor = preferences.edit();

                            //Puting the value false for loggedin
                            editor.putBoolean(Configss.LOGGEDIN_SHARED_PREF, false);

                            //Putting blank value to email
                            editor.putString(Configss.EMAIL_SHARED_PREF, "");
                            editor.putString(Configss.tokencode, "");

                            //Saving the sharedpreferences
                            editor.commit();
                            LoginManager.getInstance().logOut();
                            Intent intent = new Intent(MainActivity.this, DummyLoginpage.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                      else if(roleid.equals("2")) {
                            SharedPreferences preferences = getSharedPreferences(Configss.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            //Getting editor
                            SharedPreferences.Editor editor = preferences.edit();
                            //Puting the value false for loggedin
                            editor.putBoolean(Configss.LOGGEDIN_SHARED_PREF, false);
                            //Putting blank value to email
                            editor.putString(Configss.EMAIL_SHARED_PREF, "");
                            editor.putString(Configss.tokencode, "");

                            //Saving the sharedpreferences
                            editor.commit();
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            if (status.isSuccess()){
                                                Intent intent = new Intent(MainActivity.this, Login.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                                return;
                                                //gotoMainActivity();
                                            }else{
                                                Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        }*/
                        //Starting login activity

                    }
                });

        alertDialogBuilder.setNegativeButton(Html.fromHtml("<font color='#FF7F27'>No</font>"),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),"For Logout Click on Yes",Toast.LENGTH_SHORT).show();

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
