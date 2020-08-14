package util;

import java.util.function.Consumer;

public abstract class SerDeserUtil {

    protected static final byte[] COMMON_SEPARATOR = new byte[]{-2};
    protected static final byte[] FIELD_SEPARATOR = new byte[]{-1};
    protected static final byte[] SPECIAL_SEPARATOR = new byte[]{-9};

    protected static final Consumer<byte[]> EMPTY_BYTES_CONSUMER = bytes -> {};
    protected static final Consumer<Object> EMPTY_OBJECT_CONSUMER = object -> {};
    protected static final Consumer<Exception> EMPTY_EXCEPTION_CONSUMER = e -> {};
}
