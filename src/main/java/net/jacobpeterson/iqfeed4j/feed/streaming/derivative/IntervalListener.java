package net.jacobpeterson.iqfeed4j.feed.streaming.derivative;

import net.jacobpeterson.iqfeed4j.feed.message.FeedMessageListener;
import net.jacobpeterson.iqfeed4j.model.feed.streaming.derivative.Interval;

/**
 * {@link IntervalListener} is a {@link FeedMessageListener} for {@link Interval}s.
 */
public abstract class IntervalListener implements FeedMessageListener<Interval> {

    /**
     * Called when a request symbol was not found or the user is not authorized.
     *
     * @param symbol the symbol
     */
    public abstract void onSymbolNotWatched(String symbol);

    /**
     * Called when the attempt to watch a symbol has failed, due to the symbol limit being reached.
     *
     * @param symbol the symbol
     */
    public abstract void onSymbolLimitReached(String symbol);

    /**
     * Called when the new interval bar watch has replaced the previous watched streaming interval bar watch.
     *
     * @param symbol    the symbol
     * @param requestID the request ID
     */
    public abstract void onReplacedPreviouslyWatchedSymbol(String symbol, String requestID);
}