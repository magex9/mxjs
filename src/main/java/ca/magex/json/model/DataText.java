package ca.magex.json.model;

public final class DataText extends DataElement {

	private final String value;
	
	public DataText(String value) {
		super(value == null ? DataElement.UNDEFINED.mid() : digest(value));
		this.value = value;
	}
	
	public String value() {
		return value;
	}
	
}
