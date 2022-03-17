package project11
import static project11.JackTokenizer.KeyWords as KW
import static project11.JackTokenizer.LexicalElements as LE
import static project11.Symbol.KIND

/**
 * This class does the compilation itself.
 * It reads its input from a JackTokenizer and writes its output into a VMWriter.
 * It is organized as a series of compile_xxx ( ) routines, where xxx is a syntactic element of the Jack language.
 * The contract between these routines is that each compile_xxx ( ) routine should read the syntactic construct xxx from the input,
 * advance ( ) the tokenizer exactly beyond xxx, and emit to the output VM code effecting the semantics of xxx.
 * Thus compile_xxx ( ) may only be called if indeed xxx is the next syntactic element of the input.
 * If xxx is a part of an expression and thus has a value, the emitted code should compute this value and leave it at the top of the VM stack
 */
class CompilationEngine {
    private static JackTokenizer tokenizer
    private static VMWriter vmWriter
    private static String currentClass
    private static String currentSubroutine
    private static SymbolTable symbolTable = new SymbolTable()
    private static int Indentation = 0
    private static String width= '  '
    private int labelIndex = 0

    /**
     * return current function name, className.subroutineName
     * @return
     */
    static String currentFunction(){
        if (currentClass.length() != 0 && currentSubroutine.length() !=0){
            return currentClass + "." + currentSubroutine
        }
        return ""
    }

    /**
     *
     */
    private static void printOpenTagFunction(String nameFunction, def currentToken = tokenizer.getCurrentToken()){
        println("${width * Indentation++}<$nameFunction>")
        println("${width * Indentation}$currentToken")
    }

    /**
     *
     */
    private static void printCloseTagFunction(String nameFunction, def currentToken = tokenizer.getCurrentToken()){
        println("${width * Indentation}$currentToken")
        println("${width * --Indentation}<$nameFunction/>")
    }

    /**
     * Creates a new compilation engine with the given input and output.
     * The next routine called must be compileClass()
     * @param inFile
     * @param outFile
     */
    CompilationEngine(File inFile, File outFile) {
        tokenizer = new JackTokenizer(inFile)
        vmWriter = new VMWriter(outFile)
    }

    /**
     * Compiles a type
     * @return type
     */
    private static String compileType(){
        printOpenTagFunction("compileType")
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        LE myTokenType =  tokenizer.getTokenType()
        if (myTokenType == LE.KEYWORD && (tokenizer.keyWord() in [KW.INT, KW.CHAR, KW.BOOLEAN])){
            printCloseTagFunction("compileType")
            return tokenizer.getCurrentToken()
        }
        if (tokenizer.getTokenType() == LE.IDENTIFIER){
            printCloseTagFunction("compileType")
            return tokenizer.identifier()
        }
        printCloseTagFunction("compileType")
        error("in|char|boolean|className")
        return ""
    }

    /**
     * Complies a complete class
     * class: 'class' className '{' classVarDec* subroutineDec* '}'
     */
    void compileClass(){
        printOpenTagFunction("compileClass")
        //'class'
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.KEYWORD || tokenizer.keyWord() != KW.CLASS){
            error("class")
        }
        //className
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.IDENTIFIER){
            error("className")
        }
        //classname does not need to be put in symbol table
        currentClass = tokenizer.identifier()
        //'{'
        requireSymbol('{')
        //classVarDec* subroutineDec*
        compileClassVarDec()
        compileSubroutine()
        //'}'
        requireSymbol('}')
        if (tokenizer.hasMoreTokens()){
            throw new IllegalStateException("Unexpected tokens")
        }
        printCloseTagFunction("compileClass")
        //save file
        vmWriter.close()
    }

    /**
     * Compiles a static declaration or a field declaration
     * classVarDec ('static'|'field') type varName (','varNAme)* ';'
     */
    private void compileClassVarDec(){
        printOpenTagFunction("compileClassVarDec")
        //first determine whether there is a classVarDec, nextToken is } or start subroutineDec
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //next is a '}'
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileClassVarDec")
            return
        }
        //next is start subroutineDec or classVarDec, both start with keyword
        if (tokenizer.getTokenType() != LE.KEYWORD){
            error("Keywords")
        }
        //next is subroutineDec
        if (tokenizer.keyWord() == KW.CONSTRUCTOR || tokenizer.keyWord() == KW.FUNCTION || tokenizer.keyWord() == KW.METHOD){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileClassVarDec")
            return
        }
        //classVarDec exists
        if (tokenizer.keyWord() != KW.STATIC && tokenizer.keyWord() != KW.FIELD){
            error("static or field")
        }
        KIND kind = null
        String type
        String name
        switch (tokenizer.keyWord()){
            case KW.STATIC -> kind = KIND.STATIC
            case KW.FIELD -> kind = KIND.FIELD
        }
        //type
        type = compileType()
        //at least one varName
        //#boolean varNamesDone = false
        do {
            //varName
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() != LE.IDENTIFIER){
                error("identifier")
            }
            name = tokenizer.identifier()
            symbolTable.define(name,type,kind)
            //',' or ';'
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() != LE.SYMBOL || (tokenizer.symbol() != ',' && tokenizer.symbol() != ';')){
                error("',' or ';'")
            }
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        compileClassVarDec()
        printCloseTagFunction("compileClassVarDec")
    }

    /**
     * Compiles a complete method function or constructor
     */
    private void compileSubroutine(){
        printOpenTagFunction("compileSubroutine")
        //determine whether there is a subroutine, next can be a '}'
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //next is a '}'
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileSubroutine")
            return
        }
        //start of a subroutine
        if (tokenizer.getTokenType() != LE.KEYWORD || (tokenizer.keyWord() != KW.CONSTRUCTOR && tokenizer.keyWord() != KW.FUNCTION && tokenizer.keyWord() != KW.METHOD)){
            error("constructor|function|method")
        }
        KW keyword = tokenizer.keyWord()
        symbolTable.startSubroutine()
        //for method this is the first argument
        if (tokenizer.keyWord() == KW.METHOD){
            symbolTable.define("this",currentClass, KIND.ARG)
        }
        //#String type
        //'void' or type
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
//        if (tokenizer.getTokenType() == LE.KEYWORD && tokenizer.keyWord() == KW.VOID){
//            type = "void"
//        } else {
//            tokenizer.pointerBack()
//            type = compileType()
//        }
        //subroutineName which is a identifier
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.IDENTIFIER){
            error("subroutineName")
        }
        currentSubroutine = tokenizer.identifier()
        //'('
        requireSymbol('(')
        //parameterList
        compileParameterList()
        //')'
        requireSymbol(')')
        //subroutineBody
        compileSubroutineBody(keyword)
        compileSubroutine()
        printCloseTagFunction("compileSubroutine")
    }

    /**
     * Compiles the body of a subroutine
     * '{'  varDec* statements '}'
     */
    private void compileSubroutineBody(KW keyword){
        printOpenTagFunction("compileSubroutineBody")
        //'{'
        requireSymbol('{')
        //varDec*
        compileVarDec()
        //write VM function declaration
        writeFunctionDec(keyword)
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        printCloseTagFunction("compileSubroutineBody")
    }

    /**
     * write function declaration, load pointer when keyword is METHOD or CONSTRUCTOR
     */
    private static void writeFunctionDec(KW keyword){
        printOpenTagFunction("writeFunctionDec")
        vmWriter.writeFunction(currentFunction(),symbolTable.varCount(KIND.VAR))
        //METHOD and CONSTRUCTOR need to load this pointer
        if (keyword == KW.METHOD){
            //A Jack method with k arguments is compiled into a VM function that operates on k + 1 arguments.
            // The first argument (argument number 0) always refers to the this object.
            vmWriter.writePush(VMWriter.SEGMENT.ARG, 0)
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,0)
        } else if (keyword == KW.CONSTRUCTOR){
            //A Jack function or constructor with k arguments is compiled into a VM function that operates on k arguments.
            vmWriter.writePush(VMWriter.SEGMENT.CONST,symbolTable.varCount(KIND.FIELD))
            vmWriter.writeCall("Memory.alloc", 1)
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,0)
        }
        printCloseTagFunction("writeFunctionDec")
    }

    /**
     * Compiles a single statement
     */
    private void compileStatement(){
        printOpenTagFunction("compileStatement")
        //determine whether there is a statement next can be a '}'
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //next is a '}'
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '}'){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileStatement")
            return
        }
        //next is 'let'|'if'|'while'|'do'|'return'
        if (tokenizer.getTokenType() != LE.KEYWORD){
            error("keyword")
        } else {
            switch (tokenizer.keyWord()){
                case KW.LET   -> compileLet()
                case KW.IF    -> compileIf()
                case KW.WHILE -> compilesWhile()
                case KW.DO    -> compileDo()
                case KW.RETURN-> compileReturn()
                default       -> error("'let'|'if'|'while'|'do'|'return'")
            }
        }
        compileStatement()
        printCloseTagFunction("compileStatement")
    }

    /**
     * Compiles a (possibly empty) parameter list
     * not including the enclosing "()"
     * ((type varName)(',' type varName)*)?
     */
    private static void compileParameterList(){
        printOpenTagFunction("compileParameterList")
        //check if there is parameterList, if next token is ')' than go back
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == ')'){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileParameterList")
            return
        }
        String type
        //there is parameter, at least one varName
        tokenizer.pointerBack()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        do {
            //type
            type = compileType()
            //varName
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() != LE.IDENTIFIER){
                error("identifier")
            }
            symbolTable.define(tokenizer.identifier(),type, KIND.ARG)
            //',' or ')'
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")

            if (tokenizer.getTokenType() != LE.SYMBOL || (tokenizer.symbol() != ',' && tokenizer.symbol() != ')')){
                error("',' or ')'")
            }
            if (tokenizer.symbol() == ')'){
                tokenizer.pointerBack()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                break
            }
        } while(true)
        printCloseTagFunction("compileParameterList")
    }

    /**
     * Compiles a var declaration
     * 'var' type varName (',' varName)*;
     */
    private void compileVarDec(){
        printOpenTagFunction("compileVarDec")
        //determine if there is a varDec
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //no 'var' go back
        if (tokenizer.getTokenType() != LE.KEYWORD || tokenizer.keyWord() != KW.VAR){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            printCloseTagFunction("compileVarDec")
            return
        }
        //type
        String type = compileType()
        //at least one varName
        //#boolean varNamesDone = false
        do {
            //varName
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() != LE.IDENTIFIER){
                error("identifier")
            }
            symbolTable.define(tokenizer.identifier(),type, KIND.VAR)
            //',' or ';'
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() != LE.SYMBOL || (tokenizer.symbol() != ',' && tokenizer.symbol() != ';')){
                error("',' or ';'")
            }
            if (tokenizer.symbol() == ';'){
                break
            }
        } while(true)
        compileVarDec()
        printCloseTagFunction("compileVarDec")
    }

    /**
     * Compiles a do statement
     * 'do' subroutineCall ';'
     */
    private void compileDo(){
        printOpenTagFunction("compileDo")
        //subroutineCall
        compileSubroutineCall()
        //';'
        requireSymbol(';')
        //pop return value
        vmWriter.writePop(VMWriter.SEGMENT.TEMP,0)
        printCloseTagFunction("compileDo")
    }

    /**
     * Compiles a let statement
     * 'let' varName ('[' ']')? '=' expression ';'
     */
    private void compileLet(){
        printOpenTagFunction("compileLet")
        //varName
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.IDENTIFIER){
            error("varName")
        }
        String varName = tokenizer.identifier()
        //'[' or '='
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.SYMBOL || (tokenizer.symbol() != '[' && tokenizer.symbol() != '=')){
            error("'['|'='")
        }
        boolean expExist = false
        //'[' expression ']' ,need to deal with array [base+offset]
        if (tokenizer.symbol() == '['){
            expExist = true
            //push array variable,base address into stack
            vmWriter.writePush(getSeg(symbolTable.kindOf(varName)),symbolTable.indexOf(varName))
            //calc offset
            compileExpression()
            //']'
            requireSymbol(']')
            //base+offset
            vmWriter.writeArithmetic(VMWriter.COMMAND.ADD)
        }
        if (expExist){
              tokenizer.advance()
              println("${width * Indentation}${tokenizer.getCurrentToken()}")
        }
        //expression
        compileExpression()
        //';'
        requireSymbol(';')
        if (expExist){
            //*(base+offset) = expression
            //pop expression value to temp
            vmWriter.writePop(VMWriter.SEGMENT.TEMP,0)
            //pop base+index into 'that'
            vmWriter.writePop(VMWriter.SEGMENT.POINTER,1)
            //pop expression value into *(base+index)
            vmWriter.writePush(VMWriter.SEGMENT.TEMP,0)
            vmWriter.writePop(VMWriter.SEGMENT.THAT,0)
        } else {
            //pop expression value directly
            vmWriter.writePop(getSeg(symbolTable.kindOf(varName)), symbolTable.indexOf(varName))
        }
        printCloseTagFunction("compileLet")
    }

    /**
     * return corresponding seg for input kind
     * @param kind
     * @return
     */
    private static VMWriter.SEGMENT getSeg(KIND kind){
        printOpenTagFunction("getSeg")
        println("kind: $kind")
        VMWriter.SEGMENT mySegment
        switch (kind){
            case KIND.FIELD ->  {mySegment=VMWriter.SEGMENT.THIS}
            case KIND.STATIC -> {mySegment=VMWriter.SEGMENT.STATIC}
            case KIND.VAR ->    {mySegment=VMWriter.SEGMENT.LOCAL}
            case KIND.ARG ->    {mySegment=VMWriter.SEGMENT.ARG}
            default ->          {mySegment=VMWriter.SEGMENT.NONE}
        }
        printCloseTagFunction("getSeg")
        return mySegment
    }

    /**
     * Compiles a while statement
     * 'while' '(' expression ')' '{' statements '}'
     */
    private void compilesWhile(){
        printOpenTagFunction("compilesWhile")
        String continueLabel = newLabel()
        String topLabel = newLabel()
        //top label for while loop
        vmWriter.writeLabel(topLabel)
        //'('
        requireSymbol('(')
        //expression while condition: true or false
        compileExpression()
        //')'
        requireSymbol(')')
        //if ~(condition) go to continue label
        vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
        vmWriter.writeIf(continueLabel)
        //'{'
        requireSymbol('{')
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        //if (condition) go to top label
        vmWriter.writeGoto(topLabel)
        //or continue
        vmWriter.writeLabel(continueLabel)
        printCloseTagFunction("compilesWhile")
    }

    private String newLabel(){
        return "LABEL_" + (labelIndex++)
    }

    /**
     * Compiles a return statement
     * ‘return’ expression? ';'
     */
    private void compileReturn(){
        printOpenTagFunction("compileReturn")
        //check if there is any expression
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == ';'){
            //no expression push 0 to stack
            vmWriter.writePush(VMWriter.SEGMENT.CONST,0)
        }else {
            //expression exist
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            //expression
            compileExpression()
            //';'
            requireSymbol(';')
        }
        vmWriter.writeReturn()
        printCloseTagFunction("compileReturn")
    }

    /**
     * Compiles an if statement
     * possibly with a trailing else clause
     * 'if' '(' expression ')' '{' statements '}' ('else' '{' statements '}')?
     */
    private void compileIf(){
        printOpenTagFunction("compileIf")
        String elseLabel = newLabel()
        String endLabel = newLabel()
        //'('
        requireSymbol('(')
        //expression
        compileExpression()
        //')'
        requireSymbol(')')
        //if ~(condition) go to else label
        vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
        vmWriter.writeIf(elseLabel)
        //'{'
        requireSymbol('{')
        //statements
        compileStatement()
        //'}'
        requireSymbol('}')
        //if condition after statement finishing, go to end label
        vmWriter.writeGoto(endLabel)
        vmWriter.writeLabel(elseLabel)
        //check if there is 'else'
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() == LE.KEYWORD && tokenizer.keyWord() == KW.ELSE){
            //'{'
            requireSymbol('{')
            //statements
            compileStatement()
            //'}'
            requireSymbol('}')
        } else {
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
        }
        vmWriter.writeLabel(endLabel)
        printCloseTagFunction("compileIf")
    }

    /**
     * Compiles a term.
     * This routine is faced with a slight difficulty when trying to decide between some of the alternative parsing rules.
     * Specifically, if the current token is an identifier
     *      the routine must distinguish between a variable, an array entry and a subroutine call
     * A single look-ahead token, which may be one of "[" "(" "." suffices to distinguish between the three possibilities
     * Any other token is not part of this term and should not be advanced over
     *
     * integerConstant|stringConstant|keywordConstant|varName|varName '[' expression ']'|subroutineCall|
     * '(' expression ')'|unaryOp term
     */
    private void compileTerm(){
        printOpenTagFunction("compileTerm")
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //check if it is an identifier
        if (tokenizer.getTokenType() == LE.IDENTIFIER){
            //varName|varName '[' expression ']'|subroutineCall
            String tempId = tokenizer.identifier()
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '['){
                //this is an array entry
                //push array variable,base address into stack
                vmWriter.writePush(getSeg(symbolTable.kindOf(tempId)),symbolTable.indexOf(tempId))
                //expression
                compileExpression()
                //']'
                requireSymbol(']')
                //base+offset
                vmWriter.writeArithmetic(VMWriter.COMMAND.ADD)
                //pop into 'that' pointer
                vmWriter.writePop(VMWriter.SEGMENT.POINTER,1)
                //push *(base+index) onto stack
                vmWriter.writePush(VMWriter.SEGMENT.THAT,0)
            } else if (tokenizer.getTokenType() == LE.SYMBOL && (tokenizer.symbol() == '(' || tokenizer.symbol() == '.')){
                //this is a subroutineCall
                tokenizer.pointerBack()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                tokenizer.pointerBack()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                compileSubroutineCall()
            } else {
                //this is varName
                tokenizer.pointerBack()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                //push variable directly onto stack
                vmWriter.writePush(getSeg(symbolTable.kindOf(tempId)), symbolTable.indexOf(tempId))
            }
        } else {
            //integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term
            if (tokenizer.getTokenType() == LE.INT_CONST){
                //integerConstant just push its value onto stack
                vmWriter.writePush(VMWriter.SEGMENT.CONST,tokenizer.intVal())
            }else if (tokenizer.getTokenType() == LE.STRING_CONST){
                //stringConstant new a string and append every char to the new stack
                String str = tokenizer.stringVal()
                vmWriter.writePush(VMWriter.SEGMENT.CONST,str.length())
                vmWriter.writeCall("String.new",1)
                for (int i = 0; i < str.length(); i++){
                    vmWriter.writePush(VMWriter.SEGMENT.CONST,(int)str.charAt(i))
                    vmWriter.writeCall("String.appendChar",2)
                }
            } else if (tokenizer.getTokenType() == LE.KEYWORD && tokenizer.keyWord() == KW.TRUE){
                //~0 is true
                vmWriter.writePush(VMWriter.SEGMENT.CONST,0)
                vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
            } else if (tokenizer.getTokenType() == LE.KEYWORD && tokenizer.keyWord() == KW.THIS){
                //push this pointer onto stack
                vmWriter.writePush(VMWriter.SEGMENT.POINTER,0)
            } else if (tokenizer.getTokenType() == LE.KEYWORD && (tokenizer.keyWord() == KW.FALSE || tokenizer.keyWord() == KW.NULL)){
                //0 for false and null
                vmWriter.writePush(VMWriter.SEGMENT.CONST,0)
            } else if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '('){
                //expression
                compileExpression()
                //')'
                requireSymbol(')')
            } else if (tokenizer.getTokenType() == LE.SYMBOL && (tokenizer.symbol() == '-' || tokenizer.symbol() == '~')){
                def s = tokenizer.symbol()
                //term
                compileTerm()
                if (s == '-'){
                    vmWriter.writeArithmetic(VMWriter.COMMAND.NEG)
                } else {
                    vmWriter.writeArithmetic(VMWriter.COMMAND.NOT)
                }
            } else {
                error("integerConstant|stringConstant|keywordConstant|'(' expression ')'|unaryOp term")
            }
        }
        printCloseTagFunction("compileTerm")
    }

    /**
     * Compiles a subroutine call
     * subroutineName '(' expressionList ')' | (className|varName) '.' subroutineName '(' expressionList ')'
     */
    private void compileSubroutineCall(){
        printOpenTagFunction("compileSubroutineCall")
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.IDENTIFIER){
            error("identifier")
        }
        String name = tokenizer.identifier()
        int nArgs = 0
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '('){
            //push this pointer
            vmWriter.writePush(VMWriter.SEGMENT.POINTER,0)
            //'(' expressionList ')'
            //expressionList
            nArgs = compileExpressionList() + 1
            //')'
            requireSymbol(')')
            //call subroutine
            vmWriter.writeCall(currentClass + '.' + name, nArgs)
        } else if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == '.'){
            //(className|varName) '.' subroutineName '(' expressionList ')'
            String objName = name
            //subroutineName
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            // System.out.println("subroutineName:" + tokenizer.identifier())
            if (tokenizer.getTokenType() != LE.IDENTIFIER){
                error("identifier")
            }
            name = tokenizer.identifier()
            //check for if it is built-in type
            String type = symbolTable.typeOf(objName)
            if ((type in["int","boolean","char","void"])){
                error("no built-in type")
            } else if (type==""){
                name = objName + "." + name
            } else {
                nArgs = 1
                //push variable directly onto stack
                vmWriter.writePush(getSeg(symbolTable.kindOf(objName)), symbolTable.indexOf(objName))
                name = symbolTable.typeOf(objName) + "." + name
            }
            //'('
            requireSymbol('(')
            //expressionList
            nArgs += compileExpressionList()
            //')'
            requireSymbol(')')
            //call subroutine
            vmWriter.writeCall(name,nArgs)
        } else {
            error("'('|'.'")
        }
        printCloseTagFunction("compileSubroutineCall")
    }

    /**
     * Compiles an expression
     * term (op term)*
     */
    private void compileExpression(){
        printOpenTagFunction("compileExpression")
        //term
        compileTerm()
        //(op term)*
        do {
            tokenizer.advance()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            //op
            if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.isOp()){
                String opCmd = ""
                switch (tokenizer.symbol()){
                    case '+':opCmd = "add";break
                    case '-':opCmd = "sub";break
                    case '*':opCmd = "call Math.multiply 2";break
                    case '/':opCmd = "call Math.divide 2";break
                    case '<':opCmd = "lt";break
                    case '>':opCmd = "gt";break
                    case '=':opCmd = "eq";break
                    case '&':opCmd = "and";break
                    case '|':opCmd = "or";break
                    default:error("Unknown op!")
                }
                //term
                compileTerm()
                vmWriter.writeCommand(opCmd)
            } else {
                tokenizer.pointerBack()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                break
            }
        } while (true)
        printCloseTagFunction("compileExpression")
    }

    /**
     * Compiles a (possibly empty) comma-separated list of expressions
     * (expression(','expression)*)?
     * @return nArgs
     */
    private int compileExpressionList(){
        printOpenTagFunction("compileExpressionList")
        int nArgs = 0
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        //determine if there is any expression, if next is ')' then no
        if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == ')'){
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
        } else {
            nArgs = 1
            tokenizer.pointerBack()
            println("${width * Indentation}${tokenizer.getCurrentToken()}")
            //expression
            compileExpression()
            //(','expression)*
            do {
                tokenizer.advance()
                println("${width * Indentation}${tokenizer.getCurrentToken()}")
                if (tokenizer.getTokenType() == LE.SYMBOL && tokenizer.symbol() == ','){
                    //expression
                    compileExpression()
                    nArgs++
                } else {
                    tokenizer.pointerBack()
                    println("${width * Indentation}${tokenizer.getCurrentToken()}")
                    break
                }
            } while (true)
        }
        printCloseTagFunction("compileExpressionList")
        return nArgs
    }

    /**
     * throw an exception to report errors
     * @param val
     */
    private static void error(String val){
        throw new IllegalStateException("Expected token missing : " + val + ". Current token : " + tokenizer.getCurrentToken())
    }

    /**
     * require symbol when we know there must be such symbol
     * @param symbol
     */
    private static void requireSymbol(String symbol){
        tokenizer.advance()
        println("${width * Indentation}${tokenizer.getCurrentToken()}")
        if (tokenizer.getTokenType() != LE.SYMBOL || tokenizer.symbol() != symbol){
            error("'" + symbol + "'")
        }
    }
}