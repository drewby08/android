package edu.osu;

import android.util.Log;
import fi.foyt.foursquare.api.*;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareSearch {
    private static final String clientId = "PMA1HWVIBC4CGY2YYSFJW3AIMVDJDSKNVPTLQDT4VD050NAS";
    private static final String clientSecret = "43HH5NPC23P0CNKFKBQHTVWXLCN1WWCH3M3TZAXHDPJXPNBF";
    private static final String callback = "beerbuddy://connect";
    private static final String TAG = "FoursquareSearch";

    public void searchVenues(String ll) throws FoursquareApiException {
        FoursquareApi foursquareApi = new FoursquareApi(clientId, clientSecret, callback);

        Result<VenuesSearchResult> result = foursquareApi.venuesSearch(ll, null, null, null, "nightlife", 30, null, null, null, null, null);

        if(result.getMeta().getCode() == 200)
        {
            //Do Something with data
        } else
        {
          Log.e(TAG, "Foursquare API call returned code: " + result.getMeta().getCode());
        }
    }
}
