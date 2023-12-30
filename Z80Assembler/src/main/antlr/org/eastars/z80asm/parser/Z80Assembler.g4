grammar Z80Assembler;

@header {
package org.eastars.z80asm.parser;
}

z80compilationUnit
  : ((z80assemblerline | z80directives) EOL+)*
  | EOF
  ;

z80directives
  : (WS | EOL)* (directive WS*)? COMMENT?
  ;

directive
  : ('org' WS+ Hex16Bits)                # ORG
  | (LABEL ':' WS* 'equ' WS* expression)        # EQU
  ;

z80assemblerline
  : (WS | EOL)* (LABEL ':' WS*)? (instruction WS*)? COMMENT?
  ;

COMMENT:        '#' ~[\r\n]*;

LABEL:        '@' Letter (Letter | [0-9])* ;

fragment Letter:    [a-zA-Z_];

instruction
  : 'NOP'                        # NOP
  | ('LD'    WS+ instructionLDparameters)        # LD
  | ('INC'    WS+ instructionINCDECparameters)      # INC
  | 'RLCA'                        # RLCA
  | ('EX'    WS+ instructionEXparameters)        # EX
  | ('ADD'    WS+ instructionADDparameters)      # ADD
  | ('DEC'    WS+ instructionINCDECparameters)      # DEC
  | 'RRCA'                        # RRCA
  | 'RLA'                        # RLA
  | 'RRA'                        # RRA
  | 'DAA'                        # DAA
  | 'CPL'                        # CPL
  | 'SCF'                        # SCF
  | 'CCF'                        # CCF
  | 'HALT'                        # HALT
  | ('ADC'    WS+ instructionADCSBCparameters)      # ADC
  | ('SUB'    WS+ instructionSUBANDXORORCPparameters)  # SUB
  | ('SBC'    WS+ instructionADCSBCparameters)      # SBC
  | ('AND'    WS+ instructionSUBANDXORORCPparameters)  # AND
  | ('XOR'    WS+ instructionSUBANDXORORCPparameters)  # XOR
  | ('OR'    WS+ instructionSUBANDXORORCPparameters)  # OR
  | ('CP'    WS+ instructionSUBANDXORORCPparameters)  # CP
  | ('POP'    WS+ instructionPUSHPOPparameters)    # POP
  | ('PUSH'  WS+ instructionPUSHPOPparameters)    # PUSH
  | instructionRETparameters              # RET
  | ('RST'    WS+ instructionRSTparameters)      # RST
  | 'EXX'                        # EXX
  | instruictionJPparameters              # JP
  | 'DI'                        # DI
  | 'EI'                        # EI

  | ('DJNZ'  WS+ instructionDJNZparameters)      # DJNZ
  | instructionJRparameters              # JR
  | ('RLC'    WS+ instructionBitRotatingparameters)  # RLC
  | ('RRC'    WS+ instructionBitRotatingparameters)  # RRC
  | ('RR'    WS+ instructionBitRotatingparameters)  # RR
  | ('RL'    WS+ instructionBitRotatingparameters)  # RL
  | ('SLA'    WS+ instructionBitRotatingparameters)  # SLA
  | ('SRA'    WS+ instructionBitRotatingparameters)  # SRA
  | ('SRL'    WS+ instructionBitRotatingparameters)  # SRL
  | ('BIT'    WS+ instructionBITRESSETparameters)    # BIT
  | ('RES'    WS+ instructionBITRESSETparameters)    # RES
  | ('SET'    WS+ instructionBITRESSETparameters)    # SET
  | ('OUT'    WS+ instructionOUTparameters)      # OUT
  | ('IN'    WS+ instructionINparameters)        # IN
  | 'NEG'                        # NEG
  | 'IM0'                        # IM0
  | 'RETN'                        # RETN
  | 'RETI'                        # RETI
  | 'IM1'                        # IM1
  | 'IM2'                        # IM2
  | 'RRD'                        # RRD
  | 'RLD'                        # RLD
  | 'LDI'                        # LDI
  | 'CPI'                        # CPI
  | 'INI'                        # INI
  | 'OUTI'                        # OUTI
  | 'LDD'                        # LDD
  | 'CPD'                        # CPD
  | 'IND'                        # IND
  | 'OUTD'                        # OUTD
  | 'LDIR'                        # LDIR
  | 'CPIR'                        # CPIR
  | 'INIR'                        # INIR
  | 'OTIR'                        # OTIR
  | 'LDDR'                        # LDDR
  | 'CPDR'                        # CPDR
  | 'INDR'                        # INDR
  | 'OTDR'                        # OTDR

  | instructionCALLparameters              # CALL
  ;

instructionLDparameters
  : ((refbc='[' WS* 'BC' WS* ']' | refde='[' WS* 'DE' WS* ']')  WS* ',' WS* source='A')                          # LDfromAToAddress
  | (target='A'                        WS* ',' WS* (refbc='[' WS* 'BC' WS* ']' | refde='[' WS* 'DE' WS* ']'))  # LDfromAddressToA
  | ('HL'                WS*  ','  WS*  '['  WS* hex16bits  WS*  ']')    # LDhlrefnum16
  | ('A'                WS*  ','  WS*  '['  WS* hex16bits  WS*  ']')    # LDarefnum16
  | (registerSS            WS* ',' WS* '[' WS*  hex16bits  WS*  ']')    # LDssrefnum16
  | ('IX'                WS* ',' WS* '[' WS* hex16bits  WS*  ']')    # LDixrefnum16
  | ('IY'                WS* ',' WS* '[' WS* hex16bits  WS*  ']')    # LDiyrefnum16
  | ('[' WS*  'HL' WS* ']'       WS* ',' WS* registers)            # LDregtorefhl
  | (registers             WS* ',' WS* '[' WS*  'HL' WS* ']')      # LDrefhltoreg
  | (registers             WS* ',' WS* registersmarked)          # LDregregmarked
  | ('SP'                 WS* ',' WS* 'HL')              # LDsphl
  | (registersWithReference      WS* ',' WS* hex8bits)            # LDrhnum8
  | ('SP'                 WS* ',' WS* 'IX')              # LDspix
  | ('SP'                WS* ',' WS* 'IY')              # LDspiy
  | ('I'                WS* ',' WS* 'A')                # LDia
  | ('A'                WS* ',' WS* 'I')                # LDai
  | ('R'                WS* ',' WS* 'A')                # LDra
  | ('A'                WS* ',' WS* 'R')                # LDar
  | (registerSS            WS* ',' WS* hex16bits)            # LDssnum16
  | ('['  WS* hex16bits  WS*  ']'    WS*  ','  WS*  'HL')              # LDrefnum16hl
  | ('['  WS* hex16bits  WS*  ']'    WS*  ','  WS*  'A')                # LDrefnum16a
  | (indexedReference          WS* ',' WS* registers)            # LDidxregs
  | (registers              WS* ',' WS* indexedReference)        # LDregsidx
  | (indexedReference          WS* ',' WS* hex8bits)            # LDidxnum8
  | ('[' WS* hex16bits    WS*  ']'    WS* ',' WS* registerSS)            # LDrefnum16ss
  | ('IX'                WS* ',' WS* hex16bits)            # LDixnum16
  | ('IY'                WS* ',' WS* hex16bits)            # LDiynum16
  | ('[' WS* hex16bits    WS*  ']'    WS* ',' WS* 'IX')              # LDrefnum16ix
  | ('[' WS* hex16bits    WS*  ']'    WS* ',' WS* 'IY')              # LDrefnum16iy
  ;

instructionINCDECparameters
  : registerSS
  | registersWithReference
  | IX='IX'
  | IY='IY'
  | indexedReference
  ;

instructionADDparameters
  : (HL='HL'    WS* ',' WS* registerSS)
  | (A='A'      WS* ',' WS* registersWithReference)
  | (A='A'      WS* ',' WS* hex8bits)
  | (IX='IX'    WS* ',' WS* registerPP)
  | (IY='IY'    WS* ',' WS* registerRR)
  | (A='A'      WS* ',' WS* indexedReference)
  ;

instructionEXparameters
  : ('AF'          WS* ',' WS* 'AF\'')      # exafafmarked
  | ('[' WS* 'SP' WS* ']'  WS* ',' WS* 'HL')      # exrefsphl
  | ('DE'          WS* ',' WS* 'HL')      # exdehl
  | ('[' WS* 'SP' WS* ']'  WS* ',' WS* 'IX')      # exrefspix
  | ('[' WS* 'SP' WS* ']'  WS* ',' WS* 'IY')      # exrefspiy
  ;

instructionADCSBCparameters
  : (A='A'      WS* ',' WS* registersWithReference)
  | (A='A'      WS* ',' WS* hex8bits)
  | (HL='HL'    WS* ',' WS* registerSS)
  | (A='A'      WS* ',' WS* indexedReference)
  ;

instructionSUBANDXORORCPparameters
  : registersWithReference
  | hex8bits
  | indexedReference
  ;

instructionPUSHPOPparameters
  : registerQQ
  | IX='IX'
  | IY='IY'
  ;

instructionRSTparameters
  : parameterT
  ;

instructionRETparameters
  : 'RET'
  | NZ='RETNZ'
  | Z='RETZ'
  | NC='RETNC'
  | C='RETC'
  | PO='RETPO'
  | PE='RETPE'
  | P='RETP'
  | M='RETM'
  ;

instruictionJPparameters
  : 'JP'  WS+ '[' WS* HL='HL' WS* ']'
  | 'JP'  WS+ '[' WS* IX='IX' WS* ']'
  | 'JP'  WS+ '[' WS* IY='IY' WS* ']'
  | ('JP' | NZ='JPNZ' | Z='JPZ' | NC='JPNC' | C='JPC' | PO='JPPO' | PE='JPPE' | P='JPP' | M='JPM')? WS+ hex16bits
  ;

instructionCALLparameters
  : ('CALL' | NZ='CALLNZ' | Z='CALLZ' | NC='CALLNC' | C='CALLC' | PO='CALLPO' | PE='CALLPE' | P='CALLP' | M='CALLM')? WS+ hex16bits
  ;

instructionJRparameters
  : (('JR' | NZ='JRNZ' | Z='JRZ' | NC='JRNC' | C='JRC') WS+ hex8bits)
  ;

instructionDJNZparameters
  : hex8bits
  ;

instructionBitRotatingparameters
  : registersWithReference
  | indexedReference
  ;

instructionBITRESSETparameters
  : (hex3bits    WS* ',' WS* registersWithReference)
  | (hex3bits    WS* ',' WS* indexedReference)
  ;

instructionOUTparameters
  : (hex8bits    WS*  ','  WS*  A='A')
  | (C='C'      WS*  ','  WS*  registers)
  ;

instructionINparameters
  : A='A'    WS*  ','  WS*  hex8bits
  | registers  WS*  ','  WS*  C='C'
  ;

registerSS
  : BC='BC' | DE='DE' | HL='HL' | SP='SP'
  ;

registerQQ
  : BC='BC' | DE='DE' | HL='HL' | AF='AF'
  ;

registerPP
  : BC='BC' | DE='DE' | IX='IX' | SP='SP'
  ;

registerRR
  : BC='BC' | DE='DE' | IY='IY' | SP='SP'
  ;

parameterT
  : x00h='00' | x08h='08' | x10h='10' | x18h='18' | x20h='20' | x28h='28' | x30h='30' | x38h='38'
  ;

parameterCC
  : NZ='NZ' | Z='Z' | NC='NC' | C='C' | PO='PO' | PE='PE' | P='P' | M='M'
  ;

registers
  : B='B' | C='C' | D='D' | E='E' | H='H' | L='L' | A='A'
  ;

registersmarked
  : B='B\'' | C='C\'' | D='D\'' | E='E\'' | H='H\'' | L='L\'' | A='A\''
  ;

registersWithReference
  : B='B' | C='C' | D='D' | E='E' | H='H' | L='L' | ('[' WS* refHL='HL' WS* ']') | A='A'
  ;

indexedReference
  : '[' WS* (IX='IX' | IY='IY') WS* '+' WS* hex8bits WS* ']'
  ;

hex3bits: expression; //Hex3Bits | LABEL;

hex8bits: expression; //Hex8Bits | LABEL;

hex16bits: expression; //Hex16Bits | LABEL;

PLUS: '+';
MINUS: '-';
STAR: '*';
DIV: '/';
SHLEFT: '<<';
SHRIGHT: '>>';
BITAND: '&';
BITXOR: '^';
BITOR: '|';
BITNOT:  '!';

expression: left=bitwisexorExpression (WS* or=BITOR WS* right=bitwisexorExpression)*;

bitwisexorExpression: left=bitwiseandExpression (WS* xor=BITXOR WS* right=bitwiseandExpression)*;

bitwiseandExpression: left=bitwiseshiftExpression (WS* and=BITAND WS* right=bitwiseshiftExpression)*;

bitwiseshiftExpression: left=additiveExpression (WS* (shl=SHLEFT | WS* shr=SHRIGHT) WS* right=additiveExpression)*;

additiveExpression: left=multiplicativeExpression (WS* (plus=PLUS | minus=MINUS) WS* right=multiplicativeExpression)*;

multiplicativeExpression: left=unaryExpression (WS*  (mul=STAR | div=DIV ) WS* right=unaryExpression)*;

unaryExpression
  : (minus=MINUS | not=BITNOT) WS* unaryExpression
  | primaryExpression
  ;

primaryExpression
  : Hex16Bits
  | LABEL
  | '(' WS*  expression WS*  ')'
  ;

Hex3Bits: [0-7]'h';

Hex8Bits: Hexdigit Hexdigit 'h';

Hex16Bits: Hexdigit Hexdigit Hexdigit Hexdigit 'h';

fragment Hexdigit: [0-9A-Fa-f];

EOL : ('\r'* '\n');

WS: [ \t];
