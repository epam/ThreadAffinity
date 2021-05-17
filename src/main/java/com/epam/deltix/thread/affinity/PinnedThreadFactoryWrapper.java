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
import java.util.concurrent.ThreadFactory;

/**
 * Sets same affinity for all produced threads.
 *
 * @author Alexei Osipov
 */
@ParametersAreNonnullByDefault
public class PinnedThreadFactoryWrapper implements ThreadFactory {
    private final ThreadFactory backingThreadFactory;
    private final AffinityLayout affinity;

    public PinnedThreadFactoryWrapper(ThreadFactory backingThreadFactory, AffinityLayout affinity) {
        this.backingThreadFactory = backingThreadFactory;
        this.affinity = affinity;
    }

    @Nonnull
    @Override
    public Thread newThread(Runnable r) {
        return backingThreadFactory.newThread(new PinnedRunnable(affinity, r));
    }
}
