package org.eastars.z80asm.converter.test;

import org.eastars.asm.assember.PushbackInputStream;
import org.eastars.z80asm.assembler.converter.NoParameterInstructionConverter;
import org.eastars.z80asm.ast.instructions.NoParameterInstruction;
import org.eastars.z80asm.ast.instructions.noparam.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class NoParameterConverterTest {

  private static class ConverterArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new Object[][] {
        {new NOP(), new byte[] {0x00}},
        {new RLCA(), new byte[] {0x07}},
        {new RRCA(), new byte[] {0x0f}},
        {new RLA(), new byte[] {0x17}},
        {new RRA(), new byte[] {0x1f}},
        {new DAA(), new byte[] {0x27}},
        {new CPL(), new byte[] {0x2f}},
        {new SCF(), new byte[] {0x37}},
        {new CCF(), new byte[] {0x3f}},
        {new HALT(), new byte[] {0x76}},
        {new EXX(), new byte[] {(byte) 0xd9}},
        {new DI(), new byte[] {(byte) 0xf3}},
        {new EI(), new byte[] {(byte) 0xfb}},
        {new NEG(), new byte[] {(byte) 0xed, 0x44}},
        {new IM0(), new byte[] {(byte) 0xed, 0x46}},
        {new RETN(), new byte[] {(byte) 0xed, 0x45}},
        {new RETI(), new byte[] {(byte) 0xed, 0x4d}},
        {new IM1(), new byte[] {(byte) 0xed, 0x56}},
        {new IM2(), new byte[] {(byte) 0xed, 0x5e}},
        {new RRD(), new byte[] {(byte) 0xed, 0x67}},
        {new RLD(), new byte[] {(byte) 0xed, 0x6f}},
        {new LDI(), new byte[] {(byte) 0xed, (byte) 0xa0}},
        {new CPI(), new byte[] {(byte) 0xed, (byte) 0xa1}},
        {new INI(), new byte[] {(byte) 0xed, (byte) 0xa2}},
        {new OUTI(), new byte[] {(byte) 0xed, (byte) 0xa3}},
        {new LDD(), new byte[] {(byte) 0xed, (byte) 0xa8}},
        {new CPD(), new byte[] {(byte) 0xed, (byte) 0xa9}},
        {new IND(), new byte[] {(byte) 0xed, (byte) 0xaa}},
        {new OUTD(), new byte[] {(byte) 0xed, (byte) 0xab}},
        {new LDIR(), new byte[] {(byte) 0xed, (byte) 0xb0}},
        {new CPIR(), new byte[] {(byte) 0xed, (byte) 0xb1}},
        {new INIR(), new byte[] {(byte) 0xed, (byte) 0xb2}},
        {new OTIR(), new byte[] {(byte) 0xed, (byte) 0xb3}},
        {new LDDR(), new byte[] {(byte) 0xed, (byte) 0xb8}},
        {new CPDR(), new byte[] {(byte) 0xed, (byte) 0xb9}},
        {new INDR(), new byte[] {(byte) 0xed, (byte) 0xba}},
        {new OTDR(), new byte[] {(byte) 0xed, (byte) 0xbb}},
      }).map(Arguments::of);
    }

  }

  @ParameterizedTest
  @ArgumentsSource(ConverterArgumentProvider.class)
  public void testNoParameterConverterFromInstruction(NoParameterInstruction instruction, byte[] assembly) {
    byte[] result = new NoParameterInstructionConverter().convert(null, instruction);

    assertNotNull(result, "Converted result expected");
    assertArrayEquals(assembly, result, "Opcode must be equal");
  }

  @ParameterizedTest
  @ArgumentsSource(ConverterArgumentProvider.class)
  public void testNoParameterConverterToInstruction(NoParameterInstruction instruction, byte[] assembly)
      throws IOException {
    NoParameterInstruction result = new NoParameterInstructionConverter().convert(new PushbackInputStream(
        new ByteArrayInputStream(assembly)));

    assertNotNull(result, "Converted result expected");
    assertEquals(instruction.getClass(), result.getClass(), "Class type must match");
  }

  @Test
  public void testUnknownInstructionOneByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01}));
    NoParameterInstruction result = new NoParameterInstructionConverter().convert(pis);

    assertNull(result, "Converted result not expected");
    assertEquals(0x01, pis.read(), "Rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }

  @Test
  public void testUnknownInstructionTwoByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x01, 0x02}));
    NoParameterInstruction result = new NoParameterInstructionConverter().convert(pis);

    assertNull(result, "Converted result not expected");
    assertEquals(0x01, pis.read(), "First rolled back value expected");
    assertEquals(0x02, pis.read(), "Second rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }

}
