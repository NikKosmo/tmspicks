package geoservice;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Route {

    List<GeoPoint> points = new ArrayList<>();
    double len;
    double duration;
}
