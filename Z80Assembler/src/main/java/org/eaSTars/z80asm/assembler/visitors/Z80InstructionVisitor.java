package org.eaSTars.z80asm.assembler.visitors;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eaSTars.z80asm.assembler.visitors.oneparam.ANDVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.CPVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.DECVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.DJNZVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.INCVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.ORVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.POPVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.PUSHVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RETVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RLCVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RLVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RRCVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RRVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.RSTVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.SLAVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.SRAVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.SRLVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.SUBVisitor;
import org.eaSTars.z80asm.assembler.visitors.oneparam.XORVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.ADCVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.ADDVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.BITVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.CALLVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.EXVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.INVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.JPVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.JRVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.LDVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.OUTVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.RESVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.SBCVisitor;
import org.eaSTars.z80asm.assembler.visitors.twoparam.SETVisitor;
import org.eaSTars.z80asm.ast.Z80Instruction;
import org.eaSTars.z80asm.ast.instructions.noparam.CCF;
import org.eaSTars.z80asm.ast.instructions.noparam.CPD;
import org.eaSTars.z80asm.ast.instructions.noparam.CPDR;
import org.eaSTars.z80asm.ast.instructions.noparam.CPI;
import org.eaSTars.z80asm.ast.instructions.noparam.CPIR;
import org.eaSTars.z80asm.ast.instructions.noparam.CPL;
import org.eaSTars.z80asm.ast.instructions.noparam.DAA;
import org.eaSTars.z80asm.ast.instructions.noparam.DI;
import org.eaSTars.z80asm.ast.instructions.noparam.EI;
import org.eaSTars.z80asm.ast.instructions.noparam.EXX;
import org.eaSTars.z80asm.ast.instructions.noparam.HALT;
import org.eaSTars.z80asm.ast.instructions.noparam.IM0;
import org.eaSTars.z80asm.ast.instructions.noparam.IM1;
import org.eaSTars.z80asm.ast.instructions.noparam.IM2;
import org.eaSTars.z80asm.ast.instructions.noparam.IND;
import org.eaSTars.z80asm.ast.instructions.noparam.INDR;
import org.eaSTars.z80asm.ast.instructions.noparam.INI;
import org.eaSTars.z80asm.ast.instructions.noparam.INIR;
import org.eaSTars.z80asm.ast.instructions.noparam.LDD;
import org.eaSTars.z80asm.ast.instructions.noparam.LDDR;
import org.eaSTars.z80asm.ast.instructions.noparam.LDI;
import org.eaSTars.z80asm.ast.instructions.noparam.LDIR;
import org.eaSTars.z80asm.ast.instructions.noparam.NEG;
import org.eaSTars.z80asm.ast.instructions.noparam.NOP;
import org.eaSTars.z80asm.ast.instructions.noparam.OTDR;
import org.eaSTars.z80asm.ast.instructions.noparam.OTIR;
import org.eaSTars.z80asm.ast.instructions.noparam.OUTD;
import org.eaSTars.z80asm.ast.instructions.noparam.OUTI;
import org.eaSTars.z80asm.ast.instructions.noparam.RETI;
import org.eaSTars.z80asm.ast.instructions.noparam.RETN;
import org.eaSTars.z80asm.ast.instructions.noparam.RLA;
import org.eaSTars.z80asm.ast.instructions.noparam.RLCA;
import org.eaSTars.z80asm.ast.instructions.noparam.RLD;
import org.eaSTars.z80asm.ast.instructions.noparam.RRA;
import org.eaSTars.z80asm.ast.instructions.noparam.RRCA;
import org.eaSTars.z80asm.ast.instructions.noparam.RRD;
import org.eaSTars.z80asm.ast.instructions.noparam.SCF;
import org.eaSTars.z80asm.parser.Z80AssemblerBaseVisitor;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ADCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ADDContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ANDContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.BITContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.CALLContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.CPContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.DECContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.DJNZContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.EXContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.INCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.INContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.JPContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.JRContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.LDContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.ORContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.OUTContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.POPContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.PUSHContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RESContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RETContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RLCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RLContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RRCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RRContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.RSTContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SBCContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SETContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SLAContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SRAContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SRLContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.SUBContext;
import org.eaSTars.z80asm.parser.Z80AssemblerParser.XORContext;

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
