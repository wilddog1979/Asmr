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

	private RegistersVisitor registersVisitor = null;
	
	private RegistersMarkedVisitor registersMarkedVisitor = null;
	
	private RegisterSSVisitor registerSSVisitor = null;
	
	private RegisterPPVisitor registerPPVisitor = null;
	
	private RegisterRRVisitor registerRRVisitor = null;
	
	private RegisterQQVisitor registerQQVisitor = null;
	
	private RegistersWithReferenceVisitor registersWithReferenceVisitor = null;
	
	private ParameterTVisitor parameterTVisitor = null;
	
	private IndexedReferenceVisitor indexedReferenceVisitor = null;
	
	private HexValueVisitor hexValueVisitor = null;
	
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
		return getParameter(paramCtx, type, "registerSS", RegisterSSContext.class, ctx -> getRegisterSSVisitor().visitRegisterSS(ctx));
	}
	
	protected Optional<Parameter> getRegisterSSParameter(RegisterSSContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegisterSSVisitor().visitRegisterSS(c));
	}
	
	protected Optional<Parameter> getRegisterPPParameter(RegisterPPContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegisterPPVisitor().visitRegisterPP(c));
	}
	
	protected Optional<Parameter> getRegisterRRParameter(RegisterRRContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegisterRRVisitor().visitRegisterRR(c));
	}
	
	protected Optional<Parameter> getRegisterQQParameter(RegisterQQContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegisterQQVisitor().visitRegisterQQ(c));
	}
	
	protected <PT> Optional<Parameter> getRegistersWithReference(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registersWithReference", RegistersWithReferenceContext.class, ctx -> getRegistersWithReferenceVisitor().visitRegistersWithReference(ctx));
	}
	
	protected Optional<Parameter> getRegistersWithReference(RegistersWithReferenceContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegistersWithReferenceVisitor().visitRegistersWithReference(c));
	}
	
	protected <PT> Optional<Parameter> getRegisters(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registers", RegistersContext.class, ctx -> getRegistersVisitor().visitRegisters(ctx));
	}
	
	protected Optional<Parameter> getRegisters(RegistersContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getRegistersVisitor().visitRegisters(c));
	}
	
	protected <PT> Optional<Parameter> getRegistersMarked(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "registersmarked", RegistersmarkedContext.class, ctx -> getRegistersMarkedVisitor().visitRegistersmarked(ctx));
	}
	
	protected Optional<Parameter> getParameterT(ParameterTContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getParameterTVisitor().visitParameterT(c));
	}
	
	protected <PT> Optional<Parameter> getIndexedReference(P paramCtx, Class<PT> type) {
		return getParameter(paramCtx, type, "indexedReference", IndexedReferenceContext.class, ctx -> getIndexedReferenceVisitor().visitIndexedReference(ctx));
	}
	
	protected Optional<Parameter> getIndexedReference(IndexedReferenceContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getIndexedReferenceVisitor().visitIndexedReference(c));
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex16bitsContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getHexValueVisitor().visitHex16bits(c));
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex8bitsContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getHexValueVisitor().visitHex8bits(c));
	}
	
	protected Optional<ExpressionParameter> getExpression(Hex3bitsContext ctx) {
		return Optional.ofNullable(ctx).map(c -> getHexValueVisitor().visitHex3bits(c));
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
	
	protected RegistersVisitor getRegistersVisitor() {
		if (registersVisitor == null) {
			registersVisitor = new RegistersVisitor();
		}
		return registersVisitor;
	}

	protected RegistersMarkedVisitor getRegistersMarkedVisitor() {
		if (registersMarkedVisitor == null) {
			registersMarkedVisitor = new RegistersMarkedVisitor();
		}
		return registersMarkedVisitor;
	}

	protected RegisterSSVisitor getRegisterSSVisitor() {
		if (registerSSVisitor == null) {
			registerSSVisitor = new RegisterSSVisitor();
		}
		return registerSSVisitor;
	}

	protected RegisterPPVisitor getRegisterPPVisitor() {
		if (registerPPVisitor == null) {
			registerPPVisitor = new RegisterPPVisitor();
		}
		return registerPPVisitor;
	}

	protected RegisterRRVisitor getRegisterRRVisitor() {
		if (registerRRVisitor == null) {
			registerRRVisitor = new RegisterRRVisitor();
		}
		return registerRRVisitor;
	}

	protected RegisterQQVisitor getRegisterQQVisitor() {
		if (registerQQVisitor == null) {
			registerQQVisitor = new RegisterQQVisitor();
		}
		return registerQQVisitor;
	}

	protected RegistersWithReferenceVisitor getRegistersWithReferenceVisitor() {
		if (registersWithReferenceVisitor == null) {
			registersWithReferenceVisitor = new RegistersWithReferenceVisitor();
		}
		return registersWithReferenceVisitor;
	}

	protected ParameterTVisitor getParameterTVisitor() {
		if (parameterTVisitor == null) {
			parameterTVisitor = new ParameterTVisitor();
		}
		return parameterTVisitor;
	}

	protected IndexedReferenceVisitor getIndexedReferenceVisitor() {
		if (indexedReferenceVisitor == null) {
			indexedReferenceVisitor = new IndexedReferenceVisitor();
		}
		return indexedReferenceVisitor;
	}
	
	public HexValueVisitor getHexValueVisitor() {
		if (hexValueVisitor == null) {
			hexValueVisitor = new HexValueVisitor();
		}
		return hexValueVisitor;
	}
	
}
