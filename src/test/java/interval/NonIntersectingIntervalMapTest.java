package interval;

import org.junit.Test;

import static interval.WeekDaysInstant.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class NonIntersectingIntervalMapTest {

    @Test
    public void putEqualIntervalsInMapWithoutUniquenessCheck() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval mondayToTuesdayCopy = Interval.of(MONDAY, TUESDAY);
        map.put(mondayToTuesday, 1);
        map.put(mondayToTuesdayCopy, 2);
        assertEquals(1, map.size());
        assertEquals(2, (int) map.values().stream().findFirst().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void putEqualIntervalsInMapWithUniquenessCheck() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>(true);
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval mondayToTuesdayNew = Interval.of(MONDAY, TUESDAY);
        map.put(mondayToTuesday, 1);
        map.put(mondayToTuesdayNew, 2);
    }

    @Test
    public void put_One() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        map.put(mondayToTuesday, 1);
        assertEquals(1, map.size());
    }

    @Test
    public void put_TwoNonIntersecting() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToThursday = Interval.of(WEDNESDAY, THURSDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToThursday, 1);
        assertEquals(2, map.size());
    }

    @Test
    public void put_TwoNonIntersecting_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToThursday = Interval.of(WEDNESDAY, THURSDAY);
        map.put(wednesdayToThursday, 1);
        map.put(mondayToTuesday, 1);
        assertEquals(2, map.size());
    }

    @Test
    public void put_TwoTouchingNonIntersecting() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToThursday, 1);
        assertEquals(2, map.size());
    }

    @Test
    public void put_TwoTouchingNonIntersecting_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToThursday, 1);
        assertEquals(2, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_TwoIntersecting() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToWednesday = Interval.of(MONDAY, WEDNESDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(mondayToWednesday, 1);
        map.put(wednesdayToThursday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_TwoIntersecting_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToWednesday = Interval.of(MONDAY, WEDNESDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(wednesdayToThursday, 1);
        map.put(mondayToWednesday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_OneContainingAnother() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(mondayToSunday, 1);
        map.put(wednesdayToThursday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_OneContainingAnother_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval wednesdayToThursday = Interval.of(TUESDAY, THURSDAY);
        map.put(wednesdayToThursday, 1);
        map.put(mondayToSunday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_TwoStartingInSameDay() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval mondayToThursday = Interval.of(MONDAY, THURSDAY);
        map.put(mondayToThursday, 1);
        map.put(mondayToSunday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_TwoStartingInSameDay_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval mondayToThursday = Interval.of(MONDAY, THURSDAY);
        map.put(mondayToSunday, 1);
        map.put(mondayToThursday, 1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void put_TwoFinishingInSameDay() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval wednesdayToSunday = Interval.of(WEDNESDAY, SUNDAY);
        map.put(mondayToSunday, 1);
        map.put(wednesdayToSunday, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void put_TwoFinishingInSameDay_ReverseOrder() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToSunday = Interval.of(MONDAY, SUNDAY);
        Interval wednesdayToSunday = Interval.of(WEDNESDAY, SUNDAY);
        map.put(wednesdayToSunday, 1);
        map.put(mondayToSunday, 1);
    }

    @Test
    public void getAtTheMoment_InTheTargetInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToSaturday = Interval.of(WEDNESDAY, SATURDAY);
        map.put(wednesdayToSaturday, 1);
        assertEquals((Integer) 1, map.getAtTheMoment(FRIDAY));
    }

    @Test
    public void getAtTheMoment_BeforeTargetInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToSaturday = Interval.of(WEDNESDAY, SATURDAY);
        map.put(wednesdayToSaturday, 1);
        assertNull(map.getAtTheMoment(MONDAY));
    }

    @Test
    public void getAtTheMoment_SamesStartAsTargetInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToSaturday = Interval.of(WEDNESDAY, SATURDAY);
        map.put(wednesdayToSaturday, 1);
        assertEquals((Integer) 1, map.getAtTheMoment(WEDNESDAY));
    }

    @Test
    public void getAtTheMoment_SamesFinishTargetInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToSaturday = Interval.of(WEDNESDAY, SATURDAY);
        map.put(wednesdayToSaturday, 1);
        assertNull(map.getAtTheMoment(SATURDAY));
    }

    @Test
    public void getAtTheMoment_AfterTargetInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToSaturday = Interval.of(WEDNESDAY, SATURDAY);
        map.put(wednesdayToSaturday, 1);
        assertNull(map.getAtTheMoment(SUNDAY));
    }

    @Test
    public void getMapIntersectingWithInterval_ForContainingInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(TUESDAY, SATURDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalIntersectingAtStart() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(WEDNESDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalIntersectingAtFinish() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(THURSDAY, SUNDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForContainedInterval() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(THURSDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalTouchingAtFinish() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(FRIDAY, SUNDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalAfterFinish() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(SATURDAY, SUNDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalTouchingAtStart() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(TUESDAY, WEDNESDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_ForIntervalBeforeStart() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        map.put(wednesdayToFriday, 1);
        Interval searchInterval = Interval.of(MONDAY, TUESDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalContainsFirstOnly() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(MONDAY, WEDNESDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalContainsFirstIntersectLast() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToSaturday = Interval.of(THURSDAY, SATURDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToSaturday, 1);
        Interval searchInterval = Interval.of(MONDAY, FRIDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalContainsAllInMap() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(MONDAY, SUNDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalIntersectTwoIntervals() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToWednesday = Interval.of(MONDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(mondayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(TUESDAY, FRIDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalContainsLastOnly() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(THURSDAY, FRIDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_SearchIntervalTouchesLast() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(FRIDAY, SATURDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapIntersectingWithInterval_EmptyMap() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval searchInterval = Interval.of(FRIDAY, SATURDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapIntersectingWithInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_NothingInIntervalAndOneOnEachSide() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval fridayToSaturday = Interval.of(FRIDAY, SATURDAY);
        map.put(mondayToTuesday, 1);
        map.put(fridayToSaturday, 1);
        Interval searchInterval = Interval.of(WEDNESDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_NothingInIntervalAndNothingOnOneSideAndOneOnAnother() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(MONDAY, MONDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_NothingInIntervalAndOneOnOneSideAndNothingOnAnother() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval tuesdayToWednesday = Interval.of(TUESDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        map.put(tuesdayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        Interval searchInterval = Interval.of(SATURDAY, SUNDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(1, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_SearchIntervalContainedInOneAndOneOnEachSide() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        Interval saturdayToSunday = Interval.of(SATURDAY, SUNDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToFriday, 1);
        map.put(saturdayToSunday, 1);
        Interval searchInterval = Interval.of(THURSDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(3, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_SearchIntervalContainsOneAndOneOnEachSide() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval thursdayMidday = Interval.of(THURSDAY, THURSDAY);
        Interval saturdayToSunday = Interval.of(SATURDAY, SUNDAY);
        map.put(mondayToTuesday, 1);
        map.put(thursdayMidday, 1);
        map.put(saturdayToSunday, 1);
        Interval searchInterval = Interval.of(WEDNESDAY, FRIDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(3, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_SearchIntervalIntersectsOneAndOneOnEachSide() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToFriday = Interval.of(WEDNESDAY, FRIDAY);
        Interval saturdayToSunday = Interval.of(SATURDAY, SUNDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToFriday, 1);
        map.put(saturdayToSunday, 1);
        Interval searchInterval = Interval.of(TUESDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(3, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_SearchIntervalIntersectsOneAndOneOnOneSideNothingOnAnother() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToTuesday = Interval.of(MONDAY, TUESDAY);
        Interval wednesdayToThursday = Interval.of(WEDNESDAY, THURSDAY);
        Interval fridayToSunday = Interval.of(FRIDAY, SUNDAY);
        map.put(mondayToTuesday, 1);
        map.put(wednesdayToThursday, 1);
        map.put(fridayToSunday, 1);
        Interval searchInterval = Interval.start(SATURDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_SearchIntervalIntersectsOneAndNothingOnOneSideOneOnAnother() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval mondayToWednesday = Interval.of(MONDAY, WEDNESDAY);
        Interval thursdayToFriday = Interval.of(THURSDAY, FRIDAY);
        Interval saturdayToSunday = Interval.of(SATURDAY, SUNDAY);
        map.put(mondayToWednesday, 1);
        map.put(thursdayToFriday, 1);
        map.put(saturdayToSunday, 1);
        Interval searchInterval = Interval.finish(TUESDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(2, mapIntersectingWithInterval.size());
    }

    @Test
    public void getMapInAndAroundInterval_EmptyMap() {
        NonIntersectingIntervalMap<Integer> map = new NonIntersectingIntervalMap<>();
        Interval searchInterval = Interval.of(WEDNESDAY, THURSDAY);
        NonIntersectingIntervalMap<Integer> mapIntersectingWithInterval = map.getMapInAndAroundInterval(searchInterval);
        assertEquals(0, mapIntersectingWithInterval.size());
    }

}