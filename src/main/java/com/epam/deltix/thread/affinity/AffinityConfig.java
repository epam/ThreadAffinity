package com.epam.deltix.thread.affinity;

import javax.annotation.Nullable;

/**
 * Defines thread affinity configuration model.
 * <p>
 * As for now, this config supports only same AffinityLayout for all threads.
 * However it may contain more complex configuration like different affinity for different thread groups.
 *
 * @author Alexei Osipov
 */
public class AffinityConfig {
    private final AffinityLayout affinityLayout;

    /**
     * @param affinityLayout affinity layout to be used for all produced threads. {@code null} means no explicit affinity change.
     */
    public AffinityConfig(@Nullable AffinityLayout affinityLayout) {
        this.affinityLayout = affinityLayout;
    }

    @Nullable
    public AffinityLayout getAffinityLayout() {
        return affinityLayout;
    }
}
