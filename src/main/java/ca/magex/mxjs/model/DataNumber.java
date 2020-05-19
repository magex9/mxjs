package ca.magex.mxjs.model;

public final class DataNumber extends DataElement {

	private final Number value;
	
	public DataNumber(Number value) {
		this.value = value;
	}
	
	public Number value() {
		return value;
	}
	
}
