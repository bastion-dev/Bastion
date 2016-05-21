package org.kpull.bastion.core;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:mail@kylepullicino.com">Kyle</a>
 */
public class ApiEnvironment implements Map<String, String> {

    private Map<String, String> delegate;

    public ApiEnvironment() {
        this.delegate = new ConcurrentSkipListMap<>();
    }

    public ApiEnvironment(Map<String, String> delegate) {
        Objects.requireNonNull(delegate);
        this.delegate = new ConcurrentSkipListMap<>(delegate);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return delegate.get(key);
    }

    @Override
    public String put(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        return delegate.put(key, value);
    }

    public String putObject(String key, Object value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        return delegate.put(key, value.toString());
    }

    @Override
    public String remove(Object key) {
        return delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        delegate.putAll(m);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Set<String> keySet() {
        return delegate.keySet();
    }

    @Override
    public Collection<String> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return delegate.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return delegate.equals(o);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public String getOrDefault(Object key, String defaultValue) {
        return delegate.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super String> action) {
        delegate.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super String, ? super String, ? extends String> function) {
        delegate.replaceAll(function);
    }

    @Override
    public String putIfAbsent(String key, String value) {
        return delegate.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return delegate.remove(key, value);
    }

    @Override
    public boolean replace(String key, String oldValue, String newValue) {
        return delegate.replace(key, oldValue, newValue);
    }

    @Override
    public String replace(String key, String value) {
        return delegate.replace(key, value);
    }

    @Override
    public String computeIfAbsent(String key, Function<? super String, ? extends String> mappingFunction) {
        return delegate.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public String computeIfPresent(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return delegate.computeIfPresent(key, remappingFunction);
    }

    @Override
    public String compute(String key, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return delegate.compute(key, remappingFunction);
    }

    @Override
    public String merge(String key, String value, BiFunction<? super String, ? super String, ? extends String> remappingFunction) {
        return delegate.merge(key, value, remappingFunction);
    }

    public String process(String source) {
        // TODO: Improve the readability of this method (such as make the pattern a constant).
        String result = source;
        Pattern variablePattern = Pattern.compile("\\{\\{([a-zA-Z0-9_]*?)\\}\\}");
        Matcher matcher = variablePattern.matcher(result);
        while (matcher.find()) {
            String variableName = matcher.group(1);
            String variableValue = delegate.getOrDefault(variableName, "");
            result = matcher.replaceFirst(variableValue);      // TODO: Fix variableValues containing dollar signs ($) and backslashes
            matcher = variablePattern.matcher(result);
        }
        return result;
    }
}
