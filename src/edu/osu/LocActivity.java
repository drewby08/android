package edu.osu;

// Graphic Marker Imports
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;

import android.content.Context;
import android.location.LocationManager;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import com.google.android.maps.*;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

public class LocActivity extends MapActivity{
    private MyLocationOverlayExtension myLocationOverlay;
    private static final String TAG = "LocationActivity";
	//LinearLayout linearLayout;
    private MapController mapViewController;
    //private MapView mapView;
    LocationManager locationManager;
    Geocoder myGeoLocator;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereami);
        checkIfGPSIsEnabled();

        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);

        myLocationOverlay = new MyLocationOverlayExtension(this, mapView);
        //Quickly attempt to get the current location using the fastest means possible.
        myLocationOverlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mapViewController.animateTo(myLocationOverlay.getMyLocation());
            }
        });
        myLocationOverlay.enableMyLocation();
        mapView.getOverlays().add(myLocationOverlay);
        mapView.postInvalidate();

        mapViewController = mapView.getController();
        mapViewController.setZoom(18);

        //mapViewController.animateTo(myLocationOverlay.getMyLocation());
        mapView.invalidate();
    }

    private void checkIfGPSIsEnabled() {
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

    // Check if enabled and if not send user to the GSP settings
    // Better solution would be to display a dialog and suggesting to
    // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    private class MyLocationOverlayExtension extends MyLocationOverlay {

        public MyLocationOverlayExtension(Context context, MapView mapView) {
            super(context, mapView);
        }

        @Override
        public synchronized void onLocationChanged(Location location) {
            super.onLocationChanged(location);
            GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
            mapViewController.animateTo(point);
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
        myLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myLocationOverlay.disableMyLocation();
    }
}
