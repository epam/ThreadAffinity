package com.epam.deltix.thread.affinity;

import org.junit.Ignore;
import org.junit.Test;

import java.util.BitSet;


public class AffinityTest {

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnInvalidMaskThatLessMin() {
        BitSet mask = new BitSet();
        Affinity.setAffinity(mask);
    }

    @Ignore
    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnInvalidAffinityMaskThatMoreMax() {
        BitSet mask = new BitSet();
        int processors = Runtime.getRuntime().availableProcessors();
        mask.set(processors);
        Affinity.setAffinity(mask);
    }

    @Test
    public void shouldSetAffinityMask() {
        BitSet mask = new BitSet();
        int processors = Runtime.getRuntime().availableProcessors();
        mask.set(processors - 1);
        Affinity.setAffinity(mask);
    }

}
