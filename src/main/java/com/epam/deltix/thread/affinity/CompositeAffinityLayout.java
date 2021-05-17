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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.BitSet;

@ParametersAreNonnullByDefault
public class CompositeAffinityLayout implements AffinityLayout {

    protected final ArrayList<AffinityLayout> layouts = new ArrayList<>();

    public void addLayout(@Nonnull AffinityLayout layout) {
        layouts.add(layout);
    }

    public void removeLayout(@Nonnull AffinityLayout layout) {
        layouts.remove(layout);
    }

    @Override
    @Nonnull
    public BitSet getMask(@Nonnull Thread thread) {
        BitSet result = new BitSet();
        for (AffinityLayout layout : layouts) {
            BitSet mask = layout.getMask(thread);
            result.or(mask);
        }

        return result;
    }

}
