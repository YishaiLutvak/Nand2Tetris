package project07

class Constants {
    static enum CommandType {
        C_ARITHMETIC,
        C_PUSH,
        C_POP,
        C_LABEL,
        C_GOTO,
        C_IF,
        C_FUNCTION,
        C_RETURN,
        C_CALL,
        C_NOTHING
    }

    static final String ADD = $/
  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   ram[0] = ram[0]-1, A = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

/$

    static final String SUB = $/
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   ram[0] = ram[0]-1, A = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

/$

    static final String NEG = $/
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=-M              //   D = -ram[A]                      
  M=D               //   ram[A] = D                           
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1                    

/$

    static final String EQ = $/
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                    
  @SP               //   A = 0                     
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1                          
  D=M-D             //   D = ram[A]-D              
  @EQ.[index]_TRUE  //   A = address of label TRUE
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ.[index]_END   //   A = address of label END                          
  0;JMP             //   jump to label of END
(EQ.[index]_TRUE)   // LABEL TRUE                   
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ.[index]_END)    // LABEL END
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

/$

    static final String GT = $/
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M-D             //   D = ram[A]-D                     
  @GT.[index]_TRUE  //   A = address of label TRUE        
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT.[index]_END   //   A = address of label END         
  0;JMP             //   jump to label of END             
(GT.[index]_TRUE)   // LABEL TRUE                         
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT.[index]_END)    // LABEL END                          
  @SP               //   A = 0                            
  M=M+1             //   ram[0] = ram[0]+1                

/$

    static final String LT = $/
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                     
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M-D             //   D = ram[A]-D                   
  @LT.[index]_TRUE  //   A = address of label TRUE      
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT.[index]_END   //   A = address of label END       
  0;JMP             //   jump to label of END           
(LT.[index]_TRUE)   // LABEL TRUE                       
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT.[index]_END)    // LABEL END                        
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

/$

    static final String AND = $/
  @SP
  AM=M-1
  D=M
  @SP
  AM=M-1
  D=D&M
  M=D
  @SP
  M=M+1

/$

    static final String OR = $/
  @SP
  AM=M-1
  D=M
  @SP
  AM=M-1
  D=D|M
  M=D
  @SP
  M=M+1

/$

    static final String NOT = $/
  @SP
  AM=M-1
  D=!M
  M=D
  @SP
  M=M+1

/$

    static final String PUSH_CONSTANT = $/
  @[value]
  D=A
  @SP
  A=M
  M=D
  @SP
  M=M+1

/$

    static final String PUSH_LCL_ARG_THIS_THAT = $/
  @[index]
  D=A
  @[segment]
  A=M+D
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

/$

    static final String POP_LCL_ARG_THIS_THAT = $/
  @SP
  A=M-1
  D=M
  @[segment]
  A=M
  [offset]M=D
  @SP
  M=M-1

/$

    static final String PUSH_TEMP = $/
  @[index]
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

/$

    static final String POP_TEMP = $/
  @SP
  A=M-1
  D=M
  @[index]
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  M=D
  @SP
  M=M-1

/$

    static final String PUSH_STATIC = $/
  @[index]
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

/$

    static final String POP_STATIC = $/
  @SP
  M=M-1
  A=M
  D=M
  @[index]
  M=D

/$

    static final String PUSH_POINTER = $/
  @[index]
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1

/$

    static final String POP_POINTER = $/
  @SP
  A=M-1
  D=M
  @[index]
  M=D
  @SP
  M=M-1

/$

}

