package org.eaSTars.asm.assember;

import java.io.IOException;

import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.Instruction;

public abstract class AssemblyConverter<T extends Instruction> {

	public abstract byte[] convert(CompilationUnit compilationUnit, T instruction);
	
	public abstract T convert(PushbackInputStream pushbackInputStream) throws IOException;
	
}
