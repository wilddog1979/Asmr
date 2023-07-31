package org.eaSTars.z80asm.ast.test;

import org.eaSTars.asm.AbstractTester;
import org.eaSTars.asm.assember.CompilationContext;
import org.eaSTars.asm.assember.CompilationContext.Phase;
import org.eaSTars.asm.assember.LabelNotFoundException;
import org.eaSTars.asm.assember.MismatchingParameterSizeException;
import org.eaSTars.asm.ast.InstructionLine;
import org.eaSTars.z80asm.ast.expression.ConstantValueExpression;
import org.eaSTars.z80asm.ast.expression.Expression;
import org.eaSTars.z80asm.ast.expression.OneParameterExpression;
import org.eaSTars.z80asm.ast.expression.TwoOperandExpression;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.ast.parameter.ExpressionParameter;
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
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return Stream.of(new Object[][] {
				{"-0005h", OneParameterExpression.Operation.MINUS, 0x05, -0x05, "-(0005h)"},
				{"!0005h", OneParameterExpression.Operation.NOT, 0x05, ~0x05, "!(0005h)"}
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	private static class TwoParameterExpressionArgumentProvider implements ArgumentsProvider {

		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
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
			}).map(i -> Arguments.of(i));
		}
		
	}
	
	@ParameterizedTest
	@ArgumentsSource(TwoParameterExpressionArgumentProvider.class)
	public void testTwoParameterExpressions(String testexpression, TwoOperandExpression.Operation operation, int leftValue, int rightValue, int evaluated, String assembly) {
		Expression result = getExpression(testexpression);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof TwoOperandExpression, "Test expression must be an instance of TwoOperandExpression");
		
		TwoOperandExpression twoOperandExpression = (TwoOperandExpression) result;
		
		assertEquals(operation, twoOperandExpression.getOperation(), "Operation should match");
		
		assertParameter("Left", twoOperandExpression.getLeftOperand(), leftValue);
		assertParameter("Right", twoOperandExpression.getRightOperand(), rightValue);
		
		assertEquals(evaluated, result.evaluate(new CompilationContext()), "The expression evaluation should match");
		
		assertEquals(assembly, result.getAssembly(), "Assembly must match");
	}
	
	@ParameterizedTest
	@ArgumentsSource(OneParameterExpressionArgumentProvider.class)
	public void testOneParameterExpressions(String testexpression, OneParameterExpression.Operation operation, int intvalue, int evaluated, String assembly) {
		Expression result = getExpression(testexpression);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof OneParameterExpression, "Test expression must be an instance of OneOperandExpression");
		
		OneParameterExpression oneParameterExpression = (OneParameterExpression) result;
		
		assertEquals(operation, oneParameterExpression.getOperation(), "Operation should match");
		
		assertParameter("The", oneParameterExpression.getParameter(), intvalue);
		
		assertEquals(evaluated, result.evaluate(new CompilationContext()), "The expression evaluation should match");
		
		assertEquals(assembly, result.getAssembly(), "Assembly must match");
	}
	
	private void assertParameter(String prefix, Expression parameter, int intValue) {
		assertNotNull(parameter, () -> String.format("%s parameter should not be null", prefix));
		
		assertTrue(parameter instanceof ConstantValueExpression, () -> String.format("%s expression must be an instance of ConstantValueExpression", prefix));
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) parameter;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, () -> String.format("ContantValueParameter of %s expression should not be null", prefix.toLowerCase()));
		
		assertNull(contantValueParameter.getValue(), () -> String.format("Value of ContantValueParameter in %s expression should be null", prefix.toLowerCase()));
		assertNotNull(contantValueParameter.getIntValue(), String.format("IntValue of ConstantValueParameter in %s expression should not be null", prefix.toLowerCase()));
		assertEquals(intValue, contantValueParameter.getIntValue().intValue(), () -> String.format("IntValue of ConstantValueParameter in %s expression should match", prefix.toLowerCase()));
	}
	
	@Test
	public void testConstantValueExpressionWithValue() {
		Expression result = getExpression("0045h");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getValue(), "Value of ContantValueParameter should be null");
		assertNotNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should not be null");
		assertEquals(0x45, contantValueParameter.getIntValue().intValue(), "IntValue of ConstantValueParameter should match");
		
		CompilationContext compilationContext = new CompilationContext();
		
		assertEquals(0x45, result.evaluate(compilationContext), "The expression evaluation should match");
	}
	
	@Test
	public void testConstantValueExpressionWithLabelFound() {
		Expression result = getExpression("@testlabel12");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
		assertEquals("@testlabel12", contantValueParameter.getValue(), "Value of ConstantValueParameter should match");
		
		CompilationContext compilationContext = new CompilationContext();
		compilationContext.setAddress(0xcafe);
		InstructionLine instructionline = new InstructionLine();
		instructionline.setLabel("@testlabel12");
		compilationContext.addInstructionLine(instructionline, 0);
		compilationContext.setPhase(Phase.COMPILATION);
		
		assertEquals(0xcafe, result.evaluate(compilationContext), "The expression evaluation should match");
	}
	
	@Test
	public void testConstantValueExpressionWithLabelNotFound() {
		Expression result = getExpression("@testlabel12");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
		assertEquals("@testlabel12", contantValueParameter.getValue(), "Value of ConstantValueParameter should match");
		
		CompilationContext compilationContext = new CompilationContext();
		InstructionLine instructionline = new InstructionLine();
		instructionline.setLabel("@testlabel13");
		compilationContext.addInstructionLine(instructionline, 0);
		compilationContext.setPhase(Phase.COMPILATION);
		
		assertThrows(LabelNotFoundException.class, () -> result.evaluate(compilationContext), "LabelNotFoundException expected");
	}
	
	@Test
	public void testGrouping() {
		Expression result = getExpression("(0045h)");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getValue(), "Value of ContantValueParameter should be null");
		assertNotNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should not be null");
		assertEquals(0x45, contantValueParameter.getIntValue().intValue(), "IntValue of ConstantValueParameter should match");
	}
	
	@Test
	public void testNoMismatchingParameterSize() {
		Expression expression = getExpression("0021h");
		ExpressionParameter parameter = new ExpressionParameter(expression, 8);
		
		try {
			int result = parameter.getExpressionValue(null);
			assertEquals(0x21, result, "expression value must match");
		} catch (MismatchingParameterSizeException e) {
			fail("Unexpected MismatchingParameterSizeException");
		}
	}
	
	@Test
	public void testMismatchingParameterSize() {
		Expression result = getExpression("2100h");
		ExpressionParameter parameter = new ExpressionParameter(result, 8);
		
		assertThrows(MismatchingParameterSizeException.class, () -> parameter.getExpressionValue(null), "MismatchingParameterSizeException expected");
	}
	
}
