package deserialization;

import util.SerDeserUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static util.ArrayUtil.*;

public class DeserializeImpl extends SerDeserUtil implements deserialization.Deserialize {

    @Override
    public Object deserialize(byte[] data) throws ReflectiveOperationException {

        int i = 0;
        boolean composite = false;

        List<Byte> buffer = new ArrayList<>();
        Field buffField = null;


        while (data[i] != COMMON_SEPARATOR[0]) {
            buffer.add(data[i]);
            i++;
        }

        String className = byteArrParseToString(buffer);

        buffer.clear();

        Class<?> type = Class.forName(className);

        Object obj = type.newInstance();

        for(; i < data.length; i++){
            if(data[i] != SPECIAL_SEPARATOR[0] && !composite) {
                if((data[i] != FIELD_SEPARATOR[0]) && (data[i] != COMMON_SEPARATOR[0])) buffer.add(data[i]);
                else {
                    if(data[i] == FIELD_SEPARATOR[0]) {
                        buffField  = obj.getClass().getDeclaredField(byteArrParseToString(buffer));
                        buffer.clear();
                        buffField.setAccessible(true);
                    }
                    if((data[i] == COMMON_SEPARATOR[0]) && (buffField != null)) {
                        if(buffer.size() == 0) {
                            buffField.set(obj, null);
                            buffField = null;
                        } else {
                            String value = byteArrParseToString(buffer);
                            buffField.set(obj, dynamicParse(buffField.getType(), value));
                            buffField = null;
                            buffer.clear();
                        }
                    }
                }
            }
            else {
                if(data[i] == SPECIAL_SEPARATOR[0] && !composite) composite = true;
                else {
                    if(data[i] != SPECIAL_SEPARATOR[0] && composite) buffer.add(data[i]);
                    else if(data[i] == SPECIAL_SEPARATOR[0] && composite && data[i+1] == COMMON_SEPARATOR[0]) {

                        if(buffer.size() == 0) {
                            composite = false;
                            assert buffField != null;
                            buffField.set(obj, null);
                        }
                        else {
                            byte[] compositeObject = listToByteArray(buffer);
                            composite = false;
                            assert buffField != null;
                            buffField.set(obj, deserialize(compositeObject));
                            buffer.clear();
                        }
                        buffField = null;
                        i += 1;
                    }
                }
            }
        }
        return obj;
    }

    public Optional<Object> bytesToObj(byte[] bytes) {
        return bytesToObj(bytes, EMPTY_EXCEPTION_CONSUMER);
    }

    public Optional<Object> bytesToObj(byte[] bytes, Consumer<? super Exception> exceptionConsumer) {
        return bytesToObj(Object.class, bytes, exceptionConsumer, EMPTY_OBJECT_CONSUMER);
    }

    public <T> Optional<T> bytesToObj(Class<T> objectClass, byte[] bytes, Consumer<? super Exception> exceptionConsumer, Consumer<? super T> objectConsumer) {
        try (final ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
             final ObjectInputStream ois = new ObjectInputStream(bis)) {
            final T object = objectClass.cast(ois.readObject());
            objectConsumer.accept(object);
            return Optional.of(object);
        }
        catch (IOException | ClassNotFoundException e) {
            exceptionConsumer.accept(e);
            return Optional.empty();
        }
    }
}
