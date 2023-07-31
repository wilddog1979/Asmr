package org.eaSTars.asm.assember;

import org.eaSTars.asm.ast.Instruction;

import java.io.IOException;

public abstract class AbstractAssemblyConverter<T extends Instruction> {

	public abstract byte[] convert(CompilationContext compilationContext, T instruction);
	
	public abstract T convert(PushbackInputStream pushbackInputStream) throws IOException;
	
}
