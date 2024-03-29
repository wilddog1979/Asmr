package org.eastars.z80asm.ast.test;

import org.eastars.asm.AbstractTester;
import org.eastars.asm.assember.CompilationContext;
import org.eastars.asm.assember.CompilationContext.Phase;
import org.eastars.asm.assember.LabelNotFoundException;
import org.eastars.asm.assember.MismatchingParameterSizeException;
import org.eastars.asm.ast.InstructionLine;
import org.eastars.z80asm.ast.expression.ConstantValueExpression;
import org.eastars.z80asm.ast.expression.Expression;
import org.eastars.z80asm.ast.expression.OneParameterExpression;
import org.eastars.z80asm.ast.expression.TwoOperandExpression;
import org.eastars.z80asm.ast.parameter.ConstantValueParameter;
import org.eastars.z80asm.ast.parameter.ExpressionParameter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionTest extends AbstractTester {

  private static class OneParameterExpressionArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new Object[][] {
        {"-0005h", OneParameterExpression.Operation.MINUS, 0x05, -0x05, "-(0005h)"},
        {"!0005h", OneParameterExpression.Operation.NOT, 0x05, ~0x05, "!(0005h)"}
      }).map(Arguments::of);
    }

  }

  private static class TwoParameterExpressionArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(new Object[][] {
        {"0001h | 0002h", TwoOperandExpression.Operation.OR, 0x01, 0x02, 0x03, "(0001h) | (0002h)"},
        {"0003h ^ 0001h", TwoOperandExpression.Operation.XOR, 0x03, 0x01, 0x02, "(0003h) ^ (0001h)"},
        {"0003h & 0001h", TwoOperandExpression.Operation.AND, 0x03, 0x01, 0x01, "(0003h) & (0001h)"},
        {"0002h << 0001h", TwoOperandExpression.Operation.SHL, 0x02, 0x01, 0x04, "(0002h) << (0001h)"},
        {"0008h >> 0002h", TwoOperandExpression.Operation.SHR, 0x08, 0x02, 0x02, "(0008h) >> (0002h)"},
        {"0003h + 0005h", TwoOperandExpression.Operation.PLUS, 0x03, 0x05, 0x08, "(0003h) + (0005h)"},
        {"0003h - 0005h", TwoOperandExpression.Operation.MINUS, 0x03, 0x05, -0x02, "(0003h) - (0005h)"},
        {"0003h * 0005h", TwoOperandExpression.Operation.STAR, 0x03, 0x05, 0x0f, "(0003h) * (0005h)"},
        {"0007h / 0002h", TwoOperandExpression.Operation.DIV, 0x07, 0x02, 0x03, "(0007h) / (0002h)"}
      }).map(Arguments::of);
    }

  }

  @ParameterizedTest
  @ArgumentsSource(TwoParameterExpressionArgumentProvider.class)
  public void testTwoParameterExpressions(String testExpression, TwoOperandExpression.Operation operation,
                                          int leftValue, int rightValue, int evaluated, String assembly) {
    Expression result = getExpression(testExpression);

    assertNotNull(result, "Instruction must be recognized");
    assertTrue(result instanceof TwoOperandExpression,
        "Test expression must be an instance of TwoOperandExpression");

    TwoOperandExpression twoOperandExpression = (TwoOperandExpression) result;

    assertEquals(operation, twoOperandExpression.getOperation(), "Operation should match");

    assertParameter("Left", twoOperandExpression.getLeftOperand(), leftValue);
    assertParameter("Right", twoOperandExpression.getRightOperand(), rightValue);

    assertEquals(evaluated, result.evaluate(new CompilationContext()),
        "The expression evaluation should match");

    assertEquals(assembly, result.getAssembly(), "Assembly must match");
  }

  @ParameterizedTest
  @ArgumentsSource(OneParameterExpressionArgumentProvider.class)
  public void testOneParameterExpressions(String testExpression, OneParameterExpression.Operation operation,
                                          int intvalue, int evaluated, String assembly) {
    Expression result = getExpression(testExpression);

    assertNotNull(result, "Instruction must be recognized");
    assertTrue(result instanceof OneParameterExpression,
        "Test expression must be an instance of OneOperandExpression");

    OneParameterExpression oneParameterExpression = (OneParameterExpression) result;

    assertEquals(operation, oneParameterExpression.getOperation(), "Operation should match");

    assertParameter("The", oneParameterExpression.getParameter(), intvalue);

    assertEquals(evaluated, result.evaluate(new CompilationContext()),
        "The expression evaluation should match");

    assertEquals(assembly, result.getAssembly(), "Assembly must match");
  }

  private void assertParameter(String prefix, Expression parameter, int intValue) {
    assertNotNull(parameter, () -> String.format("%s parameter should not be null", prefix));

    assertInstanceOf(ConstantValueExpression.class, parameter,
        () -> String.format("%s expression must be an instance of ConstantValueExpression", prefix));

    ConstantValueExpression constantValueExpression = (ConstantValueExpression) parameter;

    ConstantValueParameter constantValueParameter = constantValueExpression.getConstantValueParameter();

    assertNotNull(constantValueParameter,
        () -> String.format("ConstantValueParameter of %s expression should not be null", prefix.toLowerCase()));

    assertNull(constantValueParameter.getValue(),
        () -> String.format("Value of ConstantValueParameter in %s expression should be null", prefix.toLowerCase()));
    assertNotNull(constantValueParameter.getIntValue(),
        String.format("IntValue of ConstantValueParameter in %s expression should not be null", prefix.toLowerCase()));
    assertEquals(intValue, constantValueParameter.getIntValue().intValue(),
        () -> String.format("IntValue of ConstantValueParameter in %s expression should match", prefix.toLowerCase()));
  }

  @Test
  public void testConstantValueExpressionWithValue() {
    Expression result = getExpression("0045h");

    assertNotNull(result, "Instruction must be recognized");
    assertInstanceOf(ConstantValueExpression.class, result,
        "Test expression must be an instance of ConstantValueExpression");

    ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;

    ConstantValueParameter constantValueParameter = constantValueExpression.getConstantValueParameter();

    assertNotNull(constantValueParameter, "ConstantValueParameter of the expression should not be null");

    assertNull(constantValueParameter.getValue(), "Value of ConstantValueParameter should be null");
    assertNotNull(constantValueParameter.getIntValue(),
        "IntValue of ConstantValueParameter should not be null");
    assertEquals(0x45, constantValueParameter.getIntValue().intValue(),
        "IntValue of ConstantValueParameter should match");

    CompilationContext compilationContext = new CompilationContext();

    assertEquals(0x45, result.evaluate(compilationContext), "The expression evaluation should match");
  }

  @Test
  public void testConstantValueExpressionWithLabelFound() {
    Expression result = getExpression("@testlabel12");

    assertNotNull(result, "Instruction must be recognized");
    assertInstanceOf(ConstantValueExpression.class, result,
        "Test expression must be an instance of ConstantValueExpression");

    ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;

    ConstantValueParameter contantValueParameter = constantValueExpression.getConstantValueParameter();

    assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");

    assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
    assertEquals("@testlabel12", contantValueParameter.getValue(),
        "Value of ConstantValueParameter should match");

    CompilationContext compilationContext = new CompilationContext();
    compilationContext.setAddress(0xcafe);
    InstructionLine instructionline = new InstructionLine();
    instructionline.setLabel("@testlabel12");
    compilationContext.addInstructionLine(instructionline, 0);
    compilationContext.setPhase(Phase.COMPILATION);

    assertEquals(0xcafe, result.evaluate(compilationContext),
        "The expression evaluation should match");
  }

  @Test
  public void testConstantValueExpressionWithLabelNotFound() {
    Expression result = getExpression("@testlabel12");

    assertNotNull(result, "Instruction must be recognized");
    assertInstanceOf(ConstantValueExpression.class, result,
        "Test expression must be an instance of ConstantValueExpression");

    ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;

    ConstantValueParameter contantValueParameter = constantValueExpression.getConstantValueParameter();

    assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");

    assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
    assertEquals("@testlabel12", contantValueParameter.getValue(),
        "Value of ConstantValueParameter should match");

    CompilationContext compilationContext = new CompilationContext();
    InstructionLine instructionline = new InstructionLine();
    instructionline.setLabel("@testlabel13");
    compilationContext.addInstructionLine(instructionline, 0);
    compilationContext.setPhase(Phase.COMPILATION);

    assertThrows(LabelNotFoundException.class, () -> result.evaluate(compilationContext),
        "LabelNotFoundException expected");
  }

  @Test
  public void testGrouping() {
    Expression result = getExpression("(0045h)");

    assertNotNull(result, "Instruction must be recognized");
    assertInstanceOf(ConstantValueExpression.class, result,
        "Test expression must be an instance of ConstantValueExpression");

    ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;

    ConstantValueParameter contantValueParameter = constantValueExpression.getConstantValueParameter();

    assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");

    assertNull(contantValueParameter.getValue(), "Value of ContantValueParameter should be null");
    assertNotNull(contantValueParameter.getIntValue(),
        "IntValue of ConstantValueParameter should not be null");
    assertEquals(0x45, contantValueParameter.getIntValue().intValue(),
        "IntValue of ConstantValueParameter should match");
  }

  @Test
  public void testNoMismatchingParameterSize() {
    Expression expression = getExpression("0021h");
    ExpressionParameter parameter = new ExpressionParameter(expression, 8);

    int result = parameter.getExpressionValue(null);

    assertEquals(0x21, result, "expression value must match");
  }

  @Test
  public void testMismatchingParameterSize() {
    Expression result = getExpression("2100h");
    ExpressionParameter parameter = new ExpressionParameter(result, 8);

    assertThrows(MismatchingParameterSizeException.class,
        () -> parameter.getExpressionValue(null), "MismatchingParameterSizeException expected");
  }

}
