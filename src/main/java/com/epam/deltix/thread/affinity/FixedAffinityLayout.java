package com.epam.deltix.thread.affinity;

import javax.annotation.Nonnull;
import java.util.BitSet;


public class FixedAffinityLayout implements AffinityLayout {

    protected final BitSet mask;

    public FixedAffinityLayout(@Nonnull final BitSet mask) {
        this.mask = mask;
    }

    public FixedAffinityLayout(final int... cpus) {
        final BitSet mask = new BitSet();

        for (int cpu : cpus) {
            mask.set(cpu);
        }

        this.mask = mask;
    }

    @Nonnull
    @Override
    public BitSet getMask(@Nonnull Thread thread) {
        return mask;
    }

}
