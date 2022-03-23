package project08

class Constants {

    static enum COMMAND_TYPE {
        ARITHMETIC,
        PUSH,
        POP,
        LABEL,
        GOTO,
        IF,
        FUNCTION,
        RETURN,
        CALL,
        NONE
    }

    static final String ADD = '''
  @SP               //   A = 0
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]
  @SP               //   A = 0 
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M+D             //   D = ram[A]+D
  M=D               //   ram[A] = D
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

'''

    static final String SUB = '''
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M-D             //   D = ram[A]-D                    
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

'''

    static final String NEG = '''
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=-M              //   D = -ram[A]                      
  M=D               //   ram[A] = D                           
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1                    

'''

    static final String EQ = '''
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                    
  @SP               //   A = 0                     
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1                          
  D=M-D             //   D = ram[A]-D              
  @EQ_{index}_TRUE  
  D;JEQ             //   if D==0 jump to label of TRUE
  @SP               //   A = 0                          
  A=M               //   A = ram[0]
  M=0               //   ram[A] = 0                     
  @EQ_{index}_END                             
  0;JMP             //   jump to label of END
(EQ_{index}_TRUE)                      
  @SP               //   A = 0                               
  A=M               //   A = ram[0]  
  M=-1              //   ram[A] = -1
(EQ_{index}_END)    
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1

'''

    static final String GT = '''
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M-D             //   D = ram[A]-D                     
  @GT_{index}_TRUE          
  D;JGT             //   if D>0 jump to label of TRUE    
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=0               //   ram[A] = 0                       
  @GT_{index}_END         
  0;JMP             //   jump to label of END             
(GT_{index}_TRUE)                            
  @SP               //   A = 0                            
  A=M               //   A = ram[0]                       
  M=-1              //   ram[A] = -1                      
(GT_{index}_END)                              
  @SP               //   A = 0                            
  M=M+1             //   ram[0] = ram[0]+1                

'''

    static final String LT = '''
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M               //   D = ram[A]                     
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=M-D             //   D = ram[A]-D                   
  @LT_{index}_TRUE        
  D;JLT             //   if D<0 jump to label of TRUE   
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=0               //   ram[A] = 0                     
  @LT_{index}_END        
  0;JMP             //   jump to label of END           
(LT_{index}_TRUE)                          
  @SP               //   A = 0                          
  A=M               //   A = ram[0]                     
  M=-1              //   ram[A] = -1                    
(LT_{index}_END)                            
  @SP               //   A = 0                          
  M=M+1             //   ram[0] = ram[0]+1              

'''

    static final String AND = '''
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=M               //   D = ram[A]                       
  @SP               //   A = 0                            
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1  
  D=D&M             //   D = D&&ram[A]                     
  M=D               //   ram[A] = D        
  @SP               //   A = 0     
  M=M+1             //   ram[0] = ram[0]+1

'''

    static final String OR = '''
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=M               //   D = ram[A]                      
  @SP               //   A = 0                           
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1 
  D=D|M             //   D = D||ram[A]                   
  M=D               //   ram[A] = D                      
  @SP               //   A = 0                           
  M=M+1             //   ram[0] = ram[0]+1               

'''

    static final String NOT = '''
  @SP               //   A = 0                          
  AM=M-1            //   A = ram[0]-1, ram[0] = ram[0]-1
  D=!M              //   D = !ram[A]                     
  M=D               //   ram[A] = D                          
  @SP               //   A = 0
  M=M+1             //   ram[0] = ram[0]+1                 

'''

    static final String PUSH_CONSTANT = '''
  @{value}                                   
  D=A               //   D = {value}
  @SP               //   A = 0                     
  A=M               //   A = ram[0]                          
  M=D               //   ram[ram[0]] = {value}
  @SP               //   A = 0                  
  M=M+1             //   ram[0] = ram[0]+1                     
                                             
'''

    static final String PUSH_LCL_ARG_THIS_THAT = '''
  @{index}                        
  D=A               //   D = {index}  
  @{segment}                               
  A=M+D             //   A = ram[{segment}]+{index}                          
  D=M               //   D = ram[ram[{segment}]+{index}]  
  @SP               //   A = 0                    
  A=M               //   A = ram[0]                       
  M=D               //   ram[ram[0]] = D                            
  @SP               //   A = 0               
  M=M+1             //   ram[0] = ram[0]+1 

'''

    static final String POP_LCL_ARG_THIS_THAT = '''
  @SP               //   A = 0              
  A=M-1             //   A = ram[0]-1           
  D=M               //   D = ram[ram[0]-1]  
  @{segment}        
  A=M               //   A = ram[{segment}]             
  {index}M=D               //   ram[ram[{segment}]] = ram[ram[0]-1]      
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1

'''

    static final String PUSH_TEMP = '''
  @{index}
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  D=M               //   D = ram[index+5]
  @SP               //   A = 0             
  A=M               //   A = ram[0]        
  M=D               //   ram[ram[0]] = ram[index+5]         
  @SP               //   A = 0             
  M=M+1             //   ram[0] = ram[0]+1 

'''

    static final String POP_TEMP = '''
  @SP               //   A = 0
  A=M-1             //   A = ram[0]-1  
  D=M               //   D = ram[ram[0]-1]    
  @{index}
  A=A+1
  A=A+1
  A=A+1
  A=A+1
  A=A+1             //   A = index+5
  M=D               //   ram[index+5] = D
  @SP               //   A = 0
  M=M-1             //   ram[0] = ram[0]-1

'''

    static final String PUSH_STATIC = '''
  @{index}
  D=M               //   D = ram[{index}]
  @SP               //   A = 0
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D
  @SP               //   A = 0  
  M=M+1             //   ram[0] = ram[0]+1

'''

    static final String POP_STATIC = '''
  @SP               //   A = 0         
  M=M-1             //   ram[0] = ram[0]-1              
  A=M               //   A = ram[0]         
  D=M               //   D = ram[ram[0]]         
  @{index}                     
  M=D               //   ram[{index}] = D  

'''

    static final String PUSH_POINTER = '''
  @{index}
  D=M               //   D = A[{index}]
  @SP               //   A = 0            
  A=M               //   A = ram[0]
  M=D               //   ram[ram[0]] = D       
  @SP               //   A = 0                      
  M=M+1             //   ram[0] = ram[0]+1         

'''

    static final String POP_POINTER = '''
  @SP               //   A = 0            
  A=M-1             //   A = ram[0]-1       
  D=M               //   D = ram[ram[0]-1]       
  @{index}                
  M=D               //   ram[{index}] = D            
  @SP               //   A = 0 
  M=M-1             //   ram[0] = ram[0]-1

'''

    public static final String LABEL = '''
({label})

'''

    public static final String GOTO = '''
  @{label}
  0; JMP            //   jump to {label}

'''

    public static final String IF_GOTO = '''
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1 
  A=M               //   A = ram[0]        
  D=M               //   D = ram[ram[0]]        
  @{label}                                 
  D;JNE             //   if (ram[ram[0]] != 0) jump to {label}  

'''


    static final String CALL = '''// ++++++++++++++ CALL ++++++++++++++  
  
  // *** push return-address *** 
  @{nameOfFunction}$ReturnAddress{index}  
  D=A               //   D = {nameOfFunction}$ReturnAddress{index}        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = {nameOfFunction}$ReturnAddress{index}         
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
  @{numARG}
  D=D-A             //   D = D-{numARG}
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
  @{nameOfFunction}  
  0;JMP            //   jump to {nameOfFunction}

  // *** label return-address *** 
({nameOfFunction}$ReturnAddress{index})  

// ++++++++++++++ END CALL ++++++++++++++  
'''


    static final String FUNCTION = '''// ++++++++++++++ FUNCTION ++++++++++++++

  // *** label {nameOfFunction} ***
({nameOfFunction})

  // *** initialize local variables ***
  @{numberOfLocals}
  D=A              //  D = {numberOfLocals}
  @{nameOfFunction}_End
  D;JEQ            //  if (numberOfLocals == 0) jump to {nameOfFunction}_End 
({nameOfFunction}_Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @{nameOfFunction}_Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to {nameOfFunction}_Loop 
({nameOfFunction}_End) 

// ++++++++++++++ END FUNCTION ++++++++++++++
'''

    static final String RETURN = '''// ++++++++++++++ RETURN ++++++++++++++

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
'''


    static final String BOOTSTRAPPING = '''// ++++++++++++++ BOOTSTRAPPING ++++++++++++++

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
'''

}
