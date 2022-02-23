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
  D=A               //   D = 3030
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 0    (line 9)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[A]       
  @THIS                
  M=D               //   ram[THIS] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 3040    (line 10)

  @3040                                   
  D=A               //   D = 3040
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 1    (line 11)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[A]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 32    (line 12)

  @32                                   
  D=A               //   D = 32
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop this 2    (line 13)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @THIS        
  A=M               //   A = ram[THIS]             
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 46    (line 14)

  @46                                   
  D=A               //   D = 46
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop that 6    (line 15)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @THAT        
  A=M               //   A = ram[THAT]             
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push pointer 0    (line 16)

  @THIS
  D=M               //   D = A[THIS]
  @SP               //   A = 0            
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D       
  @SP               //   A = 0                      
  M=M+1             //   ram[0] = ram[0]+1         

// push pointer 1    (line 17)

  @THAT
  D=M               //   D = A[THAT]
  @SP               //   A = 0            
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D       
  @SP               //   A = 0                      
  M=M+1             //   ram[0] = ram[0]+1         

// add    (line 18)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push this 2    (line 19)

  @2                        
  D=A               //   D = A (index)  
  @THIS                               
  A=M+D             //   A = ram[THIS]+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// sub    (line 20)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// push that 6    (line 21)

  @6                        
  D=A               //   D = A (index)  
  @THAT                               
  A=M+D             //   A = ram[THAT]+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 22)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

