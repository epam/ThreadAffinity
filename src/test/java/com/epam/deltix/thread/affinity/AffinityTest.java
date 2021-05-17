/*
 * Copyright 2021 EPAM Systems, Inc
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
