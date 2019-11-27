package interval;

import lombok.Value;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Value
public class LocalInterval {

    LocalDateTime startTime;
    LocalDateTime finishTime;

    public static LocalInterval toLocalInterval(Instant startInstant, Instant finishInstant, ZoneId zoneId) {
        return new LocalInterval(LocalDateTime.ofInstant(startInstant, zoneId), LocalDateTime.ofInstant(finishInstant, zoneId));
    }
}
