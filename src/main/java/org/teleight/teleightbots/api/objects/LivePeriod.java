package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.teleight.teleightbots.api.serialization.deserializers.LivePeriodDeserializer;
import org.teleight.teleightbots.api.serialization.serializers.LivePeriodSerializer;

import java.util.concurrent.TimeUnit;

@JsonSerialize(using = LivePeriodSerializer.class)
@JsonDeserialize(using = LivePeriodDeserializer.class)
public class LivePeriod {

    public static LivePeriod FOREVER = LivePeriod.fromSeconds(0x7FFFFFFF);

    private final long seconds;

    private LivePeriod(long seconds) {
        this.seconds = seconds;
    }

    public static LivePeriod fromSeconds(int seconds) {
        return from(seconds, TimeUnit.SECONDS);
    }

    public static LivePeriod from(int time, TimeUnit unit) {
        return new LivePeriod(unit.toSeconds(time));
    }

    public long getSeconds() {
        return seconds;
    }
}
