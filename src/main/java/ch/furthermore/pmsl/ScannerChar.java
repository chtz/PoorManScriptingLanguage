package ch.furthermore.pmsl;

public class ScannerChar {
	public final int line;
	public final int col;
	public final int c;
	
	public ScannerChar(int line, int col, int c) {
		this.line = line;
		this.col = col;
		this.c = c;
	}
	
	public char asChar() {
		return (char) c;
	}

	public int getLine() {
		return line;
	}

	public int getCol() {
		return col;
	}
	
	public String toString() {
		return "Line: " + line + ", Col: " + col + ", Char: '" + ((char) c) + "'";
	}
}
