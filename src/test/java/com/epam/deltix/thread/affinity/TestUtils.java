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
