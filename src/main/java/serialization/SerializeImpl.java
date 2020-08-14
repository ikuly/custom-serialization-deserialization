package serialization;

import entity.MySerialize;
import util.SerDeserUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.function.Consumer;

import static util.ArrayUtil.concatByteArrays;

public class SerializeImpl extends SerDeserUtil implements Serialize {


    @Override
    public byte[] serialize(Object anyBean) throws IllegalAccessException {

        if (anyBean == null) throw new IllegalAccessException();

        byte[] result = new byte[0];

        Class<?> type = anyBean.getClass();

        result = concatByteArrays(result,
                type.getTypeName().getBytes(StandardCharsets.UTF_8),
                COMMON_SEPARATOR);

        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            if(field.get(anyBean) == null) {
                result = concatByteArrays(result,
                        field.getName().getBytes(StandardCharsets.UTF_8),
                        FIELD_SEPARATOR,
                        COMMON_SEPARATOR);
            } else if (field.get(anyBean) instanceof MySerialize) result = concatByteArrays(result,
                    field.getName().getBytes(java.nio.charset.StandardCharsets.UTF_8),
                    FIELD_SEPARATOR,
                    SPECIAL_SEPARATOR,
                    serialize(field.get(anyBean)),
                    SPECIAL_SEPARATOR,
                    COMMON_SEPARATOR);
            else {
                result = concatByteArrays(result,
                        field.getName().getBytes(StandardCharsets.UTF_8),
                        FIELD_SEPARATOR,
                        field.get(anyBean).toString().getBytes(StandardCharsets.UTF_8),
                        COMMON_SEPARATOR);
            }
        }

        return result;
    }


    public Optional<byte[]> objectToByte(Object object) {
        return objectToByte(object, EMPTY_EXCEPTION_CONSUMER, EMPTY_BYTES_CONSUMER);
    }

    public Optional<byte[]> objectToByte(Object object, Consumer<? super IOException> exceptionConsumer, Consumer<byte[]> bytesConsumer) {
        try (final ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(object) ;
                oos.flush();
            }

            final byte[] bytes = bos.toByteArray();
            bytesConsumer.accept(bytes);
            return Optional.of(bytes);
        }
        catch (IOException e) {
            exceptionConsumer.accept(e);
            return Optional.empty();
        }
    }
}
