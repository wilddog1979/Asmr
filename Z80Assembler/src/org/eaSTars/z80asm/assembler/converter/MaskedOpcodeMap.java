package org.eaSTars.z80asm.assembler.converter;

import java.util.HashMap;

public class MaskedOpcodeMap<T> extends HashMap<OpcodeMask, T> {

	private static final long serialVersionUID = -3275047983128208919L;

	public T getInstruction(byte[] values) {
		return entrySet().stream().filter(e -> e.getKey().getMask().length == values.length && checkMask(values, e.getKey()))
				.findFirst().map(e -> e.getValue()).orElseGet(() -> null);
	}

	private boolean checkMask(byte[] values, OpcodeMask mask) {
		boolean result = true;
		byte[] bmask = mask.getMask();
		byte[] bvalue = mask.getValue();
		for (int i = 0; i < values.length; ++i) {
			if (!(result = (byte)(values[i] & bmask[i]) == bvalue[i])) {
				break;
			}
		}
		return result;
	}

}
