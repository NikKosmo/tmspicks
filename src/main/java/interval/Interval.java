package interval;

import lombok.Value;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Represent half-opened time interval [)
 * startTime is included
 * finishTime is not included
 */

@Value
public class Interval {

    Instant startTime;
    Instant finishTime;

    public static Interval infiniteInterval() {
        return Interval.of(Instant.MIN, Instant.MAX);
    }

    public static Interval of(Instant startTime, Instant finishTime) {
        Objects.requireNonNull(startTime, "Начало интервала не может быть пустым");
        Objects.requireNonNull(finishTime, "Конец интервала не может быть пустым");
        if (startTime.isAfter(finishTime)) {
            throw new IllegalArgumentException(String.format("Начало интервала %s не должно быть после его окончания %s",
                                                             startTime, finishTime));
        }
        return new Interval(startTime, finishTime);
    }

    public static Interval of(LocalInterval localInterval, ZoneId zoneId) {
        return of(localInterval.getStartTime().atZone(zoneId).toInstant(),
                  localInterval.getFinishTime().atZone(zoneId).toInstant());
    }

    public static Interval start(Instant startTime) {
        return of(startTime, Instant.MAX);
    }

    public static Interval finish(Instant finishTime) {
        return of(Instant.MIN, finishTime);
    }

    public boolean contains(Instant instant) {
        return !instant.isBefore(startTime) && instant.isBefore(finishTime);
    }

    public boolean contains(Interval interval) {
        return !startTime.isAfter(interval.startTime)
                && !finishTime.isBefore(interval.finishTime);
    }

    public boolean contains(Instant instant, Duration margin) {
        if (!Instant.MIN.equals(startTime) && instant.isBefore(startTime.minus(margin))) {
            return false;
        }
        return Instant.MIN.equals(finishTime) || instant.isBefore(finishTime.plus(margin));
    }

    public boolean intersect(Interval interval) {
        return startTime.isBefore(interval.finishTime) && finishTime.isAfter(interval.startTime);
    }

    public Duration calcDuration() {
        return Duration.between(startTime, finishTime);
    }

    @Override
    public String toString() {
        return "[" + nn(startTime) + "," + nn(finishTime) + ')';
    }

    private Object nn(Object o) {
        if (o == null) {
            return "\u221E";
        }
        return o;
    }
}
