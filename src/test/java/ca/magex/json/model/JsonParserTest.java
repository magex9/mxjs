package ca.magex.json.model;

import static ca.magex.json.model.JsonParser.parseObject;
import static ca.magex.json.model.JsonParser.readFile;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

public class JsonParserTest {

	@Test
	public void testParsingBook() throws Exception {
		testParsingFile("examples/book.json");
	}
	
	@Test
	public void testParsingMovie() throws Exception {
		testParsingFile("examples/movie.json");
	}
	
	public void testParsingFile(String name) throws Exception {
		File file = new File("src/test/resources/" + name);
		JsonObject obj = parseObject(file);
		JsonObject refreshed = new JsonObject(readFile(file));
		assertEquals(obj, refreshed);
		assertEquals(obj.mid(), refreshed.mid());
		assertEquals(refreshed.toString(), readFile(file));
	}
	
}