package unrefined.beans;

import unrefined.util.concurrent.ByteProducer;
import unrefined.util.concurrent.atomic.AtomicByte;
import unrefined.util.event.Event;
import unrefined.util.event.EventSlot;
import unrefined.util.function.ByteSlot;
import unrefined.util.signal.Signal;

import java.util.Objects;
import java.util.Properties;

public abstract class ByteProperty {

    public static ByteProperty bind(Properties properties, String key) {
        return new PropertiesBinding(properties, key);
    }

    public static ByteProperty bind(ByteProducer getter, ByteSlot setter) {
        return new FunctionBinding(getter, setter);
    }

    public static ByteProperty bind(ByteProducer getter) {
        return new FunctionBinding(getter);
    }

    public static ByteProperty ofAtomic(byte initialValue) {
        return new AtomicInstance(initialValue);
    }

    public static ByteProperty ofAtomic() {
        return new AtomicInstance();
    }

    public static ByteProperty of(byte initialValue) {
        return new Instance(initialValue);
    }

    public static ByteProperty of() {
        return new Instance();
    }

    private static class PropertiesBinding extends ByteProperty {
        private final Properties properties;
        private final String key;
        public PropertiesBinding(Properties properties, String key) {
            this.properties = Objects.requireNonNull(properties);
            this.key = Objects.requireNonNull(key);
        }
        @Override
        public boolean isReadOnly() {
            return false;
        }
        @Override
        public void set(byte value) {
            properties.setProperty(key, Byte.toString(value));
        }
        @Override
        public byte get() {
            return Byte.parseByte(properties.getProperty(key));
        }
    }

    private static class FunctionBinding extends ByteProperty {
        private final ByteProducer getter;
        private final ByteSlot setter;
        public FunctionBinding(ByteProducer getter) {
            this.getter = Objects.requireNonNull(getter);
            this.setter = null;
        }
        public FunctionBinding(ByteProducer getter, ByteSlot setter) {
            this.getter = Objects.requireNonNull(getter);
            this.setter = setter;
        }
        @Override
        public boolean isReadOnly() {
            return setter == null;
        }
        @Override
        public void set(byte value) {
            if (setter == null) throw new IllegalArgumentException("Property is read-only");
            else setter.accept(value);
        }
        @Override
        public byte get() {
            return getter.getAsByte();
        }
    }

    private static class AtomicInstance extends ByteProperty {
        private final AtomicByte value;
        public AtomicInstance(byte initialValue) {
            value = new AtomicByte(initialValue);
        }
        public AtomicInstance() {
            value = new AtomicByte();
        }
        @Override
        public boolean isReadOnly() {
            return false;
        }
        public void set(byte value) {
            byte previousValue = this.value.getAndSet(value);
            if (previousValue != value && !onChange().isEmpty()) onChange().emit(new ChangeEvent(this, previousValue, value));
        }
        public byte get() {
            return value.get();
        }
    }

    private static class Instance extends ByteProperty {
        private byte value;
        public Instance(byte initialValue) {
            value = initialValue;
        }
        public Instance() {
        }
        @Override
        public boolean isReadOnly() {
            return false;
        }
        public void set(byte value) {
            byte previousValue = this.value;
            this.value = value;
            if (previousValue != value && !onChange().isEmpty()) onChange().emit(new ChangeEvent(this, previousValue, value));
        }
        public byte get() {
            return value;
        }
    }

    private final Signal<EventSlot<ChangeEvent>> onChange = Signal.ofSlot();
    public Signal<EventSlot<ChangeEvent>> onChange() {
        return onChange;
    }

    public abstract void set(byte value);
    public abstract byte get();
    public abstract boolean isReadOnly();

    @Override
    public String toString() {
        return Byte.toString(get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ByteProperty that = (ByteProperty) o;

        return get() == that.get();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) get();
        return result;
    }

    public static final class ChangeEvent extends Event<ByteProperty> {

        private final byte previousValue, currentValue;

        public ChangeEvent(ByteProperty source, byte previousValue, byte currentValue) {
            super(source);
            this.previousValue = previousValue;
            this.currentValue = currentValue;
        }

        public byte getPreviousValue() {
            return previousValue;
        }

        public byte getCurrentValue() {
            return currentValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            ChangeEvent that = (ChangeEvent) o;

            if (previousValue != that.previousValue) return false;
            return currentValue == that.currentValue;
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + (int) previousValue;
            result = 31 * result + (int) currentValue;
            return result;
        }

        @Override
        public String toString() {
            return getClass().getName()
                    + '{' +
                    "previousValue=" + previousValue +
                    ", currentValue=" + currentValue +
                    '}';
        }

    }

}
