package ch.furthermore.pmsl;

public class ScannerToken {
	private final int line;
	private final int col; 
	private final ScannerTokenType type;
	private final String value;

	public ScannerToken(int line, int col, ScannerTokenType type) {
		this(line, col, type, null);
	}
	
	public ScannerToken(int line, int col, ScannerTokenType type, String value) {
		this.line = line;
		this.col = col;
		this.type = type;
		this.value = value;
	}

	public int getLine() {
		return line;
	}

	public int getCol() {
		return col;
	}

	public ScannerTokenType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
	
	public String toString() {
		return "Line: " + line + ", Col: " + col + ", Token: " + type + (value == null ? "" : ", Value: " + value);
	}
}
