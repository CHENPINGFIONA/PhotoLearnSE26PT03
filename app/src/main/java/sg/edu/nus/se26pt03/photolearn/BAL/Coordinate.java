package sg.edu.nus.se26pt03.photolearn.BAL;

/**
 * Created by Drake on 20/3/18.
 */

public class Coordinate {
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
