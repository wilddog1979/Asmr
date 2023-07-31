package org.eaSTars.asm.assember;

import java.io.IOException;

import org.eaSTars.asm.ast.Instruction;

public abstract class AbstractAssemblyConverter<T extends Instruction> {

	public abstract byte[] convert(CompilationContext compilationContext, T instruction);
	
	public abstract T convert(PushbackInputStream pushbackInputStream) throws IOException;
	
}
