package ca.magex.json.model;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

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
	
	@Test
	public void testWithClause() throws Exception {
		JsonObject data = new JsonObject()
			.with("name", "a")
			.with("type", 3)
			.with("active", true)
			.with("list", new JsonArray().with("a", "b", "c"));
		assertEquals(4, data.pairs().size());
		assertEquals(List.of("name", "type", "active", "list"),
			data.stream().map(p -> p.key()).collect(Collectors.toList()));
		assertEquals(List.of("name", "type", "active", "list"), data.keys());
		assertEquals(new JsonText("a"), data.values().get(0));
		assertEquals(new JsonNumber(3), data.values().get(0));
		assertEquals(new JsonBoolean(true), data.values().get(0));
		assertEquals(new JsonArray().with("a", "b", "c"), data.values().get(0));
	}
	
}
