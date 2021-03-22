package com.epam.deltix.thread.affinity;

import org.junit.Test;

import java.util.BitSet;

import static com.epam.deltix.thread.affinity.TestUtils.assertMask;

public class CompositeAffinityLayoutTest {

    @Test
    public void testCompositeMask() {
        CompositeAffinityLayout layout = new CompositeAffinityLayout();
        layout.addLayout(TestUtils.regExLayout("th.+", 0, 2, 4, 6));
        layout.addLayout(TestUtils.regExLayout("thread.+", 0, 1, 4, 7));

        Thread thread = new Thread("t");
        BitSet mask = layout.getMask(thread);
        TestUtils.assertEmptyMask(mask);

        thread = new Thread("thread");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 2, 4, 6);

        thread = new Thread("thread99");
        mask = layout.getMask(thread);
        assertMask(mask, 0, 1, 2, 4, 6, 7);
    }

}
