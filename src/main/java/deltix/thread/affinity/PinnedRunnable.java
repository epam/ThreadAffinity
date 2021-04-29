package com.epam.deltix.thread.affinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.BitSet;


@ParametersAreNonnullByDefault
public class PinnedRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Affinity.class);

    protected final AffinityLayout affinity;
    protected final Runnable target;

    public PinnedRunnable(AffinityLayout affinity, Runnable target) {
        this.affinity = affinity;
        this.target = target;
    }

    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        final BitSet mask = affinity.getMask(thread);

        if (mask.isEmpty()) {
            logWarning(thread);
        } else {
            Affinity.setAffinity(mask);
        }

        target.run();
    }

    private static void logWarning(final Thread thread) {
        if (LOG.isWarnEnabled()) {
            LOG.warn("{}: affinity is not specified. The current affinity: {})", thread, Affinity.getAffinity());
        }
    }

}
