package ca.magex.json.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class JsonElement {
	
	public static final JsonElement UNDEFINED = new JsonElement();
	
	private final String mid;

	public JsonElement() {
		this.mid = digest(null);
	}
	
	protected JsonElement(String mid) {
		this.mid = mid;
	}
	
	public final String mid() {
		return mid;
	}
	
	protected static final String validateKey(String key) {
		if (key == null)
			throw new IllegalArgumentException("Key cannot be null");
		if (!key.matches("[A-Za-z0-9]{1,255}"))
			throw new IllegalArgumentException("Invalid key: " + key);
		return key;
	}
	
	public static JsonElement cast(Object el) {
		if (el == null) {
			return new JsonElement();
		} else if (el instanceof JsonElement) {
			return (JsonElement)el;
		} else if (el instanceof String) {
			return new JsonText((String)el);
		} else if (el instanceof Number) {
			return new JsonNumber((Number)el);
		} else if (el instanceof Boolean) {
			return new JsonBoolean((Boolean)el);
		}
		throw new IllegalArgumentException("Unsupported type of element to convert to a data element: " + el.getClass());
	}
	
	public static Object unwrap(Object el) {
		if (el == null) {
			return null;
		} else if (el instanceof JsonText) {
			return ((JsonText)el).value();
		} else if (el instanceof JsonNumber) {
			return ((JsonNumber)el).value();
		} else if (el instanceof JsonBoolean) {
			return ((JsonBoolean)el).value();
		}
		throw new IllegalArgumentException("Unsupported type of element to unwrap: " + el.getClass());
	}

	public static final String digest(Object obj) {
		if (obj == null)
			return "";
		return DigestUtils.md5Hex(obj.toString());
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public final String toString() {
		return new JsonFormatter(true).stringify(this);
	}

}
