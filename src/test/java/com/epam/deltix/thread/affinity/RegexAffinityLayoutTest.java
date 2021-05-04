package com.epam.deltix.thread.affinity;

import org.junit.Test;

import java.util.BitSet;

import static com.epam.deltix.thread.affinity.TestUtils.*;


public class RegexAffinityLayoutTest {

    @Test
    public void testRegExpMask() {
        RegExAffinityLayout layout = regExLayout("thread.+", 0, 1, 3, 7);

        Thread thread = new Thread("thread");
        BitSet mask = layout.getMask(thread);
        assertEmptyMask(mask);

        thread = new Thread("thread1");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);

        thread = new Thread("thread99");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 3, 7);
    }

}
