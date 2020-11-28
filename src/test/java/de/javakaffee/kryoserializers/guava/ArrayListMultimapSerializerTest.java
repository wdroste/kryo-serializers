package de.javakaffee.kryoserializers.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import com.esotericsoftware.kryo.Kryo;
import de.javakaffee.kryoserializers.KryoTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotSame;

public class ArrayListMultimapSerializerTest extends MultimapSerializerTestBase {

  private Kryo _kryo;

  @BeforeClass
  public void initializeKyroWithSerializer() {
    _kryo = new Kryo();
    _kryo.setRegistrationRequired(false);
    ArrayListMultimapSerializer.registerSerializers(_kryo);
  }

  @Test(dataProvider = "Google Guava multimaps")
  public void testMultimap(Object[] contents) {
    final ArrayListMultimap<Object, Object> multimap = ArrayListMultimap.create();
    populateMultimap(multimap, contents);
    final byte[] serialized = KryoTest.serialize(_kryo, multimap);
    final ArrayListMultimap<Object, Object> deserialized =
        KryoTest.deserialize(_kryo, serialized, ArrayListMultimap.class);
    assertEqualMultimaps(false, true, deserialized, multimap);
  }

  @Test(dataProvider = "Google Guava multimaps")
  public void testMultimapCopy(Object[] contents) {
    final ListMultimap<Comparable, Comparable> multimap = ArrayListMultimap.create();
    populateMultimap(multimap, contents);

    ListMultimap<Comparable, Comparable> copy = _kryo.copy(multimap);

    assertNotSame(copy, multimap);
    assertEqualMultimaps(false, true, copy, multimap);
  }
}
