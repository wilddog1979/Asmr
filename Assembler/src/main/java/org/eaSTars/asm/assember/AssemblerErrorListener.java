package org.eaSTars.asm.assember;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.BitSet;

public class AssemblerErrorListener implements ANTLRErrorListener {

	private final String filename;
	
	public AssemblerErrorListener(String filename) {
		this.filename = filename;
	}
	
	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {
		try (LineNumberReader lnr = new LineNumberReader(new FileReader(filename))) {
			int linecounter = 0;
			String readline = null;
			while ((readline = lnr.readLine()) != null && linecounter++ < line) {
				if (linecounter == line) {
					System.out.printf("(line %d) %s\n", linecounter, readline);
					StringBuilder sb = new StringBuilder("         ");
					for (int i = 0; i < charPositionInLine; ++i) {
						sb.append(readline.charAt(i) == '\t' ? '\t' : ' ');
					}
					System.out.println(sb + "^");
					System.out.println(sb + "|_ Syntax Error"+(msg != null ? String.format(" (%s)", msg) : ""));
				}
			}
			
		} catch (IOException e1) {
		}
		
		throw new AssemblerException(e);
	}
	
	@Override
	public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction,
			ATNConfigSet configs) {
		//System.out.println("reportContextSensitivity");
	}
	
	@Override
	public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex,
			BitSet conflictingAlts, ATNConfigSet configs) {
		//System.out.println("reportAttemptingFullContext");
	}
	
	@Override
	public void reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact,
			BitSet ambigAlts, ATNConfigSet configs) {
		//System.out.println("reportAmbiguity");
	}

}
