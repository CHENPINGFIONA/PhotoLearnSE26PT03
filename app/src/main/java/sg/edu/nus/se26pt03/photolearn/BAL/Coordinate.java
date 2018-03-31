package sg.edu.nus.se26pt03.photolearn.BAL;

import java.io.Serializable;

/**
 * Created by part time team 3  on 20/3/18.
 */

public class Coordinate implements Serializable {
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
