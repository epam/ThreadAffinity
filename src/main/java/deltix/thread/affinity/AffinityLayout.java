package com.epam.deltix.thread.affinity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.BitSet;

@FunctionalInterface
@ParametersAreNonnullByDefault
public interface AffinityLayout {

    /**
     * Empty affinity mask.
     */
    AffinityLayout NO_LAYOUT = (thread) -> new BitSet();

    /**
     * Returns affinity mask for specific thread.
     * Returns empty mask if unknown.
     *
     * @param thread thread.
     * @return affinity mask for specific thread or empty mask if unknown.
     */
    @Nonnull
    BitSet getMask(@Nonnull Thread thread);

}
