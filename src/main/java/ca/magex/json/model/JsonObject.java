package ca.magex.json.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JsonObject extends JsonElement {

	private final List<JsonPair> pairs;
	
	private final Map<String, JsonElement> map;
	
	private final List<String> keys;
	
	public JsonObject() {
		this(new ArrayList<JsonPair>());
	}
	
	public JsonObject(String text) {
		this(JsonParser.parseObject(text).pairs);
	}
	
	public JsonObject(List<JsonPair> pairs) {
		super(digest(pairs.stream().map(e -> e.mid()).collect(Collectors.joining(","))));
		this.pairs = Collections.unmodifiableList(pairs);
		this.map = Collections.unmodifiableMap(pairs.stream().collect(Collectors.toMap(JsonPair::key, JsonPair::value)));
		this.keys = pairs.stream().map(p -> p.key()).collect(Collectors.toList());
	}
	
	public JsonObject with(String key, Object value) {
		return with(new JsonPair(key, cast(value)));
	}
	
	public JsonObject with(JsonPair pair) {
		List<JsonPair> values = new ArrayList<JsonPair>(pairs);
		values.add(pair);
		return new JsonObject(values);
	}
	
	public Stream<JsonPair> stream() {
		return pairs.stream();
	}
	
	public List<JsonPair> pairs() {
		return pairs;
	}
	
	public List<String> keys() {
		return keys;
	}
	
	public List<JsonElement> values() {
		return pairs.stream().map(p -> p.value()).collect(Collectors.toList());
	}
	
	public boolean contains(String key) {
		return map.get(key) != null && !map.get(key).getClass().equals(JsonElement.class);
	}

	public boolean contains(String key, Class<? extends JsonElement> cls) {
		return contains(key) && map.get(key).getClass().equals(cls);
	}
	
	public int size() {
		return map.size();
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public JsonElement get(String key) {
		return map.get(key);
	}
	
	public JsonObject getObject(String key) {
		return ((JsonObject)map.get(key));
	}

	public JsonArray getArray(String key) {
		return ((JsonArray)map.get(key));
	}

	public String getString(String key) {
		try {
			return ((JsonText)map.get(key)).value();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public Integer getInt(String key) {
		return ((JsonNumber)map.get(key)).value().intValue();
	}
	
	public Long getLong(String key) {
		return ((JsonNumber)map.get(key)).value().longValue();
	}
	
	public Float getFloat(String key) {
		return ((JsonNumber)map.get(key)).value().floatValue();
	}
	
	public Boolean getBoolean(String key) {
		return ((JsonBoolean)map.get(key)).value();
	}
	
	public LocalDate getDate(String key) {
		return LocalDate.parse(((JsonText)map.get(key)).value(), DateTimeFormatter.ISO_DATE);
	}
	
	public LocalDateTime getDateTime(String key) {
		return LocalDateTime.parse(((JsonText)map.get(key)).value(), DateTimeFormatter.ISO_DATE_TIME);
	}
	
}
