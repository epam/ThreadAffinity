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
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadFactory;

@ParametersAreNonnullByDefault
public class PinnedThreadFactory implements ThreadFactory {

    public static final long DEFAULT_STACK_SIZE = 0;

    protected AffinityLayout affinity;
    protected ThreadGroup group;
    protected String name;
    protected long stackSize;
    protected boolean daemon;

    public PinnedThreadFactory(String name, AffinityLayout affinity) {
        this(name, affinity, null);
    }

    public PinnedThreadFactory(String name, AffinityLayout affinity, @Nullable ThreadGroup group) {
        this(name, affinity, group, DEFAULT_STACK_SIZE);
    }

    public PinnedThreadFactory(String name, AffinityLayout affinity, @Nullable ThreadGroup group, long stackSize) {
        this(name, affinity, group, stackSize, false);
    }

    /**
     * @param name      thread name.
     * @param affinity  affinity layout.
     * @param group     thread group, null - parameter to be ignored.
     * @param stackSize stack size, 0 - parameter to be ignored.
     * @param daemon    daemon or not.
     */
    public PinnedThreadFactory(String name, AffinityLayout affinity, @Nullable ThreadGroup group, long stackSize, boolean daemon) {
        this.affinity = affinity;
        this.group = group;
        this.name = name;
        this.stackSize = stackSize;
        this.daemon = daemon;
    }

    public AffinityLayout getAffinity() {
        return affinity;
    }

    public void setAffinity(AffinityLayout affinity) {
        this.affinity = affinity;
    }

    @Nullable
    public ThreadGroup getGroup() {
        return group;
    }

    public void setGroup(@Nullable ThreadGroup group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStackSize() {
        return stackSize;
    }

    public void setStackSize(long stackSize) {
        this.stackSize = stackSize;
    }

    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    @Override
    @Nonnull
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, new PinnedRunnable(affinity, r), name, stackSize);
        thread.setDaemon(daemon);
        return thread;
    }

}
