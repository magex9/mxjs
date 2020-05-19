package ca.magex.mxjs.model;

import org.apache.commons.codec.digest.DigestUtils;

public class DataElement {
	
	public static final DataElement UNDEFINED = new DataElement();
	
	private final String mid;

	public DataElement() {
		this.mid = digest(stringify(new DataFormatter(false)));
	}
	
	public DataElement(String mid) {
		this.mid = mid;
	}
	
	public final String mid() {
		return mid;
	}
	
	public static DataElement cast(Object el) {
		if (el == null) {
			return new DataElement();
		} else if (el instanceof DataElement) {
			return (DataElement)el;
		} else if (el instanceof String) {
			return new DataText((String)el);
		} else if (el instanceof Number) {
			return new DataNumber((Number)el);
		} else if (el instanceof Boolean) {
			return new DataBoolean((Boolean)el);
		}
		throw new IllegalArgumentException("Unsupported type of element to convert to a data element: " + el.getClass());
	}
	
	public static Object unwrap(Object el) {
		if (el == null) {
			return null;
		} else if (el instanceof DataText) {
			return ((DataText)el).value();
		} else if (el instanceof DataNumber) {
			return ((DataNumber)el).value();
		} else if (el instanceof DataBoolean) {
			return ((DataBoolean)el).value();
		}
		throw new IllegalArgumentException("Unsupported type of element to unwrap: " + el.getClass());
	}

	public static final String digest(Object obj) {
		if (obj == null || (obj instanceof String && ((String)obj).equals("null")))
			return "";
		return DigestUtils.md5Hex(obj.toString());
	}
	
	public final String stringify(DataFormatter formatter) {
		return formatter.stringify(this);
	}

	@Override
	public final String toString() {
		return new DataFormatter(true).stringify(this);
	}

}
