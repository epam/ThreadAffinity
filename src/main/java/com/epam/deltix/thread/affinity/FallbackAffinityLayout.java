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


public class FallbackAffinityLayout implements AffinityLayout {

    protected final AffinityLayout layout;
    protected final BitSet fallback;

    public FallbackAffinityLayout(@Nonnull final AffinityLayout layout, @Nonnull final BitSet fallback) {
        this.layout = layout;
        this.fallback = fallback;
    }

    @Nonnull
    @Override
    public BitSet getMask(@Nonnull final Thread thread) {
        final BitSet mask = layout.getMask(thread);
        return mask.isEmpty() ? fallback : mask;
    }

}
