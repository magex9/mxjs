package ca.magex.json.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.magex.json.model.DataFormatter;
import ca.magex.json.model.DataObject;

public class DataObjectTest {

	@Test
	public void testEmptyArray() throws Exception {
		DataObject data = new DataObject();
		assertEquals("{}", data.toString());
		assertEquals(0, data.size());
	}
	
	@Test
	public void testStringConstructor() throws Exception {
		DataObject data = new DataObject("{\"type\":1,\"test\":true,\"name\":\"test\"}");
		assertEquals("{\"type\":1,\"test\":true,\"name\":\"test\"}", DataFormatter.compact(data));
		assertEquals("{\n" + 
			"  \"type\": 1,\n" + 
			"  \"test\": true,\n" + 
			"  \"name\": \"test\"\n" + 
			"}", data.toString());
		assertEquals(3, data.size());
	}
	
}
