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
