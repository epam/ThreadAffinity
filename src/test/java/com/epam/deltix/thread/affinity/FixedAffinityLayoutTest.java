package com.epam.deltix.thread.affinity;

import org.junit.Test;

import java.util.BitSet;

import static com.epam.deltix.thread.affinity.TestUtils.mask;


public class FixedAffinityLayoutTest {

    @Test
    public void shouldReturnSameMaskOnEachCall() {
        BitSet expected = mask(1, 3, 5);
        AffinityLayout layout = new FixedAffinityLayout(expected);

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(Integer.toHexString(i));
            BitSet actual = layout.getMask(thread);
            TestUtils.assertMask(actual, 1, 3, 5);
        }

    }

}
