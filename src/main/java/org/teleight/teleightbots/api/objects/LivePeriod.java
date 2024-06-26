package org.teleight.teleightbots.api.objects;

public class LivePeriod {

    public static LivePeriod FOREVER = LivePeriod.fromSeconds(0x7FFFFFFF);

    private final int seconds;

    public LivePeriod(int seconds) {
        this.seconds = seconds;
    }

    public static LivePeriod fromSeconds(int seconds) {
        return new LivePeriod(seconds);
    }

    public int getSeconds() {
        return seconds;
    }
}
