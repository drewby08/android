package edu.osu;

// Graphic Marker Imports
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.content.Context;
import android.location.LocationManager;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.google.android.maps.*;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapActivity extends com.google.android.maps.MapActivity {
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

        // Add Foursquare Locations
        Drawable markerDefault = this.getResources().getDrawable(R.drawable.food_biergarten);
        VenueOverlay venueOverlay = new VenueOverlay(markerDefault);
        try{
            ArrayList<FourSquareVenue> venues = new LocationOverlay().getNearby(myLocationOverlay.getMyLocation().getLatitudeE6(), myLocationOverlay.getMyLocation().getLongitudeE6());
            Log.d(TAG, "There are " + venues.size() + " venues returned");
            FourSquareVenue place = new FourSquareVenue();
            //Collections.sort(venues, CheckedInComparator());
            while (venues.size() > 0) {
                place = venues.remove(0);
                venueOverlay.addOverlayItem((int) (place.location.getLatitude()*1E6),(int) (place.location.getLongitude() * 1E6),place.name, markerDefault);
            }
            mapView.getOverlays().add(venueOverlay);
        } catch (Exception e) {
            Toast.makeText(MapActivity.this, "Unable to load Bars", Toast.LENGTH_LONG).show();
        }

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

    private class VenueOverlay extends ItemizedOverlay<OverlayItem> {

        private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

        public VenueOverlay(Drawable defaultMarker) {
            super(boundCenterBottom(defaultMarker));
        }

        public void addOverlayItem(int lat, int lon, String title, Drawable altMarker) {
            GeoPoint point = new GeoPoint(lat, lon);
            OverlayItem overlayItem = new OverlayItem(point, title, null);
            addOverlayItem(overlayItem, altMarker);
        }

        public void addOverlayItem(OverlayItem overlayItem) {
            mOverlays.add(overlayItem);
            populate();
        }

        public void addOverlayItem(OverlayItem overlayItem, Drawable altMarker) {
            overlayItem.setMarker(boundCenterBottom(altMarker));
            addOverlayItem(overlayItem);
        }

        @Override
        protected OverlayItem createItem(int i) {
            return mOverlays.get(i);
        }

        @Override
        public int size() {
            return mOverlays.size();
        }

        @Override
        protected boolean onTap(int index) {
            Toast.makeText(MapActivity.this, getItem(index).getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

    }

}
