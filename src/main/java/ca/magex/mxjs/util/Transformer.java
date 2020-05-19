package ca.magex.mxjs.util;

import ca.magex.mxjs.model.DataObject;

public interface Transformer<T extends Object> {
	
	public Class<?> getType();
	
	public DataObject format(T obj);
	
	public T parse(DataObject data);
	
}
