package edu.osu;

import android.content.Context;
import android.location.*;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

public class LocActivity extends MapActivity{
    private static final String TAG = "LocationActivity";
	LinearLayout linearLayout;
    private MapController mapViewController;
    MapView mapView;
    LocationManager locationManager;
    Geocoder myGeoLocator;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereami);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setSatellite(true);
        mapView.setBuiltInZoomControls(true);
        mapViewController = mapView.getController();
        mapViewController.setZoom(16);
        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        myGeoLocator = new Geocoder(this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            Log.d(TAG, location.toString());
            //this.onLocationChanged(location); //<6>
        }

    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    protected void onResume() {
        super.onResume();
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //locationManager.removeUpdates(this);
    }

//    @Override
//    public void onLocationChanged(Location location) { //<9>
//        Log.d(TAG, "onLocationChanged with location " + location.toString());
//
//        try {
//            //List<Address> addresses = myGeoLocator.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
//
//            int latitude = (int)(location.getLatitude() * 1000000);
//            int longitude = (int)(location.getLongitude() * 1000000);
//
//            GeoPoint point = new GeoPoint(latitude,longitude);
//            mapViewController.animateTo(point); //<11>
//
//        } catch (IOException e) {
//            Log.e("LocateMe", "Could not get Geocoder data", e);
//        }
//    }

}
