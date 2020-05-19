package ca.magex.mxjs.model;

public final class DataBoolean extends DataElement {

	private final Boolean value;
	
	public DataBoolean(Boolean value) {
		this.value = value;
	}
	
	public Boolean value() {
		return value;
	}
	
}
