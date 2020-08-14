package serialization;

import java.io.IOException;

public interface Serialize {
    byte[] serialize(Object anyBean) throws IOException, IllegalAccessException;
}
