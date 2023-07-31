package org.eaSTars.z80asm.assembler.visitors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.z80asm.assembler.visitors.oneparam.*;
import org.eaSTars.z80asm.assembler.visitors.twoparam.*;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.instructions.noparam.*;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class Z80InstructionVisitor extends Z80AssemblerBaseVisitor<Z80Instruction> {
	
	private class InstructionMapEntry {
		private String text;
		private Class<? extends Z80Instruction> instruction;
		
		public InstructionMapEntry(
				String text,
				Class<? extends Z80Instruction> instruction) {
			this.text = text;
			this.instruction = instruction;
		}
	}
	
	private Map<String, Class<? extends Z80Instruction>> instructionMap = 
			Arrays.asList(
					new InstructionMapEntry("NOP", NOP.class),
					new InstructionMapEntry("RLCA", RLCA.class),
					new InstructionMapEntry("RRCA", RRCA.class),
					new InstructionMapEntry("RLA", RLA.class),
					new InstructionMapEntry("RRA", RRA.class),
					new InstructionMapEntry("DAA", DAA.class),
					new InstructionMapEntry("CPL", CPL.class),
					new InstructionMapEntry("SCF", SCF.class),
					new InstructionMapEntry("CCF", CCF.class),
					new InstructionMapEntry("HALT", HALT.class),
					new InstructionMapEntry("EXX", EXX.class),
					new InstructionMapEntry("DI", DI.class),
					new InstructionMapEntry("EI", EI.class),
					new InstructionMapEntry("NEG", NEG.class),
					new InstructionMapEntry("IM0", IM0.class),
					new InstructionMapEntry("RETN", RETN.class),
					new InstructionMapEntry("RETI", RETI.class),
					new InstructionMapEntry("IM1", IM1.class),
					new InstructionMapEntry("IM2", IM2.class),
					new InstructionMapEntry("RRD", RRD.class),
					new InstructionMapEntry("RLD", RLD.class),
					new InstructionMapEntry("LDI", LDI.class),
					new InstructionMapEntry("CPI", CPI.class),
					new InstructionMapEntry("INI", INI.class),
					new InstructionMapEntry("OUTI", OUTI.class),
					new InstructionMapEntry("LDD", LDD.class),
					new InstructionMapEntry("CPD", CPD.class),
					new InstructionMapEntry("IND", IND.class),
					new InstructionMapEntry("OUTD", OUTD.class),
					new InstructionMapEntry("LDIR", LDIR.class),
					new InstructionMapEntry("CPIR", CPIR.class),
					new InstructionMapEntry("INIR", INIR.class),
					new InstructionMapEntry("OTIR", OTIR.class),
					new InstructionMapEntry("LDDR", LDDR.class),
					new InstructionMapEntry("CPDR", CPDR.class),
					new InstructionMapEntry("INDR", INDR.class),
					new InstructionMapEntry("OTDR", OTDR.class)
					)
			.stream().collect(Collectors.toMap(e -> e.text, e -> e.instruction));
	
	@FunctionalInterface
	private interface VisitorInvokation{
		public Z80Instruction invokeVisitor(ParseTree tree);
	}
	
	private class VisitorMapEntry {
		private Class<? extends ParserRuleContext> context;
		private VisitorInvokation invokation;
		
		private VisitorMapEntry(
				Class<? extends ParserRuleContext> context,
				VisitorInvokation invokation) {
			this.context = context;
			this.invokation = invokation;
		}
	}
	
	private Map<Class<? extends ParserRuleContext>, VisitorInvokation> visitorMap =
			Arrays.asList(
					new VisitorMapEntry(LDContext.class, t -> new LDVisitor().visitInstruction(t, LDContext.class)),
					new VisitorMapEntry(INCContext.class, t -> new INCVisitor().visitInstruction(t, INCContext.class)),
					new VisitorMapEntry(DECContext.class, t -> new DECVisitor().visitInstruction(t, DECContext.class)),
					new VisitorMapEntry(ADDContext.class, t -> new ADDVisitor().visitInstruction(t, ADDContext.class)),
					new VisitorMapEntry(EXContext.class, t -> new EXVisitor().visitInstruction(t, EXContext.class)),
					new VisitorMapEntry(ADCContext.class, t -> new ADCVisitor().visitInstruction(t, ADCContext.class)),
					new VisitorMapEntry(SUBContext.class, t -> new SUBVisitor().visitInstruction(t, SUBContext.class)),
					new VisitorMapEntry(SBCContext.class, t -> new SBCVisitor().visitInstruction(t, SBCContext.class)),
					new VisitorMapEntry(ANDContext.class, t -> new ANDVisitor().visitInstruction(t, ANDContext.class)),
					new VisitorMapEntry(XORContext.class, t -> new XORVisitor().visitInstruction(t, XORContext.class)),
					new VisitorMapEntry(ORContext.class, t -> new ORVisitor().visitInstruction(t, ORContext.class)),
					new VisitorMapEntry(CPContext.class, t -> new CPVisitor().visitInstruction(t, CPContext.class)),
					new VisitorMapEntry(POPContext.class, t -> new POPVisitor().visitInstruction(t, POPContext.class)),
					new VisitorMapEntry(PUSHContext.class, t -> new PUSHVisitor().visitInstruction(t, PUSHContext.class)),
					new VisitorMapEntry(RSTContext.class, t -> new RSTVisitor().visitInstruction(t, RSTContext.class)),
					new VisitorMapEntry(JPContext.class, t -> new JPVisitor().visitInstruction(t, JPContext.class)),
					new VisitorMapEntry(RETContext.class, t -> new RETVisitor().visitInstruction(t, RETContext.class)),
					new VisitorMapEntry(DJNZContext.class, t-> new DJNZVisitor().visitInstruction(t, DJNZContext.class)),
					new VisitorMapEntry(JRContext.class, t -> new JRVisitor().visitInstruction(t, JRContext.class)),
					new VisitorMapEntry(RLCContext.class, t -> new RLCVisitor().visitInstruction(t, RLCContext.class)),
					new VisitorMapEntry(RRCContext.class, t -> new RRCVisitor().visitInstruction(t, RRCContext.class)),
					new VisitorMapEntry(RRContext.class, t -> new RRVisitor().visitInstruction(t, RRContext.class)),
					new VisitorMapEntry(RLContext.class, t -> new RLVisitor().visitInstruction(t, RLContext.class)),
					new VisitorMapEntry(SLAContext.class, t -> new SLAVisitor().visitInstruction(t, SLAContext.class)),
					new VisitorMapEntry(SRAContext.class, t -> new SRAVisitor().visitInstruction(t, SRAContext.class)),
					new VisitorMapEntry(SRLContext.class, t -> new SRLVisitor().visitInstruction(t, SRLContext.class)),
					new VisitorMapEntry(BITContext.class, t -> new BITVisitor().visitInstruction(t, BITContext.class)),
					new VisitorMapEntry(RESContext.class, t -> new RESVisitor().visitInstruction(t, RESContext.class)),
					new VisitorMapEntry(SETContext.class, t -> new SETVisitor().visitInstruction(t, SETContext.class)),
					new VisitorMapEntry(OUTContext.class, t -> new OUTVisitor().visitInstruction(t, OUTContext.class)),
					new VisitorMapEntry(INContext.class, t -> new INVisitor().visitInstruction(t, INContext.class)),
					new VisitorMapEntry(CALLContext.class, t -> new CALLVisitor().visitInstruction(t, CALLContext.class))
					)
			.stream().collect(Collectors.toMap(e -> e.context, e -> e.invokation));

	@Override
	public Z80Instruction visit(ParseTree tree) {
		Z80Instruction instruction = null;
		
		if (tree != null) {
			Class<? extends Z80Instruction> inst = instructionMap.get(tree.getChild(0).getText());
			if (inst != null) {
				try {
					instruction = inst.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					
				}
			} else {
				VisitorInvokation invokation = visitorMap.get(tree.getClass());
				if (invokation != null) {
					instruction = invokation.invokeVisitor(tree);
				}
			}
		}
		
		return instruction;
	}
	
}
