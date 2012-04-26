package edu.osu;

import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.os.Bundle;

public class LocActivity extends MapActivity{
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.whereami);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
