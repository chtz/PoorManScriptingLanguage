package ch.furthermore.pmsl;

public class GeneratorException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private final ScannerToken t;
	
	public GeneratorException(ScannerToken t, String message) {
		super(message);
		
		this.t = t;
	}

	public ScannerToken getToken() {
		return t;
	}
	
	public String toString() {
		return t.toString() + ", Message: " + getMessage();
	}
}
