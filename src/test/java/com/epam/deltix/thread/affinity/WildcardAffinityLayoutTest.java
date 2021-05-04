package com.epam.deltix.thread.affinity;

import org.junit.Test;

import java.util.BitSet;

import static com.epam.deltix.thread.affinity.TestUtils.*;


public class WildcardAffinityLayoutTest {

    @Test
    public void testWildcardOnTheEnd() {
        AffinityLayout layout = wildcardLayout("thread*", 0, 1, 3, 7);

        Thread thread = new Thread("thread");
        BitSet mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);

        thread = new Thread("thread1");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);

        thread = new Thread("99thread");
        mask = layout.getMask(thread);
        assertEmptyMask(mask);
    }

    @Test
    public void testWildcardOnTheStart() {
        AffinityLayout layout = wildcardLayout("*thread", 0, 1, 3, 7);

        Thread thread = new Thread("thread");
        BitSet mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);

        thread = new Thread("1thread");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);

        thread = new Thread("thread99");
        mask = layout.getMask(thread);
        assertEmptyMask(mask);
    }

    @Test
    public void testComplexWildcard() {
        AffinityLayout layout = wildcardLayout("*thread*cpu*10*", 1);

        Thread thread = new Thread("thread");
        BitSet mask = layout.getMask(thread);
        assertEmptyMask(mask);

        thread = new Thread("some thread on 10 cpu");
        mask = layout.getMask(thread);
        assertEmptyMask(mask);

        thread = new Thread("some thread on cpu 10");
        mask = layout.getMask(thread);
        assertMask(mask, 1);
    }

}
