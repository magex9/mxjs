package ca.magex.json.model;

public final class DataBoolean extends DataElement {

	private final Boolean value;
	
	public DataBoolean(Boolean value) {
		super(value == null ? DataElement.UNDEFINED.mid() : digest(value));
		this.value = value;
	}
	
	public Boolean value() {
		return value;
	}
	
}
