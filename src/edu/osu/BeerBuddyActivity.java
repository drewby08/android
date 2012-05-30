package edu.osu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class BeerBuddyActivity extends TabActivity {
	protected Dialog mSplashDialog;
	
    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        showSplashScreen();
               
        setContentView(R.layout.main);
        
        //hide the action bar
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabHost = getTabHost();  // The activity TabHost
        TabHost.TabSpec spec;  // Resusable TabSpec for each tab
        Intent intent;  // Reusable Intent for each tab

        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, TabTrackerActivity.class);

        // Initialize a TabSpec for each tab and add it to the TabHost
        spec = tabHost.newTabSpec("bar tab").setIndicator("bar tab",
                res.getDrawable(R.drawable.ic_launcher))
                .setContent(intent);
        tabHost.addTab(spec);

        // Do the same for the other tabs
        intent = new Intent().setClass(this, MapActivity.class);
        spec = tabHost.newTabSpec("where am i").setIndicator("where am i",
                res.getDrawable(R.drawable.ic_launcher))
                .setContent(intent);
        tabHost.addTab(spec);
        
     // Do the same for the other tabs
        intent = new Intent().setClass(this, BacActivity.class);
        spec = tabHost.newTabSpec("BAC").setIndicator("BAC",
                res.getDrawable(R.drawable.ic_launcher))
                .setContent(intent);
        tabHost.addTab(spec);


        tabHost.setCurrentTab(3);
    }

    /* currently does nothing as action bar is hidden
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0,Menu.FIRST,0,"Quit");
        return true;
    }
    
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case Menu.FIRST:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    */
    
    /**
     * Removes the Dialog that displays the splash screen
     */
    protected void removeSplashScreen() {
        if (mSplashDialog != null) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
        }
    }
     
    /**
     * Shows the splash screen over the full Activity
     */
    protected void showSplashScreen() {
        mSplashDialog = new Dialog(this, R.layout.splash);
        mSplashDialog.setContentView(R.layout.splash);
        mSplashDialog.setCancelable(false);
        mSplashDialog.show();
     
        // Set Runnable to remove splash screen just in case
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            removeSplashScreen();
          }
        }, 3000);
    }

}