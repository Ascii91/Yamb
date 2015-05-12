package com.etf.yamb;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.etf.controller.Controler;
import com.etf.fragments.GameFragment;
import com.etf.fragments.SplashFragment;

public class MainActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new SplashFragment());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {

        FragmentManager fm = getFragmentManager();

        Fragment fragment = fm.findFragmentByTag("game");

        if (fragment != null && fragment.isResumed())
        {
            ((GameFragment) fragment).onBackPressed();
        }
        else
        {

            if (fm.getBackStackEntryCount() == 0)
            {
                super.onBackPressed();
            }
            fm.popBackStack();

        }
    }

    private void replaceFragment(Fragment fragment)
    {

        FragmentManager fm = this.getFragmentManager();
        fm.beginTransaction().replace(R.id.container, fragment, "splash").commit();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        try
        {

            Controler.getControler().getBoard().disableShaking();
        }
        catch (Exception e)
        {}

    }

    @Override
    protected void onResume()
    {
        try
        {
            if (Controler.getControler().getBrojBacanja() != 3)
            {
                Controler.getControler().getBoard().enableShaking();
            }
        }
        catch (Exception e)
        {

        }

        super.onResume();
    }

}
