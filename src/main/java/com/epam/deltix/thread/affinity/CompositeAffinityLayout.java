package com.epam.deltix.thread.affinity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.BitSet;

@ParametersAreNonnullByDefault
public class CompositeAffinityLayout implements AffinityLayout {

    protected final ArrayList<AffinityLayout> layouts = new ArrayList<>();

    public void addLayout(@Nonnull AffinityLayout layout) {
        layouts.add(layout);
    }

    public void removeLayout(@Nonnull AffinityLayout layout) {
        layouts.remove(layout);
    }

    @Override
    @Nonnull
    public BitSet getMask(@Nonnull Thread thread) {
        BitSet result = new BitSet();
        for (AffinityLayout layout : layouts) {
            BitSet mask = layout.getMask(thread);
            result.or(mask);
        }

        return result;
    }

}
