package com.epam.deltix.thread.affinity;


import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadFactory;

/**
 * Sets same affinity for all produced threads.
 *
 * @author Alexei Osipov
 */
@ParametersAreNonnullByDefault
public class PinnedThreadFactoryWrapper implements ThreadFactory {
    private final ThreadFactory backingThreadFactory;
    private final AffinityLayout affinity;

    public PinnedThreadFactoryWrapper(ThreadFactory backingThreadFactory, AffinityLayout affinity) {
        this.backingThreadFactory = backingThreadFactory;
        this.affinity = affinity;
    }

    @Nonnull
    @Override
    public Thread newThread(Runnable r) {
        return backingThreadFactory.newThread(new PinnedRunnable(affinity, r));
    }
}
