package ca.magex.json.model;

import static ca.magex.json.model.JsonParser.*;
import static ca.magex.json.model.JsonParser.readFile;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class JsonParserTest {

	@Test
	public void testParsingBook() throws Exception {
		testParsingFile("book");
	}
	
	@Test
	public void testParsingMovie() throws Exception {
		testParsingFile("movie");
	}
	
	public void testParsingFile(String name) throws Exception {
		File formattedFile = new File("src/test/resources/examples/" + name + "-formatted.json");
		JsonObject formattedObj = parseObject(formattedFile);
		assertEquals(formattedObj.toString(), readFile(formattedFile));
		assertEquals(JsonFormatter.formatted(formattedObj), readFile(formattedFile));
		
		File compactFile = new File("src/test/resources/examples/" + name + "-compact.json");
		JsonObject compactObj = parseObject(compactFile);
		assertEquals(formattedObj, compactObj);
		assertEquals(formattedObj.mid(), compactObj.mid());

		assertEquals(JsonFormatter.formatted(formattedObj), JsonFormatter.formatted(compactObj));
		assertEquals(JsonFormatter.compact(formattedObj), JsonFormatter.compact(compactObj));
		
		File outputFile = new File("target/" + name + "-formatted.json");
		writeFile(outputFile, formattedObj.toString());
		assertEquals(readFile(formattedFile), readFile(outputFile));
	}
	
}