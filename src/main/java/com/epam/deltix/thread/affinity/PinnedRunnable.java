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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.BitSet;


@ParametersAreNonnullByDefault
public class PinnedRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Affinity.class);

    protected final AffinityLayout affinity;
    protected final Runnable target;

    public PinnedRunnable(AffinityLayout affinity, Runnable target) {
        this.affinity = affinity;
        this.target = target;
    }

    @Override
    public void run() {
        final Thread thread = Thread.currentThread();
        final BitSet mask = affinity.getMask(thread);

        if (mask.isEmpty()) {
            logWarning(thread);
        } else {
            Affinity.setAffinity(mask);
        }

        target.run();
    }

    private static void logWarning(final Thread thread) {
        if (LOG.isWarnEnabled()) {
            LOG.warn("{}: affinity is not specified. The current affinity: {})", thread, Affinity.getAffinity());
        }
    }

}
