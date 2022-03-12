// StackTest.vm
// // This file is part of www.nand2tetris.org    (line 1)
// // and the book "The Elements of Computing Systems"    (line 2)
// // by Nisan and Schocken, MIT Press.    (line 3)
// // File name: projects/07/StackArithmetic/StackTest/StackTest.vm    (line 4)
//     (line 5)
// // Executes a sequence of arithmetic and logical operations    (line 6)
// // on the stack.     (line 7)
// push constant 17    (line 8)

  @17                                   
  D=A               //   D = 17
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 17
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 17    (line 9)

  @17                                   
  D=A               //   D = 17
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 17
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// eq    (line 10)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                    
  @SP               //   A = 0                     
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1                          
  D=M-D             //   D = ram[A]-D              
  @EQ_0_TRUE  
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ_0_END                             
  0;JMP             //   jump to label of END
(EQ_0_TRUE)                      
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ_0_END)    
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push constant 17    (line 11)

  @17                                   
  D=A               //   D = 17
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 17
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 16    (line 12)

  @16                                   
  D=A               //   D = 16
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 16
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// eq    (line 13)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                    
  @SP               //   A = 0                     
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1                          
  D=M-D             //   D = ram[A]-D              
  @EQ_1_TRUE  
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ_1_END                             
  0;JMP             //   jump to label of END
(EQ_1_TRUE)                      
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ_1_END)    
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push constant 16    (line 14)

  @16                                   
  D=A               //   D = 16
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 16
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 17    (line 15)

  @17                                   
  D=A               //   D = 17
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 17
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// eq    (line 16)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                    
  @SP               //   A = 0                     
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1                          
  D=M-D             //   D = ram[A]-D              
  @EQ_2_TRUE  
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ_2_END                             
  0;JMP             //   jump to label of END
(EQ_2_TRUE)                      
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ_2_END)    
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push constant 892    (line 17)

  @892                                   
  D=A               //   D = 892
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 892
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 891    (line 18)

  @891                                   
  D=A               //   D = 891
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 891
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// lt    (line 19)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                     
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M-D             //   D = ram[A]-D                   
  @LT_0_TRUE        
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT_0_END        
  0;JMP             //   jump to label of END           
(LT_0_TRUE)                          
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT_0_END)                            
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

// push constant 891    (line 20)

  @891                                   
  D=A               //   D = 891
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 891
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 892    (line 21)

  @892                                   
  D=A               //   D = 892
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 892
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// lt    (line 22)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                     
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M-D             //   D = ram[A]-D                   
  @LT_1_TRUE        
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT_1_END        
  0;JMP             //   jump to label of END           
(LT_1_TRUE)                          
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT_1_END)                            
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

// push constant 891    (line 23)

  @891                                   
  D=A               //   D = 891
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 891
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 891    (line 24)

  @891                                   
  D=A               //   D = 891
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 891
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// lt    (line 25)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                     
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M-D             //   D = ram[A]-D                   
  @LT_2_TRUE        
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT_2_END        
  0;JMP             //   jump to label of END           
(LT_2_TRUE)                          
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT_2_END)                            
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

// push constant 32767    (line 26)

  @32767                                   
  D=A               //   D = 32767
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32767
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 32766    (line 27)

  @32766                                   
  D=A               //   D = 32766
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32766
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// gt    (line 28)

  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M-D             //   D = ram[A]-D                     
  @GT_0_TRUE          
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT_0_END         
  0;JMP             //   jump to label of END             
(GT_0_TRUE)                            
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT_0_END)                              
  @SP               //   A = 0                            
  M=M+1             //   ram[0] = ram[0]+1                

// push constant 32766    (line 29)

  @32766                                   
  D=A               //   D = 32766
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32766
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 32767    (line 30)

  @32767                                   
  D=A               //   D = 32767
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32767
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// gt    (line 31)

  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M-D             //   D = ram[A]-D                     
  @GT_1_TRUE          
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT_1_END         
  0;JMP             //   jump to label of END             
(GT_1_TRUE)                            
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT_1_END)                              
  @SP               //   A = 0                            
  M=M+1             //   ram[0] = ram[0]+1                

// push constant 32766    (line 32)

  @32766                                   
  D=A               //   D = 32766
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32766
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 32766    (line 33)

  @32766                                   
  D=A               //   D = 32766
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 32766
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// gt    (line 34)

  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M-D             //   D = ram[A]-D                     
  @GT_2_TRUE          
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT_2_END         
  0;JMP             //   jump to label of END             
(GT_2_TRUE)                            
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT_2_END)                              
  @SP               //   A = 0                            
  M=M+1             //   ram[0] = ram[0]+1                

// push constant 57    (line 35)

  @57                                   
  D=A               //   D = 57
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 57
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 31    (line 36)

  @31                                   
  D=A               //   D = 31
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 31
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// push constant 53    (line 37)

  @53                                   
  D=A               //   D = 53
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 53
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// add    (line 38)

  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

// push constant 112    (line 39)

  @112                                   
  D=A               //   D = 112
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 112
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// sub    (line 40)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// neg    (line 41)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=-M              //   D = -ram[A]                      
  M=D               //   ram[A] = D                           
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1                    

// and    (line 42)

  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=D&M             //   D = D&&ram[A]                     
  M=D               //   ram[A] = D        
  @SP               //   A = 0     
  M=M+1             //   ram[0] = ram[0]+1

// push constant 82    (line 43)

  @82                                   
  D=A               //   D = 82
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = 82
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
// or    (line 44)

  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=D|M             //   D = D||ram[A]                   
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

// not    (line 45)

  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=!M              //   D = !ram[A]                     
  M=D               //   ram[A] = D                          
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1                 

