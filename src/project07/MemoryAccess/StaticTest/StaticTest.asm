// StaticTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/MemoryAccess/StaticTest/StaticTest.vm    (line 4)
//     (line 5)
// // Executes pop and push commands using the static segment.    (line 6)
// push constant 111    (line 7)
@111
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 333    (line 8)
@333
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 888    (line 9)
@888
D=A
@SP
A=M
M=D
@SP
M=M+1
// pop static 8    (line 10)
@SP
M=M-1
A=M
D=M
@StaticTest.8
M=D
// pop static 3    (line 11)
@SP
M=M-1
A=M
D=M
@StaticTest.3
M=D
// pop static 1    (line 12)
@SP
M=M-1
A=M
D=M
@StaticTest.1
M=D
// push static 3    (line 13)
@StaticTest.3
D=M
@SP
A=M
M=D
@SP
M=M+1
// push static 1    (line 14)
@StaticTest.1
D=M
@SP
A=M
M=D
@SP
M=M+1
// sub    (line 15)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=M-D
M=D
@SP
M=M+1
// push static 8    (line 16)
@StaticTest.8
D=M
@SP
A=M
M=D
@SP
M=M+1
// add    (line 17)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
