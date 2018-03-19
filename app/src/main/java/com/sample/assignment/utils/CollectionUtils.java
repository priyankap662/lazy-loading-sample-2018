package com.sample.assignment.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

@SuppressWarnings("unused")
public class CollectionUtils {

    public static <T> T first(Collection<T> unfiltered, Predicate<? super T> predicate) {
        Collection<T> collection = filter(unfiltered, predicate);
        return collection.isEmpty() ? null : collection.iterator().next();
    }

    public static <T> Collection<T> filter(Collection<T> unfiltered, Predicate<? super T> predicate) {
        Collection<T> result = new ArrayList<>();
        if (predicate != null) {
            for (T element : emptyIfNull(unfiltered)) {
                if (predicate.apply(element)) {
                    result.add(element);
                }
            }
        }
        return result;
    }

    public static <T> void remove(Collection<T> unfiltered, Predicate<? super T> predicate) {
        if (unfiltered != null && predicate != null) {
            for (Iterator<T> iterator = unfiltered.iterator(); iterator.hasNext(); ) {
                if (predicate.apply(iterator.next())) {
                    iterator.remove();
                }
            }
        }
    }

    public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
        boolean satisfied = true;
        if (predicate != null) {
            for (T element : emptyIfNull(iterable)) {
                if (!predicate.apply(element)) {
                    satisfied = false;
                    break;
                }
            }
        }
        return satisfied;
    }

    public static <T> boolean contains(Iterable<T> iterable, Predicate<? super T> predicate) {
        boolean match = false;
        if (predicate != null) {
            for (T element : emptyIfNull(iterable)) {
                if (predicate.apply(element)) {
                    match = true;
                    break;
                }
            }
        }
        return match;
    }

    public interface Predicate<T> {
        boolean apply(T type);
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    private static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
        return iterable == null ? Collections.emptyList() : iterable;
    }
}
