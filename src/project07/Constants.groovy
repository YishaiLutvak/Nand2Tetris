package project07

class Constants {
    static enum CommandType {
        C_ARITHMETIC,
        C_PUSH,
        C_POP,
        C_LABEL,
        C_GOTO,
        C_IF,
        C_FUNCTION,
        C_RETURN,
        C_CALL,
        C_NOTHING
    }

    static final String ADD = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
/$

    static final String SUB = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
M=D
@SP
M=M+1
/$

    static final String NEG = $/@SP
AM=M-1
D=-M
M=D
@SP
M=M+1
/$

    static final String EQ = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@EQ.[index]_TRUE
D;JEQ
@SP
A=M
M=0
@EQ.[index]_END
0;JMP
(EQ.[index]_TRUE)
  @SP
  A=M
  M=-1
(EQ.[index]_END)
  @SP
  M=M+1
/$

    static final String GT = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@GT.[index]_TRUE
D;JGT
@SP
A=M
M=0
@GT.[index]_END
0;JMP
(GT.[index]_TRUE)
  @SP
  A=M
  M=-1
(GT.[index]_END)
  @SP
  M=M+1
/$

    static final String LT = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@LT.[index]_TRUE
D;JLT
@SP
A=M
M=0
@LT.[index]_END
0;JMP
(LT.[index]_TRUE)
  @SP
  A=M
  M=-1
(LT.[index]_END)
  @SP
  M=M+1
/$

    static final String AND = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=D&M
M=D
@SP
M=M+1
/$

    static final String OR = $/@SP
AM=M-1
D=M
@SP
AM=M-1
D=D|M
M=D
@SP
M=M+1
/$

    static final String NOT = $/@SP
AM=M-1
D=!M
M=D
@SP
M=M+1
/$

    static final String PUSH_CONSTANT = $/@[value]
D=A
@SP
A=M
M=D
@SP
M=M+1
/$

    static final String PUSH_GROUP1 = $/@[index]
D=A
@[segment]
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
/$

    static final String POP_GROUP1 = $/@SP
A=M-1
D=M
@[segment]
A=M
[offset]M=D
@SP
M=M-1
/$

    static final String PUSH_GROUP2 = $/@[index]
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
D=M
@SP
A=M
M=D
@SP
M=M+1
/$

    static final String POP_GROUP2 = $/@SP
A=M-1
D=M
@[index]
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
M=D
@SP
M=M-1
/$

    static final String PUSH_GROUP3 = $/@[index]
D=M
@SP
A=M
M=D
@SP
M=M+1
/$

    static final String POP_GROUP3 = $/@SP
M=M-1
A=M
D=M
@[index]
M=D
/$

    static final String PUSH_GROUP4 = $/@[index]
D=M
@SP
A=M
M=D
@SP
M=M+1
/$

    static final String POP_GROUP4 = $/@SP
A=M-1
D=M
@[index]
M=D
@SP
M=M-1
/$

}

