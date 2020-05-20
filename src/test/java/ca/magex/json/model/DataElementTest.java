package ca.magex.json.model;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.Test;

import ca.magex.mxjs.util.FormattedStringBuilder;

public class DataElementTest {

	@Test
	public void testBasicDataElement() throws Exception {
		DataElement el = new DataElement();
		assertEquals(DataElement.UNDEFINED, el);
		assertEquals("abc", el.mid());
		assertEquals("abc", new DataElement().mid());
	}
	
	@Test
	public void testDataElementMid() throws Exception {
		DataElement el = new DataElement("abc");
		assertEquals("abc", el.mid());
	}
	
	@Test
	public void testValidatingKeys() throws Exception {
		try {
			DataElement.validateKey(null);
			fail("Keys must be not null");
		} catch (IllegalArgumentException e) {
			assertEquals("Key cannot be null", e.getMessage());
		}
		try {
			DataElement.validateKey("");
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
			DataElement.validateKey(key);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Test
	public void testCastNull() throws Exception {
		assertEquals(DataElement.UNDEFINED, DataElement.cast(null));
	}
	
	@Test
	public void testCastDataElement() throws Exception {
		assertEquals(DataElement.class, DataElement.cast(DataElement.UNDEFINED).getClass());
		assertEquals(DataText.class, DataElement.cast(new DataText("test")).getClass());
		assertEquals(DataNumber.class, DataElement.cast(new DataNumber(10)).getClass());
		assertEquals(DataBoolean.class, DataElement.cast(new DataBoolean(true)).getClass());
		assertEquals(DataArray.class, DataElement.cast(new DataArray()).getClass());
	}
	
	@Test
	public void testCastNumber() throws Exception {
		assertEquals(DataNumber.class, DataElement.cast(10).getClass());
		assertEquals(DataNumber.class, DataElement.cast(0.05).getClass());
	}
	
	@Test
	public void testCastText() throws Exception {
		assertEquals(DataText.class, DataElement.cast("Hello").getClass());
		assertEquals(DataText.class, DataElement.cast("Français").getClass());
	}
	
	@Test
	public void testCastBoolean() throws Exception {
		assertEquals(DataBoolean.class, DataElement.cast(true).getClass());
		assertEquals(DataBoolean.class, DataElement.cast(false).getClass());
		assertEquals(DataBoolean.class, DataElement.cast(Boolean.TRUE).getClass());
		assertEquals(DataBoolean.class, DataElement.cast(Boolean.FALSE).getClass());
	}
	
	@Test
	public void testCastInvalid() throws Exception {
		try {
			DataElement.cast(new Object());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			DataElement.cast(new Date());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			DataElement.cast(LocalDateTime.now());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
		try {
			DataElement.cast(new FormattedStringBuilder());
			fail("Invalid object");
		} catch (IllegalArgumentException e) { }
	}
	
}
