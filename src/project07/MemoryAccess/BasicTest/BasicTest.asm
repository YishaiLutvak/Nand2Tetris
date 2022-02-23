// BasicTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/MemoryAccess/BasicTest/BasicTest.vm    (line 4)
//     (line 5)
// // Executes pop and push commands using the virtual memory segments.    (line 6)
// push constant 10    (line 7)

  @10                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop local 0    (line 8)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @LCL        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramLCL             
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 21    (line 9)

  @21                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 22    (line 10)

  @22                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop argument 2    (line 11)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @ARG        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramARG             
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// pop argument 1    (line 12)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @ARG        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramARG             
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 36    (line 13)

  @36                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop this 6    (line 14)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @THIS        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramTHIS             
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 42    (line 15)

  @42                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 45    (line 16)

  @45                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop that 5    (line 17)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @THAT        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramTHAT             
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// pop that 2    (line 18)

  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @THAT        //   A = LCL\ARG\THIS\THAT
  A=M               //   A = ramTHAT             
  A=A+1
  A=A+1
  M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

// push constant 510    (line 19)

  @510                                   
  D=A               //   D = A (value)
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// pop temp 6    (line 20)

  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[A]    
  @6
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

// push local 0    (line 21)

  @0                        
  D=A               //   D = A (index)  
  @LCL        //   A = LCL\ARG\THIS\THAT                       
  A=M+D             //   A = ramLCL+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push that 5    (line 22)

  @5                        
  D=A               //   D = A (index)  
  @THAT        //   A = LCL\ARG\THIS\THAT                       
  A=M+D             //   A = ramTHAT+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 23)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push argument 1    (line 24)

  @1                        
  D=A               //   D = A (index)  
  @ARG        //   A = LCL\ARG\THIS\THAT                       
  A=M+D             //   A = ramARG+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// sub    (line 25)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// push this 6    (line 26)

  @6                        
  D=A               //   D = A (index)  
  @THIS        //   A = LCL\ARG\THIS\THAT                       
  A=M+D             //   A = ramTHIS+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// push this 6    (line 27)

  @6                        
  D=A               //   D = A (index)  
  @THIS        //   A = LCL\ARG\THIS\THAT                       
  A=M+D             //   A = ramTHIS+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 28)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// sub    (line 29)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// push temp 6    (line 30)

  @6
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  D=M               //   D = ram[A]
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[A] = D        
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

// add    (line 31)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

