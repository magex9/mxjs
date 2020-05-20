package ca.magex.mxjs.util;

import ca.magex.json.model.DataObject;

public interface Transformer<T extends Object> {
	
	public Class<T> getType();
	
	public DataObject format(T obj);
	
	public T parse(DataObject data);
	
}
