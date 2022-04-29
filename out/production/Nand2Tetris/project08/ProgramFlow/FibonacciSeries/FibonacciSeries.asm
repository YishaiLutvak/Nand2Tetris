// FibonacciSeries.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.vm    (line 4)
//     (line 5)
// // Puts the first argument[0] elements of the Fibonacci series    (line 6)
// // in the memory, starting in the address given in argument[1].    (line 7)
// // Argument[0] and argument[1] are initialized by the test script     (line 8)
// // before this code starts running.    (line 9)
//     (line 10)
// push argument 1    (line 11)

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

// pop pointer 1           // that = argument[1]    (line 12)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

//     (line 13)
// push constant 0    (line 14)

  @0                                   
  D=A               //   D = 0
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 0
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop that 0              // first element in the series = 0    (line 15)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @THAT        
  A=M               //   A = ram[THAT]             
  M=D               //   ram[ram[THAT]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 1    (line 16)

  @1                                   
  D=A               //   D = 1
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 1
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop that 1              // second element in the series = 1    (line 17)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @THAT        
  A=M               //   A = ram[THAT]             
  A=A+1
  M=D               //   ram[ram[THAT]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

//     (line 18)
// push argument 0    (line 19)

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

// push constant 2    (line 20)

  @2                                   
  D=A               //   D = 2
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 2
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// sub    (line 21)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// pop argument 0          // num_of_elements -= 2 (first 2 elements are set)    (line 22)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @ARG        
  A=M               //   A = ram[ARG]             
  M=D               //   ram[ram[ARG]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

//     (line 23)
// label MAIN_LOOP_START    (line 24)

(FibonacciSeries.MAIN_LOOP_START)

//     (line 25)
// push argument 0    (line 26)

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

// if-goto COMPUTE_ELEMENT // if num_of_elements > 0, goto COMPUTE_ELEMENT    (line 27)

  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1 
  A=M               //   A = ram[0]        
  D=M               //   D = ram[ram[0]]        
  @FibonacciSeries.COMPUTE_ELEMENT                                 
  D;JNE             //   if (ram[ram[0]] != 0) jump to FibonacciSeries.COMPUTE_ELEMENT  

// goto END_PROGRAM        // otherwise, goto END_PROGRAM    (line 28)

  @FibonacciSeries.END_PROGRAM
  0; JMP            //   jump to FibonacciSeries.END_PROGRAM

//     (line 29)
// label COMPUTE_ELEMENT    (line 30)

(FibonacciSeries.COMPUTE_ELEMENT)

//     (line 31)
// push that 0    (line 32)

  @0                        
  D=A               //   D = 0  
  @THAT                               
  A=M+D             //   A = ram[THAT]+0                          
  D=M               //   D = ram[ram[THAT]+0]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push that 1    (line 33)

  @1                        
  D=A               //   D = 1  
  @THAT                               
  A=M+D             //   A = ram[THAT]+1                          
  D=M               //   D = ram[ram[THAT]+1]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 34)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// pop that 2              // that[2] = that[0] + that[1]    (line 35)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @THAT        
  A=M               //   A = ram[THAT]             
  A=A+1
  A=A+1
  M=D               //   ram[ram[THAT]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

//     (line 36)
// push pointer 1    (line 37)

  @THAT
  D=M               //   D = A[THAT]
  @SP               //   A = 0            
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D       
  @SP               //   A = 0                      
  M=M+1             //   ram[0] = ram[0]+1         

// push constant 1    (line 38)

  @1                                   
  D=A               //   D = 1
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 1
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// add    (line 39)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// pop pointer 1           // that += 1    (line 40)

  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @THAT                
  M=D               //   ram[THAT] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

//     (line 41)
// push argument 0    (line 42)

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

// push constant 1    (line 43)

  @1                                   
  D=A               //   D = 1
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 1
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// sub    (line 44)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// pop argument 0          // num_of_elements--    (line 45)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @ARG        
  A=M               //   A = ram[ARG]             
  M=D               //   ram[ram[ARG]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

//     (line 46)
// goto MAIN_LOOP_START    (line 47)

  @FibonacciSeries.MAIN_LOOP_START
  0; JMP            //   jump to FibonacciSeries.MAIN_LOOP_START

//     (line 48)
// label END_PROGRAM    (line 49)

(FibonacciSeries.END_PROGRAM)

