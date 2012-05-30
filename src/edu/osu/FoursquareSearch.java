package edu.osu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

import java.util.ArrayList;

public class FoursquareSearch {
    private static final String clientId = "PMA1HWVIBC4CGY2YYSFJW3AIMVDJDSKNVPTLQDT4VD050NAS";
    private static final String clientSecret = "43HH5NPC23P0CNKFKBQHTVWXLCN1WWCH3M3TZAXHDPJXPNBF";
    private static final String callback = "beerbuddy://connect";
    private static final String TAG = "FoursquareSearch";
    private static FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callback);
    private boolean pause = true;

    public ArrayList<CompactVenue> searchVenues(String ll) throws FoursquareApiException {
        //FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callback);
        Log.d(TAG, ll);
        //Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, "nightlife", 30, null, null, null, null, null);
        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, null, null, null, null, null, null, null);

        if(result.getMeta().getCode() == 200)
        {
            ArrayList<CompactVenue> nearbyLocations = new ArrayList<CompactVenue>();
            for(CompactVenue venue : result.getResult().getVenues())
            {
                nearbyLocations.add(venue);
            }
            Log.d(TAG, "Returned " + nearbyLocations.size() + " locations.");
            return nearbyLocations;
        } else
        {
            Log.e(TAG, "Foursquare API call returned code: " + result.getMeta().getCode());
            return null;
        }
    }
}
