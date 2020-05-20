package ca.magex.json.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JsonObjectTest {

	@Test
	public void testEmptyArray() throws Exception {
		JsonObject data = new JsonObject();
		assertEquals("{}", data.toString());
		assertEquals(0, data.size());
	}
	
	@Test
	public void testStringConstructor() throws Exception {
		JsonObject data = new JsonObject("{\"type\":1,\"test\":true,\"name\":\"test\"}");
		assertEquals("{\"type\":1,\"test\":true,\"name\":\"test\"}", JsonFormatter.compact(data));
		assertEquals("{\n" + 
			"  \"type\": 1,\n" + 
			"  \"test\": true,\n" + 
			"  \"name\": \"test\"\n" + 
			"}", data.toString());
		assertEquals(3, data.size());
	}
	
}
