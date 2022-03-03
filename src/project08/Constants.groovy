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
  0; JMP            //   jump to {label}

/$

    public static final String IF_GOTO = $/
  @SP               //   A = 0             
  M=M-1             //   ram[0] = ram[0]-1 
  A=M               //   A = ram[0]        
  D=M               //   D = ram[ram[0]]        
  @{label}                                 
  D;JNE             //   if (ram[ram[0]] != 0) jump to {label}  

/$


    static final String CALL = $/  
  // *** push return-address *** 
  @{nameOfFunction}.ReturnAddress{index}  
  D=A               //   D = {nameOfFunction}.ReturnAddress{index}        
  @SP               //   A = 0              
  A=M               //   A = ram[0]         
  M=D               //   ram[ram[0]] = {nameOfFunction}.ReturnAddress{index}         
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
({nameOfFunction}.ReturnAddress{index})  

/$


    static final String FUNCTION = $/
  // *** label {nameOfFunction} ***
({nameOfFunction})

  // *** initialize local variables ***
  @{numberOfLocals}
  D=A              //  D = {numberOfLocals}
  @{nameOfFunction}.End
  D;JEQ            //  if (numberOfLocals == 0) jump to {nameOfFunction}.End 
({nameOfFunction}.Loop)              
  @SP              //   A = 0             
  A=M              //   A = ram[0]        
  M=0              //   ram[ram[0]] = 0        
  @SP              //   A = 0             
  M=M+1            //   ram[0] = ram[0]+1 
  @{nameOfFunction}.Loop
  D=D-1;JNE        //   if (numberOfLocals != 0) jump to {nameOfFunction}.Loop 
({nameOfFunction}.End) 

/$

    static final String RETURN = $/
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
  M=D              //   ram[A] = D  
  
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
  M=D              //   A[THAT] = D
  
  // *** THIS = *(FRAME-2) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @THIS            //   A = THIS             
  M=D              //   A[THIS] = D          

  // *** ARG = *(FRAME-3) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @ARG             //   A = ARG             
  M=D			   //   A[ARG] = D          	
  
  // *** LCL = *(FRAME-4) ***
  @LCL             //   A = LCL              
  M=M-1            //   ram[LCL] = ram[LCL]-1
  A=M              //   A = ram[LCL]         
  D=M              //   D = ram[ram[LCL]]           
  @LCL             //   A = LCL              
  M=D		       //   A[LCL] = D          
  
  // *** goto RET ***
  @13              //   A = 13              
  A=M              //   A = ram[13]
  0; JMP           //   jump to ram[ram[13]] 

/$


    static final String BOOTSTRAP = '''// bootstrap

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
