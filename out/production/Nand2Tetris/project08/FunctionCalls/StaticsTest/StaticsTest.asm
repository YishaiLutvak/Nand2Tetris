// ++++++++++++++ BOOTSTRAPPING ++++++++++++++

  // *** SP = 256 ***
  
  @256
  D=A
  @SP
  M=D

  // *** call Sys.init ***
  
  // push return-address
  @Sys.init$RETURN0  
  D=A
  @SP
  A=M
  M=D
  @SP
  M=M+1

  // push LCL
  @LCL  
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1
  
  // push ARG
  @ARG  
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

  // push THIS
  @THIS
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

  // push THAT
  @THAT
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

  // ARG = SP-n-5 
  @SP
  D=M
  @0
  D=D-A
  @5
  D=D-A
  @ARG
  M=D

  // LCL = SP 
  @SP
  D=M
  @LCL
  M=D

  @Sys.init
  0;JMP

(Sys.init$RETURN0)

// ++++++++++++++ END BOOTSTRAPPING ++++++++++++++

// Class1.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/FunctionCalls/StaticsTest/Class1.vm    (line 4)
//     (line 5)
// // Stores two supplied arguments in static[0] and static[1].    (line 6)
// function Class1.set 0    (line 7)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label Class1.set ***
(Class1.set)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Class1.set_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Class1.set_End 
(Class1.set_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Class1.set_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Class1.set_Loop 
(Class1.set_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push argument 0    (line 8)

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

// pop static 0    (line 9)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[ram[0]]         
  @Class1.0                     
  M=D               //   ram[Class1.0] = D  

// push argument 1    (line 10)

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

// pop static 1    (line 11)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[ram[0]]         
  @Class1.1                     
  M=D               //   ram[Class1.1] = D  

// push constant 0    (line 12)

  @0                                   
  D=A               //   D = 0
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 0
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// return    (line 13)
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

//     (line 14)
// // Returns static[0] - static[1].    (line 15)
// function Class1.get 0    (line 16)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label Class1.get ***
(Class1.get)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Class1.get_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Class1.get_End 
(Class1.get_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Class1.get_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Class1.get_Loop 
(Class1.get_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push static 0    (line 17)

  @Class1.0
  D=M               //   D = ram[Class1.0]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// push static 1    (line 18)

  @Class1.1
  D=M               //   D = ram[Class1.1]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// sub    (line 19)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// return    (line 20)
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

// Class2.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/FunctionCalls/StaticsTest/Class2.vm    (line 4)
//     (line 5)
// // Stores two supplied arguments in static[0] and static[1].    (line 6)
// function Class2.set 0    (line 7)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label Class2.set ***
(Class2.set)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Class2.set_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Class2.set_End 
(Class2.set_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Class2.set_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Class2.set_Loop 
(Class2.set_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push argument 0    (line 8)

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

// pop static 0    (line 9)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[ram[0]]         
  @Class2.0                     
  M=D               //   ram[Class2.0] = D  

// push argument 1    (line 10)

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

// pop static 1    (line 11)

  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[ram[0]]         
  @Class2.1                     
  M=D               //   ram[Class2.1] = D  

// push constant 0    (line 12)

  @0                                   
  D=A               //   D = 0
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 0
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// return    (line 13)
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

//     (line 14)
// // Returns static[0] - static[1].    (line 15)
// function Class2.get 0    (line 16)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label Class2.get ***
(Class2.get)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Class2.get_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Class2.get_End 
(Class2.get_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Class2.get_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Class2.get_Loop 
(Class2.get_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push static 0    (line 17)

  @Class2.0
  D=M               //   D = ram[Class2.0]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// push static 1    (line 18)

  @Class2.1
  D=M               //   D = ram[Class2.1]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

// sub    (line 19)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// return    (line 20)
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

// Sys.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/FunctionCalls/StaticsTest/Sys.vm    (line 4)
//     (line 5)
// // Tests that different functions, stored in two different     (line 6)
// // class files, manipulate the static segment correctly.     (line 7)
// function Sys.init 0    (line 8)
// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label Sys.init ***
(Sys.init)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Sys.init_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Sys.init_End 
(Sys.init_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Sys.init_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Sys.init_Loop 
(Sys.init_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++

// push constant 6    (line 9)

  @6                                   
  D=A               //   D = 6
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 6
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 8    (line 10)

  @8                                   
  D=A               //   D = 8
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 8
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// call Class1.set 2    (line 11)
// ++++++++++++++ CALL ++++++++++++++  
  
  // *** push return-address *** 
  @Class1.set$ReturnAddress0  
  D=A               //   D = Class1.set$ReturnAddress0        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Class1.set$ReturnAddress0         
  @SP               //   A = 0              
  M=M+1             //   ram[0] = ram[0]+1  

  // *** push LCL ***	
  @LCL  
  D=M               //   D = LCL 
  @SP               //   A = 0                                     
  A=M               //   A = ram[0]                                
  M=D               //   ram[ram[0]] = D                                
  @SP               //   A = 0                                     
  M=M+1             //   ram[0] = ram[0]+1                         

  // *** push ARG *** 	
  @ARG  
  D=M               //   D = ARG           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THIS *** 	
  @THIS  
  D=M               //   D = THIS           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THAT ***   	
  @THAT  
  D=M               //   D = THAT           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** ARG = SP-n-5 ***	
  @SP               //   A = 0     
  D=M               //   D = ram[0]
  @2
  D=D-A             //   D = D-2
  @5                //   A = 5
  D=D-A             //   D = D-5
  @ARG              //   A = ARG
  M=D               //   ram[ARG] = D

  // *** LCL = SP ***
  @SP              //   A = 0     
  D=M              //   D = ram[0]
  @LCL             //   A = LCL     
  M=D              //   ram[LCL] = ram[SP]

  // *** goto g *** 	
  @Class1.set  
  0;JMP            //   jump to Class1.set

  // *** label return-address *** 
(Class1.set$ReturnAddress0)  

// ++++++++++++++ END CALL ++++++++++++++  

// pop temp 0 // Dumps the return value    (line 12)

  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[ram[0]-1]    
  @0
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[index+5] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

// push constant 23    (line 13)

  @23                                   
  D=A               //   D = 23
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 23
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 15    (line 14)

  @15                                   
  D=A               //   D = 15
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 15
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// call Class2.set 2    (line 15)
// ++++++++++++++ CALL ++++++++++++++  
  
  // *** push return-address *** 
  @Class2.set$ReturnAddress1  
  D=A               //   D = Class2.set$ReturnAddress1        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Class2.set$ReturnAddress1         
  @SP               //   A = 0              
  M=M+1             //   ram[0] = ram[0]+1  

  // *** push LCL ***	
  @LCL  
  D=M               //   D = LCL 
  @SP               //   A = 0                                     
  A=M               //   A = ram[0]                                
  M=D               //   ram[ram[0]] = D                                
  @SP               //   A = 0                                     
  M=M+1             //   ram[0] = ram[0]+1                         

  // *** push ARG *** 	
  @ARG  
  D=M               //   D = ARG           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THIS *** 	
  @THIS  
  D=M               //   D = THIS           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THAT ***   	
  @THAT  
  D=M               //   D = THAT           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** ARG = SP-n-5 ***	
  @SP               //   A = 0     
  D=M               //   D = ram[0]
  @2
  D=D-A             //   D = D-2
  @5                //   A = 5
  D=D-A             //   D = D-5
  @ARG              //   A = ARG
  M=D               //   ram[ARG] = D

  // *** LCL = SP ***
  @SP              //   A = 0     
  D=M              //   D = ram[0]
  @LCL             //   A = LCL     
  M=D              //   ram[LCL] = ram[SP]

  // *** goto g *** 	
  @Class2.set  
  0;JMP            //   jump to Class2.set

  // *** label return-address *** 
(Class2.set$ReturnAddress1)  

// ++++++++++++++ END CALL ++++++++++++++  

// pop temp 0 // Dumps the return value    (line 16)

  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[ram[0]-1]    
  @0
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[index+5] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

// call Class1.get 0    (line 17)
// ++++++++++++++ CALL ++++++++++++++  
  
  // *** push return-address *** 
  @Class1.get$ReturnAddress2  
  D=A               //   D = Class1.get$ReturnAddress2        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Class1.get$ReturnAddress2         
  @SP               //   A = 0              
  M=M+1             //   ram[0] = ram[0]+1  

  // *** push LCL ***	
  @LCL  
  D=M               //   D = LCL 
  @SP               //   A = 0                                     
  A=M               //   A = ram[0]                                
  M=D               //   ram[ram[0]] = D                                
  @SP               //   A = 0                                     
  M=M+1             //   ram[0] = ram[0]+1                         

  // *** push ARG *** 	
  @ARG  
  D=M               //   D = ARG           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THIS *** 	
  @THIS  
  D=M               //   D = THIS           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THAT ***   	
  @THAT  
  D=M               //   D = THAT           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** ARG = SP-n-5 ***	
  @SP               //   A = 0     
  D=M               //   D = ram[0]
  @0
  D=D-A             //   D = D-0
  @5                //   A = 5
  D=D-A             //   D = D-5
  @ARG              //   A = ARG
  M=D               //   ram[ARG] = D

  // *** LCL = SP ***
  @SP              //   A = 0     
  D=M              //   D = ram[0]
  @LCL             //   A = LCL     
  M=D              //   ram[LCL] = ram[SP]

  // *** goto g *** 	
  @Class1.get  
  0;JMP            //   jump to Class1.get

  // *** label return-address *** 
(Class1.get$ReturnAddress2)  

// ++++++++++++++ END CALL ++++++++++++++  

// call Class2.get 0    (line 18)
// ++++++++++++++ CALL ++++++++++++++  
  
  // *** push return-address *** 
  @Class2.get$ReturnAddress3  
  D=A               //   D = Class2.get$ReturnAddress3        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Class2.get$ReturnAddress3         
  @SP               //   A = 0              
  M=M+1             //   ram[0] = ram[0]+1  

  // *** push LCL ***	
  @LCL  
  D=M               //   D = LCL 
  @SP               //   A = 0                                     
  A=M               //   A = ram[0]                                
  M=D               //   ram[ram[0]] = D                                
  @SP               //   A = 0                                     
  M=M+1             //   ram[0] = ram[0]+1                         

  // *** push ARG *** 	
  @ARG  
  D=M               //   D = ARG           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THIS *** 	
  @THIS  
  D=M               //   D = THIS           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** push THAT ***   	
  @THAT  
  D=M               //   D = THAT           
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

  // *** ARG = SP-n-5 ***	
  @SP               //   A = 0     
  D=M               //   D = ram[0]
  @0
  D=D-A             //   D = D-0
  @5                //   A = 5
  D=D-A             //   D = D-5
  @ARG              //   A = ARG
  M=D               //   ram[ARG] = D

  // *** LCL = SP ***
  @SP              //   A = 0     
  D=M              //   D = ram[0]
  @LCL             //   A = LCL     
  M=D              //   ram[LCL] = ram[SP]

  // *** goto g *** 	
  @Class2.get  
  0;JMP            //   jump to Class2.get

  // *** label return-address *** 
(Class2.get$ReturnAddress3)  

// ++++++++++++++ END CALL ++++++++++++++  

// label WHILE    (line 19)

(Sys.WHILE)

// goto WHILE    (line 20)

  @Sys.WHILE
  0; JMP            //   jump to Sys.WHILE

