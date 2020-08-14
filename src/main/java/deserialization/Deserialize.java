package deserialization;

import java.io.IOException;

public interface Deserialize {
    Object deserialize(byte[] data) throws IOException, ReflectiveOperationException;
}
