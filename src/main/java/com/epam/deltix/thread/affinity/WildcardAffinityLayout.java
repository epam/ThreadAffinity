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
import java.util.ArrayList;
import java.util.BitSet;
import java.util.function.Predicate;


public class WildcardAffinityLayout implements AffinityLayout {

    protected final String pattern;
    protected final BitSet mask;
    protected final Predicate<String> matcher;

    public WildcardAffinityLayout(@Nonnull final String pattern, @Nonnull final BitSet mask) {
        this.pattern = pattern;
        this.mask = mask;
        this.matcher = new WildcardMatcher(pattern);
    }

    @Nonnull
    @Override
    public BitSet getMask(@Nonnull final Thread thread) {
        final String name = thread.getName();
        return matcher.test(name) ? mask : new BitSet();
    }

    protected static class WildcardMatcher implements Predicate<String> {

        protected final String[] tokens;

        public WildcardMatcher(@Nonnull final String pattern) {
            this.tokens = parseTokens(normalize(pattern));
        }

        @Override
        public boolean test(final String name) {
            return match(name, this.tokens);
        }

        protected static String[] parseTokens(final String pattern) {
            final ArrayList<String> tokens = new ArrayList<>(4);

            final int end = pattern.length();
            int start = 0;

            if (pattern.charAt(0) == '*') {
                tokens.add("*");
                start = 1;
            }

            while (start < end) {
                final int index = pattern.indexOf('*', start);
                if (index < 0) {
                    break;
                }

                final String token = pattern.substring(start, index);
                tokens.add(token);
                tokens.add("*");

                start = index + 1;
            }

            if (start < end) {
                final String token = pattern.substring(start, end);
                tokens.add(token);
            }

            return tokens.toArray(new String[tokens.size()]);
        }

        protected static String normalize(final String pattern) {
            if (pattern.isEmpty()) {
                throw new IllegalArgumentException("Empty pattern");
            }

            if (!pattern.contains("**")) {
                return pattern;
            }

            final StringBuilder normalized = new StringBuilder(pattern.length());
            char last = '0';

            for (int i = 0; i < pattern.length(); i++) {
                final char c = pattern.charAt(i);
                if (c != '*' || last != '*') {
                    normalized.append(c);
                }

                last = c;
            }

            return normalized.toString();
        }

        protected static boolean match(final String name, final String[] tokens) {
            final int end = name.length();

            int start = 0;
            boolean wildcard = false;

            for (String token : tokens) {
                if (token.equals("*")) {
                    wildcard = true;
                    continue;
                }

                final int index = name.indexOf(token, start);
                if ((index < 0) || (index > start && !wildcard)) {
                    return false;
                }

                start = index + token.length();
                wildcard = false;
            }

            return wildcard || start == end;
        }

    }

}
