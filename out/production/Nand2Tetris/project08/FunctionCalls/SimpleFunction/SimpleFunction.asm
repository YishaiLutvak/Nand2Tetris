// SimpleFunction.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/FunctionCalls/SimpleFunction/SimpleFunction.vm    (line 4)
//     (line 5)
// // Performs a simple calculation and returns the result.    (line 6)
// function SimpleFunction.test 2    (line 7)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label SimpleFunction.test ***
(SimpleFunction.test)

  // *** initialize local variables ***
  @2
  D=A              //  D = 2
  @SimpleFunction.test_End
  D;JEQ            //  if (numberOfLocals == 0) jump to SimpleFunction.test_End 
(SimpleFunction.test_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @SimpleFunction.test_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to SimpleFunction.test_Loop 
(SimpleFunction.test_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push local 0    (line 8)

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

// push local 1    (line 9)

  @1                        
  D=A               //   D = 1  
  @LCL                               
  A=M+D             //   A = ram[LCL]+1                          
  D=M               //   D = ram[ram[LCL]+1]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 10)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// not    (line 11)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=!M              //   D = !ram[A]                     
  M=D               //   ram[A] = D                          
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1                 

// push argument 0    (line 12)

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

// add    (line 13)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push argument 1    (line 14)

  @1                        
  D=A               //   D = 1  
  @ARG                               
  A=M+D             //   A = ram[ARG]+1                          
  D=M               //   D = ram[ram[ARG]+1]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
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

// return    (line 16)
// ++++++++++++++ RETURN ++++++++++++++

  // *** FRAME = LCL ***
  @LCL             //   A = LCL      
  D=M              //   D = ram[LCL] 
  
  // *** RET = *(FRAME-5) ***
  // *** RAM[13] = (LOCAL-5) *** 
  @5               //   A = 5             
  A=D-A            //   A = D-5        
  D=M              //   D = ram[D-5]        
  @13              //   A = 13             
  M=D              //   ram[13] = D 
  
  // ***[ *ARG = pop() ]***
  @SP  	           //   A = 0
  M=M-1            //   ram[0] = ram[0]-1           
  A=M              //   A = ram[0]              
  D=M              //   D = ram[ram[0]]         
  @ARG             //   A = ARG         
  A=M              //   A = ram[ARG]              
  M=D              //   ram[ram[ARG]] = ram[ram[0]]  
  
  // *** SP = ARG+1 ***
  @ARG             //   A = ARG 
  D=M              //   D = ram[ARG]  
  @SP              //   A = 0     
  M=D+1	           //   ram[SP] = ram[ARG]+1
  
  // *** THAT = *(FRAME-1) ***
  @LCL             //   A = LCL       
  M=M-1            //   ram[LCL] = ram[LCL]-1  
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]  
  @THAT            //   A = THAT
  M=D              //   A[THAT] = ram[ram[LCL]]
  
  // *** THIS = *(FRAME-2) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @THIS            //   A = THIS             
  M=D              //   A[THIS] = ram[ram[LCL]]          

  // *** ARG = *(FRAME-3) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @ARG             //   A = ARG             
  M=D			   //   A[ARG] = ram[ram[LCL]]          	
  
  // *** LCL = *(FRAME-4) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @LCL             //   A = LCL              
  M=D		       //   A[LCL] = ram[ram[LCL]]          
  
  // *** goto RET ***
  @13              //   A = 13              
  A=M              //   A = ram[13]
  0; JMP           //   jump to ram[ram[13]] 

// ++++++++++++++ END RETURN ++++++++++++++

