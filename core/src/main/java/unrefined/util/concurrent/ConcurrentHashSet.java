package unrefined.util.concurrent;

import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Serializable {

    private static final long serialVersionUID = 1484496066646560523L;

    private static final Boolean PRESENT = true;

    private final ConcurrentHashMap<E, Object> backMap;

    public ConcurrentHashSet() {
        backMap = new ConcurrentHashMap<>();
    }

    public ConcurrentHashSet(int initialCapacity) {
        backMap = new ConcurrentHashMap<>(initialCapacity);
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor) {
        backMap = new ConcurrentHashMap<>(initialCapacity, loadFactor);
    }

    public ConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
        backMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    public ConcurrentHashSet(Collection<E> c) {
        backMap = new ConcurrentHashMap<>(Math.max(c.size(), 12));
        addAll(c);
    }

    @Override
    public int size() {
        return backMap.size();
    }

    @Override
    public boolean isEmpty() {
        return backMap.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return backMap.keySet().iterator();
    }

    @Override
    public boolean add(E o) {
        return backMap.put(o, PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return PRESENT.equals(backMap.remove(o));
    }

    @Override
    public boolean contains(Object o) {
        return backMap.containsKey(o);
    }

    @Override
    public void clear() {
        backMap.clear();
    }

    @Override
    public Object[] toArray() {
        return backMap.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return backMap.keySet().toArray(a);
    }

}
