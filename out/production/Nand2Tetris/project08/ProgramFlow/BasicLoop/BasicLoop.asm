// BasicLoop.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/ProgramFlow/BasicLoop/BasicLoop.vm    (line 4)
//     (line 5)
// // Computes the sum 1 + 2 + ... + argument[0] and pushes the     (line 6)
// // result onto the stack. Argument[0] is initialized by the test     (line 7)
// // script before this code starts running.    (line 8)
// push constant 0        (line 9)

  @0                                   
  D=A               //   D = 0
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 0
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop local 0         // initializes sum = 0    (line 10)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @LCL        
  A=M               //   A = ram[LCL]             
  M=D               //   ram[ram[LCL]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// label LOOP_START    (line 11)

(BasicLoop.LOOP_START)

// push argument 0        (line 12)

  @0                        
  D=A               //   D = 0  
  @ARG                               
  A=M+D             //   A = ram[ARG]+0                          
  D=M               //   D = ram[ram[ARG]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push local 0    (line 13)

  @0                        
  D=A               //   D = 0  
  @LCL                               
  A=M+D             //   A = ram[LCL]+0                          
  D=M               //   D = ram[ram[LCL]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 14)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// pop local 0	        // sum = sum + counter    (line 15)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @LCL        
  A=M               //   A = ram[LCL]             
  M=D               //   ram[ram[LCL]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push argument 0    (line 16)

  @0                        
  D=A               //   D = 0  
  @ARG                               
  A=M+D             //   A = ram[ARG]+0                          
  D=M               //   D = ram[ram[ARG]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push constant 1    (line 17)

  @1                                   
  D=A               //   D = 1
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 1
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// sub    (line 18)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// pop argument 0      // counter--    (line 19)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @ARG        
  A=M               //   A = ram[ARG]             
  M=D               //   ram[ram[ARG]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push argument 0    (line 20)

  @0                        
  D=A               //   D = 0  
  @ARG                               
  A=M+D             //   A = ram[ARG]+0                          
  D=M               //   D = ram[ram[ARG]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// if-goto LOOP_START  // If counter != 0, goto LOOP_START    (line 21)

  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1 
  A=M               //   A = ram[0]        
  D=M               //   D = ram[ram[0]]        
  @BasicLoop.LOOP_START                                 
  D;JNE             //   if (ram[ram[0]] != 0) jump to BasicLoop.LOOP_START  

// push local 0    (line 22)

  @0                        
  D=A               //   D = 0  
  @LCL                               
  A=M+D             //   A = ram[LCL]+0                          
  D=M               //   D = ram[ram[LCL]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

