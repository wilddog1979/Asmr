package org.eaSTars.z80asm.ast.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.eaSTars.asm.assember.LabelNotFoundException;
import org.eaSTars.asm.ast.CompilationUnit;
import org.eaSTars.asm.ast.InstructionLine;
import org.eaSTars.z80asm.assembler.visitors.ExpressionVisitor;
import org.eaSTars.z80asm.ast.expression.ConstantValueExpression;
import org.eaSTars.z80asm.ast.expression.Expression;
import org.eaSTars.z80asm.ast.expression.OneParameterExpression;
import org.eaSTars.z80asm.ast.expression.TwoOperandExpression;
import org.eaSTars.z80asm.ast.parameter.ConstantValueParameter;
import org.eaSTars.z80asm.parser.Z80AssemblerLexer;
import org.eaSTars.z80asm.parser.Z80AssemblerParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class ExpressionTest {

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
		Expression result = invokeParser(testexpression);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof TwoOperandExpression, "Test expression must be an instance of TwoOperandExpression");
		
		TwoOperandExpression twoOperandExpression = (TwoOperandExpression) result;
		
		assertEquals(operation, twoOperandExpression.getOperation(), "Operation should match");
		
		assertParameter("Left", twoOperandExpression.getLeftOperand(), leftValue);
		assertParameter("Right", twoOperandExpression.getRightOperand(), rightValue);
		
		assertEquals(evaluated, result.evaluate(new CompilationUnit()), "The expression evaluation should match");
		
		assertEquals(assembly, result.getAssembly(), "Assembly must match");
	}
	
	@ParameterizedTest
	@ArgumentsSource(OneParameterExpressionArgumentProvider.class)
	public void testOneParameterExpressions(String testexpression, OneParameterExpression.Operation operation, int intvalue, int evaluated, String assembly) {
		Expression result = invokeParser(testexpression);
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof OneParameterExpression, "Test expression must be an instance of OneOperandExpression");
		
		OneParameterExpression oneParameterExpression = (OneParameterExpression) result;
		
		assertEquals(operation, oneParameterExpression.getOperation(), "Operation should match");
		
		assertParameter("The", oneParameterExpression.getParameter(), intvalue);
		
		assertEquals(evaluated, result.evaluate(new CompilationUnit()), "The expression evaluation should match");
		
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
		Expression result = invokeParser("0045h");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getValue(), "Value of ContantValueParameter should be null");
		assertNotNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should not be null");
		assertEquals(0x45, contantValueParameter.getIntValue().intValue(), "IntValue of ConstantValueParameter should match");
		
		CompilationUnit compilationUnit = new CompilationUnit();
		
		assertEquals(0x45, result.evaluate(compilationUnit), "The expression evaluation should match");
	}
	
	@Test
	public void testConstantValueExpressionWithLabelFound() {
		Expression result = invokeParser("@testlabel12");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
		assertEquals("@testlabel12", contantValueParameter.getValue(), "Value of ConstantValueParameter should match");
		
		CompilationUnit compilationUnit = new CompilationUnit();
		compilationUnit.setAddresscounter(0xcafe);
		InstructionLine instructionline = new InstructionLine();
		instructionline.setLabel("@testlabel12");
		compilationUnit.addLine(instructionline);
		
		assertEquals(0xcafe, result.evaluate(compilationUnit), "The expression evaluation should match");
	}
	
	@Test
	public void testConstantValueExpressionWithLabelNotFound() {
		Expression result = invokeParser("@testlabel12");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should be null");
		assertEquals("@testlabel12", contantValueParameter.getValue(), "Value of ConstantValueParameter should match");
		
		CompilationUnit compilationUnit = new CompilationUnit();
		compilationUnit.setAddresscounter(0xcafe);
		InstructionLine instructionline = new InstructionLine();
		instructionline.setLabel("@testlabel13");
		compilationUnit.addLine(instructionline);
		
		assertThrows(LabelNotFoundException.class, () -> result.evaluate(compilationUnit), "LabelNotFoundException expected");
	}
	
	@Test
	public void testGrouping() {
		Expression result = invokeParser("(0045h)");
		
		assertNotNull(result, "Instruction must be recognized");
		assertTrue(result instanceof ConstantValueExpression, "Test expression must be an instance of ConstantValueExpression");
		
		ConstantValueExpression constantValueExpression = (ConstantValueExpression) result;
		
		ConstantValueParameter contantValueParameter = constantValueExpression.getContantValueParameter();
		
		assertNotNull(contantValueParameter, "ContantValueParameter of the expression should not be null");
		
		assertNull(contantValueParameter.getValue(), "Value of ContantValueParameter should be null");
		assertNotNull(contantValueParameter.getIntValue(), "IntValue of ConstantValueParameter should not be null");
		assertEquals(0x45, contantValueParameter.getIntValue().intValue(), "IntValue of ConstantValueParameter should match");
	}
	
	private Expression invokeParser(String content) {
		CharStream charStream = CharStreams.fromString(content);
		Lexer lexer = new Z80AssemblerLexer(charStream);
		TokenStream tokenStream = new CommonTokenStream(lexer);
		Z80AssemblerParser parser = new Z80AssemblerParser(tokenStream);
		
		return new ExpressionVisitor().visitExpression(parser.expression());
	}
}
