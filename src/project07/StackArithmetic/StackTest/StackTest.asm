// StackTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/StackArithmetic/StackTest/StackTest.vm    (line 4)
//     (line 5)
// // Executes a sequence of arithmetic and logical operations    (line 6)
// // on the stack.     (line 7)
// push constant 17    (line 8)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 17    (line 9)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq    (line 10)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@EQ.0_TRUE
D;JEQ
@SP
A=M
M=0
@EQ.0_END
0;JMP
(EQ.0_TRUE)
  @SP
  A=M
  M=-1
(EQ.0_END)
  @SP
  M=M+1
// push constant 17    (line 11)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 16    (line 12)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq    (line 13)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@EQ.1_TRUE
D;JEQ
@SP
A=M
M=0
@EQ.1_END
0;JMP
(EQ.1_TRUE)
  @SP
  A=M
  M=-1
(EQ.1_END)
  @SP
  M=M+1
// push constant 16    (line 14)
@16
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 17    (line 15)
@17
D=A
@SP
A=M
M=D
@SP
M=M+1
// eq    (line 16)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@EQ.2_TRUE
D;JEQ
@SP
A=M
M=0
@EQ.2_END
0;JMP
(EQ.2_TRUE)
  @SP
  A=M
  M=-1
(EQ.2_END)
  @SP
  M=M+1
// push constant 892    (line 17)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 891    (line 18)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// lt    (line 19)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@LT.0_TRUE
D;JLT
@SP
A=M
M=0
@LT.0_END
0;JMP
(LT.0_TRUE)
  @SP
  A=M
  M=-1
(LT.0_END)
  @SP
  M=M+1
// push constant 891    (line 20)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 892    (line 21)
@892
D=A
@SP
A=M
M=D
@SP
M=M+1
// lt    (line 22)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@LT.1_TRUE
D;JLT
@SP
A=M
M=0
@LT.1_END
0;JMP
(LT.1_TRUE)
  @SP
  A=M
  M=-1
(LT.1_END)
  @SP
  M=M+1
// push constant 891    (line 23)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 891    (line 24)
@891
D=A
@SP
A=M
M=D
@SP
M=M+1
// lt    (line 25)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@LT.2_TRUE
D;JLT
@SP
A=M
M=0
@LT.2_END
0;JMP
(LT.2_TRUE)
  @SP
  A=M
  M=-1
(LT.2_END)
  @SP
  M=M+1
// push constant 32767    (line 26)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 32766    (line 27)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// gt    (line 28)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@GT.0_TRUE
D;JGT
@SP
A=M
M=0
@GT.0_END
0;JMP
(GT.0_TRUE)
  @SP
  A=M
  M=-1
(GT.0_END)
  @SP
  M=M+1
// push constant 32766    (line 29)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 32767    (line 30)
@32767
D=A
@SP
A=M
M=D
@SP
M=M+1
// gt    (line 31)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@GT.1_TRUE
D;JGT
@SP
A=M
M=0
@GT.1_END
0;JMP
(GT.1_TRUE)
  @SP
  A=M
  M=-1
(GT.1_END)
  @SP
  M=M+1
// push constant 32766    (line 32)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 32766    (line 33)
@32766
D=A
@SP
A=M
M=D
@SP
M=M+1
// gt    (line 34)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
@GT.2_TRUE
D;JGT
@SP
A=M
M=0
@GT.2_END
0;JMP
(GT.2_TRUE)
  @SP
  A=M
  M=-1
(GT.2_END)
  @SP
  M=M+1
// push constant 57    (line 35)
@57
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 31    (line 36)
@31
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 53    (line 37)
@53
D=A
@SP
A=M
M=D
@SP
M=M+1
// add    (line 38)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
// push constant 112    (line 39)
@112
D=A
@SP
A=M
M=D
@SP
M=M+1
// sub    (line 40)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
M=D
@SP
M=M+1
// neg    (line 41)
@SP
AM=M-1
D=-M
M=D
@SP
M=M+1
// and    (line 42)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D&M
M=D
@SP
M=M+1
// push constant 82    (line 43)
@82
D=A
@SP
A=M
M=D
@SP
M=M+1
// or    (line 44)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D|M
M=D
@SP
M=M+1
// not    (line 45)
@SP
AM=M-1
D=!M
M=D
@SP
M=M+1
