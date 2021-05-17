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

import javax.annotation.Nonnull;
import java.util.BitSet;


public class FixedAffinityLayout implements AffinityLayout {

    protected final BitSet mask;

    public FixedAffinityLayout(@Nonnull final BitSet mask) {
        this.mask = mask;
    }

    public FixedAffinityLayout(final int... cpus) {
        final BitSet mask = new BitSet();

        for (int cpu : cpus) {
            mask.set(cpu);
        }

        this.mask = mask;
    }

    @Nonnull
    @Override
    public BitSet getMask(@Nonnull Thread thread) {
        return mask;
    }

}
