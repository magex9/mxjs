package ca.magex.json.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.magex.json.model.JsonArray;
import ca.magex.json.model.JsonElement;
import ca.magex.json.model.JsonFormatter;
import ca.magex.json.model.JsonParser;
import ca.magex.json.model.JsonText;

public class JsonArrayTest {

	@Test
	public void testEmptyArray() throws Exception {
		JsonArray array = new JsonArray();
		assertEquals("[]", array.toString());
		assertEquals(0, array.size());
	}
	
	@Test
	public void testStringConstructor() throws Exception {
		JsonArray array = new JsonArray("[1,true,\"test\"]");
		assertEquals("[1,true,\"test\"]", JsonFormatter.compact(array));
		assertEquals("[\n" + 
				"  1,\n" + 
				"  true,\n" + 
				"  \"test\"\n" + 
				"]", array.toString());
		assertEquals(3, array.size());
	}
	
	@Test
	public void testSingleQuote() throws Exception {
		List<JsonElement> elements = new ArrayList<JsonElement>();
		elements.add(new JsonText("a'b"));
		JsonArray array = new JsonArray(elements);
		assertEquals("[\"a\\'b\"]", JsonFormatter.compact(array));
		String compact = JsonFormatter.compact(array);
		assertEquals(compact, JsonFormatter.compact(JsonParser.parseArray(compact)));
		assertEquals(compact, JsonFormatter.formatted(JsonParser.parseArray(compact)));
	}
	
	@Test
	public void testQuoteInVariable() throws Exception {
		List<JsonElement> elements = new ArrayList<JsonElement>();
		elements.add(new JsonText("Quote's"));
		elements.add(new JsonText("\"Double\" Quotes"));
		JsonArray array = new JsonArray(elements);
		assertEquals("[\"Quote\\'s\",\"\\\"Double\\\" Quotes\"]", JsonFormatter.compact(array));
		String compact = JsonFormatter.compact(array);
		String formatted = JsonFormatter.formatted(array);
		
		assertEquals(compact, JsonFormatter.compact(JsonParser.parseArray(compact)));
		assertEquals(formatted, JsonFormatter.formatted(JsonParser.parseArray(compact)));
	}
	
}
