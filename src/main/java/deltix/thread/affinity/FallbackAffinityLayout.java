package com.epam.deltix.thread.affinity;

import javax.annotation.Nonnull;
import java.util.BitSet;


public class FallbackAffinityLayout implements AffinityLayout {

    protected final AffinityLayout layout;
    protected final BitSet fallback;

    public FallbackAffinityLayout(@Nonnull final AffinityLayout layout, @Nonnull final BitSet fallback) {
        this.layout = layout;
        this.fallback = fallback;
    }

    @Nonnull
    @Override
    public BitSet getMask(@Nonnull final Thread thread) {
        final BitSet mask = layout.getMask(thread);
        return mask.isEmpty() ? fallback : mask;
    }

}
