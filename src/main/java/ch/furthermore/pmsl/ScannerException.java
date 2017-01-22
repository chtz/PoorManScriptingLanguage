package ch.furthermore.pmsl;

public class ScannerException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final ScannerChar c;
	
	public ScannerException(ScannerChar c, String message) {
		super(message);
		
		this.c = c;
	}

	public ScannerChar getChar() {
		return c;
	}
	
	public String toString() {
		return c.toString() + ", Message: " + getMessage();
	}
}
