package geoservice;

import io.vavr.Function3;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Стратегия, в которой последовательно вызавается метод у GeoService.
 * Если сервис отработал без ошибки, то возвращается его результат.
 * Если при вызове метода у первого сервиса вылетает ошибка,
 * то вызаывается метод у следующего по приоритету сервиса и тд.
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirstErrorlessStrategy implements GeoService {

    Predicate<Optional<Double>> doubleIsPositive = o -> o.map(d -> d >= 0).orElse(false);
    List<GeoService> orderedGeoServices;

    public FirstErrorlessStrategy(List<GeoService> orderedGeoServices) {
        this.orderedGeoServices = new ArrayList<>(orderedGeoServices);
    }

    @Override
    public Optional<Double> distanceInKilometers(GeoPoint from, GeoPoint to) {
        return getFirstErrorlessReuslt(GeoService::distanceInKilometers, from, to, doubleIsPositive, Optional::empty);
    }

    @Override
    public Optional<Double> distanceInKilometers(List<GeoPoint> pointList) {
        return getFirstErrorlessReuslt(GeoService::distanceInKilometers, pointList, doubleIsPositive, Optional::empty);
    }

    @Override
    public Optional<Route> getRoute(GeoPoint a, GeoPoint b) {
        return getFirstErrorlessReuslt(GeoService::getRoute, a, b, Optional::isPresent, Optional::empty);
    }

    @Override
    public Optional<Route> getRoute(List<GeoPoint> pointList) {
        return getFirstErrorlessReuslt(GeoService::getRoute, pointList, Optional::isPresent, Optional::empty);
    }

    @Override
    public Optional<GeoPoint> addressToPoint(String address) {
        return getFirstErrorlessReuslt(GeoService::addressToPoint, address, Optional::isPresent, Optional::empty);
    }

    @Override
    public List<GeoPoint> findCandidatesForAddress(String address) {
        return getFirstErrorlessReuslt(GeoService::findCandidatesForAddress, address, Objects::nonNull, Collections::emptyList);
    }

    private <A, T> T getFirstErrorlessReuslt(BiFunction<GeoService, A, T> function, A a, Predicate<T> predicate,
                                             Supplier<T> defaultValueSupplier) {
        return getFirstErrorlessReuslt(simplifyFunction(function, a), predicate, defaultValueSupplier);
    }

    private <A, B, T> T getFirstErrorlessReuslt(Function3<GeoService, A, B, T> function, A a, B b,
                                                Predicate<T> predicate, Supplier<T> defaultValueSupplier) {
        return getFirstErrorlessReuslt(simplifyFunction(function, a, b), predicate, defaultValueSupplier);
    }

    private <T> T getFirstErrorlessReuslt(Function<GeoService, T> function, Predicate<T> predicate,
                                          Supplier<T> defaultValueSupplier) {
        for (GeoService service : orderedGeoServices) {
            try {
                T result = function.apply(service);
                if (predicate.test(result)) {
                    return result;
                }
            } catch (UnsupportedOperationException e) {
                log.info("Method is not implemented", e);
            } catch (RuntimeException e) {
                log.error("GeoService had error", e);
            }
        }
        return defaultValueSupplier.get();
    }

    private <A, T> Function<GeoService, T> simplifyFunction(BiFunction<GeoService, A, T> function, A a) {
        return geoService -> function.apply(geoService, a);
    }

    private <A, B, T> Function<GeoService, T> simplifyFunction(Function3<GeoService, A, B, T> function, A a, B b) {
        return geoService -> function.apply(geoService, a, b);
    }
}
