package ca.magex.json.model;

public final class DataNumber extends DataElement {

	private final Number value;
	
	public DataNumber(Number value) {
		super(value == null ? DataElement.UNDEFINED.mid() : digest(value));
		this.value = value;
	}
	
	public Number value() {
		return value;
	}
	
}
