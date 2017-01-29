package ch.furthermore.pmsl;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Scanner {
	private final Reader reader;
	private int line;
	private int col;
	private ScannerChar current;
	private ScannerChar pushedBack;
	
	private static Map<String,ScannerTokenType> keywordNames = new HashMap<String,ScannerTokenType>();
	static {
		keywordNames.put("var", ScannerTokenType.VAR_KEYWORD);
		keywordNames.put("variable", ScannerTokenType.VAR_KEYWORD);
		keywordNames.put("end", ScannerTokenType.END_KEYWORD);
		keywordNames.put("def", ScannerTokenType.DEF_KEYWORD);
		keywordNames.put("define", ScannerTokenType.DEF_KEYWORD);
		keywordNames.put("ret", ScannerTokenType.RETURN_KEYWORD);
		keywordNames.put("return", ScannerTokenType.RETURN_KEYWORD);
		keywordNames.put("result", ScannerTokenType.RESERVED_KEYWORD);
		keywordNames.put("if", ScannerTokenType.IF_KEYWORD);
		keywordNames.put("then", ScannerTokenType.THEN_KEYWORD);
		keywordNames.put("not", ScannerTokenType.NOT_KEYWORD);
		keywordNames.put("and", ScannerTokenType.AND_KEYWORD);
		keywordNames.put("or", ScannerTokenType.OR_KEYWORD);
		keywordNames.put("true", ScannerTokenType.TRUE_KEYWORD);
		keywordNames.put("false", ScannerTokenType.FALSE_KEYWORD);
		keywordNames.put("for", ScannerTokenType.FOR_KEYWORD);
		keywordNames.put("do", ScannerTokenType.DO_KEYWORD);
		keywordNames.put("transition", ScannerTokenType.TRANSITION_KEYWORD);
		keywordNames.put("to", ScannerTokenType.TO_KEYWORD);
		keywordNames.put("enter", ScannerTokenType.ENTER_KEYWORD);
		keywordNames.put("leave", ScannerTokenType.LEAVE_KEYWORD);
		keywordNames.put("node", ScannerTokenType.NODE_KEYWORD);
		keywordNames.put("state", ScannerTokenType.STATE_KEYWORD);
		keywordNames.put("workflow", ScannerTokenType.WORKFLOW_KEYWORD);
		keywordNames.put("join", ScannerTokenType.JOIN_KEYWORD);
	}

	public Scanner(Reader reader) {
		this.reader = reader;
	}

	public ScannerToken next() throws IOException {
		read();

		if (Character.isWhitespace(current.c)) {
			while (Character.isWhitespace(current.c)) {
				read();
			}
		}
		
		if (current.c == -1) {
			return new ScannerToken(current.line, current.col, ScannerTokenType.EOF);
		}
		else if (current.c == '>') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.REL_GT);
		}
		else if (current.c == '<') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.REL_LT);
		}
		else if (current.c == ';') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.SEMICOLON);
		}
		else if (current.c == ',') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.COMMA);
		}
		else if (current.c == '=') {
			ScannerChar start = current;
			
			read();
			
			if (current.c == '=') {
				return new ScannerToken(start.line, start.col, ScannerTokenType.REL_EQ);
			}
			else {
				unread();
				
				return new ScannerToken(current.line, current.col, ScannerTokenType.EQ);
			}
		}
		else if (current.c == '+') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.PLUS);
		}
		else if (current.c == '-') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.MINUS);
		}
		else if (current.c == '*') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.ASTERIX);
		}
		else if (current.c == '/') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.DIVISION);
		}
		else if (current.c == '(') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.LEFT_PARENTHESIS);
		}
		else if (current.c == ')') {
			return new ScannerToken(current.line, current.col, ScannerTokenType.RIGHT_PARENTHESIS);
		}
		else if (current.c == '"') {
			ScannerChar start = current;
			read();
			StringBuilder sb = new StringBuilder();
			while (current.c != '"') {
				if (current.c == '\\') {
					read();
					switch (current.c) {
					case 'n': sb.append('\n'); break;
					case 'r': sb.append('\r'); break; 
					case '"': sb.append('"'); break;
					default: throw new ScannerException(current, "Unexpected characted");
					}
				}
				else {
					sb.append(current.asChar());
				}
				read();
			}
			return new ScannerToken(start.line, start.col, ScannerTokenType.STRING_LITERAL, sb.toString());
		}
		else if (Character.isAlphabetic(current.c)) {
			ScannerChar start = current;
			StringBuilder sb = new StringBuilder();
			while (Character.isAlphabetic(current.c) || Character.isDigit(current.c)) {
				sb.append(current.asChar());
				read();
			}
			unread();
			
			String name = sb.toString();
			
			ScannerTokenType keyword = keywordNames.get(name);
			
			return keyword != null
					? new ScannerToken(start.line, start.col, keyword)
					: new ScannerToken(start.line, start.col, ScannerTokenType.IDENTIFIER, name);
		}
		else if (Character.isDigit(current.c)) {
			ScannerChar start = current;
			StringBuilder sb = new StringBuilder();
			while (Character.isDigit(current.c)) {
				sb.append(current.asChar());
				read();
			}
			unread();
			
			return new ScannerToken(start.line, start.col, ScannerTokenType.INTEGER_LITERAL, sb.toString());
		}
		else {
			throw new ScannerException(current, "Unexpected characted");
		}
	}

	private void unread() {
		pushedBack = current;
	}

	private void read() throws IOException {
		if (pushedBack != null) {
			current = pushedBack;
			pushedBack = null;
		}
		else {
			 current = new ScannerChar(line, col, reader.read());
			 if (current.c == '\n') {
				 line++;
				 col=0;
			 }
			 else {
				 col++;
			 }
		}
	}
}
