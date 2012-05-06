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
    private MyLocationOverlay myLocationOverlay;
    private static final String TAG = "LocationActivity";
	LinearLayout linearLayout;
    private MapController mapViewController;
    private MapView mapView;
    LocationManager locationManager;
    Geocoder myGeoLocator;

	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whereami);
        checkIfGPSIsEnabled();

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapView.setSatellite(true);

        mapViewController = mapView.getController();
        mapViewController.setZoom(18);
        mapViewController.animateTo(geoPoint);

        List<Overlay> overlays = mapView.getOverlays();
        overlays.clear();
        overlays.add(new MyOverlay());

        mapView.invalidate();

        // Acquire a reference to the system Location Manager
        //locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //myGeoLocator = new Geocoder(this);
        //Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //if (location != null) {
        //    Log.d(TAG, location.toString());
        //    //this.onLocationChanged(location); //<6>
        //}
        //myLocationOverlay = new MyLocationOverlay(this, mapView);
        //myLocationOverlay.enableMyLocation();
        //mapView.getOverlays().add(myLocationOverlay);
        //mapView.invalidate();

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

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

    @Override
    protected void onResume() {
        super.onResume();
        //myLocationOverlay.enableMyLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //myLocationOverlay.disableMyLocation();
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

    GeoPoint geoPoint = new GeoPoint((int) (52.334822 * 1E6), (int) (4.668907 * 1E6));
    private class MyOverlay extends com.google.android.maps.Overlay {

        @Override
        public void draw(Canvas canvas, MapView mapView, boolean shadow) {
            super.draw(canvas, mapView, shadow);

            if (!shadow) {

                Point point = new Point();
                mapView.getProjection().toPixels(geoPoint, point);

                Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.marker_default);

                /**
                 * Shift it left so the center of the image is aligned with the x-coordinate of the geo point
                 */
                int x = point.x - bmp.getWidth() / 2;

                /**
                 * Shift it upward so the bottom of the image is aligned with the y-coordinate of the geo point
                 */
                int y = point.y - bmp.getHeight();

                canvas.drawBitmap(bmp, x, y, null);

            }
        }

    }

}
