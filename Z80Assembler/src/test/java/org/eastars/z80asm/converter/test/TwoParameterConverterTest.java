package org.eastars.z80asm.converter.test;

import org.eastars.asm.assember.PushbackInputStream;
import org.eastars.z80asm.assembler.converter.TwoParameterInstructionConverter;
import org.eastars.z80asm.ast.expression.ConstantValueExpression;
import org.eastars.z80asm.ast.instructions.TwoParameterInstruction;
import org.eastars.z80asm.ast.instructions.twoparam.*;
import org.eastars.z80asm.ast.parameter.*;
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

public class TwoParameterConverterTest {

  private static class ConverterArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new Object[][] {
        {new EX(new RegisterPairParameter(RegisterPair.AF), new RegisterPairParameter(RegisterPair.AFMarked)),
            new byte[] {0x08}},
        {new EX(new RegisterIndirectAddressing(RegisterPair.SP), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {(byte) 0xe3}},
        {new EX(new RegisterPairParameter(RegisterPair.DE), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {(byte) 0xeb}},
        {new EX(new RegisterIndirectAddressing(RegisterPair.SP), new RegisterPairParameter(RegisterPair.IX)),
            new byte[] {(byte) 0xdd, (byte) 0xe3}},
        {new EX(new RegisterIndirectAddressing(RegisterPair.SP), new RegisterPairParameter(RegisterPair.IY)),
            new byte[] {(byte) 0xfd, (byte) 0xe3}},
        {new ADD(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.BC)),
            new byte[] {0x09}},
        {new ADD(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.DE)),
            new byte[] {0x19}},
        {new ADD(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {0x29}},
        {new ADD(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.SP)),
            new byte[] {0x39}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.B)), new byte[] {(byte) 0x80}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.C)), new byte[] {(byte) 0x81}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.D)), new byte[] {(byte) 0x82}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.E)), new byte[] {(byte) 0x83}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.H)), new byte[] {(byte) 0x84}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.L)), new byte[] {(byte) 0x85}},
        {new ADD(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.HL)),
            new byte[] {(byte) 0x86}},
        {new ADD(new RegisterParameter(Register.A), new RegisterParameter(Register.A)), new byte[] {(byte) 0x87}},
        {new ADD(new RegisterParameter(Register.A), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x7f)), 8)), new byte[] {(byte) 0xc6, 0x7f}},
        {new ADD(new RegisterPairParameter(RegisterPair.IX), new RegisterPairParameter(RegisterPair.BC)),
            new byte[] {(byte) 0xdd, 0x09}},
        {new ADD(new RegisterPairParameter(RegisterPair.IX), new RegisterPairParameter(RegisterPair.DE)),
            new byte[] {(byte) 0xdd, 0x19}},
        {new ADD(new RegisterPairParameter(RegisterPair.IX), new RegisterPairParameter(RegisterPair.IX)),
            new byte[] {(byte) 0xdd, 0x29}},
        {new ADD(new RegisterPairParameter(RegisterPair.IX), new RegisterPairParameter(RegisterPair.SP)),
            new byte[] {(byte) 0xdd, 0x39}},
        {new ADD(new RegisterPairParameter(RegisterPair.IY), new RegisterPairParameter(RegisterPair.BC)),
            new byte[] {(byte) 0xfd, 0x09}},
        {new ADD(new RegisterPairParameter(RegisterPair.IY), new RegisterPairParameter(RegisterPair.DE)),
            new byte[] {(byte) 0xfd, 0x19}},
        {new ADD(new RegisterPairParameter(RegisterPair.IY), new RegisterPairParameter(RegisterPair.IY)),
            new byte[] {(byte) 0xfd, 0x29}},
        {new ADD(new RegisterPairParameter(RegisterPair.IY), new RegisterPairParameter(RegisterPair.SP)),
            new byte[] {(byte) 0xfd, 0x39}},
        {new ADD(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xc)),
                8))), new byte[] {(byte) 0xdd, (byte) 0x86, 0x0c}},
        {new ADD(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xd0)),
                8))), new byte[] {(byte) 0xfd, (byte) 0x86, (byte) 0xd0}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.B)), new byte[] {(byte) 0x88}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.C)), new byte[] {(byte) 0x89}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.D)), new byte[] {(byte) 0x8a}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.E)), new byte[] {(byte) 0x8b}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.H)), new byte[] {(byte) 0x8c}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.L)), new byte[] {(byte) 0x8d}},
        {new ADC(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.HL)),
            new byte[] {(byte) 0x8e}},
        {new ADC(new RegisterParameter(Register.A), new RegisterParameter(Register.A)), new byte[] {(byte) 0x8f}},
        {new ADC(new RegisterParameter(Register.A), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x7f)), 8)), new byte[] {(byte) 0xce, 0x7f}},
        {new ADC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.BC)),
            new byte[] {(byte) 0xed, 0x4a}},
        {new ADC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.DE)),
            new byte[] {(byte) 0xed, 0x5a}},
        {new ADC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {(byte) 0xed, 0x6a}},
        {new ADC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.SP)),
            new byte[] {(byte) 0xed, 0x7a}},
        {new ADC(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xc)),
                8))), new byte[] {(byte) 0xdd, (byte) 0x8e, 0xc}},
        {new ADC(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xd0)),
                8))), new byte[] {(byte) 0xfd, (byte) 0x8e, (byte) 0xd0}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.B)), new byte[] {(byte) 0x98}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.C)), new byte[] {(byte) 0x99}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.D)), new byte[] {(byte) 0x9a}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.E)), new byte[] {(byte) 0x9b}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.H)), new byte[] {(byte) 0x9c}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.L)), new byte[] {(byte) 0x9d}},
        {new SBC(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.HL)),
            new byte[] {(byte) 0x9e}},
        {new SBC(new RegisterParameter(Register.A), new RegisterParameter(Register.A)), new byte[] {(byte) 0x9f}},
        {new SBC(new RegisterParameter(Register.A), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x7f)), 8)), new byte[] {(byte) 0xde, 0x7f}},
        {new SBC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.BC)),
            new byte[] {(byte) 0xed, 0x42}},
        {new SBC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.DE)),
            new byte[] {(byte) 0xed, 0x52}},
        {new SBC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {(byte) 0xed, 0x62}},
        {new SBC(new RegisterPairParameter(RegisterPair.HL), new RegisterPairParameter(RegisterPair.SP)),
            new byte[] {(byte) 0xed, 0x72}},
        {new SBC(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xc)),
                8))), new byte[] {(byte) 0xdd, (byte) 0x9e, 0xc}},
        {new SBC(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0xd0)),
                8))), new byte[] {(byte) 0xfd, (byte) 0x9e, (byte) 0xd0}},
        {new JP(null, new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xe9}},
        {new JP(null, new RegisterIndirectAddressing(RegisterPair.IX)), new byte[] {(byte) 0xdd, (byte) 0xe9}},
        {new JP(null, new RegisterIndirectAddressing(RegisterPair.IY)), new byte[] {(byte) 0xfd, (byte) 0xe9}},
        {new JP(null, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xc3, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.NZ), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xc2, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.Z), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xca, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.NC), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xd2, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.C), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xda, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.PO), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xe2, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.PE), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xea, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.P), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xf2, 0x00, 0x35}},
        {new JP(new ConditionParameter(Condition.M), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x3500)), 16)), new byte[] {(byte) 0xfa, 0x00, 0x35}},
        {new JR(null, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x0035)), 8)), new byte[] {0x18, 0x33}},
        {new JR(new ConditionParameter(Condition.NZ), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x0035)), 8)), new byte[] {0x20, 0x33}},
        {new JR(new ConditionParameter(Condition.Z), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x0035)), 8)), new byte[] {0x28, 0x33}},
        {new JR(new ConditionParameter(Condition.NC), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x0035)), 8)), new byte[] {0x30, 0x33}},
        {new JR(new ConditionParameter(Condition.C), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x0035)), 8)), new byte[] {0x38, 0x33}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.B)), new byte[] {(byte) 0xcb, 0x50}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.C)), new byte[] {(byte) 0xcb, 0x51}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.D)), new byte[] {(byte) 0xcb, 0x52}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.E)), new byte[] {(byte) 0xcb, 0x53}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.H)), new byte[] {(byte) 0xcb, 0x54}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.L)), new byte[] {(byte) 0xcb, 0x55}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xcb, 0x56}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.A)), new byte[] {(byte) 0xcb, 0x57}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, 0x66}},
        {new BIT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, 0x66}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.B)), new byte[] {(byte) 0xcb, (byte) 0x90}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.C)), new byte[] {(byte) 0xcb, (byte) 0x91}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.D)), new byte[] {(byte) 0xcb, (byte) 0x92}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.E)), new byte[] {(byte) 0xcb, (byte) 0x93}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.H)), new byte[] {(byte) 0xcb, (byte) 0x94}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.L)), new byte[] {(byte) 0xcb, (byte) 0x95}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xcb, (byte) 0x96}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.A)), new byte[] {(byte) 0xcb, (byte) 0x97}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, (byte) 0xa6}},
        {new RES(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, (byte) 0xa6}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.B)), new byte[] {(byte) 0xcb, (byte) 0xd0}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.C)), new byte[] {(byte) 0xcb, (byte) 0xd1}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.D)), new byte[] {(byte) 0xcb, (byte) 0xd2}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.E)), new byte[] {(byte) 0xcb, (byte) 0xd3}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.H)), new byte[] {(byte) 0xcb, (byte) 0xd4}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.L)), new byte[] {(byte) 0xcb, (byte) 0xd5}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {(byte) 0xcb, (byte) 0xd6}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0002)),
            8), new RegisterParameter(Register.A)), new byte[] {(byte) 0xcb, (byte) 0xd7}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xdd, (byte) 0xcb, (byte) 0x9a, (byte) 0xe6}},
        {new SET(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0004)),
            8), new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x9a)), 8))),
            new byte[] {(byte) 0xfd, (byte) 0xcb, (byte) 0x9a, (byte) 0xe6}},
        {new OUT(new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x0020)),
            8), new RegisterParameter(Register.A)), new byte[] {(byte) 0xd3, 0x20}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.B)), new byte[] {(byte) 0xed, 0x41}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x49}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.D)), new byte[] {(byte) 0xed, 0x51}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.E)), new byte[] {(byte) 0xed, 0x59}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.H)), new byte[] {(byte) 0xed, 0x61}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.L)), new byte[] {(byte) 0xed, 0x69}},
        {new OUT(new RegisterParameter(Register.C), new RegisterParameter(Register.A)), new byte[] {(byte) 0xed, 0x79}},
        {new IN(new RegisterParameter(Register.A), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x20)), 8)), new byte[] {(byte) 0xdb, 0x20}},
        {new IN(new RegisterParameter(Register.B), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x40}},
        {new IN(new RegisterParameter(Register.C), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x48}},
        {new IN(new RegisterParameter(Register.D), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x50}},
        {new IN(new RegisterParameter(Register.E), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x58}},
        {new IN(new RegisterParameter(Register.H), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x60}},
        {new IN(new RegisterParameter(Register.L), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x68}},
        {new IN(new RegisterParameter(Register.A), new RegisterParameter(Register.C)), new byte[] {(byte) 0xed, 0x78}},
        {new CALL(null, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xcd, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.NZ), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xc4, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.Z), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xcc, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.NC), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xd4, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.C), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xdc, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.PO), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xe4, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.PE), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xec, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.P), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xf4, (byte) 0xff,
              0x12}},
        {new CALL(new ConditionParameter(Condition.M), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x12ff)), 16)), new byte[] {(byte) 0xfc, (byte) 0xff,
              0x12}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.BC), new RegisterParameter(Register.A)), new byte[] {0x02}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.DE), new RegisterParameter(Register.A)), new byte[] {0x12}},
        {new LD(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.BC)), new byte[] {0x0a}},
        {new LD(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.DE)), new byte[] {0x1a}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.B)), new byte[] {0x70}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.C)), new byte[] {0x71}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.D)), new byte[] {0x72}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.E)), new byte[] {0x73}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.H)), new byte[] {0x74}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.L)), new byte[] {0x75}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new RegisterParameter(Register.A)), new byte[] {0x77}},
        {new LD(new RegisterParameter(Register.B), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x46}},
        {new LD(new RegisterParameter(Register.C), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x4e}},
        {new LD(new RegisterParameter(Register.D), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x56}},
        {new LD(new RegisterParameter(Register.E), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x5e}},
        {new LD(new RegisterParameter(Register.H), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x66}},
        {new LD(new RegisterParameter(Register.L), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x6e}},
        {new LD(new RegisterParameter(Register.A), new RegisterIndirectAddressing(RegisterPair.HL)), new byte[] {0x7e}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.BMarked)), new byte[] {0x40}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.CMarked)), new byte[] {0x41}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.DMarked)), new byte[] {0x42}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.EMarked)), new byte[] {0x43}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.HMarked)), new byte[] {0x44}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.LMarked)), new byte[] {0x45}},
        {new LD(new RegisterParameter(Register.B), new RegisterParameter(Register.AMarked)), new byte[] {0x47}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.BMarked)), new byte[] {0x48}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.CMarked)), new byte[] {0x49}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.DMarked)), new byte[] {0x4a}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.EMarked)), new byte[] {0x4b}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.HMarked)), new byte[] {0x4c}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.LMarked)), new byte[] {0x4d}},
        {new LD(new RegisterParameter(Register.C), new RegisterParameter(Register.AMarked)), new byte[] {0x4f}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.BMarked)), new byte[] {0x50}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.CMarked)), new byte[] {0x51}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.DMarked)), new byte[] {0x52}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.EMarked)), new byte[] {0x53}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.HMarked)), new byte[] {0x54}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.LMarked)), new byte[] {0x55}},
        {new LD(new RegisterParameter(Register.D), new RegisterParameter(Register.AMarked)), new byte[] {0x57}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.BMarked)), new byte[] {0x58}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.CMarked)), new byte[] {0x59}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.DMarked)), new byte[] {0x5a}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.EMarked)), new byte[] {0x5b}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.HMarked)), new byte[] {0x5c}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.LMarked)), new byte[] {0x5d}},
        {new LD(new RegisterParameter(Register.E), new RegisterParameter(Register.AMarked)), new byte[] {0x5f}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.BMarked)), new byte[] {0x60}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.CMarked)), new byte[] {0x61}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.DMarked)), new byte[] {0x62}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.EMarked)), new byte[] {0x63}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.HMarked)), new byte[] {0x64}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.LMarked)), new byte[] {0x65}},
        {new LD(new RegisterParameter(Register.H), new RegisterParameter(Register.AMarked)), new byte[] {0x67}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.BMarked)), new byte[] {0x68}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.CMarked)), new byte[] {0x69}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.DMarked)), new byte[] {0x6a}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.EMarked)), new byte[] {0x6b}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.HMarked)), new byte[] {0x6c}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.LMarked)), new byte[] {0x6d}},
        {new LD(new RegisterParameter(Register.L), new RegisterParameter(Register.AMarked)), new byte[] {0x6f}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.BMarked)), new byte[] {0x78}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.CMarked)), new byte[] {0x79}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.DMarked)), new byte[] {0x7a}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.EMarked)), new byte[] {0x7b}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.HMarked)), new byte[] {0x7c}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.LMarked)), new byte[] {0x7d}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.AMarked)), new byte[] {0x7f}},
        {new LD(new RegisterPairParameter(RegisterPair.SP), new RegisterPairParameter(RegisterPair.HL)),
            new byte[] {(byte) 0xf9}},
        {new LD(new RegisterParameter(Register.B), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x06, 0x4d}},
        {new LD(new RegisterParameter(Register.C), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x0e, 0x4d}},
        {new LD(new RegisterParameter(Register.D), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x16, 0x4d}},
        {new LD(new RegisterParameter(Register.E), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x1e, 0x4d}},
        {new LD(new RegisterParameter(Register.H), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x26, 0x4d}},
        {new LD(new RegisterParameter(Register.L), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x2e, 0x4d}},
        {new LD(new RegisterIndirectAddressing(RegisterPair.HL), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x36, 0x4d}},
        {new LD(new RegisterParameter(Register.A), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x4d)), 8)), new byte[] {0x3e, 0x4d}},
        {new LD(new RegisterPairParameter(RegisterPair.SP), new RegisterPairParameter(RegisterPair.IX)),
            new byte[] {(byte) 0xdd, (byte) 0xf9}},
        {new LD(new RegisterPairParameter(RegisterPair.SP), new RegisterPairParameter(RegisterPair.IY)),
            new byte[] {(byte) 0xfd, (byte) 0xf9}},
        {new LD(new RegisterParameter(Register.I), new RegisterParameter(Register.A)), new byte[] {(byte) 0xed, 0x47}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.I)), new byte[] {(byte) 0xed, 0x57}},
        {new LD(new RegisterParameter(Register.R), new RegisterParameter(Register.A)), new byte[] {(byte) 0xed, 0x4f}},
        {new LD(new RegisterParameter(Register.A), new RegisterParameter(Register.R)), new byte[] {(byte) 0xed, 0x5f}},
        {new LD(new RegisterPairParameter(RegisterPair.BC), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x8f00)), 16)), new byte[] {0x01, 0x00, (byte) 0x8f}},
        {new LD(new RegisterPairParameter(RegisterPair.DE), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x8f00)), 16)), new byte[] {0x11, 0x00, (byte) 0x8f}},
        {new LD(new RegisterPairParameter(RegisterPair.HL), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x8f00)), 16)), new byte[] {0x21, 0x00, (byte) 0x8f}},
        {new LD(new RegisterPairParameter(RegisterPair.SP), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x8f00)), 16)), new byte[] {0x31, 0x00, (byte) 0x8f}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2100)), 16)),
            new RegisterPairParameter(RegisterPair.HL)), new byte[] {0x22, 0x00, 0x21}},
        {new LD(new RegisterPairParameter(RegisterPair.HL), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x2100)), 16))),
            new byte[] {0x2a, 0x00, 0x21}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2100)), 16)), new RegisterParameter(Register.A)),
            new byte[] {0x32, 0x00, 0x21}},
        {new LD(new RegisterParameter(Register.A), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x2100)), 16))),
            new byte[] {0x3a, 0x00, 0x21}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.B)),
            new byte[] {(byte) 0xdd, 0x70, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.C)),
            new byte[] {(byte) 0xdd, 0x71, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.D)),
            new byte[] {(byte) 0xdd, 0x72, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.E)),
            new byte[] {(byte) 0xdd, 0x73, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.H)),
            new byte[] {(byte) 0xdd, 0x74, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.L)),
            new byte[] {(byte) 0xdd, 0x75, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.A)),
            new byte[] {(byte) 0xdd, 0x77, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.B)),
            new byte[] {(byte) 0xfd, 0x70, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.C)),
            new byte[] {(byte) 0xfd, 0x71, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.D)),
            new byte[] {(byte) 0xfd, 0x72, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.E)),
            new byte[] {(byte) 0xfd, 0x73, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.H)),
            new byte[] {(byte) 0xfd, 0x74, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.L)),
            new byte[] {(byte) 0xfd, 0x75, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new RegisterParameter(Register.A)),
            new byte[] {(byte) 0xfd, 0x77, 0x2d}},
        {new LD(new RegisterParameter(Register.B), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x46, 0x2d}},
        {new LD(new RegisterParameter(Register.C), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x4e, 0x2d}},
        {new LD(new RegisterParameter(Register.D), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x56, 0x2d}},
        {new LD(new RegisterParameter(Register.E), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x5e, 0x2d}},
        {new LD(new RegisterParameter(Register.H), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x66, 0x2d}},
        {new LD(new RegisterParameter(Register.L), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x6e, 0x2d}},
        {new LD(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IX,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xdd, 0x7e, 0x2d}},
        {new LD(new RegisterParameter(Register.B), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x46, 0x2d}},
        {new LD(new RegisterParameter(Register.C), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x4e, 0x2d}},
        {new LD(new RegisterParameter(Register.D), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x56, 0x2d}},
        {new LD(new RegisterParameter(Register.E), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x5e, 0x2d}},
        {new LD(new RegisterParameter(Register.H), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x66, 0x2d}},
        {new LD(new RegisterParameter(Register.L), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x6e, 0x2d}},
        {new LD(new RegisterParameter(Register.A), new IndexedAddressingParameter(RegisterPair.IY,
            new ExpressionParameter(new ConstantValueExpression(new ConstantValueParameter(0x2d)),
                8))), new byte[] {(byte) 0xfd, 0x7e, 0x2d}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IX, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x00ba)), 8)),
            new byte[] {(byte) 0xdd, 0x34, 0x2d, (byte) 0xba}},
        {new LD(new IndexedAddressingParameter(RegisterPair.IY, new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x2d)), 8)), new ExpressionParameter(
                new ConstantValueExpression(new ConstantValueParameter(0x00be)), 8)),
            new byte[] {(byte) 0xfd, 0x34, 0x2d, (byte) 0xbe}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x5a00)), 16)),
            new RegisterPairParameter(RegisterPair.BC)), new byte[] {(byte) 0xed, 0x43, 0x00, 0x5a}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x5a00)), 16)),
            new RegisterPairParameter(RegisterPair.DE)), new byte[] {(byte) 0xed, 0x53, 0x00, 0x5a}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x5a00)), 16)),
            new RegisterPairParameter(RegisterPair.HL)), new byte[] {0x22, 0x00, 0x5a}},
        // this is an exception, shorter code exists
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x5a00)), 16)),
            new RegisterPairParameter(RegisterPair.SP)), new byte[] {(byte) 0xed, 0x73, 0x00, 0x5a}},
        {new LD(new RegisterPairParameter(RegisterPair.BC), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x5a00)), 16))),
            new byte[] {(byte) 0xed, 0x4b, 0x00, 0x5a}},
        {new LD(new RegisterPairParameter(RegisterPair.DE), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x5a00)), 16))),
            new byte[] {(byte) 0xed, 0x5b, 0x00, 0x5a}},
        {new LD(new RegisterPairParameter(RegisterPair.HL), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x5a00)), 16))),
            new byte[] {0x2a, 0x00, 0x5a}}, // this is an exception, shorter code exists
        {new LD(new RegisterPairParameter(RegisterPair.SP), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0x5a00)), 16))),
            new byte[] {(byte) 0xed, 0x7b, 0x00, 0x5a}},
        {new LD(new RegisterPairParameter(RegisterPair.IX), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0xcafe)), 16)), new byte[] {(byte) 0xdd, 0x21,
              (byte) 0xfe, (byte) 0xca}},
        {new LD(new RegisterPairParameter(RegisterPair.IY), new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0xcafe)), 16)), new byte[] {(byte) 0xfd, 0x21,
              (byte) 0xfe, (byte) 0xca}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0xde00)), 16)),
            new RegisterPairParameter(RegisterPair.IX)), new byte[] {(byte) 0xdd, 0x22, 0x00, (byte) 0xde}},
        {new LD(new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0xde00)), 16)),
            new RegisterPairParameter(RegisterPair.IY)), new byte[] {(byte) 0xfd, 0x22, 0x00, (byte) 0xde}},
        {new LD(new RegisterPairParameter(RegisterPair.IX), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0xde00)), 16))),
            new byte[] {(byte) 0xdd, 0x2a, 0x00, (byte) 0xde}},
        {new LD(new RegisterPairParameter(RegisterPair.IY), new ImmediateAddressingParameter(new ExpressionParameter(
            new ConstantValueExpression(new ConstantValueParameter(0xde00)), 16))),
            new byte[] {(byte) 0xfd, 0x2a, 0x00, (byte) 0xde}}
      }).map(Arguments::of);
    }
    
  }
  
  @ParameterizedTest
  @ArgumentsSource(ConverterArgumentProvider.class)
  public void testTwoParameterConverterFromInstruction(TwoParameterInstruction instruction, byte[] assembly) {
    byte[] result = new TwoParameterInstructionConverter().convert(null, instruction);
    
    assertNotNull(result, "Converted result expected");
    assertArrayEquals(assembly, result, "Opcode must be equal");
  }
  
  @ParameterizedTest
  @ArgumentsSource(ConverterArgumentProvider.class)
  public void testTwoParameterConverterToInstruction(TwoParameterInstruction instruction, byte[] assembly)
      throws IOException {
    TwoParameterInstruction result = new TwoParameterInstructionConverter().convert(new PushbackInputStream(
        new ByteArrayInputStream(assembly)));
      
    assertNotNull(result, "Converted result expected");
    assertEquals(instruction.getClass(), result.getClass(), "Class type must match");
    assertEquals(instruction.getTarget(), result.getTarget(), "Target parameter must match");
    assertEquals(instruction.getSource(), result.getSource(), "Source parameter must match");
  }
  
  @Test
  public void testTwoParameterConverterToInstructionLDException1() throws IOException {
    testTwoParameterConverterToInstruction(new LD(new ImmediateAddressingParameter(new ExpressionParameter(
        new ConstantValueExpression(new ConstantValueParameter(0x5a00)), 16)),
        new RegisterPairParameter(RegisterPair.HL)), new byte[] {(byte) 0xed, 0x63, 0x00, 0x5a});
  }
  
  @Test
  public void testTwoParameterConverterToInstructionLDException2() throws IOException {
    testTwoParameterConverterToInstruction(new LD(new RegisterPairParameter(RegisterPair.HL),
        new ImmediateAddressingParameter(new ExpressionParameter(new ConstantValueExpression(
            new ConstantValueParameter(0x5a00)), 16))),
        new byte[] {(byte) 0xed, 0x6b, 0x00, 0x5a});
  }
  
  @Test
  public void testUnknownInstructionOneByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x04}));
    TwoParameterInstruction result = new TwoParameterInstructionConverter().convert(pis);
      
    assertNull(result, "Converted result not expected");
    assertEquals(0x04, pis.read(), "Rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }
  
  @Test
  public void testUnknownInstructionTwoByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x04, 0x03}));
    TwoParameterInstruction result = new TwoParameterInstructionConverter().convert(pis);
      
    assertNull(result, "Converted result not expected");
    assertEquals(0x04, pis.read(), "First rolled back value expected");
    assertEquals(0x03, pis.read(), "Second rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }
  
  @Test
  public void testUnknownInstructionThreeByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x04, 0x03, 0x02}));
    TwoParameterInstruction result = new TwoParameterInstructionConverter().convert(pis);
      
    assertNull(result, "Converted result not expected");
    assertEquals(0x04, pis.read(), "First rolled back value expected");
    assertEquals(0x03, pis.read(), "Second rolled back value expected");
    assertEquals(0x02, pis.read(), "Third rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }
  
  @Test
  public void testUnknownInstructionFourByte() throws IOException {
    PushbackInputStream pis = new PushbackInputStream(new ByteArrayInputStream(new byte[] {0x04, 0x03, 0x02, 0x01}));
    TwoParameterInstruction result = new TwoParameterInstructionConverter().convert(pis);
      
    assertNull(result, "Converted result not expected");
    assertEquals(0x04, pis.read(), "First rolled back value expected");
    assertEquals(0x03, pis.read(), "Second rolled back value expected");
    assertEquals(0x02, pis.read(), "Third rolled back value expected");
    assertEquals(0x01, pis.read(), "Fourth rolled back value expected");
    assertEquals(-1, pis.read(), "End of stream expected");
  }
  
}
