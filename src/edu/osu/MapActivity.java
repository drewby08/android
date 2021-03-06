package edu.osu;

// Graphic Marker Imports

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.os.AsyncTask;
import android.os.Bundle;

import android.content.Context;
import android.location.LocationManager;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.google.android.maps.*;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.entities.CompactVenue;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends com.google.android.maps.MapActivity {
    private MyLocationOverlayExtension myLocationOverlay;
    private static final String TAG = "LocationActivity";
    //LinearLayout linearLayout;
    private MapController mapViewController;
    private MapView mapView;
    LocationManager locationManager;
    Geocoder myGeoLocator;
    private static FoursquareSearch search = new FoursquareSearch();
    //private Drawable markerBeer = getResources().getDrawable(R.drawable.food_biergarten);
    private Drawable markerBeer;
    private VenueOverlay venueOverlay;


    public void onCreate(Bundle savedInstanceState) {
        markerBeer = getResources().getDrawable(R.drawable.food_biergarten);
        venueOverlay = new VenueOverlay(markerBeer);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereami);
        checkIfGPSIsEnabled();

        mapView = (MapView) findViewById(R.id.mapview);
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

        FetchFourSquare();
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
            //FetchFourSquare();
            super.onLocationChanged(location);
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

    private VenueOverlay AddFoursquareLocations() {
        // Add Foursquare Locations
        Drawable markerDefault = this.getResources().getDrawable(R.drawable.food_biergarten);
        VenueOverlay venueOverlay = new VenueOverlay(markerDefault);
        Location location = getLocation();
        try {
            FoursquareSearch search = new FoursquareSearch();
            String ll = location.getLatitude() + "," + location.getLongitude();
            ArrayList<CompactVenue> results = search.searchVenues(ll);
            while (results.size() > 0) {
                CompactVenue place = results.remove(0);
                venueOverlay.addOverlayItem((int) (place.getLocation().getLat() * 1E6), (int) (place.getLocation().getLng() * 1E6), place.getName(), markerDefault);
            }
            //mapView.getOverlays().add(venueOverlay);
            return venueOverlay;
        } catch (FoursquareApiException e) {
            Toast.makeText(MapActivity.this, "Unable to load Bars", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    private Location getLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, false);
        return locationManager.getLastKnownLocation(bestProvider);

    }

    protected class FetchFourSquareTask extends AsyncTask<String, Void, ArrayList<CompactVenue>> {
        @Override
        protected void onPreExecute() {
            Log.d(TAG, "Entering Foursquare ASync Request");
        }

        @Override
        protected void onPostExecute(ArrayList<CompactVenue> taskResult) {
            if (taskResult == null) {
                Toast.makeText(MapActivity.this, "Unable to load Bars", Toast.LENGTH_LONG).show();
            } else {
                while (taskResult.size() > 0) {
                    CompactVenue place = taskResult.remove(0);
                    venueOverlay.addOverlayItem((int) (place.getLocation().getLat() * 1E6), (int) (place.getLocation().getLng() * 1E6), place.getName(), markerBeer);
                }
                mapView.getOverlays().add(venueOverlay);
                mapView.invalidate();
            }
        }

        @Override
        protected ArrayList<CompactVenue> doInBackground(String... ll) {
            try {
                return search.searchVenues(ll[0]);
            } catch (FoursquareApiException e) {
                Log.e(TAG, "Shit went crazy with the API call");
                return null;
            }
        }

    }

    private void FetchFourSquare() {
        AsyncTask<String, Void, ArrayList<CompactVenue>> async = new FetchFourSquareTask();
        Location location = getLocation();
        String ll = location.getLatitude() + "," + location.getLongitude();
        async.execute(ll);
    }
}