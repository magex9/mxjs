package ca.magex.json.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JsonArray extends JsonElement {
	
	private final List<JsonElement> elements;
	
	public JsonArray() {
		this(new ArrayList<JsonElement>());
	}
	
	public JsonArray(String text) {
		this(JsonParser.parseArray(text).elements);
	}
	
	public JsonArray(List<JsonElement> elements) {
		super(digest(elements.stream().map(e -> e.mid()).collect(Collectors.joining(","))));
		this.elements = Collections.unmodifiableList(elements);
	}
	
	public JsonArray with(Object... values) {
		List<JsonElement> updated = new ArrayList<JsonElement>(elements);
		for (Object value : values) {
			updated.add(cast(value));
		}
		return new JsonArray(updated);
	}
	
	public Stream<JsonElement> stream() {
		return elements.stream();
	}
	
	public List<JsonElement> values() {
		return elements;
	}
	
	public int size() {
		return elements.size();
	}
	
	public boolean isEmpty() {
		return elements.isEmpty();
	}

}
