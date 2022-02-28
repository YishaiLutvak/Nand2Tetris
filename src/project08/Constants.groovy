package project08

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
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
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
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
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
  @EQ.{index}_TRUE  
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ.{index}_END                             
  0;JMP             //   jump to label of END
(EQ.{index}_TRUE)                      
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ.{index}_END)    
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
  @GT.{index}_TRUE          
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT.{index}_END         
  0;JMP             //   jump to label of END             
(GT.{index}_TRUE)                            
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT.{index}_END)                              
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
  @LT.{index}_TRUE        
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT.{index}_END        
  0;JMP             //   jump to label of END           
(LT.{index}_TRUE)                          
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT.{index}_END)                            
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

/$

    static final String AND = $/
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=D&M             //   D = D&&ram[A]                     
  M=D               //   ram[A] = D        
  @SP               //   A = 0     
  M=M+1             //   ram[0] = ram[0]+1

/$

    static final String OR = $/
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=D|M             //   D = D||ram[A]                   
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

/$

    static final String NOT = $/
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=!M              //   D = !ram[A]                     
  M=D               //   ram[A] = D                          
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1                 

/$

    static final String PUSH_CONSTANT = $/
  @{value}                                   
  D=A               //   D = {value}
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[A] = D
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
/$

    static final String PUSH_LCL_ARG_THIS_THAT = $/
  @{index}                        
  D=A               //   D = A (A=index=offset)  
  @{segment}                               
  A=M+D             //   A = ram[{segment}]+D (D=index=offset)                          
  D=M               //   D = ram[A]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[A] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

/$

    static final String POP_LCL_ARG_THIS_THAT = $/
  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[A]  
  @{segment}        
  A=M               //   A = ram[{segment}]             
  {index}M=D               //   ram[A] = D      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

/$

    static final String PUSH_TEMP = $/
  @{index}
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

/$

    static final String POP_TEMP = $/
  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[A]    
  @{index}
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

/$

    static final String PUSH_STATIC = $/
  @{index}
  D=M               //   D = ram[{index}]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

/$

    static final String POP_STATIC = $/
  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[A]         
  @{index}                     
  M=D               //   ram[{index}] = D  

/$

    static final String PUSH_POINTER = $/
  @{index}
  D=M               //   D = A[{index}]
  @SP               //   A = 0            
  A=M               //   A = ram[0]
  M=D               //   ram[A] = D       
  @SP               //   A = 0                      
  M=M+1             //   ram[0] = ram[0]+1         

/$

    static final String POP_POINTER = $/
  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[A]       
  @{index}                
  M=D               //   ram[{index}] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

/$

    public static final String LABEL = $/
({label})

/$

    public static final String GOTO = $/
  @{label}
  0; JMP

/$

    public static final String IF_GOTO = $/
  @SP
  M=M-1
  A=M
  D=M
  @{label}
  D;JNE

/$


    static final String CALL = $/
  @{nameOfFunction}.Return-Address{index}  // push return-address
  D=A
  @SP
  A=M
  M=D
  @SP
  M=M+1	
  @LCL  // push LCL
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1	
  @ARG  // push ARG
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1	
  @THIS  // push THIS
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1	
  @THAT  // push THAT
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1	
  @SP  // ARG = SP-n-5
  D=M
  @{newARG}
  D=D-A
  @5
  D=D-A
  @ARG
  M=D
  @SP  // LCL = SP
  D=M
  @LCL
  M=D	
  @{nameOfFunction}  // goto g
  0;JMP
({nameOfFunction}.Return-Address{index})  // label return-address

/$


    static final String FUNCTION = $/
({nameOfFunction})
  @{numberOfLocals}
  D=A
  @{nameOfFunction}.End
  D;JEQ
({nameOfFunction}.Loop)  // (numberOfLocals != 0) jump if false
  @SP
  A=M
  M=0
  @SP
  M=M+1
  @{nameOfFunction}.Loop
  D=D-1;JNE  // [numberOfLocals != 0) jump while 
({nameOfFunction}.End)  // (numberOfLocals == 0) finish if true 

/$

    static final String RETURN = $/
  // FRAME = LCL
  @LCL
  D=M
  @5  // RET = * (FRAME-5)  // RAM[13] = (LOCAL - 5)
  A=D-A
  D=M
  @13
  M=D
  @SP  // * ARG = pop()	
  M=M-1
  A=M
  D=M
  @ARG
  A=M
  M=D
  @ARG  // SP = ARG+1 
  D=M
  @SP
  M=D+1	
  @LCL  // THAT = *(FRAM-1)
  M=M-1
  A=M
  D=M
  @THAT
  M=D
  @LCL  // THIS = *(FRAM-2) 
  M=M-1
  A=M
  D=M
  @THIS
  M=D
  @LCL  // ARG = *(FRAM-3)
  M=M-1
  A=M
  D=M
  @ARG
  M=D				
  @LCL  // LCL = *(FRAM-4)
  M=M-1
  A=M
  D=M
  @LCL
  M=D		
  @13  // goto RET
  A=M
  0; JMP

/$


    static final String BOOTSTRAP = '''
  // bootstrap
  @256
  D=A
  @SP
  M=D
  @Sys.init$RETURN0  // push return-address
  D=A
  @SP
  A=M
  M=D
  @SP
  M=M+1
  @LCL  // push LCL
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1
  @ARG  // push ARG
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1
  @THIS  // push THIS
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1
  @THAT  // push THAT
  D=M
  @SP
  A=M
  M=D
  @SP
  M=M+1
  @SP  // ARG = SP-n-5
  D=M
  @0
  D=D-A
  @5
  D=D-A
  @ARG
  M=D
  @SP  // LCL = SP
  D=M
  @LCL
  M=D
  @Sys.init
  0;JMP
(Sys.init$RETURN0)

'''

}
