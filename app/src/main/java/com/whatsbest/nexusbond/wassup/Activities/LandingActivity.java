package com.whatsbest.nexusbond.wassup.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.whatsbest.nexusbond.wassup.Fragments.ActivitiesFragment;
import com.whatsbest.nexusbond.wassup.Fragments.GroupFragment;
import com.whatsbest.nexusbond.wassup.Fragments.PostFragment;
import com.whatsbest.nexusbond.wassup.Fragments.ProfileFragment;
import com.whatsbest.nexusbond.wassup.R;

/**
 * This java class is shown after successfully logging in
 */
public class LandingActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.nav_menu);
        theFragment(PostFragment.newInstance());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });
    }

    //selects the fragment to use when the different menus on the
    //bottom navigation view is chosen
    private void selectFragment(MenuItem item)
    {
        Fragment fragment = null;

        switch ((item.getItemId()))
        {
            case R.id.menu_home:
                fragment = PostFragment.newInstance();
                break;
            case R.id.menu_activity:
                fragment = ActivitiesFragment.newInstance();
                break;
            case R.id.menu_group:
                fragment = GroupFragment.newInstance();
                break;
            case R.id.menu_profile:
                fragment = ProfileFragment.newInstance();
                break;
        }
        theFragment(fragment);
    }

    //is reused whenever the bottom navigation changes view
    public void theFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
