package de.marenthyu.ogc.connectors.twitch;

public class TwitchEvent {
    /**
     * The type of the Event.
     */
    public final TwitchEventType type;
    /**
     * For example the start of the channel points description or what should be contained in a bits message
     */
    public final String stringParameter;

    public final int lowerBound, upperBound;
    /**
     * For use with SUBS, makes it so ONLY gift subs are used, other parameters are ignored
     */
    public final boolean giftedOnly;
    /**
     * For use with SUBS, uses the current streak for subs instead of the cumulative subscription period - this may not
     * be available.
     */
    public final boolean usesStreak;

    public TwitchEvent(TwitchEventType type, String stringParameter, int lowerBound, int upperBound, boolean giftedOnly, boolean usesStreak) {
        this.type = type;
        this.stringParameter = stringParameter;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.giftedOnly = giftedOnly;
        this.usesStreak = usesStreak;
    }
}
