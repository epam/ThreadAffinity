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
