package com.epam.deltix.thread.affinity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.BitSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ParametersAreNonnullByDefault
public class RegExAffinityLayout implements AffinityLayout {

    protected final Pattern pattern;
    protected final BitSet mask;

    public RegExAffinityLayout(@Nonnull String regexp, @Nonnull BitSet mask) {
        this.pattern = Pattern.compile(regexp);
        this.mask = mask;
    }

    @Override
    @Nonnull
    public BitSet getMask(@Nonnull final Thread thread) {
        final String name = thread.getName();
        final Matcher matcher = pattern.matcher(name);
        return matcher.matches() ? mask : new BitSet();
    }

}
