package deserialization;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class DeserializeImplTest {

    Deserialize deserialize = new DeserializeImpl();

    @Test
    public void deserializeComplexCompoundObject() throws java.io.IOException, ReflectiveOperationException {

        byte [] bytes = {
                101, 110, 116, 105, 116, 121, 46, 66, 111, 111, 107, -2,
                110, 97, 109, 101, -1, 72, 101, 108, 108, 115, 32, 65, 110,
                103, 101, 108, 115, -2, 112, 97, 103, 101, 115, -1, 50, 51,
                53, -2, 97, 117, 116, 104, 111, 114, -1, -9, 101, 110, 116,
                105, 116, 121, 46, 65, 117, 116, 104, 111, 114, -2, 110, 97,
                109, 101, -1, 72, 117, 110, 116, 101, 114, 32, 83, 46, 32, 84,
                104, 111, 109, 112, 115, 111, 110, -2, 110, 117, 109, 98, 101,
                114, -1, 51, 52, 51, -2, 108, 105, 118, 101, -1, 102, 97, 108,
                115, 101, -2, -9, -2
        };

        entity.Book book = (entity.Book) deserialize.deserialize(bytes);

        System.out.println(book.toString());

        assertEquals("Hells Angels", book.getName());
        assertEquals(235, book.getPages());
        assertEquals(343, book.getAuthor().getNumber());
        assertEquals("Hunter S. Thompson", book.getAuthor().getName());
        assertFalse(book.getAuthor().isLive());
    }

    @Test
    public void deserializeTreeMap(){
        Map<Integer, String > map = new java.util.TreeMap<>();
        map.put(123, "odin");
        map.put(-444, "duo");
        map.put(909, "tares");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105,
                108, 46, 84, 114, 101, 101, 77, 97, 112, 12, -63, -10, 62, 45, 37,
                106, -26, 3, 0, 1, 76, 0, 10, 99, 111, 109, 112, 97, 114, 97, 116,
                111, 114, 116, 0, 22, 76, 106, 97, 118, 97, 47, 117, 116, 105, 108,
                47, 67, 111, 109, 112, 97, 114, 97, 116, 111, 114, 59, 120, 112,
                112, 119, 4, 0, 0, 0, 3, 115, 114, 0, 17, 106, 97, 118, 97, 46, 108,
                97, 110, 103, 46, 73, 110, 116, 101, 103, 101, 114, 18, -30, -96,
                -92, -9, -127, -121, 56, 2, 0, 1, 73, 0, 5, 118, 97, 108, 117, 101,
                120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 78,
                117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2,
                0, 0, 120, 112, -1, -1, -2, 68, 116, 0, 3, 100, 117, 111, 115, 113,
                0, 126, 0, 3, 0, 0, 0, 123, 116, 0, 4, 111, 100, 105, 110, 115, 113,
                0, 126, 0, 3, 0, 0, 3, -115, 116, 0, 5, 116, 97, 114, 101, 115, 120
        };

        TreeMap<Integer, String> actual = (TreeMap<Integer, String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(map, actual);
        assertEquals(map.size(), actual.size());
        assertArrayEquals(map.keySet().toArray(), actual.keySet().toArray());
        assertArrayEquals(map.entrySet().toArray(), actual.entrySet().toArray());
        assertEquals(map.get(123), "odin");
        assertEquals(map.get(-444), "duo");
        assertEquals(map.get(909), "tares");
    }

    @Test
    public void deserializeHashMap(){
        Map<Integer, String > map = new HashMap<>();
        map.put(123, "odin");
        map.put(-444, "duo");
        map.put(909, "tares");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46,
                72, 97, 115, 104, 77, 97, 112, 5, 7, -38, -63, -61, 22, 96, -47, 3, 0, 2, 70,
                0, 10, 108, 111, 97, 100, 70, 97, 99, 116, 111, 114, 73, 0, 9, 116, 104, 114,
                101, 115, 104, 111, 108, 100, 120, 112, 63, 64, 0, 0, 0, 0, 0, 12, 119, 8, 0,
                0, 0, 16, 0, 0, 0, 3, 115, 114, 0, 17, 106, 97, 118, 97, 46, 108, 97, 110, 103,
                46, 73, 110, 116, 101, 103, 101, 114, 18, -30, -96, -92, -9, -127, -121, 56,
                2, 0, 1, 73, 0, 5, 118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97,
                46, 108, 97, 110, 103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11,
                -108, -32, -117, 2, 0, 0, 120, 112, 0, 0, 0, 123, 116, 0, 4, 111, 100, 105, 110,
                115, 113, 0, 126, 0, 2, -1, -1, -2, 68, 116, 0, 3, 100, 117, 111, 115, 113, 0,
                126, 0, 2, 0, 0, 3, -115, 116, 0, 5, 116, 97, 114, 101, 115, 120
        };

        HashMap<Integer, String> actual = (HashMap<Integer, String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(map, actual);
        assertEquals(map.size(), actual.size());
        assertArrayEquals(map.keySet().toArray(), actual.keySet().toArray());
        assertArrayEquals(map.entrySet().toArray(), actual.entrySet().toArray());
        assertEquals(map.get(123), "odin");
        assertEquals(map.get(-444), "duo");
        assertEquals(map.get(909), "tares");
    }

    @Test
    public void deserializeTreeSet(){
        Set<String > set = new java.util.TreeSet<>();
        set.add("one");
        set.add("two");
        set.add("tri");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105, 108,
                46, 84, 114, 101, 101, 83, 101, 116, -35, -104, 80, -109, -107, -19, -121,
                91, 3, 0, 0, 120, 112, 112, 119, 4, 0, 0, 0, 3, 116, 0, 3, 111, 110, 101,
                116, 0, 3, 116, 114, 105, 116, 0, 3, 116, 119, 111, 120
        };

        TreeSet<String> actual = (TreeSet<String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(set, actual);
        assertEquals(set.size(), actual.size());
        assertArrayEquals(set.toArray(), actual.toArray());
        assertTrue(actual.contains("one"));
        assertTrue(actual.contains("two"));
        assertTrue(actual.contains("tri"));
    }

    @Test
    public void deserializeHashSet(){
        Set<String > set = new HashSet<>();
        set.add("one");
        set.add("two");
        set.add("tri");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46,
                72, 97, 115, 104, 83, 101, 116, -70, 68, -123, -107, -106, -72, -73, 52, 3, 0,
                0, 120, 112, 119, 12, 0, 0, 0, 16, 63, 64, 0, 0, 0, 0, 0, 3, 116, 0, 3, 111,
                110, 101, 116, 0, 3, 116, 114, 105, 116, 0, 3, 116, 119, 111, 120
        };

        HashSet<String> actual = (HashSet<String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(set, actual);
        assertEquals(set.size(), actual.size());
        assertTrue(actual.contains("one"));
        assertTrue(actual.contains("two"));
        assertTrue(actual.contains("tri"));
    }

    @Test
    public void deserializeLinkedList(){
        List<String > list = new LinkedList<>();
        list.add("one");
        list.add("two");
        list.add("tri");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 20, 106, 97, 118, 97, 46, 117, 116, 105,
                108, 46, 76, 105, 110, 107, 101, 100, 76, 105, 115, 116, 12, 41, 83,
                93, 74, 96, -120, 34, 3, 0, 0, 120, 112, 119, 4, 0, 0, 0, 3, 116, 0,
                3, 111, 110, 101, 116, 0, 3, 116, 119, 111, 116, 0, 3, 116, 114, 105, 120
        };

        LinkedList<String> actual = (LinkedList<String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(list, actual);
        assertEquals(list.size(), actual.size());
        assertTrue(actual.contains("one"));
        assertTrue(actual.contains("two"));
        assertTrue(actual.contains("tri"));
        assertEquals(actual.get(0), "one");
        assertEquals(actual.get(1), "two");
        assertEquals(actual.get(2), "tri");
    }

    @Test
    public void deserializeArrayList(){
        List<String > list = new java.util.ArrayList();
        list.add("one");
        list.add("two");
        list.add("tri");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 19, 106, 97, 118, 97, 46, 117, 116, 105, 108, 46, 65,
                114, 114, 97, 121, 76, 105, 115, 116, 120, -127, -46, 29, -103, -57, 97, -99, 3,
                0, 1, 73, 0, 4, 115, 105, 122, 101, 120, 112, 0, 0, 0, 3, 119, 4, 0, 0, 0, 3, 116,
                0, 3, 111, 110, 101, 116, 0, 3, 116, 119, 111, 116, 0, 3, 116, 114, 105, 120
        };

        ArrayList<String> actual = (ArrayList<String>) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(list, actual);
        assertEquals(list.size(), actual.size());
        assertTrue(actual.contains("one"));
        assertTrue(actual.contains("two"));
        assertTrue(actual.contains("tri"));
        assertEquals(actual.get(0), "one");
        assertEquals(actual.get(1), "two");
        assertEquals(actual.get(2), "tri");
    }

    @Test
    public void deserializeString(){
        byte [] bytes = {
                -84, -19, 0, 5, 116, 0, 13, 72, 101, 108, 108, 111, 44, 32, 87, 111, 114, 108, 100, 33
        };

        String string = String.valueOf(new DeserializeImpl().bytesToObj(bytes).orElse(null));

        assertEquals("Hello, World!", string);
    }

    @Test
    public void deserializeStringBuilder(){
        StringBuilder exp = new StringBuilder("Hello! It's me, String!");

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 23, 106, 97, 118, 97, 46, 108, 97, 110, 103,
                46, 83, 116, 114, 105, 110, 103, 66, 117, 105, 108, 100, 101, 114, 60,
                -43, -5, 20, 90, 76, 106, -53, 3, 0, 0, 120, 112, 119, 4, 0, 0, 0, 23,
                117, 114, 0, 2, 91, 67, -80, 38, 102, -80, -30, 93, -124, -84, 2, 0, 0,
                120, 112, 0, 0, 0, 46, 0, 72, 0, 101, 0, 108, 0, 108, 0, 111, 0, 33, 0,
                32, 0, 73, 0, 116, 0, 39, 0, 115, 0, 32, 0, 109, 0, 101, 0, 44, 0, 32, 0,
                83, 0, 116, 0, 114, 0, 105, 0, 110, 0, 103, 0, 33, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 120
        };

        StringBuilder string = (StringBuilder) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(exp.toString(), string.toString());
        assertNotNull(string.toString());
    }

    @Test
    public void deserializeInt(){
        int exp = 100;

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 17, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 73,
                110, 116, 101, 103, 101, 114, 18, -30, -96, -92, -9, -127, -121, 56, 2, 0, 1, 73,
                0, 5, 118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110,
                103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2,
                0, 0, 120, 112, 0, 0, 0, 100
        };

        int actual = (int) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(exp, actual);
    }

    @Test
    public void deserializeDouble(){
        double exp = 100.78d;

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 68,
                111, 117, 98, 108, 101, -128, -77, -62, 74, 41, 107, -5, 4, 2, 0, 1, 68, 0, 5, 118,
                97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 78,
                117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2, 0, 0, 120, 112,
                64, 89, 49, -21, -123, 30, -72, 82
        };

        double actual = (double) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(exp, actual, 0);
    }

    @Test
    public void deserializeFloat(){
        float exp = 100.78f;

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 15, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 70,
                108, 111, 97, 116, -38, -19, -55, -94, -37, 60, -16, -20, 2, 0, 1, 70, 0, 5, 118,
                97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46,
                78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117, 2, 0, 0,
                120, 112, 66, -55, -113, 92
        };

        float actual = (float) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(exp, actual, 0);
    }

    @Test
    public void serializeLong(){
        long exp = 100L;

        byte [] bytes = {
                -84, -19, 0, 5, 115, 114, 0, 14, 106, 97, 118, 97, 46, 108, 97, 110, 103, 46,
                76, 111, 110, 103, 59, -117, -28, -112, -52, -113, 35, -33, 2, 0, 1, 74, 0, 5,
                118, 97, 108, 117, 101, 120, 114, 0, 16, 106, 97, 118, 97, 46, 108, 97, 110,
                103, 46, 78, 117, 109, 98, 101, 114, -122, -84, -107, 29, 11, -108, -32, -117,
                2, 0, 0, 120, 112, 0, 0, 0, 0, 0, 0, 0, 100
        };

        long actual = (long) new DeserializeImpl().bytesToObj(bytes).orElse(null);

        assertEquals(exp, actual, 0);
    }
}
