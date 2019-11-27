package geoservice;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter(value = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GeoPoint {

    double latitude = 0;

    double longitude = 0;

    private GeoPoint() {
    }

    public GeoPoint(String latitude, String longitude) {
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
    }

    public GeoPoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static GeoPoint zeroPoint() {
        return new GeoPoint(0, 0);
    }

    @Override
    public String toString() {
        return String.format("gp{lat:%6.4f lon:%6.4f}", latitude, longitude);
    }
}
