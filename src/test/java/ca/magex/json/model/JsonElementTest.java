package ca.magex.json.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;

import ca.magex.mxjs.util.FormattedStringBuilder;

public class JsonElementTest {

	@Test
	public void testBasicDataElement() throws Exception {
		JsonElement el = new JsonElement();
		assertEquals(JsonElement.UNDEFINED, el);
		assertEquals("abc", el.mid());
		assertEquals("abc", new JsonElement().mid());
	}
	
	@Test
	public void testDataElementMid() throws Exception {
		JsonElement el = new JsonElement("abc");
		assertEquals("abc", el.mid());
	}
	
	@Test
	public void testValidatingKeys() throws Exception {
		try {
			JsonElement.validateKey(null);
			fail("Keys must be not null");
		} catch (IllegalArgumentException e) {
			assertEquals("Key cannot be null", e.getMessage());
		}
		try {
			JsonElement.validateKey("");
			fail("Keys must not be blank");
		} catch (IllegalArgumentException e) { 
			assertEquals("Invalid key: ", e.getMessage());
		}
	}
	
	@Test
	public void testSpecialKeys() throws Exception {
		assertFalse(isValidKey(null));
		assertFalse(isValidKey(""));
		assertFalse(isValidKey(" "));
		assertFalse(isValidKey("\t"));
		assertFalse(isValidKey("\n"));
	}
	
	@Test
	public void testAmountKey() throws Exception {
		assertFalse(isValidKey("$100"));
	}

	@Test
	public void testDateKey() throws Exception {
		assertFalse(isValidKey("2020-01-01"));
	}
	
	@Test
	public void testAlphaNumericKeys() throws Exception {
		assertTrue(isValidKey("ABCD"));
		assertTrue(isValidKey("ABC132"));
		assertTrue(isValidKey("abcdEFTHI01930"));
		assertTrue(isValidKey(buildString(10, "abc")));
		assertTrue(isValidKey(buildString(100, "abcde")));
		assertTrue(isValidKey(buildString(200, "abcde")));
		assertTrue(isValidKey(buildString(255, "abcde")));
		assertFalse(isValidKey(buildString(256, "abcde")));
		assertFalse(isValidKey(buildString(300, "abcde")));
		assertFalse(isValidKey(buildString(400, "abcde")));
	}
	
	public String buildString(int length, String text) {
		StringBuilder sb = new StringBuilder();
		while (sb.length() < length) {
			sb.append(text);
		}
		return sb.substring(0, length);
	}
	
	public boolean isValidKey(String key) {
		try {
			JsonElement.validateKey(key);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Test
	public void testCastNull() throws Exception {
		assertEquals(JsonElement.UNDEFINED, JsonElement.cast(null));
	}
	
	@Test
	public void testCastDataElement() throws Exception {
		assertEquals(JsonElement.class, JsonElement.cast(JsonElement.UNDEFINED).getClass());
		assertEquals(JsonText.class, JsonElement.cast(new JsonText("test")).getClass());
		assertEquals(JsonNumber.class, JsonElement.cast(new JsonNumber(10)).getClass());
		assertEquals(JsonBoolean.class, JsonElement.cast(new JsonBoolean(true)).getClass());
		assertEquals(JsonArray.class, JsonElement.cast(new JsonArray()).getClass());
	}
	
	@Test
	public void testCastNumber() throws Exception {
		assertEquals(JsonNumber.class, JsonElement.cast(10).getClass());
		assertEquals(JsonNumber.class, JsonElement.cast(0.05).getClass());
	}
	
	@Test
	public void testCastText() throws Exception {
		assertEquals(JsonText.class, JsonElement.cast("Hello").getClass());
		assertEquals(JsonText.class, JsonElement.cast("Français").getClass());
	}
	
	@Test
	public void testCastBoolean() throws Exception {
		assertEquals(JsonBoolean.class, JsonElement.cast(true).getClass());
		assertEquals(JsonBoolean.class, JsonElement.cast(false).getClass());
		assertEquals(JsonBoolean.class, JsonElement.cast(Boolean.TRUE).getClass());
		assertEquals(JsonBoolean.class, JsonElement.cast(Boolean.FALSE).getClass());
	}
	
	@Test
	public void testCastInvalid() throws Exception {
		try {
			JsonElement.cast(new Object());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			JsonElement.cast(new Date());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			JsonElement.cast(LocalDateTime.now());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			JsonElement.cast(new FormattedStringBuilder());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
	}
	
}
