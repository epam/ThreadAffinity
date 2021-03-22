package com.epam.deltix.thread.affinity;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.BitSet;

/**
 * Abstraction to manipulate thread affinity on different OS's/platforms.
 * Doesn't support dynamic change of cpu layout.
 */
@ParametersAreNonnullByDefault
public final class Affinity {

    private static final Logger LOG = LoggerFactory.getLogger(Affinity.class);

    private Affinity() {
        throw new AssertionError("Not for you!");
    }

    public static void setAffinity(BitSet mask) {
        if (LOG.isDebugEnabled()) {
            Thread thread = Thread.currentThread();
            LOG.debug("Affinity {} for {})", mask, thread);
        }

        net.openhft.affinity.Affinity.setAffinity(mask);
    }

    public static void setAffinity(int... cpus) {
        BitSet mask = new BitSet();

        for (int cpu : cpus) {
            mask.set(cpu);
        }

        setAffinity(mask);
    }

    /**
     * @return mask for current thread or empty mask if unknown.
     */
    @Nonnull
    public static BitSet getAffinity() {
        BitSet mask = net.openhft.affinity.Affinity.getAffinity();
        return mask == null ? new BitSet() : mask;
    }

    /**
     * @return the thread id of the current thread or -1 is not available.
     */
    public static int getNativeThreadId() {
        return net.openhft.affinity.Affinity.getThreadId();
    }

}
