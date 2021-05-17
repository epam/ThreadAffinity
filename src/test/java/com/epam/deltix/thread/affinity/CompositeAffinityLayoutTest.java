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
