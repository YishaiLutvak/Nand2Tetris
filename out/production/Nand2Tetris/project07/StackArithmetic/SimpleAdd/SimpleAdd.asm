// SimpleAdd.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm    (line 4)
//     (line 5)
// // Pushes and adds two constants.    (line 6)
// push constant 7    (line 7)
@7
D=A
@SP
A=M
M=D
@SP
M=M+1
// push constant 8    (line 8)
@8
D=A
@SP
A=M
M=D
@SP
M=M+1
// add    (line 9)
@SP
AM=M-1
D=M
@SP
AM=M-1
D=D+M
M=D
@SP
M=M+1
