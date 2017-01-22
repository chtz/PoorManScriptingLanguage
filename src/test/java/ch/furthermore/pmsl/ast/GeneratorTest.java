package ch.furthermore.pmsl.ast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import org.junit.Test;

import ch.furthermore.pmsl.Context;
import ch.furthermore.pmsl.GeneratorException;
import ch.furthermore.pmsl.Keys;
import ch.furthermore.pmsl.Parser;
import ch.furthermore.pmsl.ParserException;
import ch.furthermore.pmsl.Scanner;
import ch.furthermore.pmsl.ScannerException;

public class GeneratorTest {
	@Test
	public void testGenerateExt() throws IOException {
		for (File scriptFile : new File("testInput").listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pms");
			}
		})) {
			BufferedReader r = new BufferedReader(new FileReader(scriptFile));
			try {
				Scanner s = new Scanner(r);
				Parser p = new Parser(s);
				
				Keys keys = new Keys();
				Context ctx = new Context(keys, null);
				
				StringBuilder sb = new StringBuilder();
				String ext = ".js";
				try {
					p.definition().generate(ctx, sb);
				}
				catch (ScannerException e) {
					ext = ".scanner.err";
					sb.setLength(0);
					sb.append(e.toString());
				}
				catch (ParserException e) {
					ext = ".parser.err";
					sb.setLength(0);
					sb.append(e.toString());
				}
				catch (GeneratorException e) {
					ext = ".generator.err";
					sb.setLength(0);
					sb.append(e.toString());
				}
				BufferedWriter w = new BufferedWriter(new FileWriter(new File("testOutput", 
						scriptFile.getName().substring(0, scriptFile.getName().lastIndexOf('.')) + ext)));
				try {
					w.append(sb);
				}
				finally {
					w.close();
				}
			}
			finally {
				r.close();
			}
		}
	}
}
