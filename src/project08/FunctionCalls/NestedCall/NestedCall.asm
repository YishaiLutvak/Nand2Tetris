// Sys.vm
// // Sys.vm for NestedCall test.    (line 1)
//     (line 2)
// // Sys.init()    (line 3)
// //    (line 4)
// // Calls Sys.main() and stores return value in temp 1.    (line 5)
// // Does not return.  (Enters infinite loop.)    (line 6)
//     (line 7)
// function Sys.init 0    (line 8)

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

// push constant 4000// test THIS and THAT context save    (line 9)

  @4000                                   
  D=A               //   D = 4000
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 4000
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 0    (line 10)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THIS                
  M=D               //   ram[THIS] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 5000    (line 11)

  @5000                                   
  D=A               //   D = 5000
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 5000
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 1    (line 12)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// call Sys.main 0    (line 13)
  
  // *** push return-address *** 
  @Sys.main$ReturnAddress0  
  D=A               //   D = Sys.main$ReturnAddress0        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Sys.main$ReturnAddress0         
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
  @Sys.main  
  0;JMP            //   jump to Sys.main

  // *** label return-address *** 
(Sys.main$ReturnAddress0)  

// pop temp 1    (line 14)

  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[ram[0]-1]    
  @1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[index+5] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

// label LOOP    (line 15)

(Sys.LOOP)

// goto LOOP    (line 16)

  @Sys.LOOP
  0; JMP            //   jump to Sys.LOOP

//     (line 17)
// // Sys.main()    (line 18)
// //    (line 19)
// // Sets locals 1, 2 and 3, leaving locals 0 and 4 unchanged to test    (line 20)
// // default local initialization to 0.  (RAM set to -1 by test setup.)    (line 21)
// // Calls Sys.add12(123) and stores return value (135) in temp 0.    (line 22)
// // Returns local 0 + local 1 + local 2 + local 3 + local 4 (456) to confirm    (line 23)
// // that locals were not mangled by function call.    (line 24)
//     (line 25)
// function Sys.main 5    (line 26)

  // *** label Sys.main ***
(Sys.main)

  // *** initialize local variables ***
  @5
  D=A              //  D = 5
  @Sys.main_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Sys.main_End 
(Sys.main_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Sys.main_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Sys.main_Loop 
(Sys.main_End) 

// push constant 4001    (line 27)

  @4001                                   
  D=A               //   D = 4001
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 4001
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 0    (line 28)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THIS                
  M=D               //   ram[THIS] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 5001    (line 29)

  @5001                                   
  D=A               //   D = 5001
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 5001
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 1    (line 30)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 200    (line 31)

  @200                                   
  D=A               //   D = 200
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 200
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop local 1    (line 32)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @LCL        
  A=M               //   A = ram[LCL]             
  A=A+1
  M=D               //   ram[ram[LCL]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 40    (line 33)

  @40                                   
  D=A               //   D = 40
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 40
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop local 2    (line 34)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @LCL        
  A=M               //   A = ram[LCL]             
  A=A+1
  A=A+1
  M=D               //   ram[ram[LCL]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 6    (line 35)

  @6                                   
  D=A               //   D = 6
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 6
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop local 3    (line 36)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @LCL        
  A=M               //   A = ram[LCL]             
  A=A+1
  A=A+1
  A=A+1
  M=D               //   ram[ram[LCL]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 123    (line 37)

  @123                                   
  D=A               //   D = 123
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 123
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// call Sys.add12 1    (line 38)
  
  // *** push return-address *** 
  @Sys.add12$ReturnAddress1  
  D=A               //   D = Sys.add12$ReturnAddress1        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = Sys.add12$ReturnAddress1         
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
  @1
  D=D-A             //   D = D-1
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
  @Sys.add12  
  0;JMP            //   jump to Sys.add12

  // *** label return-address *** 
(Sys.add12$ReturnAddress1)  

// pop temp 0    (line 39)

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

// push local 0    (line 40)

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

// push local 1    (line 41)

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

// push local 2    (line 42)

  @2                        
  D=A               //   D = 2  
  @LCL                               
  A=M+D             //   A = ram[LCL]+2                          
  D=M               //   D = ram[ram[LCL]+2]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push local 3    (line 43)

  @3                        
  D=A               //   D = 3  
  @LCL                               
  A=M+D             //   A = ram[LCL]+3                          
  D=M               //   D = ram[ram[LCL]+3]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push local 4    (line 44)

  @4                        
  D=A               //   D = 4  
  @LCL                               
  A=M+D             //   A = ram[LCL]+4                          
  D=M               //   D = ram[ram[LCL]+4]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 45)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// add    (line 46)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// add    (line 47)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// add    (line 48)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// return    (line 49)

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
  M=D              //   ram[A] = ram[ram[0]]  
  
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

//     (line 50)
// // Sys.add12(int n)    (line 51)
// //    (line 52)
// // Returns n+12.    (line 53)
//     (line 54)
// function Sys.add12 0    (line 55)

  // *** label Sys.add12 ***
(Sys.add12)

  // *** initialize local variables ***
  @0
  D=A              //  D = 0
  @Sys.add12_End
  D;JEQ            //  if (numberOfLocals == 0) jump to Sys.add12_End 
(Sys.add12_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @Sys.add12_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to Sys.add12_Loop 
(Sys.add12_End) 

// push constant 4002    (line 56)

  @4002                                   
  D=A               //   D = 4002
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 4002
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 0    (line 57)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THIS                
  M=D               //   ram[THIS] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push constant 5002    (line 58)

  @5002                                   
  D=A               //   D = 5002
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 5002
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop pointer 1    (line 59)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

// push argument 0    (line 60)

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

// push constant 12    (line 61)

  @12                                   
  D=A               //   D = 12
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 12
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// add    (line 62)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// return    (line 63)

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
  M=D              //   ram[A] = ram[ram[0]]  
  
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

