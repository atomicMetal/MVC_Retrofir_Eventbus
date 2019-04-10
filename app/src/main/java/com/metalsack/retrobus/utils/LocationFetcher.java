package com.metalsack.retrobus.utils;

import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * This class uses the Google Geocoder to fetch a location based on the passed geo coordinates.
 */
public class LocationFetcher {
    public static final String LOGTAG = "LocationFetcher";
    private Geocoder geocoder;

    public LocationFetcher(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    /**
     * Returns the location which the Google Geocoder finds for the passed geo coordinates.
     *
     * @param latitude  The latitude coordinate.
     * @param longitude The longitude coordinate.
     *
     * @return A concatenation of zip code and city name.
     */
    public String fetchLocation(double latitude, double longitude) {
        List<Address> addresses = null;
        String location = null;

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

        } catch (IOException ioException) {
            // Catch network or other I/O problems.
            Log.e(LOGTAG, "IOException", ioException);

        } catch (IllegalArgumentException illegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e(LOGTAG, "Invalid location data. " + "Latitude = " + latitude +
                    ", Longitude = " + longitude, illegalArgumentException);
        }

        if (addresses == null || addresses.isEmpty()) {
            Log.e(LOGTAG, "No address found.");

        } else {
            Log.i(LOGTAG, "Address found.");

            Address address = addresses.get(0);

            // Get zip and city name. According to this stackoverflow post the city name is
            // either in the locality or the subAdminArea field: http://stackoverflow.com/a/21922542
            String zipCode = address.getPostalCode();
            String cityName = address.getLocality();

            if (TextUtils.isEmpty(cityName)) {
                cityName = address.getSubAdminArea();
            }

            location = zipCode + " " + cityName;
        }

        return location;
    }
}
