package edu.osu;

import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.google.android.maps.GeoPoint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.net.URL;
import java.net.HttpURLConnection;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class LocationOverlay {

    /**
     * Callback url, as set in 'Manage OAuth Costumers' page (https://developer.foursquare.com/)
     */
    public static final String CALLBACK_URL = "BeerBuddy://connect";
    private static final String AUTH_URL = "https://foursquare.com/oauth2/authenticate?response_type=code";
    private static final String TOKEN_URL = "https://foursquare.com/oauth2/access_token?grant_type=authorization_code";
    private static final String API_URL = "https://api.foursquare.com/v2";

    private static final String TAG = "FoursquareApi";

    private static final String accessToken = "O42ODDRXG3EHFKRJ0JSKSUQOZLHXQO3GY4JFSZNNM245XDIR";

    ArrayList<FourSquareVenue> mNearbyList = new ArrayList<FourSquareVenue>();
    private void queryFoursquare(String longitude, String latitude) {
        String query = "https://api.foursquare.com/v2/venues/search?ll="+ latitude + "," + longitude + "&limit=30&query=nightlife&oauth_token=O42ODDRXG3EHFKRJ0JSKSUQOZLHXQO3GY4JFSZNNM245XDIR&v=20120528";
    }

//    private void loadNearbyPlaces(final double latitude, final double longitude) {
//
//        new Thread() {
//            @Override
//            public void run() {
//                int what = 0;
//
//                try {
//                    mNearbyList = getNearby(latitude, longitude);
//                } catch (Exception e) {
//                    what = 1;
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

    public ArrayList<FourSquareVenue> getNearby(double latitude, double longitude) throws Exception {
        ArrayList<FourSquareVenue> venueList = new ArrayList<FourSquareVenue>();

        try {
            String ll 	= String.valueOf(latitude) + "," + String.valueOf(longitude);
            URL url 	= new URL(API_URL + "/venues/search?ll=" + ll + "&limit=30&query=nightlife&oauth_token=" + accessToken);

            Log.d(TAG, "Opening URL " + url.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            urlConnection.connect();

            String response		= streamToString(urlConnection.getInputStream());
            JSONObject jsonObj 	= (JSONObject) new JSONTokener(response).nextValue();

            JSONArray groups	= (JSONArray) jsonObj.getJSONObject("response").getJSONArray("groups");

            int length			= groups.length();

            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    JSONObject group 	= (JSONObject) groups.get(i);
                    JSONArray items 	= (JSONArray) group.getJSONArray("items");

                    int ilength 		= items.length();

                    for (int j = 0; j < ilength; j++) {
                        JSONObject item = (JSONObject) items.get(j);

                        FourSquareVenue venue 	= new FourSquareVenue();

                        venue.id 		= item.getString("id");
                        venue.name		= item.getString("name");

                        JSONObject location = (JSONObject) item.getJSONObject("location");

                        Location loc 	= new Location(LocationManager.GPS_PROVIDER);

                        loc.setLatitude(Double.valueOf(location.getString("lat")));
                        loc.setLongitude(Double.valueOf(location.getString("lng")));

                        venue.location	= loc;
                        venue.address	= location.getString("address");
                        venue.distance	= location.getInt("distance");
                        venue.herenow	= item.getJSONObject("hereNow").getInt("count");
                        venue.type		= group.getString("type");

                        venueList.add(venue);
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }

        return venueList;
    }

    private String streamToString(InputStream is) throws IOException {
        String str  = "";

        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader 	= new BufferedReader(new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = sb.toString();
        }

        return str;
    }
}
