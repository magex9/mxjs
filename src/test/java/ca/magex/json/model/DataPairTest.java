package ca.magex.json.model;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import ca.magex.json.model.DataPair;

public class DataPairTest {

	@Test
	public void testDataElementKeyAndValueNull() throws Exception {
		try {
			DataPair.class.getConstructor(new Class[] { String.class, DataElement.class })
				.newInstance(new Object[] { null, null });
			fail("Key cannot be null");
		} catch (InvocationTargetException e) {
			assertEquals(IllegalArgumentException.class, e.getTargetException().getClass());
			assertEquals("Key cannot be null", e.getTargetException().getMessage());
		}
	}
	
	@Test
	public void testDataElementKeyNull() throws Exception {
		try {
			DataPair.class.getConstructor(new Class[] { String.class, DataElement.class })
				.newInstance(new Object[] { null, new DataText("value") });
			fail("Key cannot be null");
		} catch (InvocationTargetException e) {
			assertEquals(IllegalArgumentException.class, e.getTargetException().getClass());
			assertEquals("Key cannot be null", e.getTargetException().getMessage());
		}
	}
	
	@Test
	public void testDataElementValueNull() throws Exception {
		DataPair pair = DataPair.class.getConstructor(new Class[] { String.class, DataElement.class })
			.newInstance(new Object[] { "key", null });
		assertEquals("b0eafee3eafce16dad331ec1785a95d9", pair.mid());
		assertEquals("key", pair.key());
		assertEquals(DataElement.UNDEFINED, pair.value());
	}
	
	@Test
	public void testDataPairText() throws Exception {
		DataPair pair = new DataPair("key", "value");
		assertEquals("a02d0ba9e804a9125267d76ff9234bdc", pair.mid());
		assertEquals("key", pair.key());
		assertEquals(DataText.class, pair.value().getClass());
		assertEquals("value", ((DataText)pair.value()).value());
	}
	
	@Test
	public void testDataPairNumber() throws Exception {
		DataPair pair = new DataPair("key", 10);
		assertEquals("f749de0df8ac53e2a9365097c52ebe73", pair.mid());
		assertEquals("key", pair.key());
		assertEquals(DataNumber.class, pair.value().getClass());
		assertEquals(10, ((DataNumber)pair.value()).value());
	}
	
	@Test
	public void testDataPairBooleanTrue() throws Exception {
		DataPair pair = new DataPair("key", true);
		assertEquals("4040c80b204df9c80406797bd1ffa841", pair.mid());
		assertEquals("key", pair.key());
		assertEquals(DataBoolean.class, pair.value().getClass());
		assertEquals(true, ((DataBoolean)pair.value()).value());
	}
	
	@Test
	public void testDataPairBooleanFalse() throws Exception {
		DataPair pair = new DataPair("key", false);
		assertEquals("96fd663b14e0e4a6e49374c6b39e0b34", pair.mid());
		assertEquals("key", pair.key());
		assertEquals(DataBoolean.class, pair.value().getClass());
		assertEquals(false, ((DataBoolean)pair.value()).value());
	}
	
}
