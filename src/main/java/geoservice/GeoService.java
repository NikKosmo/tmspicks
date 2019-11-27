package geoservice;

import java.util.List;
import java.util.Optional;

public interface GeoService {
    default Optional<Double> distanceInKilometers(GeoPoint from, GeoPoint to) {
        throw new UnsupportedOperationException();
    }

    default Optional<Double> distanceInKilometers(List<GeoPoint> pointList) {
        throw new UnsupportedOperationException();
    }

    default Optional<Route> getRoute(GeoPoint a, GeoPoint b) {
        throw new UnsupportedOperationException();
    }

    default Optional<Route> getRoute(List<GeoPoint> pointList) {
        throw new UnsupportedOperationException();
    }

    default Optional<GeoPoint> addressToPoint(String address) {
        throw new UnsupportedOperationException();
    }

    default List<GeoPoint> findCandidatesForAddress(String address) {
        throw new UnsupportedOperationException();
    }
}
