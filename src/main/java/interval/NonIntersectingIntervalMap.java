package interval;


import java.time.Instant;
import java.util.*;


public class NonIntersectingIntervalMap<V> extends TreeMap<Interval, V> {

    private static final Comparator<Interval> comparator = Comparator.comparing(Interval::getStartTime).thenComparing(Interval::getFinishTime);

    private final boolean forbidExistingMappingChange;

    public NonIntersectingIntervalMap() {
        this(false);
    }

    public NonIntersectingIntervalMap(boolean forbidExistingMappingChange) {
        super(comparator);
        this.forbidExistingMappingChange = forbidExistingMappingChange;
    }

    public NonIntersectingIntervalMap(Map<Interval, ? extends V> m) {
        this();
        this.putAll(m);
    }

    public NonIntersectingIntervalMap(Map<Interval, ? extends V> m, boolean forbidExistingMappingChange) {
        this(forbidExistingMappingChange);
        this.putAll(m);
    }

    public NonIntersectingIntervalMap(NonIntersectingIntervalMap<? extends V> m) {
        this(m, m.forbidExistingMappingChange);
    }

    @Override
    public V put(Interval key, V value) {
        Interval lowerKey = lowerKey(key);
        if (lowerKey != null
                && lowerKey.intersect(key)) {
            throw new IllegalArgumentException(getErrorMessage(key, lowerKey));
        }
        Interval higherKey = higherKey(key);
        if (higherKey != null
                && higherKey.intersect(key)) {
            throw new IllegalArgumentException(getErrorMessage(key, higherKey));
        }

        if (forbidExistingMappingChange && containsKey(key)) {
            throw new IllegalArgumentException(String.format("В карте уже содержится указанный интервал %s", key));
        }
        return super.put(key, value);
    }

    private String getErrorMessage(Interval incomingInterval, Interval existingInterval) {
        return String.format("В карте есть интевал %s пересекающийся с входящим %s", existingInterval, incomingInterval);
    }

    public V getAtTheMoment(Instant instant) {
        Map.Entry<Interval, V> lowerEntry = getEntryAtTheMoment(instant);
        return lowerEntry != null ?
                lowerEntry.getValue()
                : null;
    }

    public Map.Entry<Interval, V> getEntryAtTheMoment(Instant instant) {
        Interval searchInterval = Interval.start(instant);
        Map.Entry<Interval, V> lowerEntry = floorEntry(searchInterval);
        if (lowerEntry != null
                && lowerEntry.getKey().contains(instant)) {
            return lowerEntry;
        }
        return null;
    }

    public NonIntersectingIntervalMap<V> getMapIntersectingWithInterval(Interval searchInterval) {
        Interval lowerKey = Optional.ofNullable(lowerKey(searchInterval))
                .orElse(searchInterval);
        Interval higherBorderInterval = Interval.of(searchInterval.getFinishTime(), searchInterval.getFinishTime());
        Interval higherKey = Optional.ofNullable(higherKey(higherBorderInterval))
                .orElse(higherBorderInterval);
        NavigableMap<Interval, V> intervalVNavigableMap = subMap(lowerKey, lowerKey.intersect(searchInterval),
                                                                 higherKey, higherKey.intersect(searchInterval));
        return new NonIntersectingIntervalMap<>(intervalVNavigableMap);
    }

    public NonIntersectingIntervalMap<V> getMapInAndAroundInterval(Interval searchInterval) {
        NonIntersectingIntervalMap<V> result = new NonIntersectingIntervalMap<>(getMapIntersectingWithInterval(searchInterval));
        if (result.isEmpty()) {
            Optional.ofNullable(lowerEntry(searchInterval))
                    .ifPresent(entry -> result.put(entry.getKey(), entry.getValue()));
            Optional.ofNullable(higherEntry(searchInterval))
                    .ifPresent(entry -> result.put(entry.getKey(), entry.getValue()));
        } else {
            Optional.ofNullable(lowerEntry(result.firstKey()))
                    .ifPresent(entry -> result.put(entry.getKey(), entry.getValue()));
            Optional.ofNullable(higherEntry(result.lastKey()))
                    .ifPresent(entry -> result.put(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    public NonIntersectingIntervalMap<V> getMapBeforeMoment(Instant moment) {
        return getMapIntersectingWithInterval(Interval.finish(moment));
    }

    public NonIntersectingIntervalMap<V> getMapAfterMoment(Instant moment) {
        return getMapIntersectingWithInterval(Interval.start(moment));
    }
}
