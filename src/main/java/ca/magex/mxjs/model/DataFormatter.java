package ca.magex.mxjs.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DataFormatter {
	
	private static final byte[] INDENT = "  ".getBytes();
	
	private static final byte[] EOL = "\n".getBytes();
	
	private static final byte[] BLANK = new byte[] { };

	private static final Map<Integer, byte[]> prefixes = new HashMap<Integer, byte[]>();
	
	private Integer indentation;
	
	private Stack<String> contexts;
	
	public DataFormatter(boolean indented) {
		this(indented ? 0 : null);
	}
	
	private DataFormatter(Integer indentation) {
		this.indentation = indentation;
		this.contexts = new Stack<String>();
	}
	
	public static final String compact(DataElement data) {
		return new DataFormatter(false).stringify(data);
	}
	
	public static final String formatted(DataElement data) {
		return new DataFormatter(true).stringify(data);
	}
	
	public Integer getIndentation() {
		return indentation;
	}
	
	public boolean isIndented() {
		return indentation != null;
	}
	
	public DataFormatter indent() {
		if (indentation != null)
			indentation += 1;
		return this;
	}

	public DataFormatter unindent() {
		if (indentation != null)
			indentation -= 1;
		return this;
	}

	public final byte[] prefix() {
		if (indentation == null)
			return BLANK;
		if (!prefixes.containsKey(indentation))
			prefixes.put(indentation, new String(new char[indentation]).replaceAll("\0", new String(INDENT)).getBytes());
		return prefixes.get(indentation);
	}
	
	public DataFormatter setIndentation(Integer indentation) {
		this.indentation = indentation;
		return this;
	}
	
	public Stack<String> getContexts() {
		return contexts;
	}
	
	public final String stringify(DataElement data) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			stream(data, baos);
		} catch (IOException e) {
			throw new RuntimeException("Problem building string", e);
		}
		return baos.toString(StandardCharsets.UTF_8);
	}
	
	public void stream(DataElement data, OutputStream os) throws IOException {
		if (data.getClass().equals(DataElement.class)) { 
			os.write("null".getBytes());
		} else {
			try {
				getClass().getMethod("stream", new Class[] { data.getClass(), OutputStream.class })
					.invoke(this, new Object[] { data, os });
			} catch (Exception e) {
				throw new IllegalArgumentException("Unable to find stream class for: " + data.getClass(), e);
			}
		}
	}
	
	public void stream(DataText data, OutputStream os) throws IOException {
		os.write((data.value() == null ? "null" : "\"" + data.value().replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\'") + "\"").getBytes());
	}
	
	public void stream(DataNumber data, OutputStream os) throws IOException {
		os.write((data.value() == null ? "null" : data.value().toString()).getBytes());
	}
	
	public void stream(DataBoolean data, OutputStream os) throws IOException {
		os.write((data.value() == null ? "null" : data.value() ? "true" : "false").getBytes());
	}
	
	public void stream(DataPair data, OutputStream os) throws IOException {
		os.write("\"".getBytes());
		os.write(data.key().getBytes());
		os.write("\":".getBytes());
		if (isIndented())
			os.write(" ".getBytes());
		stream(data.value(), os);
	}
	
	public void stream(DataArray data, OutputStream os) throws IOException {
		os.write("[".getBytes());
		if (data.values().size() == 1) {
			stream(data.values().get(0), os);
		} else if (data.values().size() > 1) {
			if (isIndented())
				os.write(EOL);
			indent();
			for (int i = 0; i < data.values().size(); i++) {
				if (isIndented())
					os.write(prefix());
				stream(data.values().get(i), os);
				if (i < data.values().size() - 1)
					os.write(",".getBytes());
				if (isIndented())
					os.write(EOL);
			}
			unindent();
			if (isIndented())
				os.write(prefix());
		}
		os.write("]".getBytes());
	}
	
	public void stream(DataObject data, OutputStream os) throws IOException {
		os.write("{".getBytes());
		if (data.pairs().size() == 0) {
			os.write("".getBytes());
		} else if (data.pairs().size() == 1 && !(data.pairs().get(0).value() instanceof DataObject)) {
			stream(data.pairs().get(0), os);
		} else {
			if (isIndented())
				os.write(EOL);
			indent();
			for (int i = 0; i < data.pairs().size(); i++) {
				if (getContexts().isEmpty() || !data.pairs().get(i).key().equals("@context") || !((DataText)data.pairs().get(i).value()).value().equals(getContexts().peek())) {
					if (isIndented())
						os.write(prefix());
					if (data.contains("@context", DataText.class))
						getContexts().push(data.getString("@context"));
					stream(data.pairs().get(i), os);
					if (data.contains("@context", DataText.class))
						getContexts().pop();
					if (i < data.pairs().size() - 1)
						os.write(",".getBytes());
					if (isIndented())
						os.write(EOL);
				}
			}
			unindent();
			if (isIndented())
				os.write(prefix());
		}
		os.write("}".getBytes());
	}

}
