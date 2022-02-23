// StaticTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/MemoryAccess/StaticTest/StaticTest.vm    (line 4)
//     (line 5)
// // Executes pop and push commands using the static segment.    (line 6)
// push constant 111    (line 7)

  @111                                   
  D=A               //   D = 111
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 333    (line 8)

  @333                                   
  D=A               //   D = 333
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 888    (line 9)

  @888                                   
  D=A               //   D = 888
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop static 8    (line 10)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[A]         
  @StaticTest.8                     
  M=D               //   ram[StaticTest.8] = D  

// pop static 3    (line 11)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[A]         
  @StaticTest.3                     
  M=D               //   ram[StaticTest.3] = D  

// pop static 1    (line 12)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[A]         
  @StaticTest.1                     
  M=D               //   ram[StaticTest.1] = D  

// push static 3    (line 13)

  @StaticTest.3
  D=M               //   D = ram[StaticTest.3]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// push static 1    (line 14)

  @StaticTest.1
  D=M               //   D = ram[StaticTest.1]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// sub    (line 15)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// push static 8    (line 16)

  @StaticTest.8
  D=M               //   D = ram[StaticTest.8]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// add    (line 17)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

