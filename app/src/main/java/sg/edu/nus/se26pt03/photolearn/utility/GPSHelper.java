package sg.edu.nus.se26pt03.photolearn.utility;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.util.List;
import java.util.Locale;

/**
 * Created by c.banisetty on 3/17/2018.
 */

public class GPSHelper {
    private Context context;

    public GPSHelper(Context context) {
        this.context = context;
    }


    public Address GetLocationByLatandLongitude(double lat, double log) {
        Geocoder geocoder = null;
        Address result = null;
        try {
            geocoder = new Geocoder(this.context, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat, log, 1);
            if (addressList != null && addressList.size() == 1) {
                result = addressList.get(0);
            }
        } catch (Exception ex) {
            Log.w("", "");
        }
        return result;

    }

    public String GetLocationByLatandLongitudeAsString(double lat, double log) {
        String result = "";
        StringBuilder stringBuilder = new StringBuilder();
        Address address = null;
        try {
            address = GetLocationByLatandLongitude(lat, log);
            if (address != null) {
                stringBuilder.append(address.getLocality()).append("\n");
                stringBuilder.append(address.getPostalCode()).append("\n");
                stringBuilder.append(address.getCountryName());
            }
        } catch (Exception ex) {
            Log.w("", "");
        }
        return stringBuilder.toString();

    }
}

