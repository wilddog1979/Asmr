package org.eastars.z80asm.assembler.converter;

import lombok.Builder;
import org.eastars.z80asm.ast.parameter.Parameter;

@Builder
public record OneParameter(Parameter parameter) {
}
