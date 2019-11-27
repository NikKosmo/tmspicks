package geoservice;

import io.vavr.Function3;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Стратегия, в которой выполняется метод во всех представленных GeoService,
 * и возвращается первый корректный возвращенный результат.
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FirstConcurrentStrategy implements GeoService {

    Predicate<Optional<Double>> doubleIsPositive = o -> o.map(d -> d >= 0).orElse(false);
    ExecutorService executorService;

    private List<GeoService> services;

    public FirstConcurrentStrategy(List<GeoService> services) {
        this.services = new ArrayList<>(services);
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public Optional<Double> distanceInKilometers(GeoPoint from, GeoPoint to) {
        return getFirstResult(GeoService::distanceInKilometers, from, to, doubleIsPositive, Optional::empty);
    }

    @Override
    public Optional<Double> distanceInKilometers(List<GeoPoint> pointList) {
        return getFirstResult(GeoService::distanceInKilometers, pointList, doubleIsPositive, Optional::empty);
    }

    @Override
    public Optional<Route> getRoute(GeoPoint a, GeoPoint b) {
        return getFirstResult(GeoService::getRoute, a, b, Optional::isPresent, Optional::empty);
    }

    @Override
    public Optional<Route> getRoute(List<GeoPoint> pointList) {
        return getFirstResult(GeoService::getRoute, pointList, Optional::isPresent, Optional::empty);
    }

    @Override
    public Optional<GeoPoint> addressToPoint(String address) {
        return getFirstResult(GeoService::addressToPoint, address, Optional::isPresent, Optional::empty);
    }

    @Override
    public List<GeoPoint> findCandidatesForAddress(String address) {
        return getFirstResult(GeoService::findCandidatesForAddress, address, Objects::nonNull, Collections::emptyList);
    }

    private <A, T> T getFirstResult(BiFunction<GeoService, A, T> function, A paramA, Predicate<T> predicate, Supplier<T> defaultValueSupplier) {
        return getFirstResult(getCallables(function, paramA), predicate, defaultValueSupplier);
    }

    private <A, B, T> T getFirstResult(Function3<GeoService, A, B, T> function, A a, B b, Predicate<T> predicate, Supplier<T> defaultValueSupplier) {
        return getFirstResult(getCallables(function, a, b), predicate, defaultValueSupplier);
    }

    private <A, T> List<Callable<T>> getCallables(BiFunction<GeoService, A, T> function, A a) {
        List<Callable<T>> result = new ArrayList<>();
        for (GeoService service : services) {
            result.add(() -> function.apply(service, a));
        }
        return result;
    }

    private <A, B, T> List<Callable<T>> getCallables(Function3<GeoService, A, B, T> function, A a, B b) {
        List<Callable<T>> result = new ArrayList<>();
        for (GeoService service : services) {
            result.add(() -> function.apply(service, a, b));
        }
        return result;
    }

    private <T> T getFirstResult(List<Callable<T>> callables, Predicate<T> predicate, Supplier<T> defaultValueSupplier) {
        try {
            List<Future<T>> futures = executorService.invokeAll(callables, 1, TimeUnit.MINUTES);
            CompletionService<T> completionService =
                    new ExecutorCompletionService<>(executorService, new LinkedBlockingQueue<>(futures));
            int recieved = 0;
            while (recieved < callables.size()) {
                try {
                    Future<T> future = completionService.take();
                    recieved++;
                    T subResult = future.get();
                    if (predicate.test(subResult)) {
                        return subResult;
                    }
                } catch (ExecutionException e) {
                    log.error("GeoService had error", e);
                }
            }
        } catch (InterruptedException e) {
            log.error("Execution was interrupted", e);
        }
        return defaultValueSupplier.get();
    }
}
