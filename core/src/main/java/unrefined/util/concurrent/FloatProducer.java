package unrefined.util.concurrent;

import java.util.concurrent.Callable;

@FunctionalInterface
public interface FloatProducer extends Callable<Float> {

    @Override
    default Float call() throws Exception {
        return getAsFloat();
    }

    default Float get() {
        return getAsFloat();
    }

    float getAsFloat();

}
