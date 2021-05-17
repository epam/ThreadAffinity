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

import javax.annotation.Nullable;

/**
 * Defines thread affinity configuration model.
 * <p>
 * As for now, this config supports only same AffinityLayout for all threads.
 * However it may contain more complex configuration like different affinity for different thread groups.
 *
 * @author Alexei Osipov
 */
public class AffinityConfig {
    private final AffinityLayout affinityLayout;

    /**
     * @param affinityLayout affinity layout to be used for all produced threads. {@code null} means no explicit affinity change.
     */
    public AffinityConfig(@Nullable AffinityLayout affinityLayout) {
        this.affinityLayout = affinityLayout;
    }

    @Nullable
    public AffinityLayout getAffinityLayout() {
        return affinityLayout;
    }
}
