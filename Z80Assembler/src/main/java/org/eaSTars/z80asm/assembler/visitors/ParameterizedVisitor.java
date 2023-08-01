package org.eaSTars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.eaSTars.z80asm.ast.parameter.*;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public abstract class ParameterizedVisitor<T, C extends InstructionContext, P extends ParserRuleContext> extends Z80AssemblerBaseVisitor<T> {

	private final RegistersVisitor registersVisitor = new RegistersVisitor();
	
	private final RegistersMarkedVisitor registersMarkedVisitor = new RegistersMarkedVisitor();
	
	private final RegisterSSVisitor registerSSVisitor = new RegisterSSVisitor();
	
	private final RegisterPPVisitor registerPPVisitor = new RegisterPPVisitor();
	
	private final RegisterRRVisitor registerRRVisitor = new RegisterRRVisitor();
	
	private final RegisterQQVisitor registerQQVisitor = new RegisterQQVisitor();
	
	private final RegistersWithReferenceVisitor registersWithReferenceVisitor = new RegistersWithReferenceVisitor();
	
	private final ParameterTVisitor parameterTVisitor = new ParameterTVisitor();
	
	private final IndexedReferenceVisitor indexedReferenceVisitor = new IndexedReferenceVisitor();
	
	private final HexValueVisitor hexValueVisitor = new HexValueVisitor();
	
	protected abstract T getInstruction();
	
	protected abstract P getInstructionParameters(C ctx);
	
	private interface MethodCaller <CTX extends ParserRuleContext> {
		public Parameter callmethod(CTX ctx);
	}
	
	private <PT, CTX extends ParserRuleContext> Optional<Parameter> getParameter(P paramCtx, Class<PT> type, String methodname, Class<CTX> ctxtype, MethodCaller<CTX> methodCaller) {
		if (type.isAssignableFrom(paramCtx.getClass())) {
			try {
				Method method = type.getMethod(methodname);
				Object ctx = method.invoke(paramCtx);
				return Optional.of(methodCaller.callmethod(ctxtype.cast(ctx)));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				// incorrect declaration?
			}
		}
		return Optional.empty();
	}
	
	protected <PT> Optional<Parameter> getRegisterSSParameter(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registerSS", RegisterSSContext.class, registerSSVisitor::visitRegisterSS);
	}
	
	protected Optional<Parameter> getRegisterSSParameter(RegisterSSContext ctx) {
		return Optional.ofNullable(ctx).map(registerSSVisitor::visitRegisterSS);
	}
	
	protected Optional<Parameter> getRegisterPPParameter(RegisterPPContext ctx) {
		return Optional.ofNullable(ctx).map(registerPPVisitor::visitRegisterPP);
	}
	
	protected Optional<Parameter> getRegisterRRParameter(RegisterRRContext ctx) {
		return Optional.ofNullable(ctx).map(registerRRVisitor::visitRegisterRR);
	}
	
	protected Optional<Parameter> getRegisterQQParameter(RegisterQQContext ctx) {
		return Optional.ofNullable(ctx).map(registerQQVisitor::visitRegisterQQ);
	}
	
	protected <PT> Optional<Parameter> getRegistersWithReference(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registersWithReference", RegistersWithReferenceContext.class, registersWithReferenceVisitor::visitRegistersWithReference);
	}
	
	protected Optional<Parameter> getRegistersWithReference(RegistersWithReferenceContext ctx) {
		return Optional.ofNullable(ctx).map(registersWithReferenceVisitor::visitRegistersWithReference);
	}
	
	protected <PT> Optional<Parameter> getRegisters(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registers", RegistersContext.class, registersVisitor::visitRegisters);
	}
	
	protected Optional<Parameter> getRegisters(RegistersContext ctx) {
		return Optional.ofNullable(ctx).map(registersVisitor::visitRegisters);
	}
	
	protected <PT> Optional<Parameter> getRegistersMarked(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registersmarked", RegistersmarkedContext.class, registersMarkedVisitor::visitRegistersmarked);
	}
	
	protected Optional<Parameter> getParameterT(ParameterTContext ctx) {
		return Optional.ofNullable(ctx).map(parameterTVisitor::visitParameterT);
	}
	
	protected <PT> Optional<Parameter> getIndexedReference(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "indexedReference", IndexedReferenceContext.class, indexedReferenceVisitor::visitIndexedReference);
	}
	
	protected Optional<Parameter> getIndexedReference(IndexedReferenceContext ctx) {
		return Optional.ofNullable(ctx).map(indexedReferenceVisitor::visitIndexedReference);
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex16bitsContext ctx) {
		return Optional.ofNullable(ctx).map(hexValueVisitor::visitHex16bits);
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex8bitsContext ctx) {
		return Optional.ofNullable(ctx).map(hexValueVisitor::visitHex8bits);
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex3bitsContext ctx) {
		return Optional.ofNullable(ctx).map(hexValueVisitor::visitHex3bits);
	}
	
	protected Optional<Parameter> getRegister(Token token, Register register) {
		return Optional.ofNullable(token).map(t -> new RegisterParameter(register));
	}
	
	protected Optional<Parameter> getRegisterPair(Token token, RegisterPair register) {
		return Optional.ofNullable(token).map(t -> new RegisterPairParameter(register));
	}
	
	protected Optional<Parameter> getRegisterIndirectAddressing(Token token, RegisterPair register) {
		return Optional.ofNullable(token).map(t -> new RegisterIndirectAddressing(register));
	}

}
