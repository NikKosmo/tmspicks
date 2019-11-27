package interval;

import org.junit.Test;

import static interval.WeekDaysInstant.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalTest {
    @Test
    public void containsDateStart() {
        assertTrue(Interval.start(MONDAY).contains(TUESDAY));
    }

    @Test
    public void containsDateFinish() {
        assertFalse(Interval.finish(MONDAY).contains(TUESDAY));
    }

    @Test
    public void containsDateFinish2() {
        assertTrue(Interval.finish(TUESDAY).contains(MONDAY));
    }

    @Test
    public void containsDateInInterval() {
        assertTrue(Interval.of(MONDAY, WEDNESDAY).contains(TUESDAY));
    }

    @Test
    public void containsDateNotInInterval() {
        assertFalse(Interval.of(TUESDAY, WEDNESDAY).contains(MONDAY));
    }

    @Test
    public void containsDateInInfInterval() {
        assertTrue(Interval.infiniteInterval().contains(MONDAY));
    }

    @Test
    public void infinityContainsInfiniteInterval() {
        assertTrue(Interval.infiniteInterval().contains(Interval.infiniteInterval()));
    }

    @Test
    public void infinityContainsInterval() {
        assertTrue(Interval.infiniteInterval().contains(Interval.of(MONDAY, TUESDAY)));
    }

    @Test
    public void infinityContainsIntervalWithoutStart() {
        assertTrue(Interval.infiniteInterval().contains(Interval.finish(MONDAY)));
    }

    @Test
    public void infinityContainsIntervalWithoutFinish() {
        assertTrue(Interval.infiniteInterval().contains(Interval.start(MONDAY)));
    }

    @Test
    public void intervalWithoutFinishContainsInterval() {
        assertTrue(Interval.start(MONDAY).contains(Interval.start(TUESDAY)));
    }

    @Test
    public void intervalWithoutStartContainsInterval() {
        assertTrue(Interval.finish(TUESDAY).contains(Interval.finish(MONDAY)));
    }

    @Test
    public void closedIntervalContainsAnotherClosedInterval() {
        assertTrue(Interval.of(MONDAY, THURSDAY)
                           .contains(Interval.of(TUESDAY, WEDNESDAY)));
    }

    @Test
    public void closedIntervalDoesntContainIntersectingInterval() {
        assertFalse(Interval.of(MONDAY, WEDNESDAY)
                            .contains(Interval.of(TUESDAY, THURSDAY)));
    }

    @Test
    public void closedIntervalDoesntContainInfiniteInterval() {
        assertFalse(Interval.of(MONDAY, TUESDAY)
                            .contains(Interval.infiniteInterval()));
    }

    @Test
    public void closedIntervalDoesntContainIntersectingInterval_2() {
        assertFalse(Interval.of(TUESDAY, THURSDAY)
                            .contains(Interval.of(MONDAY, WEDNESDAY)));
    }

    @Test
    public void containsItself() {
        Interval interval = Interval.of(MONDAY, TUESDAY);
        assertTrue(interval.contains(interval));
    }

    @Test
    public void intersectItself() {
        Interval interval = Interval.of(MONDAY, TUESDAY);
        assertTrue(interval.intersect(interval));
    }

    @Test
    public void containingIntervalIntersectWithContained() {
        assertTrue(Interval.of(MONDAY, THURSDAY)
                           .intersect(Interval.of(TUESDAY, WEDNESDAY)));
    }

    @Test
    public void containedIntervalIntersectWithContaining_2() {
        assertTrue(Interval.of(TUESDAY, WEDNESDAY)
                           .intersect(Interval.of(MONDAY, THURSDAY)));
    }

    @Test
    public void infiniteIntervalsIntersect() {
        assertTrue(Interval.infiniteInterval().intersect(Interval.infiniteInterval()));
    }

    @Test
    public void closedIntervalIntersectInfinite() {
        Interval a = Interval.of(MONDAY, TUESDAY);
        Interval b = Interval.infiniteInterval();
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void infiniteIntervalIntersectIntervalWithoutStart() {
        Interval a = Interval.infiniteInterval();
        Interval b = Interval.finish(MONDAY);
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void infiniteIntervalIntersectIntervalWithoutFinish() {
        Interval a = Interval.infiniteInterval();
        Interval b = Interval.start(MONDAY);
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void infiniteIntervalIntersectClosedInterval() {
        Interval a = Interval.infiniteInterval();
        Interval b = Interval.of(MONDAY, TUESDAY);
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void twoClosedIntervalIntersect() {
        Interval a = Interval.of(MONDAY, WEDNESDAY);
        Interval b = Interval.of(TUESDAY, THURSDAY);
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void twoOpenIntervalIntersect() {
        Interval a = Interval.finish(TUESDAY);
        Interval b = Interval.start(MONDAY);
        assertTrue(a.intersect(b));
        assertTrue(b.intersect(a));
    }

    @Test
    public void twoOpenIntervalDontIntersect() {
        Interval a = Interval.finish(MONDAY);
        Interval b = Interval.start(TUESDAY);
        assertFalse(a.intersect(b));
        assertFalse(b.intersect(a));
    }

    @Test
    public void twoOpenIntervalTouchButDontIntersect() {
        Interval a = Interval.start(MONDAY);
        Interval b = Interval.finish(MONDAY);
        assertFalse(a.intersect(b));
        assertFalse(b.intersect(a));
    }

    @Test
    public void twoClosedIntervalTouchButDontIntersect() {
        Interval a = Interval.of(MONDAY, TUESDAY);
        Interval b = Interval.of(TUESDAY, WEDNESDAY);
        assertFalse(a.intersect(b));
        assertFalse(b.intersect(a));
    }
}