package com.epam.deltix.thread.affinity;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;


class TestUtils {

    protected static WildcardAffinityLayout wildcardLayout(String pattern, int... set) {
        return new WildcardAffinityLayout(pattern, mask(set));
    }

    protected static RegExAffinityLayout regExLayout(String regexp, int... set) {
        return new RegExAffinityLayout(regexp, mask(set));
    }

    protected static void assertEmptyMask(BitSet mask) {
        assertMask(mask);
    }

    protected static void assertMask(BitSet actual, int... expected) {
        assertEquals("Masks don't match", mask(expected), actual);
    }

    protected static BitSet mask(int... set) {
        BitSet mask = new BitSet(set.length);
        for (int index : set) {
            mask.set(index);
        }

        return mask;
    }

}
