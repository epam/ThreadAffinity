package com.epam.deltix.thread.affinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Similar to Guava's ThreadFactoryBuilder but with CPU affinity support.
 *
 * @author Alexei Osipov
 */
@ParametersAreNonnullByDefault
public final class AffinityThreadFactoryBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(AffinityThreadFactoryBuilder.class);

    private String nameFormat = null;
    private Boolean daemon = null;
    private Integer priority = null;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = null;
    private ThreadFactory backingThreadFactory = null;
    private final AffinityConfig affinityConfig;

    /**
     * Creates a new {@link ThreadFactory} builder.
     */
    public AffinityThreadFactoryBuilder(@Nullable AffinityConfig affinityConfig) {
        this.affinityConfig = affinityConfig;
    }

    public AffinityThreadFactoryBuilder setNameFormat(String nameFormat) {
        String unused = format(nameFormat, 0); // fail fast if the format is bad or null
        this.nameFormat = nameFormat;
        return this;
    }

    public AffinityThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    public AffinityThreadFactoryBuilder setPriority(int priority) {
        // Thread#setPriority() already checks for validity. These error messages
        // are nicer though and will fail-fast.
        assert priority >= Thread.MIN_PRIORITY : String.format("Thread priority (%s) must be >= %s", priority, Thread.MIN_PRIORITY);
        assert priority <= Thread.MAX_PRIORITY : String.format("Thread priority (%s) must be <= %s", priority, Thread.MAX_PRIORITY);
        this.priority = priority;
        return this;
    }

    public AffinityThreadFactoryBuilder setUncaughtExceptionHandler(
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    public AffinityThreadFactoryBuilder setThreadFactory(
            ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = backingThreadFactory;
        return this;
    }

    @Nonnull
    public ThreadFactory build() {
        return build(this);
    }

    @Nullable
    private static AffinityLayout determineEffectiveAffinity(AffinityThreadFactoryBuilder builder) {
        // We can put any desired logic here.
        // As for now we just use provided AffinityLayout (if any).

        AffinityConfig affinityConfig = builder.affinityConfig;
        return affinityConfig == null ? null : affinityConfig.getAffinityLayout();
    }

    private static AffinityLayout getDefaultAffinity() {
        return null;
    }

    private static AffinityLayout getRealtimeThreadAffinity() {
        return null;
    }

    private static ThreadFactory build(AffinityThreadFactoryBuilder builder) {
        final String nameFormat = builder.nameFormat;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler =
                builder.uncaughtExceptionHandler;

        ThreadFactory baseThreadFactory = (builder.backingThreadFactory != null)
                ? builder.backingThreadFactory
                : Executors.defaultThreadFactory();

        final ThreadFactory backingThreadFactory;

        // Note: Here we choose one affinity level for all threads of this thread factory. However we can change it by using another wrapper (if needed).
        AffinityLayout affinity = determineEffectiveAffinity(builder);
        if (affinity == null) {
            backingThreadFactory = baseThreadFactory;
        } else {
            backingThreadFactory = new PinnedThreadFactoryWrapper(baseThreadFactory, affinity);
        }

        final AtomicLong count = (nameFormat != null) ? new AtomicLong(0) : null;
        return new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory.newThread(runnable);
                if (nameFormat != null) {
                    thread.setName(format(nameFormat, count.getAndIncrement()));
                }
                if (daemon != null) {
                    thread.setDaemon(daemon);
                }
                if (priority != null) {
                    thread.setPriority(priority);
                }
                if (uncaughtExceptionHandler != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler);
                }

                LOG.debug("Created thread with name: {}", thread.getName());
                return thread;
            }
        };
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}