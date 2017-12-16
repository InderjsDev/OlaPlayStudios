package com.inderjs.olaplaystudios;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {



    private BottomNavigationView mBottomNav;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           // case  R.id.menu_setting :
               // startActivity(new Intent(this,SettingActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_search:
                        selectedFragment = new SearchFragment();

                        break;
                    case R.id.menu_song:
                        selectedFragment = new SongFragment();
                        break;
                    case R.id.menu_fav:
                        selectedFragment = new FavFragment();
                        break;

                    case R.id.menu_history:
                        selectedFragment = new HistoryFragment();
                        break;

                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, new SongFragment());
        transaction.commit();

        mBottomNav.callOnClick();




    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }




}
