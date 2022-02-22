// PointerTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/MemoryAccess/PointerTest/PointerTest.vm    (line 4)
//     (line 5)
// // Executes pop and push commands using the     (line 6)
// // pointer, this, and that segments.    (line 7)
// push constant 3030    (line 8)
@3030
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop pointer 0    (line 9)
@SP
A=M-1
D=M
@THIS
M=D
@SP
M=M-1
// push constant 3040    (line 10)
@3040
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop pointer 1    (line 11)
@SP
A=M-1
D=M
@THAT
M=D
@SP
M=M-1
// push constant 32    (line 12)
@32
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop this 2    (line 13)
@SP
A=M-1
D=M
@THIS
A=M
A=A+1
A=A+1
M=D
@SP
M=M-1
// push constant 46    (line 14)
@46
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop that 6    (line 15)
@SP
A=M-1
D=M
@THAT
A=M
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
A=A+1
M=D
@SP
M=M-1
// push pointer 0    (line 16)
@THIS
D=M
@SP
A=M
M=D
@SP
M=M+1
// push pointer 1    (line 17)
@THAT
D=M
@SP
A=M
M=D
@SP
M=M+1
// add    (line 18)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
// push this 2    (line 19)
@2
D=A
@THIS
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
// sub    (line 20)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
M=D
@SP
M=M+1
// push that 6    (line 21)
@6
D=A
@THAT
A=M+D
D=M
@SP
A=M
M=D
@SP
M=M+1
// add    (line 22)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
